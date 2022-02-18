package com.sk.batch.delete.api.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sk.batch.delete.api.service.client.ClientService;
import com.sk.batch.delete.service.api.util.BeanReferenceException;
import com.sk.batch.delete.service.api.util.CommandLineException;
import com.sk.batch.delete.service.api.util.GFG;
import com.sk.batch.delete.service.api.util.ServiceClassIdentifier;
import com.sk.batch.delete.service.api.util.StringUtil;

/**
 * 
 * @author Main class to kick start the execution input :: cli argument
 *         https://pablissimo.com/1068/getting-your-eks-pod-to-assume-an-iam-role-using-irsa
 */
@SpringBootApplication
public class SkBatchDeleteApplication {
  private static final Logger logger = LoggerFactory.getLogger(SkBatchDeleteApplication.class);

  public static void main(String[] args) throws Exception {
    if (args.length == 0 || StringUtil.isEmptyOrNull(args[0])) {
      throw new CommandLineException("Command line Argument is required");
    }

    String inputJson = GFG.parse(args[0]);
    String ReceiptHandler = args[1];
    String serviceName = ServiceClassIdentifier.serviceIdenifier(inputJson);

    // String serviceName = "PERevokingApplication";
    // String appId = args[0];

    SpringApplication app = new SpringApplication(SpringApplicationConfig.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    ConfigurableApplicationContext context = app.run(args);

    if (context.containsBean(serviceName)
        && (context.getBean(serviceName) instanceof ClientService)) {
      ClientService service = (ClientService) context.getBean(serviceName);
      service.run(inputJson, ReceiptHandler);
    } else {
      throw new BeanReferenceException(String.format(
          "The service %s either does not exists or not of type ClientService", serviceName));
    }

    context.close();
    logger.debug("PROGRAM EXIT");
  }

}
