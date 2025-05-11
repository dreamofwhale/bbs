package com.kh.bbs.web.form.bbs;

import lombok.*;

import java.sql.Timestamp;

@Data
public class UpdateForm {


  private String bbsHead;
  private String bbsWriter;
  private String bbsBody;
  private Timestamp bbsUpdate;

}
