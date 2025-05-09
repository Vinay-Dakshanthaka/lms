package com.totfd.lms.controller;

//import com.totfd.lms.dto.user.request.UserRegisterDTO;
//import com.totfd.lms.dto.auth.request.LoginRequestDTO;
//import com.totfd.lms.dto.auth.response.LoginResponseDTO;
import com.totfd.lms.dto.user.response.UserResponseDTO;
import com.totfd.lms.payload.ApiResponse;
import com.totfd.lms.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/getUserById")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@RequestParam Long id, HttpServletRequest request) {
        UserResponseDTO responseDTO = userServiceImpl.getUserById(id);
        return new ResponseEntity<>(
                ApiResponse.success(responseDTO, "User Found", HttpStatus.OK.value(), request.getRequestURI()),
                HttpStatus.OK
        );
    }

//    @PostMapping("/registerUser")
//    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@RequestBody UserRegisterDTO userRegisterDTO, HttpServletRequest request){
//
//        UserResponseDTO responseDTO = userServiceImpl.registerUser(userRegisterDTO);
//
//        return new ResponseEntity<>(
//                ApiResponse.success(responseDTO, "User Registered Successfully", HttpStatus.CREATED.value(), request.getRequestURI()),
//                HttpStatus.CREATED
//        );
//
//    }
//
//    @PostMapping("/loginUser")
//    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginUser(
//            @RequestBody LoginRequestDTO loginRequestDTO,
//            HttpServletRequest request
//            ){
//        LoginResponseDTO loginResponseDTO = userServiceImpl.loginUser(loginRequestDTO);
//
//        return  new ResponseEntity<>(
//                ApiResponse.success(loginResponseDTO,"Login Success", HttpStatus.OK.value(), request.getRequestURI()),
//                HttpStatus.OK
//        );
//    }

//    @GetMapping("/getAllUsers")
//    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(HttpServletRequest request){
//        List<UserResponseDTO> usersList = userServiceImpl.getAllUsers();
//
//        return new ResponseEntity<>(
//                ApiResponse.success(usersList, "List of Users", HttpStatus.OK.value(), request.getRequestURI()),
//                HttpStatus.OK
//        );
//    }

    @GetMapping("/getPaginatedUsers")
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            HttpServletRequest request){

        Pageable pageable = PageRequest.of(page, size);

        Page<UserResponseDTO> paginatedUsers = userServiceImpl.getAllUsersWithPage(pageable);

        return  new ResponseEntity<>(
                ApiResponse.success(paginatedUsers, "Paginated List of Users", HttpStatus.OK.value(), request.getRequestURI()),
                HttpStatus.OK
        );

    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestParam String token,HttpServletRequest request) {
        String responseMessage = userServiceImpl.verifyUser(token);
        return new ResponseEntity<>(
                ApiResponse.success(null, responseMessage, HttpStatus.OK.value(), request.getRequestURI()),
                HttpStatus.OK
        );
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<String>> resendVerificationEmail(@RequestParam String email, HttpServletRequest request) {
        String responseMessage = userServiceImpl.resendVerificationEmail(email);
        return new ResponseEntity<>(
                ApiResponse.success(null, responseMessage, HttpStatus.OK.value(), request.getRequestURI()),
                HttpStatus.OK
        );
    }



}
