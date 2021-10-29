package cn.hjmao.auth.service;

import cn.hjmao.auth.entity.AccountEntity;
import cn.hjmao.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
  @Autowired
  AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    AccountEntity user = accountRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found!");
    }
    return user;
  }
}
