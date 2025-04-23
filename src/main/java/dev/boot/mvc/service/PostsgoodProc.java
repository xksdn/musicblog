package dev.boot.mvc.service;

import dev.boot.mvc.db.PostsPostsgoodMemberVO;
import dev.boot.mvc.db.PostsgoodDAOInter;
import dev.boot.mvc.db.PostsgoodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class PostsgoodProc implements PostsgoodProcInter {

  @Autowired
  PostsgoodDAOInter postsgoodDAO;
  
  @Override
  public int create(PostsgoodVO postsgoodVO) {
    int cnt = this.postsgoodDAO.create(postsgoodVO);
    return cnt;
  }

  @Override
  public ArrayList<PostsgoodVO> list_all() {
    ArrayList<PostsgoodVO> list = this.postsgoodDAO.list_all();
    return list;
  }

  @Override
  public int delete(int postsgoodVO) {
    int cnt = this.postsgoodDAO.delete(postsgoodVO);
    return cnt;
  }

  @Override
  public int hartCnt(HashMap<String, Object> map) {
    int cnt = this.postsgoodDAO.hartCnt(map);
    return cnt;
  }

  @Override
  public PostsgoodVO read(int postsgoodno) {
    PostsgoodVO postsgoodVO = this.postsgoodDAO.read(postsgoodno);
    return postsgoodVO;
  }

  @Override
  public PostsgoodVO readByContentsnoMemberno(HashMap<String, Object> map) {
    PostsgoodVO postsgoodVO = this.postsgoodDAO.readByContentsnoMemberno(map);
    return postsgoodVO;
  }

  @Override
  public ArrayList<PostsPostsgoodMemberVO> list_all_join() {
    ArrayList<PostsPostsgoodMemberVO> list = this.postsgoodDAO.list_all_join();
    return list;
  }

}



