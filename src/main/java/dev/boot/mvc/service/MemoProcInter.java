package dev.boot.mvc.service;

import dev.boot.mvc.db.MemoVO;

import java.util.ArrayList;

public interface MemoProcInter {

  public int create(MemoVO memoVO);

  public ArrayList<MemoVO> list_all();

  public ArrayList<MemoVO> list_all_userno(int user_no);

  public MemoVO read(int memo_no);

  public int update(MemoVO memoVO);

  public int delete(int memo_no);

}
