package dev.boot.mvc.controller;

import dev.boot.mvc.db.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/cate")
public class PageController {

  @GetMapping("/")
  public String page() {
    return "index";
  }

  @GetMapping("/create")
  public String create(@ModelAttribute("categoryVO")CategoryVO categoryVO) {
    System.out.println("-> http://localhost:9092/cate/create");
    return "/cate/create";
  }
}
