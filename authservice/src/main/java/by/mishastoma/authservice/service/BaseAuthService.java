package by.mishastoma.authservice.service;


import by.mishastoma.authservice.dto.TokenResponse;

public interface BaseAuthService<Q, S> {

    S register(Q requestEntity);

    TokenResponse login(Q requestEntity);
}
