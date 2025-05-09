package com.totfd.lms.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.totfd.lms.dto.auth.request.LoginRequestDTO;
import com.totfd.lms.dto.auth.response.LoginResponseDTO;
import com.totfd.lms.dto.user.request.UserRegisterDTO;
import com.totfd.lms.dto.user.response.UserResponseDTO;
import com.totfd.lms.service.GoogleOAuth2Service;
import com.totfd.lms.service.UserService;
import com.totfd.lms.payload.ApiResponse;
import com.totfd.lms.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final GoogleOAuth2Service googleOAuth2Service;
    private final UserService userService;

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> loginWithGoogle(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        try {
            // Extract the ID token from the request body
            String idToken = request.get("idToken");

            // Verify the ID token with Google OAuth2 service
            GoogleIdToken.Payload payload = googleOAuth2Service.verifyToken(idToken);

            // Get user information from payload and process login
            UserResponseDTO response = userService.loginWithGoogle(payload);

            // Return a successful API response with path and status
            return ResponseEntity.ok(ApiResponse.success(response, "Login successful", HttpStatus.OK.value(), httpRequest.getRequestURI()));

        } catch (GeneralSecurityException | IOException e) {
            // Return an error response with the appropriate status and path
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid Google token", HttpStatus.UNAUTHORIZED.value(), httpRequest.getRequestURI(), e.getMessage()));
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@RequestBody UserRegisterDTO userRegisterDTO, HttpServletRequest request){

        UserResponseDTO responseDTO = userServiceImpl.registerUser(userRegisterDTO);

        return new ResponseEntity<>(
                ApiResponse.success(responseDTO, "User Registered Successfully", HttpStatus.CREATED.value(), request.getRequestURI()),
                HttpStatus.CREATED
        );

    }

    @PostMapping("/loginUser")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginUser(
            @RequestBody LoginRequestDTO loginRequestDTO,
            HttpServletRequest request
    ){
        LoginResponseDTO loginResponseDTO = userServiceImpl.loginUser(loginRequestDTO);

        return  new ResponseEntity<>(
                ApiResponse.success(loginResponseDTO,"Login Success", HttpStatus.OK.value(), request.getRequestURI()),
                HttpStatus.OK
        );
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
