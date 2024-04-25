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

    @Override
    public SellerFollowersDTO sortGetFollowers(int userId, String order) {
      SellerFollowersDTO sellerFollowersDTO= getFollowers(userId);
      if(order.equals("name_asc")){
          sellerFollowersDTO.getFollowers().sort(Comparator.comparing(UserDTO::getUsername));
      } else if (order.equals("name_desc")) {
          sellerFollowersDTO.getFollowers().sort(Comparator.comparing(UserDTO::getUsername).reversed());

      }
      else{
          throw new BadRequestException();
      }
        return sellerFollowersDTO;
    }

    private SellerFollowersDTO converSellerToSellerFollowersDto(Seller seller) {
        List<UserDTO> followers = seller.getFollowers().stream()
                .map(x -> buyerRepository.findById(x)).filter(Optional::isPresent).map(x -> x.get())
                .map(follower -> new UserDTO(follower.getUserId(), follower.getUserName()))
                .toList();

        return new SellerFollowersDTO(seller.getUserId(), seller.getUserName(), followers);
    }

  @Override
  public List<PostResponseDTO> getPostsFromFollower(int userId) {
      Set<Seller> sellers = sellerRepository.findAll();
      List<PostResponseDTO> posts = new ArrayList<>();
      for(Seller seller: sellers) {
          if(seller.getFollowers().contains(userId)) {
              for(Post post: seller.getListPost()) {
                  PostResponseDTO postDto = objectMapper.convertValue(post, PostResponseDTO.class);
                  posts.add(postDto);
              }
          }
      }
      posts.sort(Comparator.comparing(PostResponseDTO::getDate));
      return posts;
  }

    @Override
    public List<PostResponseDTO> sortGetPostFromFollower(int userId, String order) {
        List<PostResponseDTO> ListPostDTO= getPostsFromFollower(userId);
        if (order.contains("date_asc")) {
            ListPostDTO.sort(Comparator.comparing(PostResponseDTO::getDate));
        } else if (order.contains("date_desc")) {
                     ListPostDTO.sort(Comparator.comparing(PostResponseDTO::getDate).reversed());
        }
        else {throw new BadRequestException();}
        return ListPostDTO;
    }

    @Override
    public boolean createNewPost(PostRequestDTO post){
        Optional<Seller> optionalSeller = sellerRepository.findById(post.getUserId());
        if (optionalSeller.isEmpty()){
            throw new BadRequestException();
        }
      Post postFounded = objectMapper.convertValue(post, Post.class);
        return sellerRepository.save(postFounded);
    }
}
