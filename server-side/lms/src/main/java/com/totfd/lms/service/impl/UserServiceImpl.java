package com.totfd.lms.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.totfd.lms.dto.auth.request.LoginRequestDTO;
import com.totfd.lms.dto.auth.response.LoginResponseDTO;
import com.totfd.lms.dto.user.request.UserRegisterDTO;
import com.totfd.lms.dto.user.request.UserRequestDTO;
import com.totfd.lms.dto.user.response.UserResponseDTO;
import com.totfd.lms.entity.Role;
import com.totfd.lms.entity.Users;
import com.totfd.lms.exceptions.DuplicateEmailException;
import com.totfd.lms.exceptions.UserNotFoundException;
import com.totfd.lms.mapper.UserMapper;
import com.totfd.lms.repository.RoleRepository;
import com.totfd.lms.repository.UsersRepository;
import com.totfd.lms.service.EmailService;
import com.totfd.lms.service.JwtService;
import com.totfd.lms.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EmailServiceImpl emailServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Users user = userMapper.toEntity(userRequestDTO);
        Users savedUser = usersRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO registerUser(UserRegisterDTO userRegisterDTO) {
        if (usersRepository.findByEmail(userRegisterDTO.email()).isPresent()) {
            throw new DuplicateEmailException("Email is already registered");
        }

        Role roleUser = roleRepository.findByName("ROLE_USER").orElseGet(() ->
                roleRepository.save(Role.builder().name("ROLE_USER").build())
        );

        Users user = Users.builder()
                .email(userRegisterDTO.email())
                .name(userRegisterDTO.name())
                .password(passwordEncoder.encode(userRegisterDTO.password()))
                .role(roleUser)
                .enabled(false)
                .build();

        // Generate JWT token for email verification
        String token = jwtService.generateEmailVerificationToken(userRegisterDTO.email());

        user.setVerificationToken(token);
        Users savedUser = usersRepository.save(user);

        // Send verification email with the token
        emailServiceImpl.sendVerificationEmail(savedUser.getEmail(), token);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public String verifyUser(String token) {
        String email = jwtService.validateAndGetEmail(token);

        if (jwtService.isTokenExpired(token)) {
            throw new RuntimeException("Verification token has expired");
        }

        Users user = usersRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Invalid email address"));

        user.setEnabled(true);
        user.setVerificationToken(null);
        usersRepository.save(user);

        return "Email verified successfully!";
    }

    @Override
    public String resendVerificationEmail(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User not found"));

        if (user.isEnabled()) {
            throw new RuntimeException("User is already verified");
        }

        // Generate a new JWT token for email verification
        String token = jwtService.generateEmailVerificationToken(email);
        user.setVerificationToken(token);
        usersRepository.save(user);

        // Send the email again
        emailServiceImpl.sendVerificationEmail(user.getEmail(), token);

        return "Verification email resent successfully!";
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.email();
        String rawPassword = loginRequestDTO.password();

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }

        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(user);

        return new LoginResponseDTO(userResponseDTO, "Login Success");

    }

    @Override
    @Transactional
    public UserResponseDTO loginWithGoogle(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String oauthId = payload.getSubject();
        String givenName = (String) payload.get("given_name");
        String familyName = (String) payload.get("family_name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");

        Users user = usersRepository.findByEmail(email).orElse(null);

        if (user != null) {
            boolean updated = false;
            if (!Objects.equals(user.getName(), name)) {
                user.setName(name);
                updated = true;
            }
            if (!Objects.equals(user.getOauthId(), oauthId)) {
                user.setOauthId(oauthId);
                updated = true;
            }
            if (!Objects.equals(user.getGivenName(), givenName)) {
                user.setGivenName(givenName);
                updated = true;
            }
            if (!Objects.equals(user.getFamilyName(), familyName)) {
                user.setFamilyName(familyName);
                updated = true;
            }
            if (!Objects.equals(user.getPictureUrl(), pictureUrl)) {
                user.setPictureUrl(pictureUrl);
                updated = true;
            }
            if (!Objects.equals(user.getLocale(), locale)) {
                user.setLocale(locale);
                updated = true;
            }

            if (updated) {
                usersRepository.save(user);
            }

        } else {
            Role roleUser = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
                Role newRole = Role.builder().name("ROLE_USER").build();
                return roleRepository.save(newRole);
            });

            user = Users.builder()
                    .email(email)
                    .name(name)
                    .oauthId(oauthId)
                    .givenName(givenName)
                    .familyName(familyName)
                    .pictureUrl(pictureUrl)
                    .locale(locale)
                    .role(roleUser)
                    .build();

            usersRepository.save(user);
        }

        return userMapper.toResponseDTO(user);
    }


    @Override
    public UserResponseDTO getUserById(Long id) {

        Users user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found with this id :" + id));

        return userMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<Users> usersPage = usersRepository.findAll(pageable);
//
//        return  usersPage.map(userMapper::toResponseDTO);
        List<Users> usersList = usersRepository.findAll();
        List<UserResponseDTO> responseDTOList = new ArrayList<>();

        for (Users user : usersList) {
            UserResponseDTO dto = userMapper.toResponseDTO(user);
            responseDTOList.add(dto);
        }
        return responseDTOList;
    }

    @Override
    public Page<UserResponseDTO> getAllUsersWithPage(Pageable pageable) {
        Page<Users> usersPage = usersRepository.findAll(pageable);
        return usersPage.map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found with this id :" + id));

        user.setName(userRequestDTO.name());

        Users updatedUser = usersRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found with this id :" + id));
        usersRepository.delete(user);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
