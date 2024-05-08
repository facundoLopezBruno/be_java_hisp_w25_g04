package org.example.be_java_hisp_w26_g04.service.buyer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.be_java_hisp_w26_g04.dto.BuyerDTO;
import org.example.be_java_hisp_w26_g04.exceptions.BadRequestException;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.buyer.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.example.be_java_hisp_w26_g04.service.util.UtilTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BuyerServiceTest {
    private Set<Seller> sellers;
    private Set<Buyer> buyers;
    @Mock
    private IBuyersRepository buyersRepository;
    @Mock
    private ISellerRepository sellerRepository;
    @InjectMocks
    private BuyerService buyerService;

    @BeforeEach
    public void setUp() throws IOException {
        sellers = UtilTest.getSellers();
        buyers = UtilTest.getBuyers();

    }

    @Test
    @DisplayName("T-0001 El usuario a seguir existe OK")
    void followSellerOkTest() {
        //Arrange
        int buyerId = 1;
        int sellerId = 2;

        Buyer buyer = new Buyer();
        buyer.setUserId(buyerId);
        buyer.setUserName("Comprador");

        Seller seller = new Seller();
        seller.setUserId(sellerId);
        seller.setUserName("Vendedor");

        when(buyersRepository.findById(buyerId)).thenReturn(Optional.of(buyer));

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));

        //Act
        buyerService.followSeller(buyerId, sellerId);

        //Asserts
        assertTrue(buyer.getSellersFollowing().contains(sellerId));

        assertTrue(seller.getFollowers().contains(buyerId));
    }

    @Test
    @DisplayName("T-0001 El usuario a seguir NO existe")
    void followSellerNotOkTest() {
        //Arrange
        int buyerId = 1;
        int sellerId = 9999;

        when(buyersRepository.findById(buyerId)).thenThrow(new BadRequestException());

        //Act & Assert
        Assertions.assertThrows(BadRequestException.class,
                () -> buyerService.followSeller(buyerId, sellerId)
        );
    }

    @Test
    void getFollowed() {
    }

    @Test
    @DisplayName("T-0002 El usuario a dejar de seguir existe")
    void unfollowSellerOkTest() {
        //Arrange
        int buyerId = 1;
        int sellerId = 2;

        Buyer buyer = new Buyer();
        buyer.setUserId(buyerId);
        buyer.setUserName("Comprador");

        Seller seller = new Seller();
        seller.setUserId(sellerId);
        seller.setUserName("Vendedor");

        when(buyersRepository.findById(buyerId)).thenReturn(Optional.of(buyer));

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));

        buyer.getSellersFollowing().add(sellerId);
        seller.getFollowers().add(buyerId);

        //Act
        buyerService.unfollowerSeller(buyerId, sellerId);

        //Assert
        Assertions.assertFalse(buyer.getSellersFollowing().contains(sellerId));
        Assertions.assertFalse(seller.getFollowers().contains(buyerId));
    }

    @Test
    @DisplayName("T-0002 El usuario a dejar de seguir NO existe")
    void unfollowSeller_UserNotExistsTest() {
        //Arrange
        int buyerId = 1;
        int sellerId = 999;
        Buyer buyer = new Buyer();
        buyer.setUserId(buyerId);

        when(buyersRepository.findById(buyerId)).thenThrow(new BadRequestException());

        //Act & Assert
        Assertions.assertThrows(BadRequestException.class,
                () -> buyerService.unfollowerSeller(buyerId, sellerId));
    }
    @Test
    @DisplayName("T-0004: Verifica el correcto ordenamiento ascendente por nombre de los seguidos de un buyer")
    void sortGetFollowedAsc() throws JsonProcessingException {
        int buyerId=456;
        int seller1=123;
        int seller2=234;
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        when(buyersRepository.findById(buyerId)).thenReturn(buyers.stream().filter(x-> x.getUserId()==buyerId)
                .findFirst());
        when(sellerRepository.findById(seller1)).thenReturn(sellers.stream().filter(x-> x.getUserId()==seller1)
                .findFirst());
        when(sellerRepository.findById(seller2)).thenReturn(sellers.stream().filter(x-> x.getUserId()==seller2)
                .findFirst());when(sellerRepository.findById(seller1)).thenReturn(sellers.stream().filter(x-> x.getUserId()==seller1)
                .findFirst());
        when(sellerRepository.findById(seller2)).thenReturn(sellers.stream().filter(x-> x.getUserId()==seller2)
                .findFirst());

        BuyerDTO expected= UtilTest.generateListFollowedAsc();
        //Act
        BuyerDTO result= buyerService.sortGetFollowed(buyerId, "name_asc");
        //Assert
        assertEquals(mapper.writeValueAsString(expected),mapper.writeValueAsString(result));
    }

    @Test
    @DisplayName("T-0004: Verifica el correcto ordenamiento descendente por nombre de los seguidos de un buyer")
    void sortGetFollowedDesc() throws JsonProcessingException {
        int buyerId=456;
        int seller1=123;
        int seller2=234;
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        when(buyersRepository.findById(buyerId)).thenReturn(buyers.stream().filter(x-> x.getUserId()==buyerId)
                .findFirst());
        when(sellerRepository.findById(seller1)).thenReturn(sellers.stream().filter(x-> x.getUserId()==seller1)
                .findFirst());
        when(sellerRepository.findById(seller2)).thenReturn(sellers.stream().filter(x-> x.getUserId()==seller2)
                .findFirst());

        BuyerDTO expected= UtilTest.generateListFollowedDesc();
        //Act
        BuyerDTO result= buyerService.sortGetFollowed(buyerId, "name_desc");
        //Assert
        assertEquals(mapper.writeValueAsString(expected),mapper.writeValueAsString(result));
    }

}