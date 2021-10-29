package cn.hjmao.auth;

import static org.assertj.core.api.Assertions.assertThat;

import cn.hjmao.auth.controller.AccountController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtApplicationTests {
  @Autowired
  private AccountController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }
}
