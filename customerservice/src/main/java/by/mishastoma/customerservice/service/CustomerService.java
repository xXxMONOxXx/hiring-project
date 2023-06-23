package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.CustomerRequest;
import by.mishastoma.customerservice.dto.CustomerResponse;
import by.mishastoma.customerservice.dto.DecryptResponse;
import by.mishastoma.customerservice.exception.AuthServiceNotAvailableException;
import by.mishastoma.customerservice.exception.CustomerNotFoundException;
import by.mishastoma.customerservice.exception.InvalidResponseSignatureException;
import by.mishastoma.customerservice.exception.NotValidTokenException;
import by.mishastoma.customerservice.mapper.EntityMapper;
import by.mishastoma.customerservice.model.Customer;
import by.mishastoma.customerservice.repository.CustomerRepository;
import by.mishastoma.customerservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final EntityMapper entityMapper;
    private final JwtUtil jwtUtil;
    @Value("${auth.service.url}")
    private String authUrl;

    public CustomerResponse update(String authHeader, CustomerRequest request) {
        DecryptResponse decryptResponse = decryptAuthHeader(authHeader);
        Customer customer = customerRepository.findById(decryptResponse.getId())
                .orElseThrow(CustomerNotFoundException::new);
        Customer updatedCustomer = entityMapper.updateCustomer(customer, request);
        return modelMapper.map(customerRepository.save(updatedCustomer), CustomerResponse.class);
    }

    public DecryptResponse decryptAuthHeader(String authHeader) {
        Optional<String> token = jwtUtil.getTokenFromHeader(authHeader);
        if (token.isEmpty() || !isValidToken(token.get())) {
            throw new NotValidTokenException("Token in invalid");
        }
        return decryptToken(token.get());
    }

    public CustomerResponse get(String authHeader) {
        DecryptResponse decryptResponse = decryptAuthHeader(authHeader);
        Customer customer = customerRepository.findById(decryptResponse.getId())
                .orElseThrow(CustomerNotFoundException::new);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    public DecryptResponse decryptToken(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = authUrl.concat("/customers/decrypt?token={token}");
            ResponseEntity<DecryptResponse> response = restTemplate.getForEntity(url, DecryptResponse.class, token);
            if (response.getBody() == null) {
                throw new InvalidResponseSignatureException("Decrypt response must contain id, username and authorities");
            }
            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            throw new NotValidTokenException(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            throw new AuthServiceNotAvailableException();
        }
    }

    private boolean isValidToken(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = authUrl.concat("/customers/validate?token={token}");
            ResponseEntity<DecryptResponse> response = restTemplate.getForEntity(url, DecryptResponse.class, token);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException.BadRequest e) {
            throw new NotValidTokenException(e.getMessage());
        } catch (RestClientException e) {
            throw new AuthServiceNotAvailableException();
        }
    }
}
