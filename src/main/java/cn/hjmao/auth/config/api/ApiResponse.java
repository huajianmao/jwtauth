package cn.hjmao.auth.config.api;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
  private int code;
  private String msg;
  private T data;

  public ApiResponse(T data) {
    this(ResponseCode.SUCCESS, data);
  }

  public ApiResponse(ResponseCode resultCode, T data) {
    this.code = resultCode.getCode();
    this.msg = resultCode.getMsg();
    this.data = data;
  }

  @Getter
  public enum ResponseCode {
    SUCCESS(1000, "操作成功"),
    FAILED(1001, "响应失败"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    UNAUTHENTICATED(1011, "身份验证失败"),
    UNAUTHORIZED(1012, "未授权的访问"),
    ERROR(5000, "未知错误");

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
      this.code = code;
      this.msg = msg;
    }
  }
}
