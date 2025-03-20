package dev.boot.mvc.db;


import java.util.ArrayList;

public interface CateDAOInter {
  public int create(CategoryVO categoryVO);

  public ArrayList<CategoryVO> list_all();
}
