package org.example.be_java_hisp_w26_g04.repository.sellers.imp;

import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.sellers.ISellerRepository;
import org.example.be_java_hisp_w26_g04.repository.users.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class SellersRepositoryImp implements ISellerRepository {
    @Autowired
    IUsersRepository usersRepository;

    @Override
    public Set<Seller> findAll() {
        return  usersRepository.findAll().stream().filter(u -> u instanceof Seller)
                .map(u -> (Seller) u) // Conversión a Seller
                .collect(Collectors.toSet()); // Recolectar en un conjunto Set<Seller>;
    }

    @Override
    public Optional<Seller> findById(int id) {
        Set<Seller> Sellers = this.findAll();
        return Sellers.stream().filter(b -> b.getUser_id() == id).findFirst();
    }

    @Override
    public boolean save(Seller Seller) {
        return usersRepository.findAll().add(Seller);
    }
}
