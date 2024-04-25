package org.example.be_java_hisp_w26_g04.controller;

import org.example.be_java_hisp_w26_g04.dto.PostRequestDTO;
import org.example.be_java_hisp_w26_g04.dto.PostResponseDTO;
import org.example.be_java_hisp_w26_g04.service.seller.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ISellerService sellerService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDTO post){
        if(sellerService.createNewPost(post)){
            return ResponseEntity.ok().build();
        } else{
           return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<List<PostResponseDTO>> getPostsFromFollower(
            @PathVariable int userId,
            @RequestParam(required = false, value = "order") String order
    ) {
        if (order != null) {
            return ResponseEntity.ok().body(sellerService.sortGetPostFromFollower(userId, order));
        } else {
            return ResponseEntity.ok().body(sellerService.getPostsFromFollower(userId));
        }
    }

}
