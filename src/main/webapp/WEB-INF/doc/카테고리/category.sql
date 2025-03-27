
DROP TABLE category CASCADE CONSTRAINTS; 

CREATE TABLE category (
    id NUMBER PRIMARY KEY, -- 시퀀스 사용
    title VARCHAR2(255) NOT NULL, -- 곡 제목
    artist VARCHAR2(255) NOT NULL, -- 가수/아티스트
    genre VARCHAR2(50) NOT NULL, -- 장르
    mood VARCHAR2(50) NOT NULL, -- 분위기/감정
    seqno NUMBER(5) DEFAULT 1 NOT NULL,
    era VARCHAR2(50) NOT NULL, -- 시대
    region VARCHAR2(50) NOT NULL, -- 국가/문화
    visible CHAR(1) DEFAULT 'N' CHECK (visible IN ('Y', 'N')), -- 출력 모드
    rdate DATE DEFAULT SYSDATE -- 등록일
);



CREATE SEQUENCE CATE_SEQ START WITH 1 INCREMENT BY 1;

ALTER SEQUENCE CATE_SEQ RESTART START WITH 1;



SELECT id, title, artist, genre, mood, era, region, visible, rdate
FROM category
ORDER BY id ASC;