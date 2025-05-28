package com.kh.bbs.domain.comm.dao;

import com.kh.bbs.domain.entity.Comm;

import java.util.List;

public interface CommDAO {
  /**
   * 특정 게시글에 대한 댓글 목록 조회
   *
   * @param bbsId 게시글 아이디
   * @return 댓글 리스트
   */
  List<Comm> findAllByBbsId(Long bbsId);

  /**
   * 댓글 등록
   *
   * @param comm 등록할 댓글 객체
   * @return 등록 성공 시 영향받은 행 수 (보통 1)
   */
  Long write(Comm comm);

  /**
   * 댓글 번호로 댓글 수정
   *
   * @param commid 댓글 아이디
   * @param comm 수정할 댓글 데이터
   * @return 수정 성공 건수
   */
  int updateById(Long commid, Comm comm);

  /**
   * 댓글 단건 삭제
   *
   * @param id 댓글 아이디
   * @return 삭제 성공 건수
   */
  int deleteById(Long id);

  /**
   * 여러 댓글 삭제
   *
   * @param ids 댓글 아이디 리스트
   * @return 삭제 성공 건수
   */
  int deleteByIds(List<Long> ids);

}