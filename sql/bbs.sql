create table bbs(
    bbs_id     number(20),       											--게시글ID
    bbs_head   varchar2(100),       										--제목
    bbs_body   clob,               											--내용
    bbs_writer varchar2(50),       											--작성자
    bbs_date   timestamp default systimestamp,         						--생성일시
    bbs_update  timestamp default systimestamp        					 	--수정일시
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

ALTER TABLE bbs
ADD CONSTRAINT pk_bbs PRIMARY KEY(bbs_id);


CREATE TABLE comm (
  comm_id NUMBER(20) PRIMARY KEY,        							 -- 댓글ID
  bbs_id NUMBER(20) NOT NULL,            							 -- 게시글ID(FK)
  comm_body CLOB NOT NULL,               							 -- 댓글 내용
  comm_writer VARCHAR2(50) NOT NULL,     							 -- 작성자
  comm_date TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,    			 -- 생성일시
  comm_update TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,  			 -- 수정일시
  CONSTRAINT fk_comm_bbs FOREIGN KEY (bbs_id) REFERENCES bbs(bbs_id) -- 외래키 제약조건명 명시
);

CREATE SEQUENCE comm_id_seq;

CREATE INDEX idx_comm_bbsid ON comm(bbs_id);

INSERT INTO comm (comm_id, bbs_id, COMM_BODY, COMM_WRITER)
			values(comm_id_seq.nextval,8,'코딩 강의는 따라가기에 힘들다','홍길동');

SELECT * FROM comm;

SELECT * FROM comm WHERE bbs_id = 4 ORDER BY comm_date DESC;

UPDATE comm
   SET comm_body = '수정된 댓글입니다.', comm_update = SYSTIMESTAMP
 WHERE comm_id = 8;