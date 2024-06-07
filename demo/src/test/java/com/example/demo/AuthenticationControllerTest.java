package com.example.demo;

import com.example.demo.controller.AuthenticationController;
import com.example.demo.dtos.ApiResponseDto;
import com.example.demo.dtos.SignInRequestDto;
import com.example.demo.dtos.SignUpRequestDto;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUpUser_Success() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("username", "email@example.com", "password");
        ApiResponseDto<?> apiResponseDto = ApiResponseDto.builder().success(true).message("User registered successfully!").build();
        ResponseEntity<ApiResponseDto<?>> responseEntity = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        when(authService.signUpUser(signUpRequestDto)).thenReturn(responseEntity);

        ResponseEntity<ApiResponseDto<?>> response = authenticationController.signUpUser(signUpRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully!", response.getBody().getMessage());
    }

    @Test
    void signUpUser_EmailAlreadyInUse() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("username", "email@example.com", "password");
        ApiResponseDto<?> apiResponseDto = ApiResponseDto.builder().success(false).message("Error: Email is already in use!").build();
        ResponseEntity<ApiResponseDto<?>> responseEntity = new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);

        when(authService.signUpUser(signUpRequestDto)).thenReturn(responseEntity);

        ResponseEntity<ApiResponseDto<?>> response = authenticationController.signUpUser(signUpRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Email is already in use!", response.getBody().getMessage());
    }

    @Test
    void signInUser_Success() {
        SignInRequestDto signInRequestDto = new SignInRequestDto("email@example.com", "password");
        ApiResponseDto<?> apiResponseDto = ApiResponseDto.builder().success(true).message("User signed in successfully!").build();
        ResponseEntity<ApiResponseDto<?>> responseEntity = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        when(authService.signInUser(signInRequestDto)).thenReturn(responseEntity);

        ResponseEntity<ApiResponseDto<?>> response = authenticationController.signInUser(signInRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User signed in successfully!", response.getBody().getMessage());
    }

    @Test
    void signInUser_InvalidCredentials() {
        SignInRequestDto signInRequestDto = new SignInRequestDto("email@example.com", "wrongpassword");
        ApiResponseDto<?> apiResponseDto = ApiResponseDto.builder().success(false).message("Invalid credentials!").build();
        ResponseEntity<ApiResponseDto<?>> responseEntity = new ResponseEntity<>(apiResponseDto, HttpStatus.UNAUTHORIZED);

        when(authService.signInUser(signInRequestDto)).thenReturn(responseEntity);

        ResponseEntity<ApiResponseDto<?>> response = authenticationController.signInUser(signInRequestDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials!", response.getBody().getMessage());
    }

    @Test
    void signInUser_MissingPassword() {
        SignInRequestDto signInRequestDto = new SignInRequestDto("email@example.com", "");
        ApiResponseDto<?> apiResponseDto = ApiResponseDto.builder().success(false).message("Password is required!").build();
        ResponseEntity<ApiResponseDto<?>> responseEntity = new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);

        when(authService.signInUser(signInRequestDto)).thenReturn(responseEntity);

        ResponseEntity<ApiResponseDto<?>> response = authenticationController.signInUser(signInRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Password is required!", response.getBody().getMessage());
    }
}
