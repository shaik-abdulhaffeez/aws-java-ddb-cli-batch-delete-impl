package com.sk.batch.delete.api.service.client;


public interface ClientService {
  public static final String STATE = "state";

  public void run(String json, String receiptHandler) throws Exception;

}
