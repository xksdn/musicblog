package dev.boot.mvc.service;

import dev.boot.mvc.db.UserVO;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

public interface UserProcinter {
  /**
   * 중복 아이디(이메일) 검사
   * @param email
   * @return 중복 아이디 갯수
   */
  public int checkID(String email);

  /**
   * 회원 가입
   * @param userVO
   * @return 추가한 레코드 갯수
   */
  public int create(UserVO userVO);

  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<UserVO> list();

  /**
   * memberno로 회원 정보 조회
   * @param user_no
   * @return
   */
  public UserVO read(int user_no);

  /**
   * id로 회원 정보 조회
   * @param email
   * @return
   */
  public UserVO readById(String email);

  /**
   * 로그인된 회원 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isMember(HttpSession session);

  /**
   * 로그인된 회원 관리자 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isAdmin(HttpSession session);

  /**
   * 수정 처리
   * @param userVO
   * @return
   */
  public int update(UserVO userVO);

  /**
   * 회원 삭제 처리
   * @param user_no
   * @return
   */
  public int delete(int user_no);

  /**
   * 현재 패스워드 검사
   * @param map
   * @return 0: 일치하지 않음, 1: 일치함
   */
  public int passwd_check(HashMap<String, Object> map);

  /**
   * 패스워드 변경
   * @param map
   * @return 변경된 패스워드 갯수
   */
  public int passwd_update(HashMap<String, Object> map);

  /**
   * 로그인 처리
   */
  public int login(HashMap<String, Object> map);

}
