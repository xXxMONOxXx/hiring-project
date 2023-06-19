package by.mishastoma.authservice.service;


import by.mishastoma.authservice.dto.DecryptResponse;
import by.mishastoma.authservice.dto.LoginRequest;
import by.mishastoma.authservice.dto.TokenResponse;

public interface BaseAuthService<Q, S> {

    S register(Q requestEntity);

    TokenResponse login(LoginRequest requestEntity);

    DecryptResponse decrypt(String token);

}
