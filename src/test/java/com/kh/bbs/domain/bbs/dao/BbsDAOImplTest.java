package com.kh.bbs.domain.bbs.dao;

import com.kh.bbs.domain.entity.Bbs;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class BbsDAOImplTest {

  @Autowired
  BbsDAO bbsDAO;

  @Test
  @DisplayName("게시글 목록")
  void findAll() {
    List<Bbs> list = bbsDAO.findAll();
    for (Bbs bbs : list) {
      log.info("bbs={}",bbs);
    }
  }

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

  @Test
  @DisplayName("게시글 조회")
  void findById() {
    Long bbsId = 4L;
    Optional<Bbs> optionalBbs = bbsDAO.findById(bbsId);
    Bbs findedBbs = optionalBbs.orElseThrow();
    log.info("findedBbs={}",findedBbs);
  }

  @Test
  @DisplayName("게시글 삭제(단건)")
  void deleteById() {
    Long id = 11L;
    int rows = bbsDAO.deleteById(id);
    log.info("rows={}", rows);
    Assertions.assertThat(rows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글 삭제(여러건)")
  void deleteByIds() {
    List<Long> ids = List.of(33L,35L);
    int rows = bbsDAO.deleteByIds(ids);
    Assertions.assertThat(rows).isEqualTo(2);
  }

  @Test
  @DisplayName("게시글 수정")
  void updateById() {
    Long id = 2L;
    Bbs bbs = new Bbs();
    bbs.setBbsHead("자바에서 수정");
    bbs.setBbsWriter("백엔드수정자");
    bbs.setBbsBody("이제 하나씩 바꾸는 거 하는데 힘드네");

    int rows = bbsDAO.updateById(id, bbs);
    log.info("rows={}", rows);
    Assertions.assertThat(rows).isEqualTo(1);

    Optional<Bbs> optBbs = bbsDAO.findById(id);
    Bbs modifiedBbs = optBbs.orElseThrow();

    Assertions.assertThat(modifiedBbs.getBbsHead()).isEqualTo("자바에서 수정");
    Assertions.assertThat(modifiedBbs.getBbsWriter()).isEqualTo("백엔드수정자");
    Assertions.assertThat(modifiedBbs.getBbsBody()).isEqualTo("이제 하나씩 바꾸는 거 하는데 힘드네");
    LocalDateTime updatedTime = modifiedBbs.getBbsUpdate().toLocalDateTime();
    LocalDateTime now = LocalDateTime.now();
    long secondsDiff = Math.abs(Duration.between(updatedTime, now).getSeconds());
    Assertions.assertThat(secondsDiff).isLessThanOrEqualTo(5);

  }
}