package com.example.labTFiB.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userServiceMock;
    private MockMvc mockMvc;

    @BeforeEach
    void setUpUserControllerTest(){
        UserController controller = new UserController(userServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    void  shouldReturnAllUsers_whenGettingAllUsers() throws Exception {
        User user =
                User.builder().build();
        when(userServiceMock.findAllUsers()).thenReturn(List.of(user));
        mockMvc.perform(get("/rest/users"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").isArray())
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(user.getName()))
                .andExpect((ResultMatcher) jsonPath("$[1]").doesNotExist());
    }



}