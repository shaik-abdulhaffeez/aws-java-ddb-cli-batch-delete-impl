package com.sk.batch.delete.api.service.client;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.batch.delete.api.model.Customer;
import com.sk.batch.delete.api.model.DeleteAudit;
import com.sk.batch.delete.api.model.SQSMsgBodyModel;

abstract class BaseService implements ClientService {
  private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
  protected static final String CUSTOMERID = "customer_id";
  protected static final String CUSTOMERIDGSI = "customer_id_gsi";

  @Value("${com.sk.region}")
  protected String AWSREGION;
  @Value("${com.sk.sqs.queue}")
  protected String SQSQUEUE;
  @Value("${com.sk.aws.sqs.msg.grp.id")
  protected String SQSMSGGROUPID;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected DynamoDBMapper dynamoDBMapper;

  @Autowired
  protected DynamoDB dynamoDB;

  @Autowired
  protected AmazonDynamoDB amazonDynamoDB;

  @Autowired
  protected AmazonSQS sqsClient;

  @Autowired
  protected SendMessageRequest sendMessageRequest;

  @Autowired
  protected ExecuteStatementRequest executeStatementRequest;



  protected <T> List<List<T>> chopped(List<T> list, final int L) {
    List<List<T>> parts = new ArrayList<List<T>>();
    final int N = list.size();
    for (int i = 0; i < N; i += L) {
      parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
    }
    return parts;
  }

  protected void clearAll(List list, Object[] ob) {
    list.clear();
    Arrays.fill(ob, null);
  }

  protected void processUnProcessElements(BatchWriteItemOutcome outcome) {
    Map<String, List<WriteRequest>> unprocessedItems;
    do {
      // Check for unprocessed keys which could happen if you exceed
      // provisioned throughput
      unprocessedItems = outcome.getUnprocessedItems();

      if (outcome.getUnprocessedItems().size() == 0) {
        logger.info("No unprocessed items found");
      } else {
        logger.info("Retrieving the unprocessed items");
        outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
      }

    } while (outcome.getUnprocessedItems().size() > 0);
  }

  protected DeleteAudit getRevokeAuditRecord(String id) {

    return dynamoDBMapper.load(DeleteAudit.class, id);
  }

  protected boolean deleteCustomersUsingId(List<Customer> sessionIdList) {
    try {

      List<List<Customer>> appParts = chopped(sessionIdList, 25);
      List<String> hashKeyValues = new ArrayList<String>();
      BatchWriteItemOutcome outcome;
      TableWriteItems[] threadTableWriteItems = new TableWriteItems[appParts.size()];
      for (int i = 0; i < appParts.size(); i++) {

        for (int j = 0; j < appParts.get(i).size(); j++) {
          hashKeyValues.addAll(appParts.get(i).get(j));
        }
        Object[] array = hashKeyValues.stream().toArray(String[]::new);
        threadTableWriteItems[i] = new TableWriteItems("session").withHashOnlyKeysToDelete("id", array);
        outcome = dynamoDB.batchWriteItem(threadTableWriteItems[i]);
        clearAll(hashKeyValues, array);
        if (outcome.getUnprocessedItems().size() != 0) {
          processUnProcessElements(outcome);
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

 
  protected String sqsQueueUrl() {
    logger.info("sqsQueueUrl" + sqsClient.getQueueUrl(SQSQUEUE).getQueueUrl());
    return sqsClient.getQueueUrl(SQSQUEUE).getQueueUrl();
  }

  protected void deleteSQSMessage(String receiptHandler) {
    logger.info("delete SQSMessage and receiptHandler is - " + receiptHandler);
    sqsClient.deleteMessage(sqsQueueUrl(), receiptHandler);
  }

  protected void exceptionSQSHandler(String receiptHandler, SQSMsgBodyModel sqs) {
    try {
      deleteSQSMessage(receiptHandler);
      logger.info("Resubmitting msg to SQS " + sqs.getProcessCount());
      SendMessageRequest sendmessage = sendMessageRequest.withMessageGroupId(SQSMSGGROUPID)
          .withQueueUrl(sqsQueueUrl()).withMessageBody(objectMapper.writeValueAsString(sqs));
      sqsClient.sendMessage(sendmessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

 

}
