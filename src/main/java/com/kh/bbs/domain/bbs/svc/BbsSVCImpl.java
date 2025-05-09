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
}
