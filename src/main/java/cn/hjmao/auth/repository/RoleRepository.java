package cn.hjmao.auth.repository;

import cn.hjmao.auth.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
  RoleEntity findByName(String roleName);
}
