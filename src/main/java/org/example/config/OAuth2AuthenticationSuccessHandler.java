package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            String email = oAuth2User.getAttribute("email");
            String username = oAuth2User.getAttribute("login");

            if (email == null || email.isEmpty()) {
                email = username + "@github.local";
            }

            User user = User.builder()
                    .email(email)
                    .username(username)
                    .enabled(true)
                    .build();

            String jwtToken = jwtService.generateToken(user);
            response.sendRedirect("/auth/oauth-success?token=" + jwtToken);

        }catch (Exception e) {
            response.sendRedirect("/auth/giris?error=oauth_token_failed");
        }

    }


}
