package cn.hjmao.auth.config.jwt.helper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {
    String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
    if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    try {
      SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
    } catch (Exception e) {
      JwtResponseUtils.unauthorized(response, e.getMessage());
      return;
    }

    super.doFilterInternal(request, response, chain);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader)
      throws Exception {
    String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
    boolean expiration = JwtTokenUtils.isExpiration(token);
    if (expiration) {
      throw new Exception("Token expired!");
    } else {
      String username = JwtTokenUtils.getUsername(token);
      List<String> roles = JwtTokenUtils.getUserRoles(token);
      if (username != null) {
        return new UsernamePasswordAuthenticationToken(username, null,
            roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet())
        );
      }
    }
    return null;
  }
}
