package dev.boot.mvc.db;


import lombok.Data;

@Data
public class MemoVO {
  private int memo_no;

  private String title;

  private String content;

  private String rdate;

  private int user_no;
}
