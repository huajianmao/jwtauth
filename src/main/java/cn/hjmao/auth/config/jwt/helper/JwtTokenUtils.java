package cn.hjmao.auth.config.jwt.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JwtTokenUtils {
  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  private static final String SECRET = "jwt_secret";
  private static final String ISSUER = "jwt_token_issuer";
  private static final String ROLE_CLAIMS = "role";
  private static final long EXPIRATION_SECONDS = 3600L; // 1 hour
  private static final long REMEMBER_EXPIRATION_SECONDS = 604800L; // 7 days

  public static String createToken(String username, List<String> roles, boolean shouldRememberMe) {
    long expiration = shouldRememberMe ? REMEMBER_EXPIRATION_SECONDS : EXPIRATION_SECONDS;
    HashMap<String, Object> map = new HashMap<>();
    map.put(ROLE_CLAIMS, roles);
    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .setClaims(map)
        .setIssuer(ISSUER)
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
        .compact();
  }

  public static String getUsername(String token) {
    return getTokenBody(token).getSubject();
  }

  public static List<String> getUserRoles(String token) {
    return (List<String>) getTokenBody(token).get(ROLE_CLAIMS);
  }

  public static boolean isExpiration(String token) {
    try {
      return getTokenBody(token).getExpiration().before(new Date());
    } catch (ExpiredJwtException e) {
      return true;
    }
  }

  private static Claims getTokenBody(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token)
        .getBody();
  }
}
