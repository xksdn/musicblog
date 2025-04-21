package dev.boot.mvc.db;


import java.util.ArrayList;
import java.util.HashMap;

public interface PostDAOInter {

  /**
   * 게시글을 등록합니다.
   * @param postVO 등록할 게시글 정보
   * @return 등록된 행의 수
   */
  public int create(PostVO postVO);


  /**
   * 전체 게시글 목록을 조회합니다.
   * @return 게시글 목록
   */
  public ArrayList<PostVO> list_all();


  /**
   * 카테고리별 게시글 목록을 조회합니다.
   * @param cate_id 카테고리 번호
   * @return 게시글 목록
   */
  public ArrayList<PostVO> list_by_cateno(int cate_id);


  /**
   * 특정 게시글의 상세 정보를 조회합니다.
   * @param post_no 게시글 번호
   * @return 게시글 정보
   */
  public PostVO read(int post_no);


  /**
   * 게시글에 지도(map) 정보를 등록 또는 수정합니다.
   * @param map 게시글 번호, 지도 정보 포함 HashMap
   * @return 수정된 행의 수
   */
  public int map(HashMap<String, Object> map);


  /**
   * 게시글에 유튜브(youtube) 정보를 등록 또는 수정합니다.
   * @param map 게시글 번호, 유튜브 링크 정보 포함 HashMap
   * @return 수정된 행의 수
   */
  public int youtube(HashMap<String, Object> map);


  /**
   * 카테고리별 게시글 목록 중 검색어가 포함된 목록을 조회합니다.
   * @param hashMap 카테고리 번호, 검색어 포함 HashMap
   * @return 검색된 게시글 목록
   */
  public ArrayList<PostVO> list_by_cateno_search(HashMap<String, Object> hashMap);


  /**
   * 카테고리별 게시글 중 검색 결과의 개수를 조회합니다.
   * @param hashMap 카테고리 번호, 검색어 포함 HashMap
   * @return 검색된 게시글 수
   */
  public int list_by_cateno_search_count(HashMap<String, Object> hashMap);


  /**
   * 카테고리별 검색된 게시글 목록을 페이징하여 조회합니다.
   * @param map 카테고리 번호, 검색어, 시작번호, 끝번호 포함 HashMap
   * @return 페이징된 게시글 목록
   */
  public ArrayList<PostVO> list_by_cateno_search_paging(HashMap<String, Object> map);


  /**
   * 게시글 비밀번호가 일치하는지 확인합니다.
   * @param hashMap 게시글 번호, 비밀번호 포함 HashMap
   * @return 일치하면 1, 아니면 0
   */
  public int password_check(HashMap<String, Object> hashMap);


  /**
   * 게시글 제목, 내용, 키워드(word)를 수정합니다.
   * @param postVO 수정할 게시글 정보
   * @return 수정된 행의 수
   */
  public int update_text(PostVO postVO);


  /**
   * 게시글 첨부파일 정보를 수정합니다.
   * @param postVO 수정할 게시글 정보
   * @return 수정된 행의 수
   */
  public int update_file(PostVO postVO);


  /**
   * 게시글을 삭제합니다.
   * @param post_no 삭제할 게시글 번호
   * @return 삭제된 행의 수
   */
  public int delete(int post_no);


  /**
   * 특정 카테고리의 게시글 개수를 조회합니다.
   * @param cate_id 카테고리 번호
   * @return 게시글 개수
   */
  public int count_by_cateno(int cate_id);


  /**
   * 특정 카테고리의 게시글을 모두 삭제합니다.
   * @param cate_id 카테고리 번호
   * @return 삭제된 행의 수
   */
  public int delete_by_cateno(int cate_id);

  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param user_no
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_memberno(int user_no);


  /**
   * 특정 회원이 작성한 게시글 개수를 조회합니다.
   * @param user_no 회원 번호
   * @return 게시글 개수
   */
  public int count_by_memberno(int user_no);


  /**
   * 특정 게시글의 댓글 수(replycnt)를 1 증가시킵니다.
   * @param post_no 게시글 번호
   * @return 수정된 행의 수
   */
  public int increaseReplycnt(int post_no);


  /**
   * 특정 게시글의 댓글 수(replycnt)를 1 감소시킵니다.
   * @param post_no 게시글 번호
   * @return 수정된 행의 수
   */
  public int decreaseReplycnt(int post_no);

}
