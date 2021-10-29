package cn.hjmao.auth.controller;

import cn.hjmao.auth.config.JwtSettings;
import cn.hjmao.auth.entity.AccountEntity;
import cn.hjmao.auth.entity.RoleEntity;
import cn.hjmao.auth.repository.AccountRepository;
import cn.hjmao.auth.repository.RoleRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private BCryptPasswordEncoder bcryptPasswordEncoder;

  @PostMapping(JwtSettings.SIGNUP_ROUTE_PATH)
  public AccountEntity registerUser(@RequestBody Map<String, String> registerUser) {
    AccountEntity user = new AccountEntity();
    user.setUsername(registerUser.get(JwtSettings.FORM_USERNAME));
    user.setPassword(bcryptPasswordEncoder.encode(registerUser.get(JwtSettings.FORM_PASSWORD)));
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    long createdAt = new Date().getTime();
    user.setCreateTime(createdAt);
    user.setUpdateTime(createdAt);
    String roleName = "ROLE_" + JwtSettings.DEFAULT_USER_ROLE_NAME;
    RoleEntity role = roleRepository.findByName(roleName);
    if (role == null) {
      role = new RoleEntity(roleName);
    }
    List<RoleEntity> roles = new ArrayList<>();
    roles.add(role);
    user.setRoles(roles);
    return accountRepository.save(user);
  }
}
