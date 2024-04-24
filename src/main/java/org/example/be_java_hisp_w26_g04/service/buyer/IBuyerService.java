package org.example.be_java_hisp_w26_g04.service.buyer;

import org.example.be_java_hisp_w26_g04.model.Buyer;

import java.util.Optional;

public interface IBuyerService {
    void followSeller(int buyerId, int sellerId);
    Optional<Buyer> getById(int id);
    void unfollowerSeller(int userId, int userIdToUnfollow);
}
