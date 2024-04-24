package org.example.be_java_hisp_w26_g04.service.seller;

import java.util.List;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDto;
import org.example.be_java_hisp_w26_g04.dto.UserDto;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService implements ISellerService {

  @Autowired
  ISellerRepository sellerRepository;

  @Override
  public SellerFollowersDto getFollowers(int userId) {
    // TODO: Create a coherent Exception to be thrown here
    Seller seller = sellerRepository.findById(userId).orElseThrow(RuntimeException::new);

    return converSellerToSellerFollowersDto(seller);
  }

  private SellerFollowersDto converSellerToSellerFollowersDto(Seller seller) {
    List<UserDto> followers = seller.getListFollowers().stream()
        .map(follower -> new UserDto(follower.getUserId(), follower.getUserName()))
        .toList();

    return new SellerFollowersDto(seller.getUserId(), seller.getUserName(), followers);
  }

}
