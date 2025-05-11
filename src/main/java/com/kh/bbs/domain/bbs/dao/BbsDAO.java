package com.kh.bbs.domain.bbs.dao;

import com.kh.bbs.domain.entity.Bbs;

import java.util.List;
import java.util.Optional;

public interface BbsDAO {
  //게시글 등록
  Long write(Bbs bbs);

  //게시글 삭제(단건)
  int deleteById(Long id);

  //게시글 삭제(여러건)
  int deleteByIds(List<Long> ids);

  //게시글 목록
  List<Bbs> findAll();

  Optional<Bbs> findById(Long id);

  //게시글 수정
  int updateById(Long bbsId, Bbs bbs);


}
