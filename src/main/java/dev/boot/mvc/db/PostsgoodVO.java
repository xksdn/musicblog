package dev.boot.mvc.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CREATE TABLE contentsgood (
//    contentsgoodno  NUMBER(10)    NOT NULL PRIMARY KEY,
//    rdate         DATE    NOT NULL,
//    contentsno      NUMBER(10)    NOT NULL,
//    memberno      NUMBER(10)    NOT NULL,
//      FOREIGN KEY (contentsno) REFERENCES contents(contentsno),
//      FOREIGN KEY (memberno) REFERENCES member(memberno)
//  );

@Getter @Setter @ToString
public class PostsgoodVO {
  /** 컨텐츠 추천 번호 */
  private int postsgoodno;
  
  /** 등록일 */
  private String rdate;
  
  /** 컨텐츠 번호 */
  private int post_no;
  
  /** 회원 번호 */
  private int user_no;
  
}



