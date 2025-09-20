package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LoginUserDto;
import org.example.dto.RegisterUserDto;
import org.example.dto.VerifyUserDto;
import org.example.model.User;
import org.example.responses.LoginResponse;
import org.example.service.AuthenticationService;
import org.example.service.CaptchaService;
import org.example.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final CaptchaService captchaService;

    @GetMapping("/giris")
    public String giris(){
        return "giris.html";
    }

    @GetMapping("/uye_ol")
    public String uye_ol(){
        return "uye_ol.html";
    }

    @GetMapping("/dogrula")
    public String dogrula(){
        return "dogrula.html";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard.html";
    }

    @GetMapping("/oauth-success")
    public String oauthSuccess(){
        return "oauth-success.html";
    }


    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto){

        boolean captchaValid = captchaService.isValid(registerUserDto.getRecaptchaResponse());

        if(!captchaValid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto){

        boolean captchaValid = captchaService.isValid(loginUserDto.getRecaptchaResponse());

        if(!captchaValid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto){

        try{
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email){
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
