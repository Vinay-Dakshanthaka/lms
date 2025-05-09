package com.totfd.lms.dto.user.request;

public record UserRegisterDTO(
        String name,
        String email,
        String password
) {}

