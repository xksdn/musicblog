package dev.boot.mvc.controller;


import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.db.PostsPostsgoodMemberVO;
import dev.boot.mvc.db.PostsgoodVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.service.PostsgoodProcInter;
import dev.boot.mvc.service.UserProcinter;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

// http://localhost:9092/
// http://192.168.12.151:9092/

@Controller
@RequestMapping(value = "/postsgood")
public class PostsgoodController {
  @Autowired
  private UserProcinter userProc;

  @Autowired// @Component("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  @Autowired
  PostsgoodProcInter postsgoodProc;

  public PostsgoodController() {
    System.out.println("-> ContentsgoodCont created.");
  }
  
  /**
   * POST 요청시 새로고침 방지, POST 요청 처리 완료 → redirect → url → GET → forward -> html 데이터
   * 전송
   * 
   * @return
   */
  @GetMapping(value = "/post2get")
  public String post2get(Model model, 
      @RequestParam(name="url", defaultValue = "") String url) {
    ArrayList<MenuVO> menu = this.cateProc.menu();
    model.addAttribute("menu", menu);

    return url; // forward, /templates/...
  }
  
  @PostMapping(value="/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody PostsgoodVO postsgoodVO) {
    System.out.println("-> 수신 데이터:" + postsgoodVO.toString());
    
    // int memberno = 1; // test
    int user_no = (int)session.getAttribute("user_no"); // 보안성 향상
    postsgoodVO.setUser_no(user_no);
    
    int cnt = this.postsgoodProc.create(postsgoodVO);
    
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    
    return json.toString();
  }
  
//  /**
//   * 목록
//   *
//   * @param model
//   * @return
//   */
//  // http://localhost:9091/contentsgood/list_all
//  @GetMapping(value = "/list_all")
//  public String list_all(Model model) {
//    ArrayList<PostsgoodVO> list = this.postsgoodProc.list_all();
//    model.addAttribute("list", list);
//
//    ArrayList<MenuVO> menu = this.cateProc.menu();
//    model.addAttribute("menu", menu);
//
//    return "postsgood/list_all"; // /templates/postsgood/list_all.html
//  }
  
  /**
   * 목록
   *
   * @param model
   * @return
   */
  // http://localhost:9091/contentsgood/list_all
  @GetMapping(value = "/list_all")
  public String list_all(Model model) {
    ArrayList<PostsPostsgoodMemberVO> list = this.postsgoodProc.list_all_join();
    model.addAttribute("list", list);

    ArrayList<MenuVO> menu = this.cateProc.menu();
    model.addAttribute("menu", menu);

    return "postsgood/list_all_join"; // /templates/contentsgood/list_all.html
  }
  
  /**
   * 삭제 처리 http://localhost:9091/contentsgood/delete?calendarno=1
   * 
   * @return
   */
  @PostMapping(value = "/delete")
  public String delete_proc(HttpSession session, 
      Model model, 
      @RequestParam(name="postsgoodno", defaultValue = "0") int postsgoodno,
      RedirectAttributes ra) {    
    
    if (this.userProc.isAdmin(session)) { // 관리자 로그인 확인
      this.postsgoodProc.delete(postsgoodno);       // 삭제

      return "redirect:/postsgood/list_all";

    } else { // 정상적인 로그인이 아닌 경우 로그인 유도
      ra.addAttribute("url", "/user/login_cookie_need"); // /templates/member/login_cookie_need.html
      return "redirect:/postsgood/post2get"; // @GetMapping(value = "/msg")
    }

  }
  
}







