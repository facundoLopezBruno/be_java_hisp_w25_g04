package org.example.be_java_hisp_w26_g04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BadResponseDto {
  String message;
  int status;
}
