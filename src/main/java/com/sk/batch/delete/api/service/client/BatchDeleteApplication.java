package com.sk.batch.delete.api.service.client;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.batch.delete.api.model.Customer;
import com.sk.batch.delete.api.model.SQSMsgBodyModel;

/**
 * @author SK
 * 
 * @description DDB batch delete
 * @In the context of - 
 */
@Service
public class BatchDeleteApplication extends BaseService {
  private static final Logger logger = LoggerFactory.getLogger(BatchDeleteApplication.class);


  @Override
  public void run(String json, String receiptHandler) throws Exception {


    SQSMsgBodyModel sqs = new ObjectMapper().readValue(json, SQSMsgBodyModel.class);
    sqsClient.changeMessageVisibility(sqsQueueUrl(), receiptHandler, 43200);

    try {
      if (sqs.getProcessCount() < 3) {
        logger.info("API for deleting application and id to deleted was - "
            + sqs.getdeleteTableId());

        Map<String, AttributeValue> value = new HashMap<String, AttributeValue>();
        value.put(":value", new AttributeValue().withS(sqs.getdeleteTableId())); // need to
        // below code is responsible to query applciation table parent id index
        DynamoDBQueryExpression<Customer> qryExp = new DynamoDBQueryExpression<Customer>()
            .withKeyConditionExpression("id" + " = :value");
        qryExp.setIndexName("cid");
        qryExp.withExpressionAttributeValues(value).withConsistentRead(false);
        PaginatedQueryList<Customer> appList = dynamoDBMapper.query(Customer.class, qryExp);
        // below code is responsible to iterate over list and check greater than zero and is not
        // empty conditon
        // Also responsible to process all child records
        if (appList.size() > 0 && !appList.isEmpty()) {
        	deleteCustomersUsingId(appList);
        }
        // below method is responsible to delete customer id(s)
        logger.info("add DLQ mechanisum");
      }
    } catch (Exception e) {
      sqs.setProcessCount(sqs.getProcessCount() + 1);
      exceptionSQSHandler(receiptHandler, sqs);
    }



  }

}
