package org.example.be_java_hisp_w26_g04.service.seller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.be_java_hisp_w26_g04.dto.PostResponseDTO;
import org.example.be_java_hisp_w26_g04.exceptions.BadRequestException;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Post;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.buyer.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.example.be_java_hisp_w26_g04.service.util.UtilTest;
import org.example.be_java_hisp_w26_g04.util.crud.exceptionsHandler.ObjectExist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    SellerService service;

    private int buyerId;
    private List<Post> posts;
    private int sellerId;


    @BeforeEach
    public void setup() throws IOException {
        sellers = UtilTest.getSellers();
        buyers = UtilTest.getBuyers();
        sellerId = 123;
        buyerId = 456;
        posts  = new ArrayList<>();
        sellers.forEach(x ->
                posts.addAll(x.getListPost())
        );
        when(buyerRepository.findById(buyerId)).thenReturn(
                buyers.stream().filter(b -> b.getUserId() == buyerId)
                        .findFirst()
        );
        when(sellerRepository.getPosts()).thenReturn(
               posts
        );
    }

    @Test
    void findFollowers() {
    }

    @Test
    void getFollowers() {
    }

    @Test
    void sortGetFollowers() {
    }

    @Test
    void sortGetPostFromFollower() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String order = "date_asc";
        List<Post> orderedPost = filterPostsTwoWeeksAgo(posts);
        List<PostResponseDTO> listPostDTO = orderedPost.stream().filter(post -> post.getUserId() == sellerId || post.getUserId() == 234)
                .map(
                        x -> mapper.convertValue(x, PostResponseDTO.class)
                ).sorted(Comparator.comparing(PostResponseDTO::getDate)).toList();

        List<PostResponseDTO> result = service.sortGetPostFromFollower(buyerId, order);

        Assertions.assertEquals(mapper.writeValueAsString(result), mapper.writeValueAsString(listPostDTO));

    }

    private List<Post> filterPostsTwoWeeksAgo(List<Post> posts) {
        LocalDate dateTwoWeeksAgo = LocalDate.now().minusWeeks(2);
        return posts.stream().filter(p -> p.getDate().isAfter(dateTwoWeeksAgo))
                .sorted(Comparator.comparing(Post::getDate)).toList();
    }

    @Test
    void createNewPost() {
    }
}