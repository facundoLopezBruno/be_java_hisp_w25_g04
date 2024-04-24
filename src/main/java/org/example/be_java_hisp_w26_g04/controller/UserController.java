package org.example.be_java_hisp_w26_g04.controller;

import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.service.buyer.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IBuyerService buyerService;

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> follow(@PathVariable int userId, @PathVariable int userIdToFollow) {
        buyerService.followSeller(userId, userIdToFollow);
        return ResponseEntity.ok().build();
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
