package by.mishastoma.companyservice.service;

import by.mishastoma.companyservice.dto.DecryptResponse;
import by.mishastoma.companyservice.dto.EmployeeRequest;
import by.mishastoma.companyservice.dto.EmployeeResponse;
import by.mishastoma.companyservice.exception.NotValidTokenException;
import by.mishastoma.companyservice.mapper.EntityMapper;
import by.mishastoma.companyservice.model.Employee;
import by.mishastoma.companyservice.repository.EmployeeRepository;
import by.mishastoma.companyservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EntityMapper entityMapper;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final EmployeeAuthService employeeAuthService;

    public EmployeeResponse update(String authHeader, EmployeeRequest request) {
        String token = getToken(authHeader);
        DecryptResponse decryptResponse = employeeAuthService.decryptToken(token);
        Employee employee = employeeRepository.findById(decryptResponse.getId()).get();
        Employee updatedEmployee = entityMapper.updateEmployee(employee, request);
        return modelMapper.map(employeeRepository.save(updatedEmployee), EmployeeResponse.class);
    }

    public EmployeeResponse get(String authHeader) {
        String token = getToken(authHeader);
        DecryptResponse decryptResponse = employeeAuthService.decryptToken(token);
        return modelMapper.map(employeeRepository.findById(decryptResponse.getId()).get(), EmployeeResponse.class);
    }

    private String getToken(String authHeader){
        Optional<String> token = jwtUtil.getTokenFromHeader(authHeader);
        if(token.isEmpty()){
            throw new NotValidTokenException("Token needs Bearer");
        }
        return token.get();
    }
}
