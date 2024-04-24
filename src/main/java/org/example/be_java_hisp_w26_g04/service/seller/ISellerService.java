package org.example.be_java_hisp_w26_g04.service.seller;

import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;
import org.example.be_java_hisp_w26_g04.dto.PostDto;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDto;

import java.util.List;

public interface ISellerService {
  SellerFollowersDto getFollowers(int userId);

  FollowersCountDTO findFollowers(int sellerId);

  List<PostDto> getPostsFromFollower(int userId);
}
