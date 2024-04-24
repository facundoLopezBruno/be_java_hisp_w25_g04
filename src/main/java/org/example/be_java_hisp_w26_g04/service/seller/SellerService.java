package org.example.be_java_hisp_w26_g04.service.seller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDto;
import org.example.be_java_hisp_w26_g04.dto.UserDto;
import org.example.be_java_hisp_w26_g04.exceptions.NotFoundException;
import org.example.be_java_hisp_w26_g04.model.Post;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SellerService implements ISellerService {

  @Autowired
  ISellerRepository sellerRepository;

  private final ObjectMapper objectMapper;

  @Override
  public FollowersCountDTO findFollowers(int sellerId) {
    Seller seller = sellerRepository.findById(sellerId).orElseThrow(NotFoundException::new);
    int followerCount = seller.getListFollowers().size();

    FollowersCountDTO followersCountDTO = objectMapper.convertValue(seller, FollowersCountDTO.class);
    followersCountDTO.setFollowersCount(followerCount);

    return followersCountDTO;
  }

  @Override
  public SellerFollowersDto getFollowers(int userId) {
    Seller seller = sellerRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("Seller with id " + userId + " not found"));

    return converSellerToSellerFollowersDto(seller);
  }

  private SellerFollowersDto converSellerToSellerFollowersDto(Seller seller) {
    List<UserDto> followers = seller.getListFollowers().stream()
        .map(follower -> new UserDto(follower.getUserId(), follower.getUserName()))
        .toList();

    return new SellerFollowersDto(seller.getUserId(), seller.getUserName(), followers);
  }
  @Override
    public boolean createNewPost(Post post){
        Optional<Seller> optionalSeller = sellerRepository.findById(post.getUserId());
        if (optionalSeller.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return sellerRepository.save(post);
    }
}
