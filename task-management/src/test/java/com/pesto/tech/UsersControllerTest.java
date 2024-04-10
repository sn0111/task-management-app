package com.pesto.tech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesto.tech.data.dto.UserDTO;
import com.pesto.tech.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUserEmail("test@test.com");
        userDTO.setPassword("password");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "test",null,authorityList
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDTO));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userEmail").value("test@test.com"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testUserLogin() throws Exception {
        when(userService.loginUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/users/login")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userEmail").value("test@test.com"));

        verify(userService, times(1)).loginUser(any(UserDTO.class));
    }

    @Test
    public void testSaveUser() throws Exception {
        when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/users/signup")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userEmail").value("test@test.com"));

        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/users/update")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userEmail").value("test@test.com"));

        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }
}
