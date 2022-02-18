package com.sk.batch.delete.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class DeleteCLIOptions extends BaseModel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String securityid;
  private String delete_api_path;

  private String delete_table_id;

  private String delete_audit_id;

  private String delete_category;

  private String delete_description;

  private String status;

  private long process_count;

  private String sqs_message_id;

  private String error_description;


  private long requested_time;

  private String user;


}
