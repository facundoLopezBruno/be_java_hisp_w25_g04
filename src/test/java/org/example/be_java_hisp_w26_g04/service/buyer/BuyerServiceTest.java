package org.example.be_java_hisp_w26_g04.service.buyer;

import org.example.be_java_hisp_w26_g04.exceptions.BadRequestException;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.buyer.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerServiceTest {
    @Mock
    private IBuyersRepository buyersRepository;
    @Mock
    private ISellerRepository sellerRepository;
    @InjectMocks
    private BuyerService buyerService;

    @Test
    void followSellerOkTest() {
        //Arrange
        int buyerId = 1;
        int sellerId= 2;

        Buyer buyer = new Buyer();
        buyer.setUserId(buyerId);
        buyer.setUserName("Comprador");

        Seller seller = new Seller();
        seller.setUserId(sellerId);
        seller.setUserName("Vendedor");

        when(buyersRepository.findById(buyerId)).thenReturn(Optional.of(buyer));

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));

        //Act
        buyerService.followSeller(buyerId,sellerId);

        //Asserts
        assertTrue(buyer.getSellersFollowing().contains(sellerId));

        assertTrue(seller.getFollowers().contains(buyerId));
    }

    @Test
    void followSellerNotOkTest(){
        int buyerId = 1;
        int sellerId = 9999;

        when(buyersRepository.findById(buyerId)).thenThrow(new BadRequestException());

        Assertions.assertThrows(BadRequestException.class ,()->
            buyerService.followSeller(buyerId,sellerId)
        );
    }

    @Test
    void getFollowed() {
    }

    @Test
    void unfollowerSeller() {
    }

    @Test
    void sortGetFollowed() {
    }
}