package com.kh.bbs.web.form.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class LoginMember {

  private Long memberId;
  private String email;
  private String nickname;
  private String gubun;

}
