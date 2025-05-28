package com.kh.bbs.domain.comm.dao;

import com.kh.bbs.domain.entity.Comm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommDAOImpl implements CommDAO {

  private final NamedParameterJdbcTemplate template;

  // RowMapper 정의
  private RowMapper<Comm> commRowMapper() {
    return (rs, rowNum) -> {
      Comm comm = new Comm();
      comm.setCommId(rs.getLong("comm_id"));
      comm.setBbsId(rs.getLong("bbs_id"));
      comm.setCommBody(rs.getString("comm_body"));
      comm.setCommWriter(rs.getString("comm_writer"));
      comm.setCommDate(rs.getTimestamp("comm_date"));
      comm.setCommUpdate(rs.getTimestamp("comm_update"));
      return comm;
    };
  }

  // 댓글 저장 (작성)
  @Override
  public Long write(Comm comm) {
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO comm (comm_id, bbs_id, comm_body, comm_writer) " );
    sql.append("           VALUES (comm_id_seq.NEXTVAL, :bbsId, :commBody, :commWriter) ");

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("bbsId", comm.getBbsId())
        .addValue("commBody", comm.getCommBody())
        .addValue("commWriter", comm.getCommWriter());

    KeyHolder keyHolder = new GeneratedKeyHolder();

    int rows = template.update(sql.toString(), params, keyHolder, new String[] {"comm_id"});
    log.info("rows={}", rows);

    Number key = keyHolder.getKey();
    if (key != null) {
      return key.longValue();  // Long 타입으로 반환
    } else {
      log.warn("KeyHolder did not return generated key.");
      return null;  // 실패 시 null 반환 가능
    }
  }

  // 댓글 목록 조회
  @Override
  public List<Comm> findAllByBbsId(Long bbsId) {
    String sql = "SELECT comm_id, bbs_id, comm_body, comm_writer, comm_date, comm_update " +
        "FROM comm WHERE bbs_id = :bbsId ORDER BY comm_date DESC";

    return template.query(sql, Map.of("bbsId", bbsId), commRowMapper());
  }

  // 댓글 수정
  @Override
  public int updateById(Long commId, Comm comm) {
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE comm ");
    sql.append("    SET comm_body = :commBody, comm_update = SYSTIMESTAMP ");
    sql.append("  WHERE comm_id = :commId ");

    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("commBody",comm.getCommBody())
        .addValue("commUpdate",comm.getCommUpdate())
        .addValue("commId",commId);

    return template.update(sql.toString(), params);
  }

  // 댓글 단건 삭제
  @Override
  public int deleteById(Long id) {
    String sql = " DELETE FROM comm WHERE comm_id = :commId ";
    return template.update(sql, Map.of("commId", id));
  }

  // 댓글 다중 삭제
  @Override
  public int deleteByIds(List<Long> ids) {
    String sql = " DELETE FROM comm WHERE comm_id IN (:ids) ";
    return template.update(sql, Map.of("ids", ids));
  }


}