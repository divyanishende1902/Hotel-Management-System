package com.hms.HMS.services;

import com.hms.HMS.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto); // Create a new user

    //get by id
    UserDto getUserById(Long id); // Get user by ID

    //list of users
    List<UserDto> getAllUsers(); // Get all users
    //update user
    UserDto updateUser(Long id, UserDto userDto); // Update an existing user

    //delete user
    void deleteUser(Long id); // Delete a user by ID




}

