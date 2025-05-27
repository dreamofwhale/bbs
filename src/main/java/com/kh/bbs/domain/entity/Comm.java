package com.kh.bbs.domain.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comm {

  private Long commId;                //댓글ID
  private Long bbsId;                 //게시글ID(FK)
  private String commBody;            //댓글내용
  private String commWriter;          //댓글작성자
  private Timestamp commDate;         //게시글생성일시
  private Timestamp commUpdate;       //게시글수정일시

}
