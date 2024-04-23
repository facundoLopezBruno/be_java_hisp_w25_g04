package org.example.be_java_hisp_w26_g04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    private List<Seller> listSellers;
}
