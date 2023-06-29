package by.mishastoma.companyservice.service;

import by.mishastoma.companyservice.dto.DecryptResponse;
import by.mishastoma.companyservice.dto.MessageRequest;
import by.mishastoma.companyservice.dto.MessageResponse;
import by.mishastoma.companyservice.exception.ForbiddenException;
import by.mishastoma.companyservice.exception.MessageNotFoundException;
import by.mishastoma.companyservice.mapper.EntityMapper;
import by.mishastoma.companyservice.model.Message;
import by.mishastoma.companyservice.repository.ApplicationRepository;
import by.mishastoma.companyservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final EntityMapper entityMapper;
    private final CompanyAuthService companyAuthService;
    private final ApplicationRepository applicationRepository;
    public List<MessageResponse> getMessages(String authHeader, Long applicationId) {
        DecryptResponse decryptResponse = companyService.decryptAuthHeader(authHeader);
        if (!applicationRepository.existsByCompanyIdAndOrderPositionId(decryptResponse.getId(), applicationId)) {
            throw new ForbiddenException("Company is not allowed to chat in this chat");
        } else {
            List<Message> messages = messageRepository.findAllByApplicationId(applicationId);
            return messages
                    .stream()
                    .map(message -> modelMapper.map(message, MessageResponse.class))
                    .toList();
        }
    }

    public MessageResponse createMessage(MessageRequest request) {
        DecryptResponse decryptResponse = companyAuthService.decryptToken(request.getToken());
        if (!applicationRepository.existsByCompanyIdAndOrderPositionId(
                decryptResponse.getId(), request.getApplicationId())) {
            throw new ForbiddenException("Company is not allowed to chat in this chat");
        }
        Message message = modelMapper.map(request, Message.class);
        message.setIsOwner(Boolean.FALSE);
        message.setSenderId(decryptResponse.getId());
        message.setApplicationId(request.getApplicationId());
        return modelMapper.map(messageRepository.save(message), MessageResponse.class);
    }

    public void deleteMessage(String token, Long messageId) {
        Message message = customerIsAllowedToChangeMessage(token, messageId);
        messageRepository.delete(message);
    }

    public MessageResponse updateMessage(Long messageId, MessageRequest request) {
        Message message = customerIsAllowedToChangeMessage(request.getToken(), messageId);
        DecryptResponse decryptResponse = companyService.decryptAuthHeader(request.getToken());
        message.setIsOwner(Boolean.FALSE);
        message.setSenderId(decryptResponse.getId());
        message = entityMapper.updateMessage(message, request);
        return modelMapper.map(messageRepository.save(message), MessageResponse.class);
    }

    private Message customerIsAllowedToChangeMessage(String token, Long messageId) {
        DecryptResponse decryptResponse = companyAuthService.decryptToken(token);
        Message message = messageRepository.findById(messageId)
                .orElseThrow(MessageNotFoundException::new);
        if (Boolean.FALSE.equals(message.getIsOwner())) {
            throw new ForbiddenException("Message was created by company, not customer");
        }
        if (!decryptResponse.getId().equals(message.getSenderId())) {
            throw new ForbiddenException("Users ids are different, access denied");
        } else {
            return message;
        }
    }
}
