package dev.boot.mvc.controller;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.tool.Tool;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
  private CateProcInter cateProcInter;


  @GetMapping("/create")
  public String create(@ModelAttribute("categoryVO")CategoryVO categoryVO) {
//    System.out.println("-> http://localhost:9092/cate/create");
    return "/cate/create";
  }



  @PostMapping("/create")
  public String create(Model model, @Valid CategoryVO categoryVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/cate/create";
    }

    int cnt = this.cateProcInter.create(categoryVO);

    if (cnt == 1) {
//      model.addAttribute("code", Tool.CREATE_SUCCESS);
//      model.addAttribute("title", categoryVO.getTitle());
//      model.addAttribute("artist", categoryVO.getArtist());
      return "redirect:/cate/list_all";
    } else {
      model.addAttribute("code", Tool.CREATE_FAIL);
    }

    return "/cate/msg";
  }


  @GetMapping("/list_all")
  public String list_all(Model model, @ModelAttribute("categoryVO") CategoryVO categoryVO) {
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);


    return "/cate/list_all";
  }

  // 조회
  // http://localhost:9092/cate/read?cateno=1
  // http://localhost:9092/cate/read/1
  @GetMapping("/read/{id}")
  public String read(
          Model model,
          @PathVariable("id") Integer id
  ) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    return "cate/read"; // templates/cate/read.html
  }

  @GetMapping("/update/{id}")
  public String update(
          Model model,
          @PathVariable("id") Integer id
  ){

    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);
//
//    System.out.println("title: " + categoryVO.getTitle());
//    System.out.println("artist: " + categoryVO.getArtist());
//    System.out.println("genre: " + categoryVO.getGenre());
//    System.out.println("visible: " + categoryVO.getVisible());

    return "cate/update";
  }

  @PostMapping("/update")
  public String update(Model model, @Valid CategoryVO categoryVO, BindingResult bindingResult) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    if (bindingResult.hasErrors()) {
      return "cate/update";
    }

    int cnt = this.cateProcInter.update(categoryVO);

    if (cnt == 1) {
//      model.addAttribute("code", Tool.UPDATE_SUCCESS);
//      model.addAttribute("title", categoryVO.getTitle());
      return "redirect:/cate/update/" + categoryVO.getId();
    } else {
      model.addAttribute("code", Tool.UPDATE_FAIL);
    }
//    System.out.println("cnt: " + cnt);
//    System.out.println("title: " + categoryVO.getTitle());
//    System.out.println("artist: " + categoryVO.getArtist());
//    System.out.println("genre: " + categoryVO.getGenre());



    return "cate/msg";
  }

  @GetMapping("/delete/{id}")
  public String delete(Model model, @PathVariable("id") Integer id) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    return "/cate/delete";

  }


  @PostMapping("/delete/{id}")
  public String delete_process(
          Model model,
          @PathVariable("id") Integer id
  ) {
    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    int cnt = this.cateProcInter.delete(id);

    if (cnt == 1) {
//      model.addAttribute("code", Tool.DELETE_SUCCESS);
      return "redirect:/cate/list_all";
    } else {
      model.addAttribute("code", Tool.DELETE_FAIL);
    }

    model.addAttribute("title", categoryVO.getTitle());
    model.addAttribute("artist", categoryVO.getArtist());
    model.addAttribute("cnt", cnt);

    return "/cate/msg";
  }


  // 우선순위 높임 , 10등 -> 1등
  // http://localhost:9091/update_seqno_forward/1
  @GetMapping("/update_seqno_forward/{id}")
  public String update_seqno_forward(Model model, @PathVariable("id") Integer id) {
    this.cateProcInter.update_seqno_forward(id);

    return "redirect:/cate/list_all";
  }

  // 우선순위 낮춤 , 1등 -> 10등
  // http://localhost:9091/update_seqno_backward/1
  @GetMapping("/update_seqno_backward/{id}")
  public String update_seqno_backward(Model model, @PathVariable("id") Integer id) {
    this.cateProcInter.update_seqno_backward(id);

    return "redirect:/cate/list_all";
  }


  @GetMapping("/update_visible_y/{id}")
  public String update_visible_y(
          Model model,
          @PathVariable("id") Integer id
  ) {
    this.cateProcInter.update_visible_y(id);
//    System.out.println("->update_visible_y");

    return "redirect:/cate/list_all";
  }

  @GetMapping("/update_visible_n/{id}")
  public String update_visible_n(
          Model model,
          @PathVariable("id") Integer id
  ) {
    this.cateProcInter.update_visible_n(id);
//    System.out.println("->update_visible_y");

    return "redirect:/cate/list_all";
  }


}


