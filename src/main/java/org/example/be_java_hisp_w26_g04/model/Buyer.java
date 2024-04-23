package org.example.be_java_hisp_w26_g04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Buyer  {
    private int userId;
    private String userName;
    private Set<Seller> listSellers = new HashSet<>();

    public boolean addFollow(Seller seller){
        return listSellers.add(seller);
    }
}
