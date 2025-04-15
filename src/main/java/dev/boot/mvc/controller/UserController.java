package dev.boot.mvc.controller;

import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.db.UserVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.service.UserProcinter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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
  public String list(Model model, HttpSession session) {
    ArrayList<MenuVO> menu = this.cateProc.menu();
    model.addAttribute("menu", menu);

    if (this.userProc.isAdmin(session)) {
      ArrayList<UserVO> list = this.userProc.list();

      model.addAttribute("list", list);

      return "user/list";
    } else {
      return "redirect:/user/login_cookie_need?url=/user/list";
    }
  }


//  // 조회
//  @GetMapping("/read")
//  public String read (
//          Model model,
//          @RequestParam(name = "user_no", defaultValue = "") int user_no
//  ) {
//    UserVO userVO = this.userProc.read(user_no);
//    model.addAttribute("userVO", userVO);
//
//    ArrayList<MenuVO> menu = this.cateProc.menu();
//    model.addAttribute("menu", menu);
//
//    return "user/read";
//  }

  /**
   * 조회
   * @param model
   * @param user_no 회원 번호
   * @return 회원 정보
   */
  @GetMapping("/read")
  public String read (
          HttpSession session,
          Model model,
          @RequestParam(name = "user_no", defaultValue = "") int user_no
  ) {
    String user_level = (String)session.getAttribute("user_level");

    if (user_level.equals("user") && user_no == (int)session.getAttribute("user_no")) {
      UserVO userVO = this.userProc.read(user_no);
      model.addAttribute("userVO", userVO);

      return "user/read";
    } else if (user_level.equals("admin")) {
      UserVO userVO = this.userProc.read(user_no);
      model.addAttribute("userVO", userVO);

      return "user/read";
    } else {
      return "redirect:/user/login_cookie_need";
    }

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


  //  /**
//   * 로그인
//   * @param model
//   * @param // user_no 회원 번호
//   * @return 회원 정보
//   */
//  @GetMapping(value="/login")
//  public String login_form(Model model) {
//    return "/user/login";   // templates/user/login.html
//  }
//
//  /**
//   * 로그인 처리
//   * @param model
//   * @param // user_no 회원 번호
//   * @return 회원 정보
//   */
//  @PostMapping(value="/login")
//  public String login_proc(HttpSession session, Model model,
//                           @RequestParam(name="email", defaultValue = "") String email,
//                           @RequestParam(name="password", defaultValue = "") String password) {
//    HashMap<String, Object> map = new HashMap<String, Object>();
//    map.put("email", email);
//    map.put("password", password);
//
//    int cnt = this.userProc.login(map);
//    System.out.println("-> login_proc cnt: " + cnt);
//
//    model.addAttribute("cnt", cnt);
//
//    if (cnt == 1) {
//      UserVO userVO = this.userProc.readById(id);
//      session.setAttribute("userVO", userVO.getUser_no());
//      session.setAttribute("email", userVO.getEmail());
//      session.setAttribute("u_name", userVO.getU_name());
//      session.setAttribute("user_level", userVO.getUser_level());
//
//      return "redirect:/";
//    } else {
//      model.addAttribute("code", "login_fail");
//      return "/user/msg";
//    }
//
//  }

  // ----------------------------------------------------------------------------------
  // Cookie 사용 로그인 관련 코드 시작
  // ----------------------------------------------------------------------------------

  /**
   * 로그인
   * */
  @GetMapping("/login")
  public String login_form (Model model, HttpServletRequest request)   {
    ArrayList<MenuVO> menu = this.cateProc.menu();
    model.addAttribute("menu", menu);

    // Cookie 관련 코드
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_email = "";
    String ck_email_save = "";
    String ck_passwd = "";
    String ck_passwd_save = "";

    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        cookie = cookies[i];

        if (cookie.getName().equals("ck_email")) {
          ck_email = cookie.getValue(); // email
        } else if (cookie.getName().equals("ck_email_save")) {
          ck_email_save = cookie.getValue(); // Y, N
        } else if (cookie.getName().equals("ck_passwd")) {
          ck_passwd = cookie.getValue(); // password
        } else if (cookie.getName().equals("ck_passwd_save")) {
          ck_passwd_save = cookie.getValue(); // Y, N
        }
      }
    }

    model.addAttribute("ck_email", ck_email);
    model.addAttribute("ck_email_save", ck_email_save);
    model.addAttribute("ck_passwd", ck_passwd);
    model.addAttribute("ck_passwd_save", ck_passwd_save);



    return "user/login_cookie";
  }


  /**
   * Cookie 기반 로그인 처리
   * */
  @PostMapping("/login")
  public String login_proc (
          HttpSession session,
          HttpServletRequest request,
          HttpServletResponse response,
          Model model,
          @RequestParam(value = "email", defaultValue = "") String email,
          @RequestParam(value="password", defaultValue = "") String password,
          @RequestParam(value="email_save", defaultValue = "") String email_save,
          @RequestParam(value="passwd_save", defaultValue = "") String passwd_save,
          @RequestParam(value = "url", defaultValue = "") String url
  ) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("email", email);
    map.put("password", password);

//    System.out.println("email: " + map.get("email"));
//    System.out.println("password: " + map.get("password"));

    int cnt = this.userProc.login(map);

    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
      UserVO userVO = this.userProc.readById(email);

      session.setAttribute("user_no", userVO.getUser_no());
      session.setAttribute("email", userVO.getEmail());
      session.setAttribute("u_name", userVO.getU_name());
      session.setAttribute("user_level", userVO.getUser_level());


      // 회원 등급 처리
      if (userVO.getUser_level() >= 1 && userVO.getUser_level() <= 10) {
        session.setAttribute("user_level", "admin");
      } else if (userVO.getUser_level() >= 11 && userVO.getUser_level() <= 20) {
        session.setAttribute("user_level", "user");
      } else if (userVO.getUser_level() >= 21) {
        session.setAttribute("user_level", "guest");
      }

      System.out.println("-> user_level: " + session.getAttribute("user_level"));


      // Cookie 관련 코드---------------------------------------------------------
      // -------------------------------------------------------------------
      // id 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (email_save.equals("Y")) { // 아이디(이메일)를 저장 할 경우
        Cookie ck_email = new Cookie("ck_email", email);
        ck_email.setPath("/"); // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_email.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(ck_email); // 아이디(이메일) 저장
      } else { // 아이디(이메일)를 저장하지 않을 경우
        Cookie ck_email = new Cookie("ck_email", email);
        ck_email.setPath("/");
        ck_email.setMaxAge(0);
        response.addCookie(ck_email);
      }

      // 아이디(이메일)를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_email_save = new Cookie("ck_email_save", email_save);
      ck_email_save.setPath("/");
      ck_email_save.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(ck_email_save);



      // -------------------------------------------------------------------
      // Password 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (passwd_save.equals("Y")) { // passwd를 저장 할 경우
        Cookie ck_passwd = new Cookie("ck_passwd", password);
        ck_passwd.setPath("/"); // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_passwd.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(ck_passwd); // passwd를 저장
      } else { // passwd를 저장하지 않을 경우
        Cookie ck_passwd = new Cookie("ck_passwd", password);
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }

      // passwd를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(ck_passwd_save);

      if (url.length() > 0) {
        return "redirect:" + url;
      } else {
        return "redirect:/";
      }

    } else {
      model.addAttribute("code", "login_fail");
      return "user/msg";
    }
  }


  /**
   * 로그아웃
   * */
  @GetMapping("/logout")
  public String logout (HttpSession session, Model model) {
    session.invalidate(); // 모든 변수 삭제
    return "redirect:/";
  }

  /**
   * password 수정 폼
   * */
  @GetMapping("/passwd_update")
  public String passwd_update_form (HttpSession session, Model model) {
    if (this.userProc.isMember(session)) {
      ArrayList<MenuVO> menu = this.cateProc.menu();
      model.addAttribute("menu", menu);

      int user_no = (int) session.getAttribute("user_no");

      UserVO userVO = this.userProc.read(user_no);

      model.addAttribute("userVO", userVO);

      return "user/passwd_update";
    } else {
      return "redirect:/user/login_cookie_need";
    }
  }


  /**
   * 현재 패스워드 확인
   * */
  @PostMapping("passwd_check")
  @ResponseBody
  public String passwd_check (HttpSession session, @RequestBody String json_src) {
    JSONObject src = new JSONObject(json_src);
    String current_passwd = (String) src.get("current_passwd");

    try {Thread.sleep(3000);} catch (Exception e) {}


    int user_no = (int) session.getAttribute("user_no"); // session에서 가져오기
    HashMap<String, Object> map = new HashMap<>();
    map.put("user_no", user_no);
    map.put("password", current_passwd);

    int cnt = this.userProc.passwd_check(map);

    JSONObject json = new JSONObject();
    json.put("cnt", cnt);

    return json.toString();
  }


  /**
   * 패스워드 변경
   */
  @PostMapping("/passwd_update_proc")
  public String passwd_update_proc (
          HttpSession session,
          Model model,
          @RequestParam(value = "current_passwd", defaultValue = "") String current_passwd,
          @RequestParam(value = "password", defaultValue = "") String password
  ) {
    if (this.userProc.isMember(session)) {
      int user_no = (int) session.getAttribute("user_no");
      HashMap<String, Object> map = new HashMap<>();
      map.put("user_no", user_no);
      map.put("password", current_passwd);

      System.out.println("user_no: " + user_no);
      System.out.println("password: " + password);

      int cnt = this.userProc.passwd_check(map);

      if (cnt == 0) { // 패스워드 불일치
        model.addAttribute("code", "passwd_not_equal");
        model.addAttribute("cnt", 0);
      } else {
        map = new HashMap<>();
        map.put("user_no", user_no);
        map.put("password", password);

        int passwd_change_cnt = this.userProc.passwd_update(map);

        if (passwd_change_cnt == 1) {
          model.addAttribute("code", "passwd_change_success");
          model.addAttribute("cnt", 1);
        } else {
          model.addAttribute("code", "passwd_change_fail");
          model.addAttribute("cnt", 0);
        }
      }

      return "user/msg";
    } else {
      return "redirect:/user/login_cookie_need";
    }

  }


  /**
   * 로그인 요구에 따른 로그인 폼 출력
   * @param model
   * @param // memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping("/login_cookie_need")
  public String login_cookie_need (
          Model model,
          HttpServletRequest request,
          @RequestParam(name = "url", defaultValue = "") String url
  ) {
    ArrayList<MenuVO> menu = this.cateProc.menu();
    model.addAttribute("menu", menu);

    // Cookie 관련 코드---------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_email = "";
    String ck_email_save = "";
    String ck_passwd = "";
    String ck_passwd_save = "";

    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        cookie = cookies[i];

        if (cookie.getName().equals("ck_email")) {
          ck_email = cookie.getValue(); // email
        } else if (cookie.getName().equals("ck_email_save")) {
          ck_email_save = cookie.getValue(); // Y, N
        } else if (cookie.getName().equals("ck_passwd")) {
          ck_passwd = cookie.getValue(); // password
        } else if (cookie.getName().equals("ck_passwd_save")) {
          ck_passwd_save = cookie.getValue(); // Y, N
        }
      }
    }

    model.addAttribute("ck_email", ck_email);
    model.addAttribute("ck_email_save", ck_email_save);
    model.addAttribute("ck_passwd", ck_passwd);
    model.addAttribute("ck_passwd_save", ck_passwd_save);;

    model.addAttribute("url", url);

    return "user/login_cookie_need"; // templates/user/login_cookie_need.html
  }

}
