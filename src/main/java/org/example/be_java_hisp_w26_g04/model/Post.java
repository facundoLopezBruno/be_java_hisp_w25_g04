package org.example.be_java_hisp_w26_g04.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private int idPost;
    private LocalDate date;
    Product product;
    private int category;
    private double price;

    @JsonAlias({"user_id","userId"})
    private int userId;
}
