package dev.boot.mvc.service;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.db.MenuVO;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface CateProcInter {

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

  public ArrayList<MenuVO> menu();


  ArrayList<CategoryVO> list_search(String word);

  int list_search_count(String word);

  public ArrayList<CategoryVO> list_search_paging(String word, int now_page, int record_per_page);

  String pagingBox(int now_page, String word, String list_url, int search_count, int record_per_page,
                   int page_per_block);

}
