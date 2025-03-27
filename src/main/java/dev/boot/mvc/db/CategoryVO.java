package dev.boot.mvc.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CategoryVO {

  /* 카테고리 번호 */ 
  private int id;
  
  /* 노래 제목 */ 
  private String title;
  
  /* 가수명 */ 
  private String artist;
  
  /* 노래 장르 */ 
  private String genre;
  
  /* 노래 분위기 */ 
  private String mood;
  
  /* 발매 연도 */ 
  private String era;
  
  /* 발매 지역 */
  private String region;
  
  /* 공개 여부 */
  private String visible;
  
  /* 등록 일자 */
  private String rdate;

  /* 우선 순위 */
  private int seqno;

}
