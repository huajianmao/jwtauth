package cn.hjmao.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/tasks")
public class TestController {

  @GetMapping("/hello")
  public String get() {
    return "/hello world!";
  }

  @GetMapping("")
  public String getRoot() {
    return "/get task root!";
  }
}
