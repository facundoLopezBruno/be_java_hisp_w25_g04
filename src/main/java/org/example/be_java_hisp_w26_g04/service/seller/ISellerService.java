package org.example.be_java_hisp_w26_g04.service.seller;

import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDTO;
import org.example.be_java_hisp_w26_g04.model.Post;

public interface ISellerService {
  SellerFollowersDTO getFollowers(int userId);

  FollowersCountDTO findFollowers(int sellerId);

  boolean createNewPost(Post post);
}
