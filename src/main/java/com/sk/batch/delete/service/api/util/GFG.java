package com.sk.batch.delete.service.api.util;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.batch.delete.api.model.SQSMessageModel;
public class GFG {
	
	public static String parse(String encoded) {
		byte[] actualByte = Base64.getDecoder() 
                .decode(encoded);
		String actualString = new String(actualByte); 
		return actualString;
	}
	
	public static void main(String[] args) throws IOException 
    { 
  
        // create an encoded String to decode 
        String encoded 
            = "e01lc3NhZ2VJZDogMzgxNzk1NDQtNDY5Zi00MjJhLTlhZTQtMmY3NzkzMWFhNTE0LFJlY2VpcHRIYW5kbGU6IEFRRUJQMStGek5tRlZKN09jL1J2Vzc0aVhwU3VCLzlrYS9nNzlKSDYwYU5sdVFZSEY2YlNCcFRpWjRHbHU3UkhqNWRBYlkwenNjb0Z4T3VyaSt2ajZGNDRtSWN0ZDFpWGd1SHJIR2E1SEx1dlNnT2s4TzRFbGZrNGw1TGlLZTk3RTc3OVhYNWdHZ3g0anRnMSsrbGpTdnhaVE5JWjNwV0REUGcvQWVjdUp5bU9OMjlkeHUvTVI3aGVSb2JKN3lBcVc0QzZCV041aFB6cDZUTTNsR3BUaU5CaFNPMWhzQkNsNGpXR3JERS82d3JwMFBZMGkvS2JIeGJSQ0l1REhCOHhmMzM4N1BEd2R0eGpNRUhST3pFOGNFd0V1c01mYURhVWU0eTNvSlFuTHg1Z0drQT0sTUQ1T2ZCb2R5OiA2ZTM1MzA1NDNhN2JhM2FmOTdhMTU1MGNjYTI0NzQ3OSxCb2R5OiB7InJlcXVlc3RlZF90aW1lIjogMTYwMDg0OTA1OCwic3FzX21lc3NhZ2VfaWQiOiAiM2M5NWE3ODAtMTBmZi00MGNhLWIyOGEtYmFjMjg1YmQyNzUzIiwgICAicmV2b2tlX2Rlc2NyaXB0aW9uIjogInJldm9rZSBhcHBsaWNhdGlvbiIsICAgInByb2Nlc3NfY291bnQiOiAwLCAgICJyZXZva2VfYXVkaXRfaWQiOiAiNjMxYjBmMGYtMWZjMC00YTQwLWFlZGItNzBhMDhiOGJiZWFhIiwgICAidXNlciI6ICJ0ZXN0dXNlciIsICAgInJldm9rZV90YWJsZV9pZCI6ICI4MDRhMjZiMS0xYTg1LTQ4ZjAtYjZjMC03M2U1NGQ0NjU1OWIiLCAgICJyZXZva2VfYXBpX3BhdGgiOiAiL2FkbWluaXN0cmF0aW9uL2FwcGxpY2F0aW9uL3tpZH0vcmV2b2tlIiwgICAgInJldm9rZV9jYXRlZ29yeSI6ICJhcHBsaWNhdGlvbiIsICAgICJzdGF0dXMiOiAiaW4gcHJvZ3Jlc3MifSxBdHRyaWJ1dGVzOiB7fSxNZXNzYWdlQXR0cmlidXRlczoge319"; 
  
        // print encoded String 
//        System.out.println("Encoded String:\n"
//                           + encoded); 
  
        // decode into String from encoded format 
        byte[] actualByte = Base64.getDecoder() 
                                .decode(encoded); 
  
        String actualString = new String(actualByte); 
        System.out.println("actual String:\n"
                + actualString); 
        Map<String, Object> jso = JsonP.parseJSON(actualString);
        System.out.println("---------------------------------");
        System.out.println(jso);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(JsonP.toJSONString(jso, true));
        
        ObjectMapper om = new ObjectMapper();
        SQSMessageModel smm =om.readValue(JsonP.toJSONString(jso, true),
        		SQSMessageModel.class);
        System.out.println(smm.getBody().getRequestedTime());
        
    } 
}
