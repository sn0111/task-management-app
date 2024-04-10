package com.pesto.tech;

import com.pesto.tech.dao.UserDAO;
import com.pesto.tech.data.dto.UserDTO;
import com.pesto.tech.data.entity.UserEntity;
import com.pesto.tech.data.entity.UserRole;
import com.pesto.tech.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private UserEntity userEntity;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUserEmail("test@test.com");
        userDTO.setPassword("password");

        userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setUserEmail("test@test.com");
        userEntity.setPassword("password");
        userEntity.setRole(UserRole.USER);

        userDetails = new User("test@test.com", "password", Collections.emptyList());
    }

    @Test
    public void testGetAllUsers() {
        when(userDAO.users()).thenReturn(Collections.singletonList(userEntity));
        when(modelMapper.map(any(UserEntity.class), eq(UserDTO.class))).thenReturn(userDTO);

        assertEquals(1, userService.getAllUsers().size());
        assertEquals("test@test.com", userService.getAllUsers().get(0).getUserEmail());

    }

    @Test
    public void testSaveUser() {
        when(modelMapper.map(any(UserDTO.class), eq(UserEntity.class))).thenReturn(userEntity);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(userDAO.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(modelMapper.map(any(UserEntity.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO savedUser = userService.saveUser(userDTO);

        assertEquals("encodedPassword", userEntity.getPassword());
        assertEquals("test@test.com", savedUser.getUserEmail());

        verify(modelMapper, times(1)).map(any(UserDTO.class), eq(UserEntity.class));
        verify(encoder, times(1)).encode(anyString());
        verify(userDAO, times(1)).saveUser(any(UserEntity.class));
        verify(modelMapper, times(1)).map(any(UserEntity.class), eq(UserDTO.class));
    }

    @Test
    public void testUpdateUser() {
        when(userDAO.findByUserId(1L)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(any(UserDTO.class), eq(UserEntity.class))).thenReturn(userEntity);
        when(userDAO.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(modelMapper.map(any(UserEntity.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO updatedUser = userService.updateUser(userDTO);

        assertEquals("test@test.com", updatedUser.getUserEmail());

        verify(userDAO, times(1)).findByUserId(1L);
        verify(modelMapper, times(1)).map(any(UserDTO.class), eq(UserEntity.class));
        verify(userDAO, times(1)).saveUser(any(UserEntity.class));
        verify(modelMapper, times(1)).map(any(UserEntity.class), eq(UserDTO.class));
    }

//    @Test
//    public void testLoginUser() {
//        when(userDAO.userDetails("test@test.com")).thenReturn(Optional.of(userDetails));
//        when(encoder.matches("password", userDetails.getPassword())).thenReturn(true);
//
//        UserDTO loggedInUser = userService.loginUser(userDTO);
//
//        assertNotNull(loggedInUser);
//        assertEquals("test@test.com", loggedInUser.getUserEmail());
//        assertEquals(UserRole.USER, loggedInUser.getRole());
//
//        verify(userDAO, times(1)).userDetails("test@test.com");
//        verify(encoder, times(1)).matches("password", userDetails.getPassword());
//    }

    @Test
    public void testUpdateUserElse() {
        when(userDAO.findByUserId(1L)).thenReturn(Optional.empty());
        when(modelMapper.map(any(UserDTO.class), eq(UserEntity.class))).thenReturn(userEntity);
        when(userDAO.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(modelMapper.map(any(UserEntity.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO updatedUser = userService.updateUser(userDTO);

        assertNull(updatedUser);
    }

    @Test
    public void testLoginUserElse() {
        when(userDAO.userDetails("test@test.com")).thenReturn(Optional.empty());
        when(encoder.matches("password", userDetails.getPassword())).thenReturn(true);

        UserDTO loggedInUser = userService.loginUser(userDTO);

        assertNull(loggedInUser);

    }
}

