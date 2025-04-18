package dev.boot.mvc.controller;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.service.UserProcinter;
import dev.boot.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/cate")
public class PageController {

  // http://localhost:9092/
  // http://192.168.12.151:9092/

  @Autowired
  private CateProcInter cateProcInter;

  @Autowired
  private UserProcinter userProcinter;

  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 6;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  /** 페이징 목록 주소, @GetMappint("/list_search") */
  private String list_file_name = "/cate/list_search";


  @GetMapping("/create")
  public String create(@ModelAttribute("categoryVO")CategoryVO categoryVO, Model model) {
//    System.out.println("-> http://localhost:9092/cate/create");

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    return "/cate/create";
  }



  @PostMapping("/create")
  public String create(
          Model model,
          @Valid CategoryVO categoryVO,
          BindingResult bindingResult,
          @RequestParam(name = "word", defaultValue = "") String word,
          RedirectAttributes ra
  ) {
    if (bindingResult.hasErrors()) {
      return "/cate/create";
    } else {
      int cnt = this.cateProcInter.create(categoryVO);

      if (cnt == 1) {
        ra.addAttribute("word", word);
        return "redirect:/cate/list_search";
      } else {
        model.addAttribute("code", "create_fail");
        model.addAttribute("cnt", cnt);
        return "cate/msg";
      }
    }
  }


  @GetMapping("/list_all")
  public String list_all(Model model, @ModelAttribute("categoryVO") CategoryVO categoryVO) {
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);


    return "/cate/list_all";
  }

  @GetMapping("/list_search")
  public String list_search(
          Model model,
          HttpSession session,
          @ModelAttribute("categoryVO") CategoryVO categoryVO,
          @RequestParam(name = "word", defaultValue = "") String word,
          @RequestParam(name = "now_page", defaultValue = "1") int now_page
  ) {
    if (this.userProcinter.isAdmin(session)) {
      ArrayList<CategoryVO> list = this.cateProcInter.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      word = Tool.checkNull(word);

      ArrayList<MenuVO> menu = this.cateProcInter.menu();
      model.addAttribute("menu", menu);

      int list_search_count = this.cateProcInter.list_search_count(word);
      model.addAttribute("list_search_count", list_search_count);

      model.addAttribute("word", word);

      // --------------------------------------------------------------------------------------
      // 페이지 번호 목록 생성
      // --------------------------------------------------------------------------------------
      int search_count = this.cateProcInter.list_search_count(word);
      String paging = this.cateProcInter.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);

      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      // --------------------------------------------------------------------------------------

      return "cate/list_search";
    } else {
      return "redirect:/user/login_cookie_need?url=/cate/list_search";
    }
  }



  // 조회
  // http://localhost:9092/cate/read?cateno=1
  // http://localhost:9092/cate/read/1
  @GetMapping("/read/{id}")
  public String read(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word,
          @RequestParam(name = "now_page", defaultValue = "1") int now_page
  ) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    ArrayList<CategoryVO> list1 = this.cateProcInter.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list1", list1);

    int list_search_count = this.cateProcInter.list_search_count(word);
    model.addAttribute("list_search_count", list_search_count);

    // --------------------------------------------------------------------------------------
    // 페이지 번호 목록 생성
    // --------------------------------------------------------------------------------------
    int search_count = this.cateProcInter.list_search_count(word);
    String paging = this.cateProcInter.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    // --------------------------------------------------------------------------------------

    return "cate/read"; // templates/cate/read.html
  }

  @GetMapping("/update/{id}")
  public String update(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word,
          @RequestParam(name = "now_page", defaultValue = "1") int now_page
  ){

//    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
//    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    ArrayList<CategoryVO> list = this.cateProcInter.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);
    model.addAttribute("word", word);


    int list_search_count = this.cateProcInter.list_search_count(word);
    model.addAttribute("list_search_count", list_search_count);

    // --------------------------------------------------------------------------------------
    // 페이지 번호 목록 생성
    // --------------------------------------------------------------------------------------
    int search_count = this.cateProcInter.list_search_count(word);
    String paging = this.cateProcInter.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    // --------------------------------------------------------------------------------------

    return "cate/update";
  }

  @PostMapping("/update")
  public String update(Model model,
                       @Valid CategoryVO categoryVO,
                       BindingResult bindingResult,
                       @RequestParam(name = "word", defaultValue = "") String word,
                       RedirectAttributes ra
  ) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    if (bindingResult.hasErrors()) {
      return "cate/update";
    } else {
      int cnt = this.cateProcInter.update(categoryVO);

      if (cnt == 1) {
        ra.addAttribute("word", word);
        return "redirect:/cate/update/" + String.valueOf(categoryVO.getId());
      } else {
        model.addAttribute("code", Tool.UPDATE_FAIL);
        model.addAttribute("cnt", cnt);
        return "cate/msg";
      }
    }
  }

  @GetMapping("/delete/{id}")
  public String delete(Model model,
                       @PathVariable("id") Integer id,
                       @RequestParam(name = "word", defaultValue = "") String word,
                       @RequestParam(name = "now_page", defaultValue = "1") int now_page
  ) {
//    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
//    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    ArrayList<CategoryVO> list = this.cateProcInter.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);
    model.addAttribute("word", word);

    // --------------------------------------------------------------------------------------
    // 페이지 번호 목록 생성
    // --------------------------------------------------------------------------------------
    int search_count = this.cateProcInter.list_search_count(word);
    String paging = this.cateProcInter.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    // --------------------------------------------------------------------------------------

    return "/cate/delete";

  }


  @PostMapping("/delete/{id}")
  public String delete_process(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word,
          RedirectAttributes ra,
          @RequestParam(name = "now_page", defaultValue = "1") int now_page
  ) {
    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    int cnt = this.cateProcInter.delete(id);

    if (cnt == 1) {
      // 마지막 페이지에서 모든 레코드가 삭제되면 페이지수를 1 감소 시켜야함.
      int search_cnt = this.cateProcInter.list_search_count(word);
      if (search_cnt % this.record_per_page == 0) {
        now_page = now_page - 1;
        if (now_page < 1) {
          now_page = 1; // 최소 시작 페이지
        }
      }

      ra.addAttribute("now_page", now_page);
      ra.addAttribute("word", word);
      return "redirect:/cate/list_search";
    } else {
      model.addAttribute("code", Tool.DELETE_FAIL);

      model.addAttribute("title", categoryVO.getTitle());
      model.addAttribute("artist", categoryVO.getArtist());
      model.addAttribute("cnt", cnt);

      return "/cate/msg";
    }
  }


  // 우선순위 높임 , 10등 -> 1등
  // http://localhost:9091/update_seqno_forward/1
  @GetMapping("/update_seqno_forward/{id}")
  public String update_seqno_forward(Model model,
                                     @PathVariable("id") Integer id,
                                     @RequestParam(name = "word", defaultValue = "") String word,
                                     @RequestParam(name = "now_page", defaultValue = "1") int now_page,
                                     RedirectAttributes ra
  ) {
    this.cateProcInter.update_seqno_forward(id);

    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/cate/list_search";
  }

  // 우선순위 낮춤 , 1등 -> 10등
  // http://localhost:9091/update_seqno_backward/1
  @GetMapping("/update_seqno_backward/{id}")
  public String update_seqno_backward(Model model,
                                      @PathVariable("id") Integer id,
                                      @RequestParam(name = "word", defaultValue = "") String word,
                                      @RequestParam(name = "now_page", defaultValue = "1") int now_page,
                                      RedirectAttributes ra
  ) {
    this.cateProcInter.update_seqno_backward(id);

    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/cate/list_search";
  }


  @GetMapping("/update_visible_y/{id}")
  public String update_visible_y(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word,
          @RequestParam(name = "now_page", defaultValue = "1") int now_page,
          RedirectAttributes ra
  ) {
    this.cateProcInter.update_visible_y(id);
//    System.out.println("->update_visible_y");

    System.out.println("Redirecting to list_search with word=" + word + "&now_page=" + now_page);

    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/cate/list_search";
  }

  @GetMapping("/update_visible_n/{id}")
  public String update_visible_n(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word,
          @RequestParam(name = "now_page", defaultValue = "1") int now_page,
          RedirectAttributes ra
  ) {
    this.cateProcInter.update_visible_n(id);
//    System.out.println("->update_visible_y");

    System.out.println("Redirecting to list_search with word=" + word + "&now_page=" + now_page);

    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/cate/list_search";
  }


}


