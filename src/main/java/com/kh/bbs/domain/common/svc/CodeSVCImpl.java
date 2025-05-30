package com.kh.bbs.domain.common.svc;

import com.kh.bbs.domain.common.CodeId;
import com.kh.bbs.domain.common.dao.CodeDAO;
import com.kh.bbs.domain.dto.CodeDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeSVCImpl implements CodeSVC {

  private final CodeDAO codeDAO;
  private List<CodeDTO> a02;

  @Override
  public List<CodeDTO> getCodes(CodeId pcodeId) {
    return codeDAO.loadCodes(pcodeId);
  }

  @PostConstruct    // 생성자 호출 후 실행될 메소드에 선언하면 해당 메소드가 자동 호출
  public List<CodeDTO> getA02Code() {
    log.info("getA02Code() 수행됨!");
    a02 = codeDAO.loadCodes(CodeId.A02);
    return a02;
  }

  public List<CodeDTO> getA02() {
    return a02;
  }
}
