package cn.hjmao.auth.config.jwt;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
  RoleEntity findByName(String roleName);
}
