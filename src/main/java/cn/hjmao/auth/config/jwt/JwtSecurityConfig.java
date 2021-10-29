package cn.hjmao.auth.config.jwt;

import cn.hjmao.auth.config.jwt.helper.JwtAccessDeniedHandler;
import cn.hjmao.auth.config.jwt.helper.JwtAuthenticationEntryPoint;
import cn.hjmao.auth.config.jwt.helper.JwtAuthenticationFilter;
import cn.hjmao.auth.config.jwt.helper.JwtAuthorizationFilter;
import cn.hjmao.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
  private final AccountService accountService;

  @Value("${api.prefix}")
  private String apiPrefix;

  public JwtSecurityConfig(AccountService accountService) {
    this.accountService = accountService;
  }

  @Bean
  public BCryptPasswordEncoder bcryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(accountService).passwordEncoder(bcryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().cors();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        .accessDeniedHandler(new JwtAccessDeniedHandler());

    http.antMatcher("/**")
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/**").denyAll()
        .antMatchers(HttpMethod.DELETE, apiPrefix + "/**").hasRole("ADMIN")
        .antMatchers(apiPrefix + "/**").authenticated()
        .anyRequest().permitAll()
            .and().headers().frameOptions().disable();

    http.addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtAuthorizationFilter(authenticationManager()));
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(HttpMethod.GET,
        "/favicon.ico",
        "/**/*.png",
        "/**/*.ttf",
        "/*.html",
        "/**/*.css",
        "/**/*.js");
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
