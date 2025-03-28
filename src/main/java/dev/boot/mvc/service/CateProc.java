package dev.boot.mvc.service;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.db.MenuVO;
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

  @Override
  public int delete(int id) {
    int cnt = this.cateDAOInter.delete(id);

    return cnt;
  }

  @Override
  public int update_seqno_forward(int id) {
    int cnt = this.cateDAOInter.update_seqno_forward(id);

    return cnt;
  }

  @Override
  public int update_seqno_backward(int id) {
    int cnt = this.cateDAOInter.update_seqno_backward(id);

    return cnt;
  }

  @Override
  public int update_visible_y(int id) {
    int cnt = this.cateDAOInter.update_visible_y(id);

    return cnt;
  }

  @Override
  public int update_visible_n(int id) {
    int cnt = this.cateDAOInter.update_visible_n(id);

    return cnt;
  }

  @Override
  public ArrayList<CategoryVO> list_all_grp_y() {
    ArrayList<CategoryVO> list = this.cateDAOInter.list_all_grp_y();

    return list;
  }

  @Override
  public ArrayList<CategoryVO> list_all_name_y(String genre) {
    ArrayList<CategoryVO> list = this.cateDAOInter.list_all_name_y(genre);

    return list;
  }

  @Override
  public ArrayList<MenuVO> menu() {
    ArrayList<MenuVO> menu = new ArrayList<MenuVO>();
    ArrayList<CategoryVO> grps = this.cateDAOInter.list_all_grp_y();

    for (CategoryVO cateVO : grps) {
      MenuVO menuVO = new MenuVO();

      menuVO.setGenre(cateVO.getGenre());

      ArrayList<CategoryVO> list_name = this.cateDAOInter.list_all_name_y(cateVO.getGenre());
      menuVO.setList_name(list_name);

      menu.add(menuVO);
    }

    return menu;
  }
}
