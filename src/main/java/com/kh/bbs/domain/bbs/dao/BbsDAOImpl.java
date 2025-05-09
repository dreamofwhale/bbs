package com.kh.bbs.domain.bbs.dao;

import com.kh.bbs.domain.entity.Bbs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
class BbsDAOImpl implements BbsDAO {

  final private NamedParameterJdbcTemplate template;

  RowMapper<Bbs> bbsRowMapper(){

    return (rs, rowNum)->{
      Bbs bbs = new Bbs();
      bbs.setBbsId(rs.getLong("bbs_id"));
      bbs.setBbsHead(rs.getString("bbs_head"));

      bbs.setBbsWriter(rs.getString("bbs_writer"));
      bbs.setBbsDate(rs.getTimestamp("bbs_date"));
      bbs.setBbsUpdate(rs.getTimestamp("bbs_update"));
      return bbs;
    };
  }
  /**
   * 게시글 등록
   * @param bbs
   * @return 게시글번호
   */
  @Override
  public Long write(Bbs bbs) {
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO bbs (bbs_id, bbs_HEAD, bbs_BODY, bbs_WRITER) ");
    sql.append("      values(seq_bbs_id.nextval,:bbs_HEAD,:bbs_BODY, :bbs_WRITER) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(bbs);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    long rows = template.update(sql.toString(),param, keyHolder, new String[]{"bbs_id"} );
    log.info("rows={}",rows);

    Number bidNumber = (Number)keyHolder.getKeys().get("bbs_id");
    long bid = bidNumber.longValue();
    return bid;
  }
  //게시글 목록
  @Override
  public List<Bbs> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("  SELECT bbs_id, bbs_HEAD, bbs_WRITER, bbs_date, bbs_update ");
    sql.append("    FROM bbs ");
    sql.append(" order BY bbs_id desc ");

    //db요청
    List<Bbs> list = template.query(sql.toString(), bbsRowMapper());

    return list;
  }
  //게시글 상세
  @Override
  public Optional<Bbs> findById(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT bbs_id, bbs_HEAD, bbs_BODY, bbs_WRITER, bbs_date, bbs_update ");
    sql.append("  FROM bbs ");
    sql.append(" WHERE bbs_id = :id ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
    Bbs bbs = null;

    try {
      bbs = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Bbs.class));
    } catch (EmptyResultDataAccessException e)  { // template.queryForObject() : 레코드를 못 찾으면 예외 발생
      return Optional.empty();
    }

    return Optional.of(bbs);
  }
}
