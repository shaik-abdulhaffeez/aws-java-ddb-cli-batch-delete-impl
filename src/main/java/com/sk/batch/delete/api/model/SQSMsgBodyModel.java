package com.sk.batch.delete.api.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"requested_time", "sqs_message_id", "delete_description", "process_count",
    "delete_audit_id", "user", "delete_table_id", "delete_api_path", "delete_category", "status"})
public class SQSMsgBodyModel {

  private Integer requestedTime;
  @JsonProperty("sqs_message_id")
  private String sqsMessageId;
  @JsonProperty("delete_description")
  private String deleteDescription;
  @JsonProperty("process_count")
  private Integer processCount;
  @JsonProperty("delete_audit_id")
  private String deleteAuditId;
  @JsonProperty("user")
  private String user;
  @JsonProperty("delete_table_id")
  private String deleteTableId;
  @JsonProperty("delete_api_path")
  private String deleteApiPath;
  @JsonProperty("delete_category")
  private String deleteCategory;
  @JsonProperty("status")
  private String status;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("requested_time")
  public Integer getRequestedTime() {
    return requestedTime;
  }

  @JsonProperty("requested_time")
  public void setRequestedTime(Integer requestedTime) {
    this.requestedTime = requestedTime;
  }

  @JsonProperty("sqs_message_id")
  public String getSqsMessageId() {
    return sqsMessageId;
  }

  @JsonProperty("sqs_message_id")
  public void setSqsMessageId(String sqsMessageId) {
    this.sqsMessageId = sqsMessageId;
  }

  @JsonProperty("delete_description")
  public String getdeleteDescription() {
    return deleteDescription;
  }

  @JsonProperty("delete_description")
  public void setdeleteDescription(String deleteDescription) {
    this.deleteDescription = deleteDescription;
  }

  @JsonProperty("process_count")
  public Integer getProcessCount() {
    return processCount;
  }

  @JsonProperty("process_count")
  public void setProcessCount(Integer processCount) {
    this.processCount = processCount;
  }

  @JsonProperty("delete_audit_id")
  public String getdeleteAuditId() {
    return deleteAuditId;
  }

  @JsonProperty("delete_audit_id")
  public void setdeleteAuditId(String deleteAuditId) {
    this.deleteAuditId = deleteAuditId;
  }

  @JsonProperty("user")
  public String getUser() {
    return user;
  }

  @JsonProperty("user")
  public void setUser(String user) {
    this.user = user;
  }

  @JsonProperty("delete_table_id")
  public String getdeleteTableId() {
    return deleteTableId;
  }

  @JsonProperty("delete_table_id")
  public void setdeleteTableId(String deleteTableId) {
    this.deleteTableId = deleteTableId;
  }

  @JsonProperty("delete_api_path")
  public String getdeleteApiPath() {
    return deleteApiPath;
  }

  @JsonProperty("delete_api_path")
  public void setdeleteApiPath(String deleteApiPath) {
    this.deleteApiPath = deleteApiPath;
  }

  @JsonProperty("delete_category")
  public String getdeleteCategory() {
    return deleteCategory;
  }

  @JsonProperty("delete_category")
  public void setdeleteCategory(String deleteCategory) {
    this.deleteCategory = deleteCategory;
  }

  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}

