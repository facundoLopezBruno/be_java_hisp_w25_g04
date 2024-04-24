package org.example.be_java_hisp_w26_g04.service.seller;

import org.example.be_java_hisp_w26_g04.exceptions.NotFoundException;
import org.example.be_java_hisp_w26_g04.model.Post;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.seller.SellersRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {
    private final SellersRepositoryImp sellersRepositoryImp;

    public SellerService(SellersRepositoryImp sellersRepositoryImp) {
        this.sellersRepositoryImp = sellersRepositoryImp;
    }

    public boolean createNewPost(Post post){
        Optional<Seller> optionalSeller = sellersRepositoryImp.findById(post.getUserId());
        if (optionalSeller.isEmpty()){
            throw new NotFoundException();
        }
        return sellersRepositoryImp.save(post);
    }
}
