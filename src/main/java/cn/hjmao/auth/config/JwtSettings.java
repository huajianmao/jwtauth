package cn.hjmao.auth.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class JwtSettings {
  public static String FORM_USERNAME = "username";
  public static String FORM_PASSWORD = "password";
  public static String FORM_REMEMBER = "rememberMe";

  public static final String SIGNIN_ROUTE_PATH = "/auth/signin";
  public static final String SIGNUP_ROUTE_PATH = "/auth/signup";

  public static final String TABLE_NAME_USER = "auth_user";
  public static final String TABLE_NAME_ROLE = "auth_role";
  public static final String DEFAULT_USER_ROLE_NAME = "USER";

  public static void setAuthPaths(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
        .antMatchers("/api/**").authenticated()
        .anyRequest().permitAll();
  }
}
