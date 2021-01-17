package cn.hjmao.auth.config.jwt;

import static org.assertj.core.api.Assertions.assertThat;

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
class UserServiceTest {
  @TestConfiguration
  static class UserServiceImpl {
    @Bean
    public UserService userService() {
      return new UserService();
    }
  }

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Test
  void loadUserByUsername() {
    UserEntity user = new UserEntity();
    user.setUsername("hjmao");
    Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

    UserDetails found = userService.loadUserByUsername("hjmao");
    assertThat(found).isNotNull();
    assertThat(found.getUsername()).isEqualTo("hjmao");
  }
}
