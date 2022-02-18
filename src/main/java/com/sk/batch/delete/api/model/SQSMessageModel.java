package com.sk.batch.delete.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"MessageId",
"ReceiptHandle",
"MD5OfBody",
"Body",
"Attributes",
"MessageAttributes"
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SQSMessageModel {
	
	@JsonProperty("MessageId")
	private String messageId;
	@JsonProperty("ReceiptHandle")
	private String receiptHandle;
	@JsonProperty("MD5OfBody")
	private String mD5OfBody;
	@JsonProperty("Body")
	private SQSMsgBodyModel body;
	@JsonProperty("Attributes")
	private Attributes attributes;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
