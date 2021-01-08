package cn.hjmao.auth.config.api;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
  @ExceptionHandler(ApiException.class)
  public ApiResponse<String> apiExceptionHandler(ApiException e) {
    return new ApiResponse<>(ApiResponse.ResponseCode.FAILED, e.getMsg());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResponse<String> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException e) {
    ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
    return new ApiResponse<>(
        ApiResponse.ResponseCode.VALIDATE_FAILED, objectError.getDefaultMessage()
    );
  }
}
