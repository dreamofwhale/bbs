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
import java.util.Map;
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
    sql.append(" INSERT INTO bbs(bbs_id,bbs_head,bbs_body,bbs_writer) ");
    sql.append("      values(seq_bbs_id.nextval,:bbsHead,:bbsBody,:bbsWriter) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(bbs);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    long rows = template.update(sql.toString(),param, keyHolder, new String[]{"bbs_id"} );
    log.info("rows={}",rows);

    Number bidNumber = (Number)keyHolder.getKeys().get("bbs_id");
    long bid = bidNumber.longValue();
    return bid;
  }
  /**
   * 게시글 삭제(단건)
   * @param id 게시글 번호
   * @return 삭제건수
   */
  @Override
  public int deleteById(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append(" DELETE FROM BBS ");
    sql.append(" WHERE bbs_id = :id ");

    Map<String, Long> param = Map.of("id", id);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  /**
   * 게시글삭제(여러건)
   * @param ids 게시글번호s
   * @return 삭제건수
   */
  @Override
  public int deleteByIds(List<Long> ids) {
    StringBuffer sql = new StringBuffer();
    sql.append(" DELETE FROM bbs ");
    sql.append(" WHERE bbs_id IN (:ids) ");

    Map<String, List<Long>> param = Map.of("ids", ids);
    int rows = template.update(sql.toString(), param);
    return rows;
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


  //게시글 조회
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
  //게시글 목록 - 페이징
  @Override
  public List<Bbs> findAll(int pageNo, int numOfRows) {
    //sql
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT bbs_id, bbs_HEAD, bbs_BODY, bbs_WRITER, bbs_date, bbs_update  ");
    sql.append("   FROM bbs  ");
    sql.append(" ORDER BY bbs_id DESC  ");
    sql.append("  OFFSET (:pageNo -1) * :numOfRows ROWS  ");
    sql.append(" FETCH NEXT :numOfRows ROWS only  ");

    Map<String, Integer> map = Map.of("pageNo",pageNo,"numOfRows",numOfRows);
    List<Bbs> list = template.query(sql.toString(), map, bbsRowMapper());

    return list;
  }

  //게시글 총 건수
  @Override
  public int getTotalCount() {
    String sql = "select count(bbs_id) from bbs ";

    SqlParameterSource param = new MapSqlParameterSource();
    int i = template.queryForObject(sql, param, Integer.class);

    return i;
  }

  /**
   * 게시글 수정
   * @param bbsId 게시글 번호
   * @param bbs 게시글
   * @return 상품 수정 건수
   */
  @Override
  public int updateById(Long bbsId, Bbs bbs) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE bbs ");
    sql.append("   SET bbs_head = :bbsHead, bbs_writer = :bbsWriter, bbs_body = :bbsBody, bbs_update = systimestamp ");
    sql.append(" WHERE bbs_id = :bbsId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("bbsHead", bbs.getBbsHead())
        .addValue("bbsWriter", bbs.getBbsWriter())
        .addValue("bbsBody", bbs.getBbsBody())
        .addValue("bbsUpdate", bbs.getBbsUpdate())
        .addValue("bbsId", bbsId);


    int rows = template.update(sql.toString(), param);

    return rows;
  }
}
