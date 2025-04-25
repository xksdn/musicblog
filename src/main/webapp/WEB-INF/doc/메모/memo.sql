DROP TABLE meno CASCADE CONSTRAINTS;


CREATE TABLE memo (
    memo_no NUMBER(10) NOT NULL,
    title VARCHAR(30) NOT NULL,
    content CLOB NOT NULL,
    rdate DATE NOT NULL,
    user_no NUMBER(10) NOT NULL,
    PRIMARY KEY (memo_no),
    FOREIGN KEY (user_no) REFERENCES user_info (user_no)
);

DROP SEQUENCE meno_seq;

CREATE SEQUENCE memo_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;   