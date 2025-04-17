package dev.boot.mvc.service;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.db.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

//  @Override
//  public ArrayList<MenuVO> menu() {
//    ArrayList<MenuVO> menu = new ArrayList<MenuVO>();
//    ArrayList<CategoryVO> grps = this.cateDAOInter.list_all_grp_y();
//
//    for (CategoryVO cateVO : grps) {
//      MenuVO menuVO = new MenuVO();
//
//      menuVO.setGenre(cateVO.getGenre());
//
//      // list_name을 가져오고 중복 제거 및 정렬 처리
//      ArrayList<CategoryVO> list_name = this.cateDAOInter.list_all_name_y(cateVO.getGenre());
//
//      // 중복 제거 및 era 기준으로 정렬
//      ArrayList<CategoryVO> uniqueSortedCategories = list_name.stream()
//              .distinct() // 중복 제거
//              .sorted((c1, c2) -> c1.getEra().compareTo(c2.getEra())) // era 기준 오름차순 정렬
//              .collect(Collectors.toCollection(ArrayList::new)); // 명시적으로 ArrayList로 변환
//
//      menuVO.setList_name(uniqueSortedCategories);
//
//      menu.add(menuVO);
//    }
//
//    return menu;
//  }




  @Override
  public ArrayList<CategoryVO> list_search(String word) {
    ArrayList<CategoryVO> list = this.cateDAOInter.list_search(word);

    return list;
  }

  @Override
  public int list_search_count(String word) {
    int cnt = this.cateDAOInter.list_search_count(word);

    return cnt;
  }

  @Override
  public ArrayList<CategoryVO> list_search_paging(String word, int now_page, int record_per_page) {
    int start_num = ((now_page - 1) * record_per_page) + 1;
    int end_num=(start_num + record_per_page) - 1;

    System.out.println("WHERE r >= "+start_num+" AND r <= " + end_num);

    Map<String, Object> map = new HashMap<String, Object>();

    map.put("word", word);
    map.put("start_num", start_num);
    map.put("end_num", end_num);

    ArrayList<CategoryVO> list = this.cateDAOInter.list_search_paging(map);

    return list;
  }

  @Override
  public String pagingBox(int now_page, String word, String list_url,
                          int search_count, int record_per_page, int page_per_block
  ) {
    // 전체 페이지 수: (double)1/10 -> 0.1 -> 1 페이지, (double)12/10 -> 1.2 페이지 -> 2 페이지
    int total_page = (int)(Math.ceil((double)search_count / record_per_page));

    // 전체 그룹  수: (double)1/10 -> 0.1 -> 1 그룹, (double)12/10 -> 1.2 그룹-> 2 그룹
    int total_grp = (int)(Math.ceil((double)total_page / page_per_block));

    // 현재 그룹 번호: (double)13/10 -> 1.3 -> 2 그룹
    int now_grp = (int)(Math.ceil((double)now_page / page_per_block));


    int start_page = ((now_grp - 1) * page_per_block) + 1; // 특정 그룹의 시작 페이지
    int end_page = (now_grp * page_per_block);               // 특정 그룹의 마지막 페이지

    StringBuffer str = new StringBuffer();

    str.append("<div id='paging'>");

    int _now_page = (now_grp - 1) * page_per_block;
    if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성
      str.append("<span class='span_box_1'><a href='"+list_url+"?&word="+word+"&now_page="+_now_page+"'>이전</a></span>");
    }

    // 중앙의 페이지 목록
    for(int i=start_page; i<=end_page; i++){
      if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이 출력 종료
        break;
      }

      if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
        str.append("<span class='span_box_2'>"+i+"</span>"); // 현재 페이지, 강조
      }else{
        // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
        str.append("<span class='span_box_1'><a href='"+list_url+"?word="+word+"&now_page="+i+"'>"+i+"</a></span>");
      }
    }


    _now_page = (now_page * page_per_block) + 1;
    if (now_grp < total_grp) {
      str.append("<span class='span_box_1'><a href='"+list_url+"?&word="+word+"&now_page="+_now_page+"'>다음</a></span>");
    }
    str.append("</div>");


    return str.toString();
  }
}
