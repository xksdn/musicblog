package dev.boot.mvc.db;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostVO {
//  post_no                            NUMBER(10)         NOT NULL         PRIMARY KEY,
//  user_no                              NUMBER(10)     NOT NULL , -- FK
//  cate_id                                NUMBER(10)         NOT NULL , -- FK
//  title                                 VARCHAR2(200)         NOT NULL,
//  content                               CLOB                  NOT NULL,
//  recom                                 NUMBER(7)         DEFAULT 0         NOT NULL,
//  cnt                                   NUMBER(7)         DEFAULT 0         NOT NULL,
//  replycnt                              NUMBER(7)         DEFAULT 0         NOT NULL,
//  password                                VARCHAR2(100)         NOT NULL,
//  word                                  VARCHAR2(200)         NULL ,
//  rdate                                 DATE               NOT NULL,
//  file1                                   VARCHAR(100)          NULL,  -- 원본 파일명 image
//  file1saved                            VARCHAR(100)          NULL,  -- 저장된 파일명, image
//  thumb1                              VARCHAR(100)          NULL,   -- preview image
//  size1                                 NUMBER(10)      DEFAULT 0 NULL,  -- 파일 사이즈
//  map                                   VARCHAR2(1000)            NULL,
//  youtube                               VARCHAR2(1000)            NULL,
//  mp3                                  VARCHAR2(100)            NULL,
//  visible                                CHAR(1)         DEFAULT 'Y' NOT NULL,
//  FOREIGN KEY (user_no) REFERENCES user_info (user_no),
//  FOREIGN KEY (cate_id) REFERENCES category (id)


  /** 게시글 번호 */
  private int post_no;

  /** 작성자 번호 (user_info 테이블 FK) */
  private int user_no;

  /** 카테고리 번호 (category 테이블 FK) */
  private int cate_id;

  /** 게시글 제목 */
  private String title = "";

  /** 게시글 내용 */
  private String content = "";

  /** 추천 수 */
  private int recom;

  /** 조회 수 */
  private int cnt;

  /** 댓글 수 */
  private int replycnt;

  /** 게시글 비밀번호 */
  private String password = "";

  /** 검색 키워드 */
  private String word = "";

  /** 등록일 */
  private String rdate;

  /**
   이미지 파일
   <input type='file' class="form-control" name='file1MF' id='file1MF'
   value='' placeholder="파일 선택">
   */
  private MultipartFile file1MF = null;

  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";

  /** 원본 이미지 파일명 */
  private String file1 = "";

  /** 저장된 이미지 파일명 */
  private String file1saved = "";

  /** 미리보기 이미지 파일명 */
  private String thumb1 = "";

  /** 이미지 파일 크기 (Byte) */
  private long size1 = 0;

  /** 지도 위치 정보 */
  private String map = "";

  /** Youtube 영상 URL */
  private String youtube = "";

  /** 음원 파일명 */
  private String mp3 = "";

  /** 게시글 출력 여부 (Y/N) */
  private String visible = "Y";

}
