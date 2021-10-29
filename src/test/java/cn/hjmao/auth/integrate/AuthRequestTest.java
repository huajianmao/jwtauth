package cn.hjmao.auth.integrate;

import static org.assertj.core.api.Assertions.assertThat;

import cn.hjmao.auth.config.JwtSettings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthRequestTest {
  @LocalServerPort
  private int port;

  @Value("${api.prefix}")
  private String apiPrefix;

  @Autowired
  private TestRestTemplate restTemplate;

  HttpHeaders headers = new HttpHeaders();

  @BeforeAll
  public void setup() {
    signup("hello", "world");
  }

  @Test
  void signinWithNoExistUserShouldFail() {
    ResponseEntity<String> response = signin("nonExist", "world");
    int actual = response.getStatusCode().value();
    assertThat(actual).isEqualTo(401);
    assertThat(response.getBody()).contains("1011").contains("Bad credentials");
  }

  @Test
  void signupNewUserShouldSuccess() {
    ResponseEntity<String> response = signup("newNonExist", "world");
    int actual = response.getStatusCode().value();
    assertThat(actual).isEqualTo(200);
    assertThat(response.getBody())
        .contains("\"code\":1000")
        .contains("\"username\":\"newNonExist\"")
        .contains("\"password\"");
  }

  @Test
  void signupExistUserShouldFail() {
    ResponseEntity<String> response = signup("hello", "world");
    int actual = response.getStatusCode().value();
    assertThat(actual).isEqualTo(200);
    assertThat(response.getBody())
        .contains("\"code\":5000")
        .contains("could not execute statement");
  }

  @Test
  void signinWithExistUserShouldSuccess() {
    ResponseEntity<String> response = signin("hello", "world");
    int actual = response.getStatusCode().value();
    assertThat(actual).isEqualTo(200);
    assertThat(response.getHeaders().containsKey("token")).isTrue();
  }

  @Test
  void fetchResourceWithoutSigninShouldFail() {
    assertThat(restTemplate.getForObject(createServiceUrlWithPort("/tasks/hello"), String.class))
        .contains("Full authentication is required to access this resource");
  }

  @Test
  void fetchResourceWithSigninShouldSuccess() {
    ResponseEntity<String> response = signin("hello", "world");
    List<String> tokens = response.getHeaders().get("token");
    assertThat(tokens).isNotNull().size().isEqualTo(1);
    headers.setBearerAuth(tokens.get(0).substring("Bearer ".length()));
    HttpEntity entity = new HttpEntity(headers);
    String url = createServiceUrlWithPort("/tasks/hello");
    response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody()).contains("\"code\":1000");
    headers.remove("Authorization");
  }

  private ResponseEntity<String> signup(String username, String password) {
    Map<String, String> map = new HashMap<>();
    map.put("username", username);
    map.put("password", password);
    HttpEntity<Map> entity = new HttpEntity<>(map, headers);
    return restTemplate.exchange(
      createAuthUrlWithPort(JwtSettings.SIGNUP_ROUTE_PATH),
      HttpMethod.POST, entity, String.class
    );
  }

  private ResponseEntity<String> signin(String username, String password) {
    Map<String, String> map = new HashMap<>();
    map.put("username", username);
    map.put("password", password);
    HttpEntity<Map> entity = new HttpEntity<>(map, headers);
    return restTemplate.exchange(
      createAuthUrlWithPort(JwtSettings.SIGNIN_ROUTE_PATH),
      HttpMethod.POST, entity, String.class
    );
  }

  private String createAuthUrlWithPort(String entryPoint) {
    return "http://localhost:" + port + entryPoint;
  }

  private String createServiceUrlWithPort(String entryPoint) {
    return "http://localhost:" + port + apiPrefix + entryPoint;
  }
}
