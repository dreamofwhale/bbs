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


 -------
 --회원
 -------
 create table member (
     member_id   number(10),     --내부 관리 아이디
     email       varchar2(50),   --로긴 아이디
     passwd      varchar2(12),   --로긴 비밀번호
     tel         varchar2(13),   --연락처 ex)010-1234-5678
     nickname    varchar2(30),   --별칭
     gender      varchar2(6),    --성별
     hobby       varchar2(300),  --취미
     region      varchar2(11),   --지역
     gubun       varchar2(11)   default 'M0101', --회원구분 (일반,우수,관리자..)
     pic         blob,            --사진
     cdate       timestamp default systimestamp,         --생성일시
     udate       timestamp default systimestamp          --수정일시
 );
 --기본키생성
 alter table member add Constraint member_member_id_pk primary key (member_id);

 --외래키
 alter table member add constraint member_region_fk
     foreign key(region) references code(code_id);
 alter table member add constraint member_gubun_fk
     foreign key(gubun) references code(code_id);

 --제약조건
 alter table member modify email constraint member_email_uk unique;
 alter table member modify email constraint member_email_nn not null;
 alter table member add constraint member_gender_ck check (gender in ('남자','여자'));

 --시퀀스
 create sequence member_member_id_seq;

 --샘플데이터 of member
 insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
     values(member_member_id_seq.nextval, 'test1@kh.com', '1234', '010-1111-1111','테스터1','남자','골프,독서','A0201', 'M0101');
 insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
     values(member_member_id_seq.nextval, 'test2@kh.com', '1234', '010-1111-1112','테스터2','여자','골프,수영','A0202', 'M0102');
 insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
     values(member_member_id_seq.nextval, 'admin1@kh.com', '1234','010-1111-1113','관리자1', '남자','등산,독서','A0203','M01A1');
 insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
     values(member_member_id_seq.nextval, 'admin2@kh.com', '1234','010-1111-1114','관리자2', '여자','골프,독서','A0204','M01A2');
 select * from member;
 commit;