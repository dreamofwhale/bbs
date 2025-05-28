package com.kh.bbs.domain.comm.svc;

import com.kh.bbs.domain.entity.Comm;

import java.util.List;

/**
 * 댓글 서비스 인터페이스
 */
public interface CommSVC {

  /**
   * 특정 게시글의 댓글 목록 조회
   * @param bbsId 게시글 ID
   * @return 댓글 목록
   */
  List<Comm> findAllByBbsId(Long bbsId);

  /**
   * 댓글 등록
   * @param comm 등록할 댓글 객체
   * @return 영향을 받은 행 수 (1이면 성공)
   */
  Long write(Comm comm);

  /**
   * 댓글 수정
   * @param comm 수정할 댓글 객체
   * @return 영향을 받은 행 수
   */
  int updateById(Long id,Comm comm);

  /**
   * 댓글 단건 삭제
   * @param commId 댓글 ID
   * @return 영향을 받은 행 수
   */
  int deleteById(Long commId);

  /**
   * 댓글 다중 삭제
   * @param commIds 댓글 ID 리스트
   * @return 영향을 받은 행 수
   */
  int deleteByIds(List<Long> commIds);

}