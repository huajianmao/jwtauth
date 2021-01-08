package cn.hjmao.auth.config.jwt;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findByUsername(String username);
}
