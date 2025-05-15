--게시판 테이블 생성
create table bbs(
    bbs_id     number(20),       					   --게시글ID
    bbs_head   varchar2(100),       				   --제목
    bbs_body   clob,               					   --내용
    bbs_writer varchar2(50),       					   --작성자
    bbs_date   timestamp default systimestamp,         --생성일시
    bbs_update  timestamp default systimestamp         --수정일시
);

DROP TABLE bbs;
DROP SEQUENCE	seq_bbs_id;

--게시글 넘버 시퀀스 생성
CREATE SEQUENCE	seq_bbs_id;

--게시글 삽입
INSERT INTO bbs (bbs_id, bbs_HEAD, bbs_BODY, bbs_WRITER)
			values(seq_bbs_id.nextval,'평가 제목','평가 본문','평가 작성자');

--게시글 목록 보기
SELECT * FROM bbs;

ROLLBACK;
COMMIT;

--게시글 상세 보기
SELECT bbs_id, bbs_HEAD, bbs_WRITER, bbs_date, bbs_update
	FROM	bbs
 WHERE	bbs_id = 1;

--게시글 삭제(단건)
DELETE FROM BBS
	WHERE bbs_id = 1;

--게시글 삭제(여러건)
DELETE FROM bbs
	WHERE bbs_id IN (2,3);

--게시글 수정
UPDATE bbs
   SET bbs_head ='수정 제목', bbs_writer ='수정 작성자', bbs_body ='수정 본문', bbs_update = systimestamp
 WHERE bbs_id = 2;