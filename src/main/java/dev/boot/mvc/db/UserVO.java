package dev.boot.mvc.db;

import lombok.Data;

@Data
public class UserVO {

  /*
  CREATE TABLE user_info (
      user_no NUMBER(10) NOT NULL, -- 회원 번호, 레코드를 구분하는 컬럼
      email VARCHAR(30) NOT NULL UNIQUE, -- 이메일(아이디), 중복 안됨, 레코드를 구분
      password VARCHAR(200) NOT NULL, -- 패스워드, 영숫자 조합, 암호화
      u_name VARCHAR(30) NOT NULL, -- 성명, 한글 10자 저장 가능
      phone VARCHAR(14) NOT NULL, -- 전화번호
      zipcode VARCHAR(5) NULL, -- 우편번호, 12345
      address VARCHAR(80) NULL, -- 주소
      address_detail VARCHAR(50) NULL, -- 상세 주소
      mdate DATE NOT NULL, -- 가입일
      user_level NUMBER(2) NOT NULL, -- 등급(1~10: 관리자, 11~20: 회원, 40~49: 정지 회원, 99: 탈퇴 회원)
      PRIMARY KEY (user_no) -- 한번 등록된 값은 중복 안됨
  );
  */




  /** 회원 번호 */
  private int user_no;

  /** 아이디(이메일) */
  private String email;

  /** 패스워드 */
  private String password;

  /** 회원 이름 */
  private String u_name;

  /** 회원 전화번호 */
  private String phone;

  /** 우편번호 */
  private String zipcode;

  /** 주소 */
  private String address;

  /** 상세 주소 */
  private String address_detail;

  /** 가입일 */
  private String mdate;

  /** 등급 */
  private int user_level;

  /** 등록된 패스워드 */
  private String old_password;
  /** id 저장 여부 */
  private String id_save;
  /** passwd 저장 여부 */
  private String passwd_save;
  /** 이동할 주소 저장 */
  private String url_address;



}
