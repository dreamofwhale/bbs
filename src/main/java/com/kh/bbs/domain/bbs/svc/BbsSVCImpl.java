package com.kh.bbs.domain.bbs.svc;

import com.kh.bbs.domain.entity.Bbs;
import com.kh.bbs.domain.bbs.dao.BbsDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BbsSVCImpl implements BbsSVC {

  final private BbsDAO bbsDAO;

  //게시글 등록
  @Override
  public Long write(Bbs bbs) {
    return bbsDAO.write(bbs);
  }


  //게시글 삭제(단건)
  @Override
  public int deleteById(Long id) {
    return bbsDAO.deleteById(id);
  }


  //게시글 삭제(여러건)
  @Override
  public int deleteByIds(List<Long> ids) {
    return bbsDAO.deleteByIds(ids);
  }


  //게시글 목록
  @Override
  public List<Bbs> findAll() {
    return bbsDAO.findAll();
  }


  //게시글 상세
  @Override
  public Optional<Bbs> findById(Long id) {
    return bbsDAO.findById(id);
  }

  //게시글 목록-페이징
  @Override
  public List<Bbs> findAll(int pageNo, int numOfRows) {
    return bbsDAO.findAll(pageNo, numOfRows);
  }

  //게시글 총건수
  @Override
  public int getTotalCount() {
    return bbsDAO.getTotalCount();
  }

  //게시글 수정
  @Override
  public int updateById(Long id, Bbs bbs) {
    return bbsDAO.updateById(id, bbs);
  }


}
