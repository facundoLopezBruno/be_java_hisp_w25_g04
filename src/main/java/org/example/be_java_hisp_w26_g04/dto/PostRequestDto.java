package org.example.be_java_hisp_w26_g04.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("category")
    private int category;
    @JsonProperty("price")
    private double price;
    @JsonProperty("product")
    private ProductDto product;
}
