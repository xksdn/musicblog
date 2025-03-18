package dev.boot.mvc.db;

import lombok.Data;
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
  
  /* 노래가 발표된 시대 */ 
  private String era;
  
  /* 노래가 만들어진 지역 */
  private String region;
  
  /* 노래 목적 */
  private String purpose;
  
  /* 출력 모드 */
  private String visible;
  
  /* 등록 일자 */
  private String rdate;

}
