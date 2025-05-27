package com.kh.bbs.domain.comm.svc;

import com.kh.bbs.domain.comm.dao.CommDAO;
import com.kh.bbs.domain.entity.Comm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommSVCImpl implements CommSVC {

  final private CommDAO commDAO;

  @Override
  public List<Comm> findAllByBbsId(Long bbsId) {
    return commDAO.findAllByBbsId(bbsId);
  }

  @Override
  public int write(Comm comm) {
    return commDAO.write(comm);
  }

  @Override
  public int updateById(Comm comm) {
    return commDAO.updateById(comm.getCommId(), comm);
  }

  @Override
  public int deleteById(Long commId) {
    return commDAO.deleteById(commId);
  }

  @Override
  public int deleteByIds(List<Long> commIds) {
    return commDAO.deleteByIds(commIds);
  }
}