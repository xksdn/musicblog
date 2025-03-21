package dev.boot.mvc.service;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface CateProcInter {

  public int create(CategoryVO categoryVO);

  public ArrayList<CategoryVO> list_all();

  public CategoryVO read(int id);

  public int update(CategoryVO categoryVO);
}
