package dev.boot.mvc.controller;

import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.db.UserVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.service.UserProcinter;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;

// http://localhost:9092/user
// http://192.168.12.151:9092/user

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  @Qualifier("dev.boot.mvc.service.UserProc")
  private UserProcinter userProc;

  @Autowired
  private CateProcInter cateProc;

  public UserController () {
    System.out.println("-> MemberCont created.");
  }


  @GetMapping(value="/checkID") // http://localhost:9091/user/checkID?id=admin
  @ResponseBody
  public String checkID(@RequestParam(name="id", defaultValue = "") String id) {
    System.out.println("-> id: " + id);
    int cnt = this.userProc.checkID(id);

    // return "cnt: " + cnt;
    // return "{\"cnt\": " + cnt + "}";    // {"cnt": 1} JSON

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);

    return obj.toString();
  }


  // 회원가입 폼
  @GetMapping("/create")
  public String create_form (
          Model model,
          @ModelAttribute("userVO")UserVO userVO
  ) {
    ArrayList<MenuVO> menu = this.cateProc.menu();
    model.addAttribute("menu", menu);

    return "user/create";
  }

  @PostMapping("/create")
  public String create_proc(
          Model model,
          @ModelAttribute("userVO")UserVO userVO
  ) {
    int checkID_cnt = this.userProc.checkID(userVO.getEmail());

    if (checkID_cnt == 0) {
      userVO.setUser_level(15);
      int cnt = this.userProc.create(userVO);

      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("u_name", userVO.getU_name());
        model.addAttribute("email", userVO.getEmail());
      } else {
        model.addAttribute("code", "create_fail");
      }

      model.addAttribute("cnt", cnt);
    } else {
      model.addAttribute("code", "duplicte_fail");
      model.addAttribute("cnt", 0);
    }

    return "user/msg";
  }

  // http://192.168.12.151:9092/user/list
  @GetMapping("/list")
  public String list(Model model) {
    ArrayList<UserVO> list = this.userProc.list();

    model.addAttribute("list", list);

    return "user/list";
  }


  // 조회
  @GetMapping("/read")
  public String read (
          Model model,
          @RequestParam(name = "user_no", defaultValue = "") int user_no
  ) {
    UserVO userVO = this.userProc.read(user_no);
    model.addAttribute("userVO", userVO);

    ArrayList<MenuVO> menu = this.cateProc.menu();
    model.addAttribute("menu", menu);

    return "user/read";
  }


  // 수정 처리
  @PostMapping("/update")
  public String update_proc (
          Model model,
          @ModelAttribute("userVO")UserVO userVO
  ) {
    int cnt = this.userProc.update(userVO);

    if (cnt == 1) {
      model.addAttribute("code", "update_success");
      model.addAttribute("u_name", userVO.getU_name());
      model.addAttribute("email", userVO.getEmail());
    } else {
      model.addAttribute("code", "update_fail");
    }

    model.addAttribute("cnt", cnt);

    return "user/msg";
  }


  // 삭제
  @GetMapping("/delete")
  public String delete (
          Model model,
          @RequestParam(name = "user_no", defaultValue = "") int user_no
  ) {
    UserVO userVO = this.userProc.read(user_no);
    model.addAttribute("userVO", userVO);


    return "user/delete";
  }


  @PostMapping("/delete")
  public String delete_proces (
          Model model,
          @RequestParam(name = "user_no", defaultValue = "") int user_no
  ) {
    int cnt = this.userProc.delete(user_no);

    if (cnt == 1) {
      return "redirect:/user/list";
    } else {
      model.addAttribute("code", "delete_fail");
      return "user/msg";
    }
  }

}
