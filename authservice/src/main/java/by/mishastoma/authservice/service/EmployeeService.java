package by.mishastoma.authservice.service;


import by.mishastoma.authservice.dto.DecryptResponse;
import by.mishastoma.authservice.dto.EmployeeRequest;
import by.mishastoma.authservice.dto.EmployeeResponse;
import by.mishastoma.authservice.dto.LoginRequest;
import by.mishastoma.authservice.dto.TokenResponse;
import by.mishastoma.authservice.exception.EmployeeNotFoundException;
import by.mishastoma.authservice.exception.InvalidUserException;
import by.mishastoma.authservice.exception.LoginFailedException;
import by.mishastoma.authservice.exception.UsernameIsOccupiedException;
import by.mishastoma.authservice.exception.WrongRoleException;
import by.mishastoma.authservice.model.Employee;
import by.mishastoma.authservice.repository.EmployeeRepository;
import by.mishastoma.authservice.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements BaseAuthService<EmployeeRequest, EmployeeResponse>, UserDetailsService {

    @Value("${rabbitmq.queue.employee.auth}")
    private String queueName;
    private final RabbitTemplate rabbitTemplate;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${employee.role}")
    private String employeeRole;
    private final ObjectMapper objectMapper;

    @Override
    public EmployeeResponse register(EmployeeRequest requestEntity) {
        try {
            rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(requestEntity));
        } catch (JsonProcessingException e) {
            throw new InvalidUserException(e.getMessage());
        }
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(requestEntity.getUsername());
        if (optionalEmployee.isPresent()) {
            throw new UsernameIsOccupiedException(requestEntity.getUsername());
        }
        Employee employee = modelMapper.map(requestEntity, Employee.class);
        employee.setIsBlocked(Boolean.FALSE);
        return modelMapper.map(employeeRepository.save(employee), EmployeeResponse.class);
    }

    @Override
    public TokenResponse login(LoginRequest requestEntity) {
        try {
            rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(requestEntity));
        } catch (JsonProcessingException e) {
            throw new InvalidUserException(e.getMessage());
        }
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(requestEntity.getUsername());
        if (optionalEmployee.isEmpty()) {
            throw new LoginFailedException("Username does not exist");
        }
        if (!optionalEmployee.get().getPassword().equals(requestEntity.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }
        return new TokenResponse(jwtTokenUtil.generateJwtToken(optionalEmployee.get()));
    }

    @Override
    public DecryptResponse decrypt(String token) {
        rabbitTemplate.convertAndSend(queueName, token);
        DecryptResponse response = jwtTokenUtil.decrypt(token);
        if (response.getAuthorities() == null || !response.getAuthorities().contains(employeeRole)) {
            throw new WrongRoleException("No employee role found in decrypt.");
        }
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException(username);
        }
        return employee.get();
    }
}
