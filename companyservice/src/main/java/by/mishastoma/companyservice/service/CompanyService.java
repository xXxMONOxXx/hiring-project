package by.mishastoma.companyservice.service;

import by.mishastoma.companyservice.dto.CompanyRequest;
import by.mishastoma.companyservice.dto.CompanyResponse;
import by.mishastoma.companyservice.dto.DecryptResponse;
import by.mishastoma.companyservice.exception.CompanyNotFoundException;
import by.mishastoma.companyservice.exception.NotValidTokenException;
import by.mishastoma.companyservice.mapper.EntityMapper;
import by.mishastoma.companyservice.model.Company;
import by.mishastoma.companyservice.repository.CompanyRepository;
import by.mishastoma.companyservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final EntityMapper entityMapper;
    private final JwtUtil jwtUtil;
    private final CompanyAuthService companyAuthService;

    public CompanyResponse update(String authHeader, CompanyRequest request) {
        DecryptResponse decryptResponse = decryptAuthHeader(authHeader);
        Company company = companyRepository.findById(decryptResponse.getId())
                .orElseThrow(() -> new CompanyNotFoundException("Company noy found"));
        Company updatedCompany = entityMapper.updateCompany(company, request);
        return modelMapper.map(companyRepository.save(updatedCompany), CompanyResponse.class);
    }

    public DecryptResponse decryptAuthHeader(String authHeader) {
        Optional<String> token = jwtUtil.getTokenFromHeader(authHeader);
        if (token.isEmpty()) {
            throw new NotValidTokenException("Token in invalid");
        }
        return companyAuthService.decryptToken(token.get());
    }

    public CompanyResponse get(String authHeader) {
        DecryptResponse decryptResponse = decryptAuthHeader(authHeader);
        Company company = companyRepository.findById(decryptResponse.getId())
                .orElseThrow(() -> new CompanyNotFoundException("Company noy found"));
        return modelMapper.map(company, CompanyResponse.class);
    }
}
