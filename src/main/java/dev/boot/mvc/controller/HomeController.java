package dev.boot.mvc.controller;

import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.service.CateProcInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

  @Autowired
  private CateProcInter cateProcInter;

  @GetMapping("/")
  public String page(Model model) {
    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);

    return "index";
  }

}
