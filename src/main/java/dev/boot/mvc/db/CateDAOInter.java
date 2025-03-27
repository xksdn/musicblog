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
}
