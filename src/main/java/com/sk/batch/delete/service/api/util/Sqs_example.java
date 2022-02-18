package com.sk.batch.delete.service.api.util;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.ListQueuesResult;

public class Sqs_example {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		ListQueuesResult lq_result = sqs.listQueues();
		System.out.println("Your SQS Queue URLs:");
		for (String url : lq_result.getQueueUrls()) {
		    System.out.println(url);
		}

	}

}
