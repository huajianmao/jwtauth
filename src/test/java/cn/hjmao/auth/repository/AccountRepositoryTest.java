package cn.hjmao.auth.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cn.hjmao.auth.entity.AccountEntity;
import cn.hjmao.auth.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AccountRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  void findByUsername() {
    // given
    AccountEntity user = new AccountEntity();
    user.setUsername("hjmao");
    entityManager.persist(user);
    entityManager.flush();

    // when
    AccountEntity found = accountRepository.findByUsername("hjmao");

    // then
    assertThat(found.getUsername()).isEqualTo(user.getUsername());
  }
}
