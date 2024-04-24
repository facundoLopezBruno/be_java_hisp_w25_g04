package org.example.be_java_hisp_w26_g04.repository.seller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Set;

@Repository
public class SellersRepositoryImp implements ISellerRepository {

    private Set<Seller> sellers;
    private int idCounter;

    public SellersRepositoryImp() throws IOException {
        populate();
    }
    @Override
    public Set<Seller> findAll() {
        return  sellers;
    }

    @Override
    public Optional<Seller> findById(int id) {
        return sellers.stream().filter(b -> b.getUserId() == id).findFirst();
    }

    @Override
    public boolean save(Seller seller) {
        idCounter++;
        seller.setUserId(idCounter);
        return sellers.add(seller);
    }

    private void populate() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        Resource resource = new ClassPathResource("data/seller.json");
        sellers = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }
}
