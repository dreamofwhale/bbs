package com.kh.bbs.web;

import com.kh.bbs.domain.bbs.svc.BbsSVC;
import com.kh.bbs.domain.entity.Bbs;
import com.kh.bbs.web.api.ApiResponse;
import com.kh.bbs.web.api.ApiResponseCode;
import com.kh.bbs.web.api.bbs.EditApi;
import com.kh.bbs.web.api.bbs.WriteApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/bbss")
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class ApiBbsController {

  private final BbsSVC bbsSVC;

  //게시글 생성      //   POST    /bbss  =>      POST http://localhost:9081/api/bbss
  @PostMapping
  //@RequestBody : 요청메세지 body에 포함된 json포멧 문자열을 java 객체로 변환
  public ResponseEntity<ApiResponse<Bbs>> write(
      @RequestBody @Valid WriteApi writeApi
  ) {
    log.info("writeApi={}", writeApi);

    Bbs bbs = new Bbs();
    BeanUtils.copyProperties(writeApi, bbs);

    Long id = bbsSVC.write(bbs);
    Optional<Bbs> optionalBbs = bbsSVC.findById(id);
    Bbs findedBbs = optionalBbs.orElseThrow();

    ApiResponse<Bbs> bbsApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, findedBbs);

    return ResponseEntity.status(HttpStatus.CREATED).body(bbsApiResponse);
  }


  //게시글 조회      //   GET     /bbss/{id} =>  GET http://localhost:9081/api/bbss/{id}
  @GetMapping("/{id}")
//  @ResponseBody   // 응답메세지 body에 자바 객체를 json포맷 문자열로 변환
  public ResponseEntity<ApiResponse<Bbs>> findById(@PathVariable("id") Long id) {

    Optional<Bbs> optionalBbs = bbsSVC.findById(id);
    Bbs findedBbs = optionalBbs.orElseThrow();  // 찾고자하는 상품이 없으면 NoSuchElementException 예외발생

    ApiResponse<Bbs> bbsApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, findedBbs);

    return ResponseEntity.ok(bbsApiResponse);  //상태코드 200, 응답메세지Body:productApiResponse객채가 json포맷 문자열로 변환됨
  }

  //게시글 수정      //   PATCH   /bbss/{id} =>  PATCH http://localhost:9080/api/bbss/{id}
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<Bbs>> updateById(
      @PathVariable("id") Long id,
      @RequestBody @Valid EditApi editApi // 요청메세지의 json포맷의 문자열을 자바 객체로 변환
  ) {

    //1) 게시글 조회
    Optional<Bbs> optionalBbs = bbsSVC.findById(id);
    Bbs findedBbs = optionalBbs.orElseThrow(
        ()->new NoSuchElementException("게시글 번호 : " + id + " 를 찾을 수 없습니다.")
    );  // 찾고자하는 게시글이 없으면 NoSuchElementException 예외발생

    //2) 게시글 수정
    Bbs bbs = new Bbs();
    BeanUtils.copyProperties(editApi, bbs);
    int editedRow = bbsSVC.updateById(id, bbs);

    //3) 수정된 게시글 조회
    optionalBbs = bbsSVC.findById(id);
    Bbs editedBbs = optionalBbs.orElseThrow();

    //4) REST API 응답 표준 메시지 생성
    ApiResponse<Bbs> bbsApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, editedBbs);

    //5) HTTP 응답 메세지 생성
    return ResponseEntity.ok(bbsApiResponse);
  }

  //게시글 삭제      //   DELETE  /bbss/{id} =>  DELETE http://localhost:9081/api/bbss/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Bbs>> deleteById(@PathVariable("id") Long id) {
    //1) 게시글 조회
    Optional<Bbs> optionalBbs = bbsSVC.findById(id);
    Bbs findedBbs = optionalBbs.orElseThrow(
        ()->new NoSuchElementException("게시글번호 : " + id + " 를 찾을 수 없습니다.")
    );  // 찾고자하는 게시글이 없으면 NoSuchElementException 예외발생

    //2) 게시글 삭제
    int deletedRow = bbsSVC.deleteById(id);

    //3) REST API 표준 응답 생성
    ApiResponse<Bbs> bbsApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, findedBbs);

    //4) HTTP응답 메세지 생성
    return ResponseEntity.ok(bbsApiResponse);
  }

  //게시글 목록      //   GET     /bbss      =>  GET http://localhost:9081/api/bbss
  @GetMapping
//  @ResponseBody
  public ResponseEntity<ApiResponse<List<Bbs>>> findAll() {

    List<Bbs> list = bbsSVC.findAll();
    ApiResponse<List<Bbs>> listApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, list);

    return ResponseEntity.ok(listApiResponse);
  }

  //게시글 목록-페이징      //   GET     /bbss      =>  GET http://localhost:9081/api/bbss/paging?pageNo=1&numOfRows=10
  @GetMapping("/paging")
//  @ResponseBody
  public ResponseEntity<ApiResponse<List<Bbs>>> findAll(
      @RequestParam(value="pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(value="numOfRows", defaultValue = "10") Integer numOfRows
  ) {
    log.info("pageNo={},numOfRows={}", pageNo, numOfRows);
    //게시글 목록 가져오기
    List<Bbs> list = bbsSVC.findAll(pageNo, numOfRows);
    //게시글 총건수 가져오기
    int totalCount = bbsSVC.getTotalCount();
    //REST API 표준 응답 만들기
    ApiResponse<List<Bbs>> listApiResponse = ApiResponse.of(
        ApiResponseCode.SUCCESS,
        list,
        new ApiResponse.Paging(pageNo, numOfRows, totalCount)
    );
    return ResponseEntity.ok(listApiResponse);
  }

  //전체 건수 가져오기      //   GET   /bbss/totCnt =>  GET http://localhost:9081/api/bbss/totCnt
  @GetMapping("/totCnt")
  public ResponseEntity<ApiResponse<Integer>> totalCount() {

    int totalCount = bbsSVC.getTotalCount();
    ApiResponse<Integer> bbsApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, totalCount);

    return ResponseEntity.ok(bbsApiResponse);  //상태코드 200, 응답메세지Body:bbsApiResponse객채가 json포맷 문자열로 변환됨
  }
}
