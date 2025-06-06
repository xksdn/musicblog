package dev.boot.mvc.service;

import dev.boot.mvc.db.UserDAOInter;
import dev.boot.mvc.db.UserVO;
import dev.boot.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Component("dev.boot.mvc.service.UserProc")
public class UserProc implements UserProcinter{

  @Autowired
  private UserDAOInter userDAOInter;

  @Autowired
  private Security security;

  @Override
  public int checkID(String email) {
    int cnt = this.userDAOInter.checkID(email);

    return cnt;
  }

  @Override
  public int create(UserVO userVO) {
    String password = userVO.getPassword();
    Security security = new Security();
    String password_encoded = security.aesEncode(password);
    userVO.setPassword(password_encoded);


    int cnt = this.userDAOInter.create(userVO);

    return cnt;
  }

  @Override
  public ArrayList<UserVO> list() {
    ArrayList<UserVO> list = this.userDAOInter.list();

    return list;
  }

  @Override
  public UserVO read(int user_no) {
    UserVO userVO = this.userDAOInter.read(user_no);

    return userVO;
  }

  @Override
  public UserVO readById(String email) {
    UserVO userVO = this.userDAOInter.readById(email);

    return userVO;
  }

//  @Override
//  public boolean isMember(HttpSession session) {
//    boolean sw = false;
//    int grade = 99;
//
//    if (session != null) {
//      String id = (String)session.getAttribute("id");
//
//      if (session.getAttribute("grade") != null) {
//        grade = (int) session.getAttribute("grade");
//      }
//
//      if (id != null && grade <= 20) { // 관리자 + 회원
//        sw = true; // 로그인 한 경우
//      }
//    }
//
//    return sw;
//  }
//
//  @Override
//  public boolean isMemberAdmin(HttpSession session) {
//    boolean sw = false; // 로그인하지 않은 것으로 초기화
//    int grade = 99;
//
//    // System.out.println("-> grade: " + session.getAttribute("grade"));
//    if (session != null) {
//      String id = (String)session.getAttribute("id");
//      if (session.getAttribute("grade") != null) {
//        grade = (int)session.getAttribute("grade");
//      }
//
//      if (id != null && grade <= 10){ // 관리자
//        sw = true;  // 로그인 한 경우
//      }
//    }
//
//    return sw;
//  }

  @Override
  public boolean isMember(HttpSession session) {
    boolean sw = false;
    String user_level = (String) session.getAttribute("user_level");

    if (user_level != null) {
      System.out.println("User level in session: " + user_level);
      if (user_level.equals("admin") || user_level.equals("user")) {
        sw = true;
      }
    }

    return sw;
  }

  @Override
  public boolean isAdmin(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String user_level = (String)session.getAttribute("user_level");

    if (user_level != null) {
      if (user_level.equals("admin")) {
        sw = true;
      }
    }

    return sw;
  }

  @Override
  public int update(UserVO userVO) {
    int cnt = this.userDAOInter.update(userVO);

    return cnt;
  }

  @Override
  public int delete(int user_no) {
    int cnt = this.userDAOInter.delete(user_no);

    return cnt;
  }

  @Override
  public int passwd_check(HashMap<String, Object> map) {
    String password = (String) map.get("password");
    map.put("password", this.security.aesEncode(password));

    int cnt = this.userDAOInter.passwd_check(map);

    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    String password = (String) map.get("password");
    map.put("password", this.security.aesEncode(password));

    int cnt = this.userDAOInter.passwd_update(map);

    return cnt;
  }

  @Override
  public int login(HashMap<String, Object> map) {
    String password = (String) map.get("password");
    map.put("password", this.security.aesEncode(password));

    int cnt = this.userDAOInter.login(map);

    return cnt;
  }

}
