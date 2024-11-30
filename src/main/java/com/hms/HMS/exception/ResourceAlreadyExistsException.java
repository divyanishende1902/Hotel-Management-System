package com.hms.HMS.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}

