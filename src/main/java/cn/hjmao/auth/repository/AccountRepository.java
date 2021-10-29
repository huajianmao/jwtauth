package cn.hjmao.auth.repository;

import cn.hjmao.auth.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
  AccountEntity findByUsername(String username);
}
