package org.example.be_java_hisp_w26_g04.repository.buyers.imp;

import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.repository.buyers.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.users.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BuyerRepositoryImp implements IBuyersRepository {
    @Autowired
    IUsersRepository usersRepository;

    @Override
    public Set<Buyer> findAll() {
        return usersRepository.findAll().stream().filter(u -> u instanceof Buyer)
                .map(u -> (Buyer) u) // Conversión a Buyer
                .collect(Collectors.toSet()); // Recolectar en un conjunto Set<Buyer>;
    }

    @Override
    public Optional<Buyer> findById(int id) {
        Set<Buyer> buyers = this.findAll();
        return buyers.stream().filter(b -> b.getUser_id() == id).findFirst();
    }

    @Override
    public boolean save(Buyer buyer) {
        return usersRepository.findAll().add(buyer);
    }
}
