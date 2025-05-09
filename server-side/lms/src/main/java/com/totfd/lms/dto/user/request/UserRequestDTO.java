package com.totfd.lms.dto.user.request;

public record UserRequestDTO(
        Long id,
        String email,
        String name,
        String role,
        String givenName,
        String familyName,
        String pictureUrl,
        String locale
) {
}
