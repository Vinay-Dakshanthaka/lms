package com.totfd.lms.dto.auth.response;

import com.totfd.lms.dto.user.response.UserResponseDTO;

public record LoginResponseDTO(UserResponseDTO user, String message) {
}
