package com.hms.HMS.services.Impl;

import com.hms.HMS.dto.UserDto;
import com.hms.HMS.entity.User;
import com.hms.HMS.exception.ResourceAlreadyExistsException;
import com.hms.HMS.exception.ResourceNotFoundException;
import com.hms.HMS.repository.UserRepository;
import com.hms.HMS.services.EmailService;
import com.hms.HMS.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper mapper;

    /**
     * Create a new user
     */
    @Override
    public UserDto createUser(UserDto userDto)  {
        User savedUser = null;
        // Check if the email already exists
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User with email " + userDto.getEmail() + " already exists.");
        }

        // Map DTO to Entity
        User user = mapper.map(userDto, User.class);



            // Send email notification
            String subject = "Welcome to Agoda!";
            String body = "Dear " + user.getName() + ",\n\n"
                    + "Thank you for registering with our Hotel. "
                    + "Your account has been successfully created.\n\n"
                    + "Best Regards,\nAgoda Team";
        String msg = emailService.sendEmail(user.getEmail(), subject, body);

        if(msg!=null && !msg.equals("")){
            savedUser = userRepository.save(user);
        }

        // Map Entity to DTO
        return mapper.map(savedUser, UserDto.class);
    }

    /**
     * Get user by ID
     */
    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Map Entity to DTO
        return mapper.map(user, UserDto.class);
    }

    /**
     * Get all users
     */
    @Override
    public List<UserDto> getAllUsers() {
        // Fetch all users and map them to DTOs
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Update an existing user
     */
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        // Fetch the existing user
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Update user details
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setPassword(userDto.getPassword());

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);

        // Map Entity to DTO
        return mapper.map(updatedUser, UserDto.class);
    }

    /**
     * Delete a user
     */
    @Override
    public void deleteUser(Long id) {
        // Check if the user exists
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Delete user
        userRepository.delete(existingUser);
    }


}
