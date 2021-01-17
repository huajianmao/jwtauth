package cn.hjmao.auth.config.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  void findByUsername() {
    // given
    UserEntity user = new UserEntity();
    user.setUsername("hjmao");
    entityManager.persist(user);
    entityManager.flush();

    // when
    UserEntity found = userRepository.findByUsername("hjmao");

    // then
    assertThat(found.getUsername()).isEqualTo(user.getUsername());
  }
}
