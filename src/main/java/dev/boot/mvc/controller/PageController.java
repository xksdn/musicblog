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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  public String create(
          Model model,
          @Valid CategoryVO categoryVO,
          BindingResult bindingResult,
          @RequestParam(name = "word", defaultValue = "") String word,
          RedirectAttributes ra
  ) {
    if (bindingResult.hasErrors()) {
      return "/cate/create";
    } else {
      int cnt = this.cateProcInter.create(categoryVO);

      if (cnt == 1) {
        ra.addAttribute("word", word);
        return "redirect:/cate/list_search";
      } else {
        model.addAttribute("code", "create_fail");
        model.addAttribute("cnt", cnt);
        return "/cate/msg";
      }
    }
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

  @GetMapping("/list_search")
  public String list_search(
          Model model,
          @ModelAttribute("categoryVO") CategoryVO categoryVO,
          @RequestParam(name = "word", defaultValue = "") String word
  ) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_search(word);
    model.addAttribute("list", list);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    model.addAttribute("word", word);

    int list_search_count = this.cateProcInter.list_search_count(word);
    model.addAttribute("list_search_count", list_search_count);

    return "cate/list_search";

  }



  // 조회
  // http://localhost:9092/cate/read?cateno=1
  // http://localhost:9092/cate/read/1
  @GetMapping("/read/{id}")
  public String read(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word
  ) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    ArrayList<CategoryVO> list1 = this.cateProcInter.list_search(word);
    model.addAttribute("list1", list1);

    int list_search_count = this.cateProcInter.list_search_count(word);
    model.addAttribute("list_search_count", list_search_count);

    return "cate/read"; // templates/cate/read.html
  }

  @GetMapping("/update/{id}")
  public String update(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word
  ){

    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    ArrayList<CategoryVO> list1 = this.cateProcInter.list_search(word);
    model.addAttribute("word", word);
//
//    System.out.println("title: " + categoryVO.getTitle());
//    System.out.println("artist: " + categoryVO.getArtist());
//    System.out.println("genre: " + categoryVO.getGenre());
//    System.out.println("visible: " + categoryVO.getVisible());

    return "cate/update";
  }

  @PostMapping("/update")
  public String update(Model model,
                       @Valid CategoryVO categoryVO,
                       BindingResult bindingResult,
                       @RequestParam(name = "word", defaultValue = "") String word,
                       RedirectAttributes ra
  ) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    if (bindingResult.hasErrors()) {
      return "cate/update";
    } else {
      int cnt = this.cateProcInter.update(categoryVO);

      if (cnt == 1) {
        ra.addAttribute("word", word);
        return "redirect:/cate/update/" + String.valueOf(categoryVO.getId());
      } else {
        model.addAttribute("code", Tool.UPDATE_FAIL);
        model.addAttribute("cnt", cnt);
        return "cate/msg";
      }
    }
  }

  @GetMapping("/delete/{id}")
  public String delete(Model model,
                       @PathVariable("id") Integer id,
                       @RequestParam(name = "word", defaultValue = "") String word
  ) {
    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    ArrayList<CategoryVO> list1 = this.cateProcInter.list_search(word);
    model.addAttribute("word", word);

    return "/cate/delete";

  }


  @PostMapping("/delete/{id}")
  public String delete_process(
          Model model,
          @PathVariable("id") Integer id,
          @RequestParam(name = "word", defaultValue = "") String word,
          RedirectAttributes ra
  ) {
    CategoryVO categoryVO = this.cateProcInter.read(id);
    model.addAttribute("categoryVO", categoryVO);

    ArrayList<CategoryVO> list = this.cateProcInter.list_all();
    model.addAttribute("list", list);

    int cnt = this.cateProcInter.delete(id);

    if (cnt == 1) {
      ra.addAttribute("word", word);
      return "redirect:/cate/list_all";
    } else {
      model.addAttribute("code", Tool.DELETE_FAIL);

      model.addAttribute("title", categoryVO.getTitle());
      model.addAttribute("artist", categoryVO.getArtist());
      model.addAttribute("cnt", cnt);

      return "/cate/msg";
    }
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


