package org.example.be_java_hisp_w26_g04.service.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.be_java_hisp_w26_g04.dto.FollowersCountDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Verifico que el vendedor 123 tiene 1 seguidor (verifico cantidad correcta).")
    public void getFollowersCountTest() throws Exception {
        //Arrange
        FollowersCountDTO expectedFollowersCountDTO = new FollowersCountDTO();
        expectedFollowersCountDTO.setUserId(123);
        expectedFollowersCountDTO.setUserName("JohnDoe");
        expectedFollowersCountDTO.setFollowersCount(1);

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(expectedFollowersCountDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/count", expectedFollowersCountDTO.getUserId())
                        .param("userId", String.valueOf(expectedFollowersCountDTO.getUserId())))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DisplayName("Verifico que me devuelve error si ingreso un vendedor inexistente 999.")
    public void getFollowersCountSadPathTest() throws Exception {
        //Arrange
        FollowersCountDTO expectedFollowersCountDTO = new FollowersCountDTO();
        expectedFollowersCountDTO.setUserId(999);
        expectedFollowersCountDTO.setUserName("JohnDoe");
        expectedFollowersCountDTO.setFollowersCount(1);

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(expectedFollowersCountDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/count", expectedFollowersCountDTO.getUserId())
                        .param("userId", String.valueOf(expectedFollowersCountDTO.getUserId())))
                .andExpect(status().isBadRequest());
    }

}
