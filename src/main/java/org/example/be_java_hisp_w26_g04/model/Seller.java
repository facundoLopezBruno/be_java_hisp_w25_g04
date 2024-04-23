package org.example.be_java_hisp_w26_g04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller extends User{
    private List<Post> listPost;
    private List<Product> listProduct;
    private List<Buyer>  listFollowers;
}
