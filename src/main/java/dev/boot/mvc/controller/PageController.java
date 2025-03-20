package dev.boot.mvc.controller;

import dev.boot.mvc.db.CateDAOInter;
import dev.boot.mvc.db.CategoryVO;
import dev.boot.mvc.tool.Tool;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/cate")
public class PageController {

  @Autowired
  private CateDAOInter cateDAOInter;


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

    int cnt = this.cateDAOInter.create(categoryVO);

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
    ArrayList<CategoryVO> list = this.cateDAOInter.list_all();
    model.addAttribute("list", list);

    return "/cate/list_all";

  }
}
