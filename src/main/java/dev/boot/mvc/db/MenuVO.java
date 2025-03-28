package dev.boot.mvc.db;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MenuVO {
  private String genre;

  private ArrayList<CategoryVO> list_name;
}
