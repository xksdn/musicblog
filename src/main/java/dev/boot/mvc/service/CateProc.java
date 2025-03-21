package dev.boot.mvc.service;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CateProc implements CateProcInter{

  @Autowired
  private CateDAOInter cateDAOInter;

  @Override
  public int create(CategoryVO categoryVO) {
    int cnt = this.cateDAOInter.create(categoryVO);

    return cnt;
  }

  @Override
  public ArrayList<CategoryVO> list_all() {
    ArrayList<CategoryVO> list = this.cateDAOInter.list_all();

    return list;
  }

  @Override
  public CategoryVO read(int id) {
    CategoryVO categoryVO = this.cateDAOInter.read(id);

    return categoryVO;
  }

  @Override
  public int update(CategoryVO categoryVO) {
    int cnt = this.cateDAOInter.update(categoryVO);

    return cnt;
  }
}
