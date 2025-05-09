package com.totfd.lms.dto.user.response;

public record UserResponseDTO(
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
