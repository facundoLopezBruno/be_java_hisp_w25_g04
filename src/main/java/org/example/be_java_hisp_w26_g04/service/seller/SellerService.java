package org.example.be_java_hisp_w26_g04.service.seller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;


import java.util.*;

import lombok.RequiredArgsConstructor;
import org.example.be_java_hisp_w26_g04.dto.*;
import org.example.be_java_hisp_w26_g04.exceptions.BadRequestException;
import org.example.be_java_hisp_w26_g04.exceptions.NotFoundException;
import org.example.be_java_hisp_w26_g04.model.Post;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.buyer.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService implements ISellerService {

  @Autowired
  ISellerRepository sellerRepository;

  @Autowired
  IBuyersRepository buyerRepository;

  private final ObjectMapper objectMapper;

  @Override
  public FollowersCountDTO findFollowers(int sellerId) {
    Seller seller = sellerRepository.findById(sellerId).orElseThrow(NotFoundException::new);
    int followerCount = seller.getFollowers().size();

    FollowersCountDTO followersCountDTO = objectMapper.convertValue(seller, FollowersCountDTO.class);
    followersCountDTO.setFollowersCount(followerCount);

    return followersCountDTO;
  }

  @Override
  public SellerFollowersDTO getFollowers(int userId) {
    Seller seller = sellerRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("Seller with id " + userId + " not found"));

    return converSellerToSellerFollowersDto(seller);
  }

    private SellerFollowersDTO converSellerToSellerFollowersDto(Seller seller) {
        List<UserDto> followers = seller.getFollowers().stream()
                .map(x -> buyerRepository.findById(x)).filter(Optional::isPresent).map(x -> x.get())
                .map(follower -> new UserDto(follower.getUserId(), follower.getUserName()))
                .toList();

        return new SellerFollowersDTO(seller.getUserId(), seller.getUserName(), followers);
    }

  @Override
  public List<PostResponseDto> getPostsFromFollower(int userId) {
      Set<Seller> sellers = sellerRepository.findAll();
      List<PostResponseDto> posts = new ArrayList<>();
      for(Seller seller: sellers) {
          if(seller.getFollowers().contains(userId)) {
              for(Post post: seller.getListPost()) {
                  PostResponseDto postDto = objectMapper.convertValue(post, PostResponseDto.class);
                  posts.add(postDto);
              }
          }
      }
      posts.sort(Comparator.comparing(PostResponseDto::getDate));
      return posts;
  }

  @Override
    public boolean createNewPost(PostRequestDto post){
        Optional<Seller> optionalSeller = sellerRepository.findById(post.getUserId());
        if (optionalSeller.isEmpty()){
            throw new BadRequestException();
        }
      Post postFounded = objectMapper.convertValue(post, Post.class);
        return sellerRepository.save(postFounded);
    }
}
