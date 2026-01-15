package com.ips.hospitalmanagementsystem.controller;

import com.ips.hospitalmanagementsystem.dto.LoginRequestDto;
import com.ips.hospitalmanagementsystem.dto.LoginResponseDto;
import com.ips.hospitalmanagementsystem.dto.SignupRequestDto;
import com.ips.hospitalmanagementsystem.dto.SignupResponseDto;
import com.ips.hospitalmanagementsystem.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor


public class AuthController {
    private final AuthService  authService ;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){

        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto response = authService.signup(signupRequestDto);
        return ResponseEntity.ok(response);
    }


}
