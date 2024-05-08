package org.example.be_java_hisp_w26_g04.service.seller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.be_java_hisp_w26_g04.dto.PostResponseDTO;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDTO;
import org.example.be_java_hisp_w26_g04.exceptions.BadRequestException;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Post;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SellerServiceTest {

    private Set<Seller> sellers;
    private Set<Buyer> buyers;

    @Mock
    ISellerRepository sellerRepository;

    @Mock
    IBuyersRepository buyerRepository;

    @InjectMocks
    SellerService service;


    @BeforeEach
    public void setUp() throws IOException {
        sellers = UtilTest.getSellers();
        buyers = UtilTest.getBuyers();

    }

    @Test
    void findFollowers() {
    }

    @Test
    void getFollowers() {
    }

    @Test
    @DisplayName("T-0003: Check if name_asc ordering exist")
    void sortGetFollowersAscExist() {
        Assertions.assertDoesNotThrow(() -> service.sortGetFollowers(123, "name_asc"));
    }

    @Test
    @DisplayName("T-0003: Check if name_desc ordering exist")
    void sortGetFollowersDescExist() {
        Assertions.assertDoesNotThrow(() -> service.sortGetFollowers(123, "name_desc"));
    }

    @Test
    @DisplayName("T-0003: check if an invalid order param in name ordering throw exception ")
    void sortGetFollowersIvalidNameORdering() {
        Assertions.assertThrows(BadRequestException.class, () -> service.sortGetFollowers(123, "invalid_order_type"));
    }



    @Test
    @DisplayName("T-0006: Verificar el correcto ordenamiento ascendente por fecha")
    void sortGetPostFromFollowerAsc() throws JsonProcessingException {
        //Arrange
        int buyerId = 456;
        //Armamos un listado con todos los post de los vendedores
        List<Post> posts = sellers.stream().flatMap(x -> x.getListPost().stream()).toList();

        when(buyerRepository.findById(buyerId)).thenReturn(
                buyers.stream().filter(b -> b.getUserId() == buyerId)
                        .findFirst()
        );

        when(sellerRepository.getPosts()).thenReturn(
                posts
        );

        List<PostResponseDTO> expected = UtilTest.generatePostResponseDTOAsc();

        //Act
        List<PostResponseDTO> result = service.sortGetPostFromFollower(buyerId, "date_asc");

        //Assert
        sortGetPostFromFollower(expected, result);
    }



    @Test
    @DisplayName("T-0006: Verificar el correcto ordenamiento descendente por fecha")
    void sortGetPostFromFollowerDesc() throws JsonProcessingException {
        //Arrange
        int buyerId = 456;
        //Armamos un listado con todos los post de los vendedores
        List<Post> posts = sellers.stream().flatMap(x -> x.getListPost().stream()).toList();

        when(buyerRepository.findById(buyerId)).thenReturn(
                buyers.stream().filter(b -> b.getUserId() == buyerId)
                        .findFirst()
        );

        when(sellerRepository.getPosts()).thenReturn(
                posts
        );

        List<PostResponseDTO> expected = UtilTest.generatePostResponseDTODesc();

        //Act
        List<PostResponseDTO> result = service.sortGetPostFromFollower(buyerId, "date_desc");

        //Assert
        sortGetPostFromFollower(expected, result);
    }


    private void sortGetPostFromFollower(List<PostResponseDTO> expected, List<PostResponseDTO> result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Assertions.assertEquals(mapper.writeValueAsString(expected), mapper.writeValueAsString(result));
    }


    @Test
    void createNewPost() {
    }
}