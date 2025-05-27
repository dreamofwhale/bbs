package com.kh.bbs.web.api.comm;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 댓글 수정용 DTO
 */
@Data
public class EditApi {

  @NotBlank(message = "댓글 내용은 필수입니다.")
  private String commBody;

  // 일반적으로 댓글 작성자는 수정하지 않으므로 작성자 필드는 제외하거나 필요시 포함
  // @NotBlank(message = "댓글 작성자는 필수입니다.")
  // private String commWriter;
}