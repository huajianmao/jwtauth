package cn.hjmao.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @RequestMapping("/tasks/hello")
  public String get() {
    return "/hello world!";
  }

  @RequestMapping("/")
  public String getRoot() {
    return "/get root!";
  }
}
