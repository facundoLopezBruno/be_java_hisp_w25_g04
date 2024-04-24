package org.example.be_java_hisp_w26_g04.controller;

import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDto;
import org.example.be_java_hisp_w26_g04.service.buyer.IBuyerService;
import org.example.be_java_hisp_w26_g04.service.seller.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  IBuyerService buyerService;

  @Autowired
  ISellerService sellerService;

  @PostMapping("/{userId}/follow/{userIdToFollow}")
  public ResponseEntity<?> follow(@PathVariable int userId, @PathVariable int userIdToFollow) {
    buyerService.followSeller(userId, userIdToFollow);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{userId}/followers/list")
  public ResponseEntity<SellerFollowersDto> getFollowers(@PathVariable int userId) {
    return ResponseEntity.ok().body(sellerService.getFollowers(userId));
  }
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<FollowersCountDTO> getFollowersCount(@PathVariable int userId) {
        return ResponseEntity.ok(sellerService.findFollowers(userId));
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<?> getSellerList(@PathVariable int userId){
        Optional<Buyer> buyer= buyerService.getById(userId);
        if(buyer.isPresent()){
            return new ResponseEntity<>(buyer.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No se encuentra el usuario con id: "+userId, HttpStatus.NOT_FOUND);//return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        buyerService.unfollowerSeller(userId, userIdToUnfollow);
        return ResponseEntity.ok().build();
    }
}
