package dev.boot.mvc.db;


import java.util.ArrayList;

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

}
