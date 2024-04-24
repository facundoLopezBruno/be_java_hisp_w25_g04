package org.example.be_java_hisp_w26_g04.service.buyer;

import org.example.be_java_hisp_w26_g04.dto.BuyerDTO;
import org.example.be_java_hisp_w26_g04.dto.UserDto;
import org.example.be_java_hisp_w26_g04.exceptions.BadRequestException;
import org.example.be_java_hisp_w26_g04.exceptions.NotFoundException;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.buyer.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuyerService implements IBuyerService {

    @Autowired
    IBuyersRepository buyersRepository;
    @Autowired
    ISellerRepository sellerRepository;

    @Override
    public void followSeller(int buyerId, int sellerId) {
        Buyer buyer = buyersRepository.findById(buyerId).orElseThrow(RuntimeException::new);
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(RuntimeException::new);

        if (!buyer.addFollow(seller) || !seller.addFollower(buyer)) {
            throw new RuntimeException();
        }
    }

    @Override
    public BuyerDTO getById(int id) {

        if(buyersRepository.findById(id).isEmpty())
            throw new NotFoundException("No existe el id: "+id);
        Buyer buyer=buyersRepository.findById(id).get();
        List<Seller> sellerList=buyer.getListSellers().stream().toList();
        List<UserDto> userDtoList= new ArrayList<>();
        for(Seller seller: sellerList){
            userDtoList.add(new UserDto(seller.getUserId(), seller.getUserName() ));
        }

        return new BuyerDTO(buyer.getUserId(), buyer.getUserName(), userDtoList);
    }

    @Override
    public void unfollowerSeller(int userId, int userIdToUnfollow) {
        Optional<Buyer> buyer = buyersRepository.findById(userId);
        if (buyer.isEmpty()) throw new BadRequestException();

        Optional<Seller> seller = sellerRepository.findById(userIdToUnfollow);
        if (seller.isEmpty()) throw new BadRequestException();

        buyer.get().getListSellers().remove(seller.get());
        seller.get().getListFollowers().remove(buyer.get());
    }
}
