package cn.hjmao.auth.config.api;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
  private int code;
  private String msg;

  public ApiException() {
    this(1001, "Api Exception");
  }

  public ApiException(String msg) {
    this(1001, msg);
  }

  public ApiException(int code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }
}