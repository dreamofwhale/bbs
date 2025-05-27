package com.kh.bbs.domain.bbs.svc;

import com.kh.bbs.domain.entity.Bbs;

import java.util.List;
import java.util.Optional;

public interface BbsSVC {

  //게시글 등록
  Long write(Bbs bbs);

  //게시글 삭제(단건)
  int deleteById(Long id);

  //게시글 삭제(여러건)
  int deleteByIds(List<Long> ids);

  //게시글 목록
  List<Bbs> findAll();

  Optional<Bbs> findById(Long id);

  /**
   * 상품목록 페이징
   * @param pageNo 요청 페이지
   * @param numOfRows 요청 페이지 레코드 수
   * @return 상품목록
   */
  List<Bbs> findAll(int pageNo, int numOfRows);

  /**
   *  상품 총 건수
   * @return 총 건수
   */
  int getTotalCount();

  //게시글 수정
  int updateById(Long id,Bbs bbs);


}
