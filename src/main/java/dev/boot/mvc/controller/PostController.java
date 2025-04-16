package dev.boot.mvc.controller;


import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.db.PostVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.service.PostProcInter;
import dev.boot.mvc.service.Posts;
import dev.boot.mvc.service.UserProcinter;
import dev.boot.mvc.tool.Tool;
import dev.boot.mvc.tool.Upload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RequestMapping("/posts")
@Controller
public class PostController {

  @Autowired
  @Qualifier("dev.boot.mvc.service.UserProc")
  private UserProcinter userProcinter;

  @Autowired
  private CateProcInter cateProcInter;

  @Autowired
  private PostProcInter postProcInter;

  public PostController () {
    System.out.println("-> PostController created.");
  }


  /**
   * POST 요청시 새로고침 방지, POST 요청 처리 완료 → redirect → url → GET → forward -> html 데이터
   * 전송
   *
   * @return
   */
  @GetMapping(value = "/msg")
  public String post2get(Model model, String url) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    return url;
  }

  @GetMapping("/create")
  public String create (
          Model model,
          @ModelAttribute("postVO")PostVO postVO,
          @RequestParam(name = "cate_id", defaultValue = "") int cate_id
          ) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    CategoryVO categoryVO = this.cateProcInter.read(cate_id);
    model.addAttribute("categoryVO", categoryVO);

    return "posts/create";
  }


  /**
   * 등록 처리 http://localhost:9091/contents/create
   *
   * @return
   */
  @PostMapping("/create")
  public String create(
          HttpServletRequest request,
          HttpSession session,
          Model model,
          @ModelAttribute("postVO")PostVO postVO,
          RedirectAttributes ra
  ) {
    if (userProcinter.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = ""; // 원본 파일명 image
      String file1saved = ""; // 저장된 파일명, image
      String thumb1 = ""; // preview image

      String upDir = Posts.getUploadDir(); // 파일을 업로드할 폴더 준비
      // upDir = upDir + "/" + 한글을 제외한 카테고리 이름
      System.out.println("-> upDir: " + upDir);

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF'
      // value='' placeholder="파일 선택">
      MultipartFile mf = postVO.getFile1MF();

      file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);

      long size1 = mf.getSize(); // 파일 크기
      if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
        if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir);

          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb1 = Tool.preview(upDir, file1saved, 200, 150);
          }

          postVO.setFile1(file1); // 순수 원본 파일명
          postVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
          postVO.setThumb1(thumb1); // 원본이미지 축소판
          postVO.setSize1(size1); // 파일 크기

        } else { // 전송 못하는 파일 형식
          ra.addFlashAttribute("code", Tool.UPLOAD_FILE_CHECK_FAIL); // 업로드 할 수 없는 파일
          ra.addFlashAttribute("cnt", 0); // 업로드 실패
          ra.addFlashAttribute("url", "/posts/msg"); // msg.html, redirect parameter 적용
          return "redirect:/posts/msg"; // Post -> Get - param...
        }
      } else { // 글만 등록하는 경우
        System.out.println("-> 글만 등록");
      }

      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------

      // Call By Reference: 메모리 공유, Hashcode 전달
      int user_no = (int) session.getAttribute("user_no"); // memberno FK
      postVO.setUser_no(user_no);
      int cnt = this.postProcInter.create(postVO);

      // ------------------------------------------------------------------------------
      // PK의 return
      // ------------------------------------------------------------------------------
      // System.out.println("--> contentsno: " + contentsVO.getContentsno());
      // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect
      // parameter 적용
      // ------------------------------------------------------------------------------

      if (cnt == 1) {
        // type 1, 재업로드 발생
        // return "<h1>파일 업로드 성공</h1>"; // 연속 파일 업로드 발생

        // type 2, 재업로드 발생
        // model.addAttribute("cnt", cnt);
        // model.addAttribute("code", "create_success");
        // return "contents/msg";

        // type 3 권장
        // return "redirect:/contents/list_all"; // /templates/contents/list_all.html

        // System.out.println("-> contentsVO.getCateno(): " + contentsVO.getCateno());
        // ra.addFlashAttribute("cateno", contentsVO.getCateno()); // controller ->
        // controller: X

//        return "redirect:/contents/list_all";

        ra.addAttribute("cate_id", postVO.getCate_id()); // controller -> controller: O
        return "redirect:/posts/list_by_cateno";

        // return "redirect:/contents/list_by_cateno?cateno=" + contentsVO.getCateno();
        // // /templates/contents/list_by_cateno.html
      } else {
        ra.addFlashAttribute("code", Tool.CREATE_FAIL); // DBMS 등록 실패
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/posts/msg"); // msg.html, redirect parameter 적용
        return "redirect:/posts/msg"; // Post -> Get - param...
      }
    } else { // 로그인 실패 한 경우
      return "redirect:/user/login_cookie_need?url=/posts/create?cate_id=" + postVO.getCate_id(); // /member/login_cookie_need.html
    }
  }


  /**
   * 전체 목록, 관리자만 사용 가능 http://localhost:9092/posts/list_all
   *
   * @return
   */
  @GetMapping("/list_all")
  public String list_all (HttpSession session, Model model) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    if (this.userProcinter.isAdmin(session)) {
      ArrayList<PostVO> list = this.postProcInter.list_all();

      model.addAttribute("list", list);
      return "posts/list_all";
    } else {
      return "redirect:/user/login_cookie_need";
    }
  }

  /**
   * 유형 1
   * 카테고리별 목록
   */
  @GetMapping("/list_by_cateno")
  public String list_by_cateno (
          HttpSession session,
          Model model,
          @RequestParam(name = "cate_id", defaultValue = "") int cate_id
  ) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    CategoryVO categoryVO = this.cateProcInter.read(cate_id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<PostVO> list = this.postProcInter.list_by_cateno(cate_id);
    model.addAttribute("list", list);

    return "posts/list_by_cateno";
  }

  @GetMapping("/read")
  public String read (Model model, int post_no, String word, int now_page) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    PostVO postVO = this.postProcInter.read(post_no);

    long size1 = postVO.getSize1();
    String size1_label = Tool.unit(size1);
    postVO.setSize1_label(size1_label);

    model.addAttribute("postVO", postVO);

    CategoryVO categoryVO = this.cateProcInter.read(postVO.getCate_id());
    model.addAttribute("categoryVO", categoryVO);

    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    return "posts/read";
  }



}
