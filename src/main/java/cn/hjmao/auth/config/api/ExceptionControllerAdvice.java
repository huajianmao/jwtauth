package cn.hjmao.auth.config.api;

import cn.hjmao.auth.config.api.annotation.ExceptionCode;
import java.lang.reflect.Field;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
  @ExceptionHandler(ApiException.class)
  public ApiResponse<String> apiExceptionHandler(ApiException e) {
    return new ApiResponse<>(ApiResponse.ResponseCode.FAILED, e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResponse<String> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException e) throws NoSuchFieldException {
    String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

    Class<?> parameterType = e.getParameter().getParameterType();
    FieldError error = e.getBindingResult().getFieldError();
    if (error != null) {
      Field field = parameterType.getDeclaredField(error.getField());
      ExceptionCode annotation = field.getAnnotation(ExceptionCode.class);
      if (annotation != null) {
        return new ApiResponse<>(annotation, message);
      }
    }
    return new ApiResponse<>(ApiResponse.ResponseCode.VALIDATE_FAILED, message);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ApiResponse<String> noHandlerFoundException(NoHandlerFoundException e) {
    return new ApiResponse<>(ApiResponse.ResponseCode.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ApiResponse<String> handleException(Exception e) {
    return new ApiResponse<>(ApiResponse.ResponseCode.ERROR, e.getMessage());
  }
}
