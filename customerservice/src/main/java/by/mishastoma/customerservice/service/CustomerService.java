package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.CustomerRequest;
import by.mishastoma.customerservice.dto.CustomerResponse;
import by.mishastoma.customerservice.dto.DecryptResponse;
import by.mishastoma.customerservice.exception.CustomerNotFoundException;
import by.mishastoma.customerservice.exception.NotValidTokenException;
import by.mishastoma.customerservice.mapper.EntityMapper;
import by.mishastoma.customerservice.model.Customer;
import by.mishastoma.customerservice.repository.CustomerRepository;
import by.mishastoma.customerservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final EntityMapper entityMapper;
    private final JwtUtil jwtUtil;
    private final CustomerAuthService customerAuthService;

    public CustomerResponse update(String authHeader, CustomerRequest request) {
        DecryptResponse decryptResponse = decryptAuthHeader(authHeader);
        Customer customer = customerRepository.findById(decryptResponse.getId())
                .orElseThrow(CustomerNotFoundException::new);
        Customer updatedCustomer = entityMapper.updateCustomer(customer, request);
        return modelMapper.map(customerRepository.save(updatedCustomer), CustomerResponse.class);
    }

    public DecryptResponse decryptAuthHeader(String authHeader) {
        Optional<String> token = jwtUtil.getTokenFromHeader(authHeader);
        if (token.isEmpty()) {
            throw new NotValidTokenException("Token in invalid");
        }
        return customerAuthService.decryptToken(token.get());
    }

    public CustomerResponse get(String authHeader) {
        DecryptResponse decryptResponse = decryptAuthHeader(authHeader);
        Customer customer = customerRepository.findById(decryptResponse.getId())
                .orElseThrow(CustomerNotFoundException::new);
        return modelMapper.map(customer, CustomerResponse.class);
    }
}
