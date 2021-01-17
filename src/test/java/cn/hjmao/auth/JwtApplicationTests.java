package cn.hjmao.auth;

import cn.hjmao.auth.config.jwt.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtApplicationTests {
  @Autowired
  private UserController controller;

  @Test
  void contextLoads() {
    assert controller != null;
  }
}
