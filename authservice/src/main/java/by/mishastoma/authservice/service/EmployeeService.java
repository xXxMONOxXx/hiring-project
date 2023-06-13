package by.mishastoma.authservice.service;


import by.mishastoma.authservice.dto.EmployeeRequest;
import by.mishastoma.authservice.dto.EmployeeResponse;
import by.mishastoma.authservice.dto.TokenResponse;
import by.mishastoma.authservice.exception.EmployeeNotFoundException;
import by.mishastoma.authservice.exception.LoginFailedException;
import by.mishastoma.authservice.exception.UsernameIsOccupiedException;
import by.mishastoma.authservice.model.Employee;
import by.mishastoma.authservice.repository.EmployeeRepository;
import by.mishastoma.authservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements BaseAuthService<EmployeeRequest, EmployeeResponse>, UserDetailsService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public EmployeeResponse register(EmployeeRequest requestEntity) {
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(requestEntity.getUsername());
        if (optionalEmployee.isPresent()) {
            throw new UsernameIsOccupiedException(requestEntity.getUsername());
        }
        Employee employee = modelMapper.map(requestEntity, Employee.class);
        employee.setIsBlocked(Boolean.FALSE);
        return modelMapper.map(employeeRepository.save(employee), EmployeeResponse.class);
    }

    @Override
    public TokenResponse login(EmployeeRequest requestEntity) {
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException(username);
        }
        return employee.get();
    }
}
