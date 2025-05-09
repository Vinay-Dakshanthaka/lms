package com.totfd.lms.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.totfd.lms.dto.user.request.UserRegisterDTO;
import com.totfd.lms.dto.auth.request.LoginRequestDTO;
import com.totfd.lms.dto.user.request.UserRequestDTO;
import com.totfd.lms.dto.auth.response.LoginResponseDTO;
import com.totfd.lms.dto.user.response.UserResponseDTO;
import com.totfd.lms.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO registerUser(UserRegisterDTO userRegisterDTO);

    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

    UserResponseDTO loginWithGoogle(GoogleIdToken.Payload payload);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    Page<UserResponseDTO> getAllUsersWithPage(Pageable pageable);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);

    String verifyUser(String Token);

    String resendVerificationEmail(String email);

    Optional<Users> findByEmail(String email);

}
