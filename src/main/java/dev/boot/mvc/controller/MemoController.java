package dev.boot.mvc.controller;


import dev.boot.mvc.db.MemoVO;
import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/memo")
public class MemoController {

  @Autowired
  @Qualifier("dev.boot.mvc.service.UserProc")
  private UserProcinter userProcinter;

  @Autowired
  private CateProcInter cateProcInter;

  @Autowired
  private PostProcInter postProcInter;

  @Autowired
  private PostsgoodProcInter postsgoodProc;

  @Autowired
  private MemoProcInter memoProcInter;

  public MemoController () {
    System.out.println("-> MemoController created.");
  }


  /**
   * POST 요청시 새로고침 방지, POST 요청 처리 완료 → redirect → url → GET → forward -> html 데이터
   * 전송
   *
   * @return
   */
  @GetMapping(value = "/post2get")
  public String post2get(Model model, String url) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    return url;
  }


  @GetMapping("/create")
  public String create (
          Model model,
          @ModelAttribute("memoVO") MemoVO memoVO,
          @RequestParam(name = "user_no", defaultValue = "0") int user_no
  ) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    return "memo/create";
  }


  @PostMapping("/create")
  public String create(
          HttpServletRequest request,
          HttpSession session,
          Model model,
          @ModelAttribute("memoVO") MemoVO memoVO,
          RedirectAttributes ra
  ) {
    if (userProcinter.isMember(session)) {

      int user_no = (int) session.getAttribute("user_no");
      memoVO.setUser_no(user_no);

      int cnt = this.memoProcInter.create(memoVO);

      if (cnt == 1) {
        System.out.println("-> memo create!");
        return "redirect:/memo/list_all";
      } else {
        model.addAttribute("code", "create_fail");
        model.addAttribute("cnt", cnt);
        return "memo/msg";
      }

    } else {
      return "redirect:/user/login_cookie_need";
    }

  }


  @GetMapping("/list_all")
  public String list_all(
          HttpSession session,
          Model model
  ) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    if (this.userProcinter.isAdmin(session)) {
      ArrayList<MemoVO> list = this.memoProcInter.list_all();

//      System.out.println("-> memo list: " + list);

      model.addAttribute("list", list);
      return "memo/list_all";
    } else {
      return "redirect:/user/login_cookie_need";
    }
  }

  @GetMapping("/list_all_userno")
  public String list_all_userno(
          HttpSession session,
          Model model,
          @RequestParam(name = "user_no", defaultValue = "") int user_no
  ) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    if (this.userProcinter.isMember(session)) {
      ArrayList<MemoVO> list = this.memoProcInter.list_all_userno(user_no);

      model.addAttribute("list", list);

      return "memo/list_all_userno";
    } else {
      return "redirect:/user/login_cookie_need";
    }
  }




  @GetMapping("/read")
  public String read(
          Model model,
          HttpSession session,
          @RequestParam(name = "memo_no", defaultValue = "0") int memo_no
  ) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    if (this.userProcinter.isMember(session)){
      MemoVO memoVO = this.memoProcInter.read(memo_no);

      model.addAttribute("memoVO", memoVO);

      return "memo/read";
    } else {
      return "redirect:/user/login_cookie_need";
    }
  }


  @GetMapping("/update")
  public String update(
          Model model,
          HttpSession session,
          @RequestParam(name = "memo_no", defaultValue = "0") int memo_no
  ) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    if (this.userProcinter.isMember(session)) {
      MemoVO memoVO = this.memoProcInter.read(memo_no);
      model.addAttribute("memoVO", memoVO);

      return "memo/update";
    } else {
      return "redirect:/user/login_cookie_need";
    }
  }

  @PostMapping("/update")
  public String update(
          HttpSession session,
          Model model,
          RedirectAttributes ra,
          MemoVO memoVO
  ) {
    if (this.userProcinter.isMember(session)) {
      this.memoProcInter.update(memoVO);

      return "redirect:/memo/read?memo_no="+memoVO.getMemo_no();
    } else {
      return "redirect:/user/login_cookie_need";
    }
  }
}
