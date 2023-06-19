package by.mishastoma.authservice.service;

import by.mishastoma.authservice.dto.CompanyRequest;
import by.mishastoma.authservice.dto.CompanyResponse;
import by.mishastoma.authservice.dto.DecryptResponse;
import by.mishastoma.authservice.dto.LoginRequest;
import by.mishastoma.authservice.dto.TokenResponse;
import by.mishastoma.authservice.exception.CompanyNotFoundException;
import by.mishastoma.authservice.exception.LoginFailedException;
import by.mishastoma.authservice.exception.UsernameIsOccupiedException;
import by.mishastoma.authservice.exception.WrongRoleException;
import by.mishastoma.authservice.model.Company;
import by.mishastoma.authservice.repository.CompanyRepository;
import by.mishastoma.authservice.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService implements BaseAuthService<CompanyRequest, CompanyResponse>, UserDetailsService {

    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${company.role}")
    private String companyRole;

    @Override
    public CompanyResponse register(CompanyRequest requestEntity) {
        Optional<Company> optionalCompany = companyRepository.findByUsername(requestEntity.getUsername());
        if (optionalCompany.isPresent()) {
            throw new UsernameIsOccupiedException(requestEntity.getUsername());
        }
        Company company = modelMapper.map(requestEntity, Company.class);
        company.setIsBlocked(Boolean.FALSE);
        return modelMapper.map(companyRepository.save(company), CompanyResponse.class);
    }

    @Override
    public TokenResponse login(LoginRequest requestEntity) {
        Optional<Company> optionalCompany = companyRepository.findByUsername(requestEntity.getUsername());
        if (optionalCompany.isEmpty()) {
            throw new LoginFailedException("Username does not exist");
        }
        if (!optionalCompany.get().getPassword().equals(requestEntity.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }
        return new TokenResponse(jwtTokenUtil.generateJwtToken(optionalCompany.get()));
    }

    @Override
    public DecryptResponse decrypt(String token) {
        DecryptResponse response = jwtTokenUtil.decrypt(token);
        if (response.getAuthorities() == null || !response.getAuthorities().contains(companyRole)) {
            throw new WrongRoleException("No company role found in decrypt.");
        }
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Company> company = companyRepository.findByUsername(username);
        if (company.isEmpty()) {
            throw new CompanyNotFoundException(username);
        }
        return company.get();
    }
}
