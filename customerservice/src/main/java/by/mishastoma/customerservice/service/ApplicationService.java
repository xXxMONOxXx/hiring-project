package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.ApplicationRequest;
import by.mishastoma.customerservice.dto.ApplicationResponse;
import by.mishastoma.customerservice.exception.ApplicationNotFoundException;
import by.mishastoma.customerservice.mapper.EntityMapper;
import by.mishastoma.customerservice.model.Application;
import by.mishastoma.customerservice.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private ModelMapper modelMapper;
    private CustomerService customerService;
    private OrderPositionService orderPositionService;
    private ApplicationRepository applicationRepository;
    private EntityMapper entityMapper;

    public ApplicationResponse updateApplication(String authHeader, ApplicationRequest request, Long id) {
        Application application = customerIsAllowedToChangeApplication(authHeader, id);
        Application updatedApplication = entityMapper.updateApplication(application, request);
        return modelMapper.map(applicationRepository.save(updatedApplication), ApplicationResponse.class);
    }

    private Application customerIsAllowedToChangeApplication(String authHeader, Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        orderPositionService.customerIsAllowedToChangeOrderPosition(authHeader, application.getOrderPositionId());
        return application;
    }
}
