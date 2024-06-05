package com.example.demo.service;

import com.example.demo.dtos.ApiResponseDto;
import com.example.demo.dtos.SignInRequestDto;
import com.example.demo.dtos.SignUpRequestDto;
import com.example.demo.dtos.SignInResponseDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body(
                    ApiResponseDto.builder()
                            .success(false)
                            .message("Error: Email is already in use!")
                            .build()
            );
        }

        User user = new User(signUpRequestDto.getUserName(), signUpRequestDto.getEmail(), encoder.encode(signUpRequestDto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .success(true)
                        .message("User registered successfully!")
                        .build()
        );
    }

    public ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SignInResponseDto response = SignInResponseDto.builder()
                .token(jwt)
                .id(Long.valueOf(userDetails.getId()))
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build();

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .success(true)
                        .message("User signed in successfully!")
                        .response(response)
                        .build()
        );
    }
}
