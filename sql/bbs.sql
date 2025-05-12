create table bbs(
    bbs_id     number(20),       											--게시글ID
    bbs_head   varchar2(100),       										--제목
    bbs_body   clob,               										--내용
    bbs_writer varchar2(50),       										--작성자
    bbs_date   timestamp default systimestamp,         --생성일시
    bbs_update  timestamp default systimestamp         --수정일시
);

DROP TABLE bbs;
DROP SEQUENCE	seq_bbs_id;
CREATE SEQUENCE	seq_bbs_id;

INSERT INTO bbs (bbs_id, bbs_HEAD, bbs_BODY, bbs_WRITER)
			values(seq_bbs_id.nextval,'정보교육원','코딩 강의는 따라가기에 힘들다','홍길동');


SELECT * FROM bbs;
ROLLBACK;
COMMIT;

SELECT bbs_id, bbs_HEAD, bbs_WRITER, bbs_date, bbs_update
	FROM	bbs
 WHERE	bbs_id = 1;


DELETE FROM BBS
	WHERE bbs_id = 1;

DELETE FROM bbs
	WHERE bbs_id IN (2,3);

UPDATE bbs
   SET bbs_head ='수정하기 시작해보자', bbs_writer ='수정자', bbs_body ='이렇게 일단 하나씩 수정해보고', bbs_update = systimestamp
 WHERE bbs_id = 2;

SELECT * FROM bbs;


SELECT SYSDATE FROM DUAL;
SELECT SYSTIMESTAMP FROM DUAL;
SELECT DBTIMEZONE FROM DUAL;
SELECT SESSIONTIMEZONE FROM DUAL;