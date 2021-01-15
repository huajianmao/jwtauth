package cn.hjmao.auth.config.api;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
  private final int code;
  private final String message;
  private final T data;

  public ApiResponse(T data) {
    this(ResponseCode.SUCCESS, data);
  }

  public ApiResponse(ResponseCode resultCode, T data) {
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
    this.data = data;
  }

  @Getter
  public enum ResponseCode {
    SUCCESS(1000, "操作成功"),
    FAILED(1001, "响应失败"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    NOT_FOUND(1003, "访问路径错误"),
    UNAUTHENTICATED(1011, "身份验证失败"),
    UNAUTHORIZED(1012, "未授权的访问"),
    ERROR(5000, "未知错误");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
      this.code = code;
      this.message = message;
    }
  }
}
