package cn.hjmao.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix.v1}/tasks")
public class TestController {

  @RequestMapping("/hello")
  public String get() {
    return "/hello world!";
  }

  @RequestMapping("")
  public String getRoot() {
    return "/get task root!";
  }
}
