package org.example.be_java_hisp_w26_g04.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"user_id", "user_name", "followers"})
public class SellerFollowersDto {
  @JsonProperty("user_id")
  int id;

  @JsonProperty("user_name")
  String username;

  List<UserDto> followers;
}
