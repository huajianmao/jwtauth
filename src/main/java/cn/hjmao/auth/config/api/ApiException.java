package cn.hjmao.auth.config.api;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
  private final int code;
  private final String message;

  public ApiException(String message) {
    this(1001, message);
  }

  public ApiException(int code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }
}
