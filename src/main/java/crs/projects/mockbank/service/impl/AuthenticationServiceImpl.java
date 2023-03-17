package crs.projects.mockbank.service.impl;


import crs.projects.mockbank.service.JwtService;
import crs.projects.mockbank.dto.AuthenticationRequest;
import crs.projects.mockbank.dto.AuthenticationResponse;
import crs.projects.mockbank.dto.RegisterRequest;
import crs.projects.mockbank.model.Role;
import crs.projects.mockbank.model.User;
import crs.projects.mockbank.repository.UserRepository;
import crs.projects.mockbank.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(newUser);
        String token = jwtService.generateToken(savedUser);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // has side effect of throwing exception if not successful
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
