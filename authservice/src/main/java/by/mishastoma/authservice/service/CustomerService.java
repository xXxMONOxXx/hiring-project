package by.mishastoma.authservice.service;

import by.mishastoma.authservice.config.RoleConfig;
import by.mishastoma.authservice.dto.CustomerRequest;
import by.mishastoma.authservice.dto.CustomerResponse;
import by.mishastoma.authservice.dto.DecryptResponse;
import by.mishastoma.authservice.dto.LoginRequest;
import by.mishastoma.authservice.dto.TokenResponse;
import by.mishastoma.authservice.exception.CustomerNotFoundException;
import by.mishastoma.authservice.exception.LoginFailedException;
import by.mishastoma.authservice.exception.UsernameIsOccupiedException;
import by.mishastoma.authservice.exception.WrongRoleException;
import by.mishastoma.authservice.model.Customer;
import by.mishastoma.authservice.repository.CustomerRepository;
import by.mishastoma.authservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements BaseAuthService<CustomerRequest, CustomerResponse>, UserDetailsService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public CustomerResponse register(CustomerRequest requestEntity) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(requestEntity.getUsername());
        if (optionalCustomer.isPresent()) {
            throw new UsernameIsOccupiedException(requestEntity.getUsername());
        }
        Customer customer = modelMapper.map(requestEntity, Customer.class);
        customer.setIsBlocked(Boolean.FALSE);
        return modelMapper.map(customerRepository.save(customer), CustomerResponse.class);
    }

    @Override
    public TokenResponse login(LoginRequest requestEntity) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(requestEntity.getUsername());
        if (optionalCustomer.isEmpty()) {
            throw new LoginFailedException("Username does not exist");
        }
        if (!optionalCustomer.get().getPassword().equals(requestEntity.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }
        return new TokenResponse(jwtTokenUtil.generateJwtToken(optionalCustomer.get()));
    }

    @Override
    public DecryptResponse decrypt(String token) {
        DecryptResponse response = jwtTokenUtil.decrypt(token);
        if (response.getAuthorities() == null || !response.getAuthorities().contains(RoleConfig.getCUSTOMER_ROLE())) {
            throw new WrongRoleException("No customer role found in decrypt.");
        }
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(username);
        }
        return customer.get();
    }
}
