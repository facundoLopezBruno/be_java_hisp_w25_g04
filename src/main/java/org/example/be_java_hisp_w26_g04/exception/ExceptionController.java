package org.example.be_java_hisp_w26_g04.exception;

import org.example.be_java_hisp_w26_g04.dto.BadResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
 @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<BadResponseDto> handleNotFoundException(NotFoundException ex) {
   BadResponseDto badResponseDto = new BadResponseDto(
        ex.getMessage(), HttpStatus.NOT_FOUND.value()
    );

    return new ResponseEntity<>(badResponseDto, HttpStatus.NOT_FOUND);
  }
}
