package dev.boot.mvc.service;

import dev.boot.mvc.db.MemoDAOInter;
import dev.boot.mvc.db.MemoVO;
import dev.boot.mvc.db.PostDAOInter;
import dev.boot.mvc.tool.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MemoProc implements MemoProcInter{

  @Autowired
  Security security;

  @Autowired
  private MemoDAOInter memoProcInter;

  @Override
  public int create(MemoVO memoVO) {
    int cnt = this.memoProcInter.create(memoVO);

    return cnt;
  }

  @Override
  public ArrayList<MemoVO> list_all() {
    ArrayList<MemoVO> list = this.memoProcInter.list_all();

    return list;
  }

  @Override
  public ArrayList<MemoVO> list_all_userno(int user_no) {
    ArrayList<MemoVO> list = this.memoProcInter.list_all_userno(user_no);

    return list;
  }

  @Override
  public MemoVO read(int memo_no) {
    MemoVO memoVO = this.memoProcInter.read(memo_no);

    return memoVO;
  }

  @Override
  public int update(MemoVO memoVO) {
    int cnt = this.memoProcInter.update(memoVO);

    return cnt;
  }

  @Override
  public int delete(int memo_no) {
    int cnt = this.memoProcInter.delete(memo_no);

    return cnt;
  }
}
