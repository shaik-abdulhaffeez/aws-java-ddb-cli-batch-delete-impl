package com.sk.batch.delete.api.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.ExecuteStatementRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

/**
 * SpringBootApplication class which invokes the application context and creates the spring
 * container.
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.sk.batch.delete.api")
@EnableAutoConfiguration
public class SpringApplicationConfig {
  @Autowired
  Environment env;
  /*
   * StsWebIdentityCredentialsProviderFactory StsWebIdentityCredentialsProviderFactory;
   * WebIdentityTokenCredentialsProvider WebIdentityTokenFileCredentialsProvider
   * https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
   */
  private AWSCredentialsProvider credProvider;

  // private AWSCredentialsProvider awsCredentialProvider = WebIdentityTokenCredentialsProvider
  // .builder()
  // .roleArn(System.getenv("AWS_ROLE_ARN"))
  // .roleSessionName(System.getenv("AWS_ROLE_SESSION_NAME"))
  // .webIdentityTokenFile(System.getenv("AWS_WEB_IDENTITY_TOKEN_FILE"))
  // .build(); precedence order keys , role and container creds

  @Bean
  public ExecuteStatementRequest executeStatementRequest() {
    return new ExecuteStatementRequest();
  }

  @Bean
  public SendMessageRequest sendMessageRequest() {
    return new SendMessageRequest();
  }

  private DefaultAWSCredentialsProviderChain defaultChain() {
    return new DefaultAWSCredentialsProviderChain();
  }

  @Bean
  public AmazonSQS amazonSQS() {
    return AmazonSQSClientBuilder.standard().withCredentials(defaultChain())
        .withRegion(Regions.US_EAST_1).build();
  }

  @Bean
  public DynamoDBMapper dynamoDBMapper() {
    return new DynamoDBMapper(buildAmazonDynamoDB());
  }

  @Bean
  public DynamoDB dynamoDB() {
    return new DynamoDB(buildAmazonDynamoDB());
  }

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    return buildAmazonDynamoDB();
  }

  private AmazonDynamoDB buildAmazonDynamoDB() {
    return AmazonDynamoDBClientBuilder.standard().withCredentials(defaultChain()).build();
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
