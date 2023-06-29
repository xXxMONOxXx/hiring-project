package by.mishastoma.companyservice.service;

import by.mishastoma.companyservice.dto.CompanyEmployeeRequest;
import by.mishastoma.companyservice.dto.CompanyEmployeeResponse;
import by.mishastoma.companyservice.dto.DecryptResponse;
import by.mishastoma.companyservice.exception.CompanyEmployeeAlreadyExists;
import by.mishastoma.companyservice.exception.CompanyEmployeeNotFound;
import by.mishastoma.companyservice.exception.NotValidTokenException;
import by.mishastoma.companyservice.model.CompanyEmployee;
import by.mishastoma.companyservice.repository.CompanyEmployeeRepository;
import by.mishastoma.companyservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyEmployeeService {
    private final ModelMapper modelMapper;
    private final CompanyEmployeeRepository companyEmployeeRepository;
    private final EmployeeAuthService employeeAuthService;
    private final CompanyAuthService companyAuthService;
    private final JwtUtil jwtUtil;

    public CompanyEmployeeResponse update(String authHeader, CompanyEmployeeRequest request) {
        String token = getToken(authHeader);
        DecryptResponse decryptResponse = companyAuthService.decryptToken(token);
        Optional<CompanyEmployee> companyEmployee = companyEmployeeRepository.
                findByCompanyIdAndEmployeeId(decryptResponse.getId(), request.getEmployeeId());
        if(companyEmployee.isEmpty()) {
            throw new CompanyEmployeeNotFound("Position not found");
        }
        CompanyEmployee updateCompanyEmployee = companyEmployee.get();
        updateCompanyEmployee.setApproved(request.getApproved());
        return modelMapper.map(companyEmployeeRepository.save(updateCompanyEmployee), CompanyEmployeeResponse.class);
    }

    public CompanyEmployeeResponse create(String authHeader, CompanyEmployeeRequest request) {
        String token = getToken(authHeader);
        DecryptResponse decryptResponse = employeeAuthService.decryptToken(token);
        if(companyEmployeeRepository.existsByCompanyIdAndEmployeeId(decryptResponse.getId(), request.getEmployeeId())){
            throw new CompanyEmployeeAlreadyExists("Position for this company and employee already exists");
        }
        CompanyEmployee companyEmployee = CompanyEmployee.builder()
                .companyId(request.getCompanyId())
                .employeeId(decryptResponse.getId())

                .build();
        return modelMapper.map(companyEmployeeRepository.save(companyEmployee), CompanyEmployeeResponse.class);
    }

    private String getToken(String authHeader){
        Optional<String> token = jwtUtil.getTokenFromHeader(authHeader);
        if(token.isEmpty()){
            throw new NotValidTokenException("Token needs Bearer");
        }
        return token.get();
    }
}
