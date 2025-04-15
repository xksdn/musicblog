package dev.boot.mvc.controller;

import dev.boot.mvc.db.MenuVO;
import dev.boot.mvc.service.CateProcInter;
import dev.boot.mvc.tool.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

  @Autowired
  private CateProcInter cateProcInter;

  @Autowired
  private Security security;

  @GetMapping("/")
  public String page(Model model) {

    if (this.security != null) {
      System.out.println("-> 객체 고유 코드: " + security.hashCode());
      System.out.println(security.aesEncode("1234"));
    }

    ArrayList<MenuVO> menu = this.cateProcInter.menu();
    model.addAttribute("menu", menu);
    model.addAttribute("word", "");

    return "index";
  }

}
