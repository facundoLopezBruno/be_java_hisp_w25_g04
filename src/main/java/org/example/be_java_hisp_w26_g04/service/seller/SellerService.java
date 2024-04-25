package org.example.be_java_hisp_w26_g04.service.seller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;
import org.example.be_java_hisp_w26_g04.dto.PostRequestDTO;
import org.example.be_java_hisp_w26_g04.dto.PostResponseDTO;
import org.example.be_java_hisp_w26_g04.dto.ProductDTO;
import org.example.be_java_hisp_w26_g04.dto.SellerFollowersDTO;
import org.example.be_java_hisp_w26_g04.dto.UserDTO;
import org.example.be_java_hisp_w26_g04.exceptions.BadRequestException;
import org.example.be_java_hisp_w26_g04.exceptions.NotFoundException;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Post;
import org.example.be_java_hisp_w26_g04.model.Product;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.repository.buyer.IBuyersRepository;
import org.example.be_java_hisp_w26_g04.repository.seller.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService implements ISellerService {

  @Autowired
  ISellerRepository sellerRepository;

  @Autowired
  IBuyersRepository buyerRepository;

  private final ObjectMapper objectMapper;

  @Override
  public FollowersCountDTO findFollowers(int sellerId) {
    Seller seller = sellerRepository.findById(sellerId).orElseThrow(NotFoundException::new);
    int followerCount = seller.getFollowers().size();

    FollowersCountDTO followersCountDTO = objectMapper.convertValue(seller, FollowersCountDTO.class);
    followersCountDTO.setFollowersCount(followerCount);

    return followersCountDTO;
  }

  @Override
  public SellerFollowersDTO getFollowers(int userId) {
    Seller seller = sellerRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("Seller with id " + userId + " not found"));

    return converSellerToSellerFollowersDto(seller);
  }

  @Override
  public SellerFollowersDTO sortGetFollowers(int userId, String order) {
    SellerFollowersDTO sellerFollowersDTO = getFollowers(userId);
    if (order.equals("name_asc")) {
      sellerFollowersDTO.getFollowers().sort(Comparator.comparing(UserDTO::getUsername));
    } else if (order.equals("name_desc")) {
      sellerFollowersDTO.getFollowers().sort(Comparator.comparing(UserDTO::getUsername).reversed());

    } else {
      throw new BadRequestException();
    }
    return sellerFollowersDTO;
  }

  private SellerFollowersDTO converSellerToSellerFollowersDto(Seller seller) {
    List<UserDTO> followers = seller.getFollowers().stream()
        .map(x -> buyerRepository.findById(x)).filter(Optional::isPresent).map(x -> x.get())
        .map(follower -> new UserDTO(follower.getUserId(), follower.getUserName()))
        .toList();

    return new SellerFollowersDTO(seller.getUserId(), seller.getUserName(), followers);
  }


  private List<PostResponseDTO> getPostsFromFollower(int userId) {
    Optional<Buyer> buyer = buyerRepository.findById(userId);
    if (buyer.isEmpty()) throw new BadRequestException();
    List<Post> posts = sellerRepository.getPosts();
    List<Post> filteredPost = filterPostsTwoWeeksAgo(posts);
    List<PostResponseDTO> postsDTO = new ArrayList<>();
    buyer.get().getSellersFollowing().forEach(
        id -> filteredPost.stream().filter(post -> post.getUserId() == id)
            .forEach(
                x -> postsDTO.add(objectMapper.convertValue(x, PostResponseDTO.class))
            )
    );
    return postsDTO;
  }

  private List<Post> filterPostsTwoWeeksAgo(List<Post> posts) {
    LocalDate dateTwoWeeksAgo = LocalDate.now().minusWeeks(2);
    return posts.stream().filter(p -> p.getDate().isAfter(dateTwoWeeksAgo))
        .sorted(Comparator.comparing(Post::getDate)).toList();
  }


  @Override
  public List<PostResponseDTO> sortGetPostFromFollower(int userId, String order) {
    List<PostResponseDTO> ListPostDTO = getPostsFromFollower(userId);
    if (order != null) {
      if (order.contains("date_asc")) {
        ListPostDTO.sort(Comparator.comparing(PostResponseDTO::getDate));
      } else if (order.contains("date_desc")) {
        ListPostDTO.sort(Comparator.comparing(PostResponseDTO::getDate).reversed());
      } else {
        throw new BadRequestException();
      }
    }
    return ListPostDTO;
  }

  @Override
  public boolean createNewPost(PostRequestDTO post) {
    Optional<Seller> optionalSeller = sellerRepository.findById(post.getUserId());
    if (optionalSeller.isEmpty()) {
      throw new BadRequestException();
    }

    Post unPost = convertPostRequestDtoToPost(post);

    return sellerRepository.save(unPost);
  }

  public Post convertPostRequestDtoToPost(PostRequestDTO postRequestDTO) {
    return Post.builder()
        .userId(postRequestDTO.getUserId())
        .date(LocalDate.parse(postRequestDTO.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
        .category(postRequestDTO.getCategory())
        .price(postRequestDTO.getPrice())
        .product(convertProductDtoToProduct(postRequestDTO.getProduct()))
        .build();
  }

  public Product convertProductDtoToProduct(ProductDTO productDTO) {
    return Product.builder()
        .productId(productDTO.getProductId())
        .productName(productDTO.getProductName())
        .typeProduct(productDTO.getTypeProduct())
        .brand(productDTO.getBrand())
        .color(productDTO.getColor())
        .notes(productDTO.getNotes())
        .build();
  }

}
