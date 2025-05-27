package com.kh.bbs.web.api.comm;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 댓글 등록용 DTO
 */
@Data
public class WriteApi {

  @NotBlank(message = "댓글 내용은 필수입니다.")
  private String commBody;

  @NotBlank(message = "댓글 작성자는 필수입니다.")
  private String commWriter;
}