package dev.boot.mvc.db;

import java.util.ArrayList;
import java.util.HashMap;

public interface PostsgoodDAOInter {
  /**
   * 등록, 추상 메소드
   * @param postsgoodVO
   * @return
   */
  public int create(PostsgoodVO postsgoodVO);
  
  /**
   * 모든 목록
   * @return
   */
  public ArrayList<PostsgoodVO> list_all();
  
  /**
   * 삭제
   * @param postsgoodVO
   * @return
   */
  public int delete(int postsgoodVO);
  
  /**
   * 특정 컨텐츠의 특정 회원 추천 갯수 산출
   * @param map
   * @return
   */
  public int hartCnt(HashMap<String, Object> map);  

  /**
   * 조회
   * @param postsgoodVO
   * @return
   */
  public PostsgoodVO read(int postsgoodVO);

  /**
   * contentsno, memberno로 조회
   * @param map
   * @return
   */
  public PostsgoodVO readByContentsnoMemberno(HashMap<String, Object> map);
  
  /**
   * 모든 목록, 테이블 3개 join
   * @return
   */
  public ArrayList<PostsPostsgoodMemberVO> list_all_join();
  
}




