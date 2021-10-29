package cn.hjmao.auth.config;

public class JwtSettings {
  private JwtSettings() {
    throw new IllegalStateException("Utility class");
  }

  public static final String FORM_USERNAME = "username";
  public static final String FORM_PASSWORD = "password";
  public static final String FORM_REMEMBER = "rememberMe";

  public static final String SIGNIN_ROUTE_PATH = "/auth/signin";
  public static final String SIGNUP_ROUTE_PATH = "/auth/signup";

  public static final String TABLE_NAME_USER = "auth_user";
  public static final String TABLE_NAME_ROLE = "auth_role";
  public static final String DEFAULT_USER_ROLE_NAME = "USER";
}
