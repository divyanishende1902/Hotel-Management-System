package com.hms.HMS.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
}

