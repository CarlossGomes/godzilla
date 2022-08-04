package com.application.godzilla.security;

import com.application.godzilla.model.User;
import com.application.godzilla.security.model.AuthenticationResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    @Value(value = "${jwt.expiration}")
    public static final int EXPIRATION_TOKEN = 86400_000;

    @Value(value = "${jwt.secret}")
    public static final String SECRET = "cb6982a8-bfbe-4d07-bd61-baf7ab77a82f";

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities()));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuário", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        ModelMapper modelMapper = new ModelMapper();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        User user = (User) authResult.getPrincipal();
        String access_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                .sign(Algorithm.HMAC512(SECRET));

        AuthenticationResponse authenticationResponse = modelMapper.map(user, AuthenticationResponse.class);
        authenticationResponse.setAccess_token(access_token);

        response.setContentType("application/json");

        response.getWriter().write(ow.writeValueAsString(authenticationResponse));
        response.getWriter().flush();
    }

}
