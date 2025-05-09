package com.kh.bbs.domain.bbs.dao;

import com.kh.bbs.domain.entity.Bbs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BbsDAOImplTest {

  @Autowired
  BbsDAO bbsDAO;

  @Test
  @DisplayName("게시글작성")
  void write() {
    Bbs bbs = new Bbs();
    bbs.setBbsHead("집에 가고싶다");
    bbs.setBbsWriter("내가낸데");
    bbs.setBbsBody("이제 집에 갈 시간디 다 된 거 같다.");

    Long bid = bbsDAO.write(bbs);
    log.info("bbs={}", bid);
  }

}