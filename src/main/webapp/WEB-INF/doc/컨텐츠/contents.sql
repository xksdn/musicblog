DROP TABLE music_post CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE music_post;

CREATE TABLE music_post (
        post_no                            NUMBER(10)         NOT NULL         PRIMARY KEY,
        user_no                              NUMBER(10)     NOT NULL , -- FK
        cate_id                                NUMBER(10)         NOT NULL , -- FK
        title                                 VARCHAR2(200)         NOT NULL,
        content                               CLOB                  NOT NULL,
        recom                                 NUMBER(7)         DEFAULT 0         NOT NULL,
        cnt                                   NUMBER(7)         DEFAULT 0         NOT NULL,
        replycnt                              NUMBER(7)         DEFAULT 0         NOT NULL,
        password                                VARCHAR2(100)         NOT NULL,
        word                                  VARCHAR2(200)         NULL ,
        rdate                                 DATE               NOT NULL,
        file1                                   VARCHAR(100)          NULL,  -- 원본 파일명 image
        file1saved                            VARCHAR(100)          NULL,  -- 저장된 파일명, image
        thumb1                              VARCHAR(100)          NULL,   -- preview image
        size1                                 NUMBER(10)      DEFAULT 0 NULL,  -- 파일 사이즈
        map                                   VARCHAR2(1000)            NULL,
        youtube                               VARCHAR2(1000)            NULL,
        mp3                                  VARCHAR2(100)            NULL,
        visible                                CHAR(1)         DEFAULT 'Y' NOT NULL,
        FOREIGN KEY (user_no) REFERENCES user_info (user_no),
        FOREIGN KEY (cate_id) REFERENCES category (id)
);

COMMENT ON TABLE music_post IS '음악 리뷰 및 게시글 테이블';

COMMENT ON COLUMN music_post.post_no IS '게시글 번호';
COMMENT ON COLUMN music_post.user_no IS '작성자 번호 (user_info 테이블 FK)';
COMMENT ON COLUMN music_post.cate_id IS '카테고리 번호 (category 테이블 FK)';
COMMENT ON COLUMN music_post.title IS '게시글 제목';
COMMENT ON COLUMN music_post.content IS '게시글 내용';
COMMENT ON COLUMN music_post.recom IS '추천 수';
COMMENT ON COLUMN music_post.cnt IS '조회 수';
COMMENT ON COLUMN music_post.replycnt IS '댓글 수';
COMMENT ON COLUMN music_post.password IS '게시글 비밀번호';
COMMENT ON COLUMN music_post.word IS '검색 키워드';
COMMENT ON COLUMN music_post.rdate IS '등록일';
COMMENT ON COLUMN music_post.file1 IS '원본 이미지 파일명';
COMMENT ON COLUMN music_post.file1saved IS '저장된 이미지 파일명';
COMMENT ON COLUMN music_post.thumb1 IS '미리보기 이미지 파일명';
COMMENT ON COLUMN music_post.size1 IS '이미지 파일 크기 (Byte)';
COMMENT ON COLUMN music_post.map IS '지도 위치 정보';
COMMENT ON COLUMN music_post.youtube IS 'Youtube 영상 URL';
COMMENT ON COLUMN music_post.mp3 IS '음원 파일명';
COMMENT ON COLUMN music_post.visible IS '게시글 출력 여부 (Y/N)';

DROP SEQUENCE music_post_seq;

CREATE SEQUENCE music_post_seq
  START WITH 1               -- 시작 번호
  INCREMENT BY 1             -- 증가값
  MAXVALUE 9999999999        -- 최대값: NUMBER(10) 대응
  CACHE 2                    -- 2개는 메모리에서 계산
  NOCYCLE;                   -- 다시 1부터 생성 안 함



 -- insert 문
INSERT INTO music_post (
    post_no, user_no, cate_id, title, content, recom, cnt, replycnt, password, word, rdate, 
    file1, file1saved, thumb1, size1, map, youtube, mp3, visible
) VALUES (
    music_post_seq.NEXTVAL, 1, 1, '아이유 - 밤편지', 
    '조용한 밤에 듣기 좋은 별빛 감성 음악.', 
    0, 0, 0, 'abcd1234', '감성, 별빛, 밤하늘', SYSDATE, 
    'star.jpg', 'star_20250416.jpg', 'star_thumb.jpg', 204800, 
    '서울특별시 종로구 청운동', 
    'https://www.youtube.com/watch?v=abc123xyz', 
    'starlight.mp3', 'Y'
);

-- 전체 목록
SELECT post_no, title, user_no, cate_id, rdate, recom, cnt, replycnt, visible
FROM music_post
ORDER BY post_no DESC;
