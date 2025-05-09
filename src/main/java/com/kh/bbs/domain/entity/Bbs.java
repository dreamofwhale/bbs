package com.kh.bbs.domain.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Bbs {
  private Long bbsId;        //게시글ID
  private String bbsHead;    //게시글제목
  private String bbsBody;    //게시글내용
  private String bbsWriter;  //게시글작성자
  private Timestamp bbsDate;        //게시글생성일시
  private Timestamp bbsUpdate;        //게시글수정일시
}
