package crs.projects.mockbank.service;

import crs.projects.mockbank.dto.AuthenticationRequest;
import crs.projects.mockbank.dto.AuthenticationResponse;
import crs.projects.mockbank.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
