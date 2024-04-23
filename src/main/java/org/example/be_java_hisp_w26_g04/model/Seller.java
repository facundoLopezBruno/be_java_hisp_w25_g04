package org.example.be_java_hisp_w26_g04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    private int userId;
    private String userName;
    private List<Post> listPost;
    private List<Product> listProduct;
    private Set<Buyer> listFollowers = new HashSet<>();

    public boolean addFollower(Buyer buyer){
        return listFollowers.add(buyer);
    }
}
