package dev.boot.mvc.db;


import java.util.ArrayList;
import java.util.Map;

public interface CateDAOInter {
  public int create(CategoryVO categoryVO);

  public ArrayList<CategoryVO> list_all();

  public CategoryVO read(int id);

  public int update(CategoryVO categoryVO);

  public int delete(int id);

  public int update_seqno_forward(int id);

  public int update_seqno_backward(int id);

  // 공개 설정
  public int update_visible_y(int cateno);

  // 비공개 설정
  public int update_visible_n(int cateno);

  public ArrayList<CategoryVO> list_all_grp_y();

  public ArrayList<CategoryVO> list_all_name_y(String genre);


  ArrayList<CategoryVO> list_search(String word);

  int list_search_count(String word);

  /**
   * 검색, 전체 목록
   * @return
   */
  public ArrayList<CategoryVO> list_search_paging(Map map);


  /**
   * 중분류의 cnt 값을 직접 업데이트하는 쿼리
   * @param params cateno와 cnt를 담은 Map
   * @return 업데이트된 레코드 수
   */
  public int update_cnt_by_cateno(Map<String, Object> params);

  /**
   * 대분류의 cnt 값을 중분류들의 cnt 합계로 업데이트하는 쿼리
   * @return 업데이트된 레코드 수
   */
  public int update_cnt_by_grp();

}
