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
      return "redirect://user/login_cookie_need";
    }

  }


}
