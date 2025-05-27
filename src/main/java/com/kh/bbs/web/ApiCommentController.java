package com.kh.bbs.web;

import com.kh.bbs.domain.comm.svc.CommSVC;
import com.kh.bbs.domain.entity.Comm;
import com.kh.bbs.web.api.ApiResponse;
import com.kh.bbs.web.api.ApiResponseCode;
import com.kh.bbs.web.api.comm.EditApi;
import com.kh.bbs.web.api.comm.WriteApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bbss/{bbsId}/comments")
public class ApiCommentController {

  private final CommSVC commSVC;

  // 댓글 목록 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<Comm>>> findAllByBbsId(@PathVariable Long bbsId) {
    List<Comm> list = commSVC.findAllByBbsId(bbsId);
    ApiResponse<List<Comm>> res = ApiResponse.of(ApiResponseCode.SUCCESS, list);
    return ResponseEntity.ok(res);
  }

  // 댓글 등록
  @PostMapping
  public ResponseEntity<ApiResponse<Comm>> write(@PathVariable Long bbsId,
                                                 @RequestBody @Valid WriteApi writeApi) {
    try {
      Comm comm = new Comm();
      BeanUtils.copyProperties(writeApi, comm);
      comm.setBbsId(bbsId);
      int rows = commSVC.write(comm);
      if (rows > 0) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.of(ApiResponseCode.SUCCESS, comm));
      }
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.of(ApiResponseCode.INTERNAL_SERVER_ERROR, null));
    } catch (Exception e) {
      log.error("댓글 등록 중 예외 발생", e);
      throw e;  // 예외 다시 던져서 스프링 로그 기본 출력
    }
  }

  // 댓글 수정
  @PatchMapping("/{commId}")
  public ResponseEntity<ApiResponse<Comm>> updateById(@PathVariable Long bbsId, @PathVariable Long commId, @RequestBody @Valid EditApi editApi) {
    try {
      Comm comm = new Comm();
      BeanUtils.copyProperties(editApi, comm);
      comm.setCommId(commId);
      comm.setBbsId(bbsId);
      int rows = commSVC.updateById(comm);
      if(rows > 0) {
        ApiResponse<Comm> res = ApiResponse.of(ApiResponseCode.SUCCESS, comm);
        return ResponseEntity.ok(res);
      }
      log.warn("댓글 수정 실패 - 존재하지 않는 댓글 ID: {}", commId);
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.of(ApiResponseCode.ENTITY_NOT_FOUND, null));
    } catch (Exception e) {
      log.error("댓글 수정 중 예외 발생", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.of(ApiResponseCode.INTERNAL_SERVER_ERROR, null));
    }
  }

  // 댓글 삭제
  @DeleteMapping("/{commId}")
  public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long bbsId, @PathVariable Long commId) {
    try {
      int rows = commSVC.deleteById(commId);
      if(rows > 0) {
        ApiResponse<Void> res = ApiResponse.of(ApiResponseCode.SUCCESS, null);
        return ResponseEntity.ok(res);
      }
      log.warn("댓글 삭제 실패 - 존재하지 않는 댓글 ID: {}", commId);
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.of(ApiResponseCode.ENTITY_NOT_FOUND, null));
    } catch (Exception e) {
      log.error("댓글 삭제 중 예외 발생", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.of(ApiResponseCode.INTERNAL_SERVER_ERROR, null));
    }
  }
}