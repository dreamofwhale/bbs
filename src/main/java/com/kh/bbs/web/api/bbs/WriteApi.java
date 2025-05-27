package com.kh.bbs.web.api.bbs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WriteApi {

  @NotBlank(message = "제목은 필수로 입력해야합니다.")
  @Size(min =1, max = 20, message = "게시글의 제목은 20자를 초과할 수 없습니다.")
  private String bbsHead;

  @NotBlank(message = "작성자는 필수로 입력해야합니다.")
  @Size(min=1,max=10,message = "작성자는 10자를 초과할 수 없습니다.")
  private String bbsWriter;

  @NotBlank(message = "내용은 필수로 입력해야합니다.")
  private String bbsBody;

}
