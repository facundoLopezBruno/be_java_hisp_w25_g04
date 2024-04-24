package org.example.be_java_hisp_w26_g04.service.seller;

import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDto;
import org.example.be_java_hisp_w26_g04.model.Post;

public interface ISellerService {
  SellerFollowersDto getFollowers(int userId);

  FollowersCountDTO findFollowers(int sellerId);

  boolean createNewPost(Post post);
}
