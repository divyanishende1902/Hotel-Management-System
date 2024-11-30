package com.hms.HMS.serviceTest;

import com.hms.HMS.dto.UserDto;
import com.hms.HMS.entity.User;
import com.hms.HMS.exception.ResourceAlreadyExistsException;
import com.hms.HMS.exception.ResourceNotFoundException;
import com.hms.HMS.repository.UserRepository;
import com.hms.HMS.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample User Entity
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhone("1234567890");

        // Sample User DTO
        userDto = new UserDto();
       // userDto.setId(1L);
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPhone("1234567890");
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(mapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);

        // Act
        UserDto result = userService.createUser(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(userDto));
        verify(userRepository, never()).save(any(User.class));  // Verifying save() was not called
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);

        // Act
        List<UserDto> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);

        userDto.setName("Updated Name");

        // Act
        UserDto result = userService.updateUser(1L, userDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(userRepository, times(1)).save(user);  // Verifying that save() was called exactly once
    }

    @Test
    void testUpdateUser_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, userDto));
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(2L);

        // Assert
        verify(userRepository, times(1)).delete(user);  // Verifying that delete() was called exactly once
    }

    @Test
    void testDeleteUser_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).delete(any(User.class));  // Verifying delete() was not called
    }
}

