package by.mishastoma.companyservice.service;

import by.mishastoma.companyservice.dto.ApplicationRequest;
import by.mishastoma.companyservice.dto.ApplicationResponse;
import by.mishastoma.companyservice.dto.DecryptResponse;
import by.mishastoma.companyservice.exception.ApplicationAlreadyExists;
import by.mishastoma.companyservice.exception.NotValidTokenException;
import by.mishastoma.companyservice.model.Application;
import by.mishastoma.companyservice.model.ApplicationStatus;
import by.mishastoma.companyservice.repository.ApplicationRepository;
import by.mishastoma.companyservice.repository.ApplicationStatusRepository;
import by.mishastoma.companyservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final ApplicationRepository applicationRepository;
    private final CompanyAuthService companyAuthService;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final JwtUtil jwtUtil;
    @Value("${default.application.status.id}")
    private Long defaultApplicationStatusId;

    public ApplicationResponse create(String authHeader, ApplicationRequest request) {
        Optional<String> token = jwtUtil.getTokenFromHeader(authHeader);
        if(token.isEmpty()){
            throw new NotValidTokenException("No Bearer found");
        }
        DecryptResponse decryptResponse = companyAuthService.decryptToken(token.get());
        if(applicationRepository.existsByCompanyIdAndOrderPositionId(
                decryptResponse.getId(), request.getOrderPositionId())){
            throw new ApplicationAlreadyExists();
        }
        ApplicationStatus applicationStatus = applicationStatusRepository.findById(defaultApplicationStatusId).get();
        Application application = Application.builder()
                .orderPositionId(request.getOrderPositionId())
                .companyId(decryptResponse.getId())
                .status(applicationStatus)
                .build();
        return modelMapper.map(applicationRepository.save(application), ApplicationResponse.class);
    }
}
