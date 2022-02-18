package com.sk.batch.delete.service.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.batch.delete.api.model.SQSMsgBodyModel;

public class ServiceClassIdentifier {

  private static final String CUSTOMER = "customer";
  private static final String DELETE_CUST_ENDPOINT = "/cust/{id}/delete";

  public static String serviceIdenifier(String cliOptions)
      throws JsonMappingException, JsonProcessingException {
    String serviceName = null;
    // Map<String, Object> jso = JsonP.parseJSON(cliOptions);
    SQSMsgBodyModel sqsModel = new ObjectMapper().readValue(cliOptions, SQSMsgBodyModel.class);

    if (sqsModel.getdeleteAuditId().toLowerCase().equals(CUSTOMER)) {
      if (sqsModel.getdeleteAuditId().equals(DELETE_CUST_ENDPOINT)) {
        serviceName = "BatchDeleteApplication";

    }
    return serviceName;
  }
	return serviceName;

}
}
