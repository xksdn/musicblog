DROP TABLE user_info CASCADE CONSTRAINTS; 

CREATE TABLE user_info (
  user_no NUMBER(10) NOT NULL, -- 회원 번호, 레코드를 구분하는 컬럼 
  email VARCHAR(30) NOT NULL UNIQUE, -- 이메일(아이디), 중복 안됨, 레코드를 구분 
  password VARCHAR(200) NOT NULL, -- 패스워드, 영숫자 조합, 암호화
  u_name VARCHAR(30) NOT NULL, -- 성명, 한글 10자 저장 가능
  phone VARCHAR(14) NOT NULL, -- 전화번호
  zipcode VARCHAR(5) NULL, -- 우편번호, 12345
  address VARCHAR(80) NULL, -- 주소 
  address_detail VARCHAR(50) NULL, -- 상세 주소 
  mdate DATE NOT NULL, -- 가입일    
  user_level NUMBER(2) NOT NULL, -- 등급(1~10: 관리자, 11~20: 회원, 40~49: 정지 회원, 99: 탈퇴 회원)
  PRIMARY KEY (user_no) -- 한번 등록된 값은 중복 안됨
);

COMMENT ON TABLE USER_INFO IS '사용자';
COMMENT ON COLUMN USER_INFO.USER_NO IS '사용자 번호';
COMMENT ON COLUMN USER_INFO.EMAIL IS '이메일(아이디)';
COMMENT ON COLUMN USER_INFO.PASSWORD IS '패스워드';
COMMENT ON COLUMN USER_INFO.U_NAME IS '성명';
COMMENT ON COLUMN USER_INFO.PHONE IS '전화번호';
COMMENT ON COLUMN USER_INFO.ZIPCODE IS '우편번호';
COMMENT ON COLUMN USER_INFO.ADDRESS IS '주소';
COMMENT ON COLUMN USER_INFO.ADDRESS_DETAIL IS '상세 주소';
COMMENT ON COLUMN USER_INFO.MDATE IS '가입일';
COMMENT ON COLUMN USER_INFO.USER_LEVEL IS '사용자 등급';




DROP SEQUENCE user_seq;

CREATE SEQUENCE user_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
 
 
 
 COMMIT;
 
 
 SELECT *
 FROM user_info;
 
 
 -- 등록
 -- 회원 관리용 계정 ( 관리자 )
 INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address,
                        address_detail, mdate, user_level)
 VALUES (user_seq.nextval, 'admin', '1234', '통합 관리자', '000-0000-0000', '12345',
             '서울시 종로구', '관철동', sysdate, 1);


-- 개인 회원 테스트 계정
INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level)
VALUES (user_seq.nextval, 'user1@gmail.com', '1234', '왕눈이', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level)
VALUES (user_seq.nextval, 'user2@gmail.com', '1234', '아로미', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level)
VALUES (user_seq.nextval, 'user3@gmail.com', '1234', '투투투', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 


-- 부서별 공유 회원 기준
INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level)
VALUES (user_seq.nextval,'team1', '1234', '개발팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);

INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level)
VALUES (user_seq.nextval,'team2', '1234', '웹퍼블리셔팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);

INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level)
VALUES (user_seq.nextval,'team3', '1234', '디자인팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);


INSERT INTO user_info (user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level)
VALUES (user_seq.nextval,'admin2', '1234', '디자인팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);

 
COMMIT;


-- 목록 검색

SELECT user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level
FROM user_info
ORDER BY user_level ASC, email ASC;


SELECT user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level
FROM user_info
WHERE user_no = 1;


SELECT user_no, email, password, u_name, phone, zipcode, address, address_detail, mdate, user_level
FROM user_info
WHERE email = 'user1@gmail.com';

-- 수정
UPDATE user_info
SET u_name='조인성', phone='111-1111-1111', zipcode='00000',
    address='강원도', address_detail='홍천군', user_level=14
WHERE email = 'admin2';



-- 삭제
DELETE FROM user_info;


DELETE FROM user_info
WHERE user_no = 5;

COMMIT;

