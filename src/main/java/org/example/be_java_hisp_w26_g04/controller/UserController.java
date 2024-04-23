package org.example.be_java_hisp_w26_g04.controller;

import org.example.be_java_hisp_w26_g04.service.buyer.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
