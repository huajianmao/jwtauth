package cn.hjmao.auth.config.jwt.helper;

import cn.hjmao.auth.config.JwtSettings;
import cn.hjmao.auth.config.jwt.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
  private final AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    super.setFilterProcessesUrl(JwtSettings.SIGNIN_ROUTE_PATH);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, String> parameters = mapper.readValue(request.getInputStream(), Map.class);
      String username = parameters.getOrDefault(JwtSettings.FORM_USERNAME, "");
      String password = parameters.getOrDefault(JwtSettings.FORM_PASSWORD, "");
      boolean remember = Boolean.parseBoolean(
          parameters.getOrDefault(JwtSettings.FORM_REMEMBER, "false")
      );
      rememberMe.set(remember);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>())
      );
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) {
    UserEntity user = (UserEntity) authResult.getPrincipal();

    List<String> roles = new ArrayList<>();
    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
    for (GrantedAuthority authority : authorities) {
      roles.add(authority.getAuthority());
    }
    String token = JwtTokenUtils.createToken(user.getUsername(), roles, this.rememberMe.get());
    this.rememberMe.remove();
    response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException e) throws IOException {
    JwtResponseUtils.unauthenticated(response, e.getMessage());
  }
}
