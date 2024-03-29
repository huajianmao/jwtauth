package cn.hjmao.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import cn.hjmao.auth.entity.AccountEntity;
import cn.hjmao.auth.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {
  @TestConfiguration
  static class UserServiceImpl {
    @Bean
    public AccountService userService() {
      return new AccountService();
    }
  }

  @Autowired
  private AccountService accountService;

  @MockBean
  private AccountRepository accountRepository;

  @Test
  void loadUserByUsername() {
    AccountEntity user = new AccountEntity();
    user.setUsername("hjmao");
    Mockito.when(accountRepository.findByUsername(user.getUsername())).thenReturn(user);

    UserDetails found = accountService.loadUserByUsername("hjmao");
    assertThat(found).isNotNull();
    assertThat(found.getUsername()).isEqualTo("hjmao");
  }
}
