package org.example.be_java_hisp_w26_g04.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private String typeProduct;
    private String brand;
    private String color;
    private String notes;
}
