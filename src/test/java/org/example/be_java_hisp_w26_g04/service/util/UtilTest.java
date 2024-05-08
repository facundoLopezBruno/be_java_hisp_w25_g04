package org.example.be_java_hisp_w26_g04.service.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.be_java_hisp_w26_g04.dto.PostResponseDTO;
import org.example.be_java_hisp_w26_g04.dto.ProductDTO;
import org.example.be_java_hisp_w26_g04.model.Buyer;
import org.example.be_java_hisp_w26_g04.model.Post;
import org.example.be_java_hisp_w26_g04.model.Seller;
import org.example.be_java_hisp_w26_g04.util.crud.mapper.CustomMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UtilTest {



    public static Set<Buyer> getBuyers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("data/buyer.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }

    public static Set<Seller> getSellers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        Resource resource = new ClassPathResource("data/seller.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }

    public static List<PostResponseDTO> generatePostResponseDTOAsc(){

        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        ProductDTO productDTO1= new ProductDTO();
        productDTO1.setProductId(2);
        productDTO1.setProductName("Product2");
        productDTO1.setTypeProduct("TypeB");
        productDTO1.setBrand("BrandY");
        productDTO1.setColor("Red");
        productDTO1.setNotes("Some notes about Product2");

        ProductDTO productDTO2= new ProductDTO();
        productDTO2.setProductId(4);
        productDTO2.setProductName("Product4");
        productDTO2.setTypeProduct("TypeD");
        productDTO2.setBrand("BrandW");
        productDTO2.setColor("Yellow");
        productDTO2.setNotes("Some notes about Product4");

        PostResponseDTO post1=new PostResponseDTO(2, 123, LocalDate.of(2024, 4, 24) , 2, 75.0, productDTO1);
        PostResponseDTO post2= new PostResponseDTO(5, 234, LocalDate.of(2024, 5, 28) , 2, 65.0, productDTO2);

        postResponseDTOList.add(post1);
        postResponseDTOList.add(post2);

        return postResponseDTOList;

    }

    public static List<PostResponseDTO> generatePostResponseDTODesc() {

        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setProductId(2);
        productDTO1.setProductName("Product2");
        productDTO1.setTypeProduct("TypeB");
        productDTO1.setBrand("BrandY");
        productDTO1.setColor("Red");
        productDTO1.setNotes("Some notes about Product2");

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setProductId(4);
        productDTO2.setProductName("Product4");
        productDTO2.setTypeProduct("TypeD");
        productDTO2.setBrand("BrandW");
        productDTO2.setColor("Yellow");
        productDTO2.setNotes("Some notes about Product4");

        PostResponseDTO post1 = new PostResponseDTO(2, 123, LocalDate.of(2024, 4, 24), 2, 75.0, productDTO1);
        PostResponseDTO post2 = new PostResponseDTO(5, 234, LocalDate.of(2024, 5, 28), 2, 65.0, productDTO2);

        postResponseDTOList.add(post2);
        postResponseDTOList.add(post1);

        return postResponseDTOList;
    }

    public static List<PostResponseDTO> mapListPostToPostResponseDto(List<Post> posts) {
        return posts.stream().map(p -> CustomMapper.mapper(p, PostResponseDTO.class)).toList();
    }

    public static Post todayPost() {
        return createPostOfDate(1, LocalDate.now());
    }

    public static Post weekAgoPost() {
        return createPostOfDate(2, LocalDate.now().minusWeeks(1));
    }

    public static Post twoWeeksAgoPost() {
        return createPostOfDate(3, LocalDate.now().minusWeeks(2));
    }

    public static Post createPostOfDate(int postId, LocalDate date) {
        return new Post(postId, 1, date, null, 0, 0);
    }
}
