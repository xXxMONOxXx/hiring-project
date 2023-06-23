package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.DecryptResponse;
import by.mishastoma.customerservice.dto.MessageRequest;
import by.mishastoma.customerservice.dto.MessageResponse;
import by.mishastoma.customerservice.exception.ForbiddenException;
import by.mishastoma.customerservice.exception.MessageNotFoundException;
import by.mishastoma.customerservice.mapper.EntityMapper;
import by.mishastoma.customerservice.model.Message;
import by.mishastoma.customerservice.repository.MessageRepository;
import by.mishastoma.customerservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final EntityMapper entityMapper;

    public List<MessageResponse> getMessages(String authHeader, Long applicationId) {
        DecryptResponse decryptResponse = customerService.decryptAuthHeader(authHeader);
        if (!customerIsAllowedToChat(decryptResponse.getId(), applicationId)) {
            throw new ForbiddenException("Customer is not allowed to chat in this chat");
        } else {
            List<Message> messages = messageRepository.findAllByApplicationId(applicationId);
            return messages
                    .stream()
                    .map(message -> modelMapper.map(message, MessageResponse.class))
                    .toList();
        }
    }

    public MessageResponse createMessage(MessageRequest request) {
        DecryptResponse decryptResponse = customerService.decryptToken(request.getToken());
        if (!customerIsAllowedToChat(decryptResponse.getId(), request.getApplicationId())) {
            throw new ForbiddenException("Customer is not allowed to chat in this chat");
        }
        Message message = modelMapper.map(request, Message.class);
        message.setIsOwner(Boolean.TRUE);
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
        DecryptResponse decryptResponse = customerService.decryptAuthHeader(request.getToken());
        message.setIsOwner(Boolean.TRUE);
        message.setSenderId(decryptResponse.getId());
        message = entityMapper.updateMessage(message, request);
        return modelMapper.map(messageRepository.save(message), MessageResponse.class);
    }

    private Message customerIsAllowedToChangeMessage(String token, Long messageId) {
        DecryptResponse decryptResponse = customerService.decryptToken(token);
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

    private boolean customerIsAllowedToChat(Long customerId, Long applicationId) {
        return orderRepository.existsByCustomerIdAndPositions_Applications_Id(customerId, applicationId);
    }
}
