package org.example.be_java_hisp_w26_g04.exceptions;

import org.example.be_java_hisp_w26_g04.dto.BadResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> badRequest() {
    return ResponseEntity.badRequest().build();
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<BadResponseDTO> handleNotFoundException(NotFoundException ex) {
    BadResponseDTO badResponseDto = new BadResponseDTO(
        ex.getMessage(), HttpStatus.NOT_FOUND.value()
    );

    return new ResponseEntity<>(badResponseDto, HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errorMap = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errorMap.put(error.getField(), error.getDefaultMessage());
    });

    return ResponseEntity.badRequest().body(errorMap);
  }
}
