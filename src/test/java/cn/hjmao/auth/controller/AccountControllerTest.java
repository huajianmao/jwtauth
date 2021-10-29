package cn.hjmao.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.hjmao.auth.config.JwtSettings;
import cn.hjmao.auth.config.jwt.JwtSecurityConfig;
import cn.hjmao.auth.controller.AccountController;
import cn.hjmao.auth.repository.AccountRepository;
import cn.hjmao.auth.repository.RoleRepository;
import cn.hjmao.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
@Import(JwtSecurityConfig.class)

class AccountControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;
  @MockBean
  private AccountRepository accountRepository;
  @MockBean
  private RoleRepository roleRepository;

  @BeforeEach
  void setUp() {
  }

  @Test
  void register() throws Exception {
    ResultActions actions = mvc
        .perform(get(JwtSettings.SIGNUP_ROUTE_PATH).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(5000))
        .andExpect(jsonPath("$.data").value("Request method 'GET' not supported"));
  }
}
