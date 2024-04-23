package org.example.be_java_hisp_w26_g04.service.buyer;

import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.buyer.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerService implements IBuyerService{

    @Autowired
    IBuyersRepository buyersRepository;
    @Autowired
    ISellerRepository sellerRepository;

    @Override
    public void followSeller(int buyerId, int sellerId) {
        Buyer buyer = buyersRepository.findById(buyerId).orElseThrow(RuntimeException::new);
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(RuntimeException::new);

        if(!buyer.addFollow(seller) || !seller.addFollower(buyer)) {
            throw new RuntimeException();
        }
    }
}
