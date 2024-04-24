package org.example.be_java_hisp_w26_g04.controller;

import org.example.be_java_hisp_w26_g04.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post){

    }

}
