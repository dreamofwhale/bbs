package com.kh.bbs.web.form.bbs;

import lombok.Data;

@Data
public class EditForm {


  private Long BbsId;
  private String bbsHead;
  private String bbsBody;
  private String bbsWriter;

}
