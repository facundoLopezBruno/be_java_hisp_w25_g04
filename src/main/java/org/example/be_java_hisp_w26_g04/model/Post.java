package org.example.be_java_hisp_w26_g04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Post {
    private int idPost;
    private int userId;
    private LocalDate date;
    Product product;
    private int category;
    private double price;
}
