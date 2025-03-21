package dev.boot.mvc.controller;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.tool.Tool;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/cate")
public class PageController {

  @Autowired
  private CateDAOInter cateDAOInter;
  @Autowired
  private CateProcInter cateProcInter;


  @GetMapping("/")
  public String page() {
    return "index";
  }

  @GetMapping("/create")
  public String create(@ModelAttribute("categoryVO")CategoryVO categoryVO) {
    System.out.println("-> http://localhost:9092/cate/create");
    return "/cate/create";
  }



  @PostMapping("/create")
  public String create(Model model, @Valid CategoryVO categoryVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/cate/create";
    }

    int cnt = this.cateProcInter.create(categoryVO);

    if (cnt == 1) {
      model.addAttribute("code", Tool.CREATE_SUCCESS);
      model.addAttribute("title", categoryVO.getTitle());
      model.addAttribute("artist", categoryVO.getArtist());
    } else {
      model.addAttribute("code", Tool.CREATE_FAIL);
    }

    return "/cate/msg";
  }


  @GetMapping("/list_all")
  public String list_all(Model model) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    return "/cate/list_all";

  }

  // 조회
  // http://localhost:9092/cate/read?cateno=1
  // http://localhost:9092/cate/read/1
  @GetMapping("/read")
  public String read(
          Model model,
          @RequestParam(name = "id", defaultValue = "0") Integer id
  ) {
    CategoryVO categoryVO = this.cateProcInter.read(id);

    model.addAttribute("categoryVO", categoryVO);

    return "cate/read"; // templates/cate/read.html
  }

  @GetMapping("/update")
  public String update(
          Model model,
          @RequestParam(name = "id", defaultValue = "0") Integer id
  ){
    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);
//
//    System.out.println("title: " + categoryVO.getTitle());
//    System.out.println("artist: " + categoryVO.getArtist());
//    System.out.println("genre: " + categoryVO.getGenre());
//    System.out.println("visible: " + categoryVO.getVisible());

    return "cate/update";
  }

  @PostMapping("/update")
  public String update(Model model, @Valid CategoryVO categoryVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "cate/update";
    }

    int cnt = this.cateProcInter.update(categoryVO);

    if (cnt == 1) {
      model.addAttribute("code", Tool.UPDATE_SUCCESS);
      model.addAttribute("title", categoryVO.getTitle());
    } else {
      model.addAttribute("code", Tool.UPDATE_FAIL);
    }
//    System.out.println("cnt: " + cnt);
//    System.out.println("title: " + categoryVO.getTitle());
//    System.out.println("artist: " + categoryVO.getArtist());
//    System.out.println("genre: " + categoryVO.getGenre());



    return "cate/msg";
  }




}


