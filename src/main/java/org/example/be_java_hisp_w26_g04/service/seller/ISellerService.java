package org.example.be_java_hisp_w26_g04.service.seller;

import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;

public interface ISellerService {
    FollowersCountDTO findFollowers(int sellerId);
}
