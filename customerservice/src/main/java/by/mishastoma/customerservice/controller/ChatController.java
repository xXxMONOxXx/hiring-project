package by.mishastoma.customerservice.controller;

import by.mishastoma.customerservice.dto.MessageRequest;
import by.mishastoma.customerservice.dto.MessageResponse;
import by.mishastoma.customerservice.service.MessageService;
import by.mishastoma.customerservice.validation.ApplicationExistsConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    @ResponseBody
    @GetMapping("/messages/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponse> getMessages(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                             @PathVariable @ApplicationExistsConstraint Long applicationId) {
        return messageService.getMessages(authHeader, applicationId);
    }

    @MessageMapping("/chat")
    @SendTo("/chat.sendMessage")
    public MessageResponse sendMessage(@Payload MessageRequest messageRequest) {
        return messageService.createMessage(messageRequest);
    }

    @SendTo("/chat")
    @MessageMapping("/chat.editMessage/{messageId}")
    public MessageResponse editMessage(@PathVariable Long messageId,
                                       @Payload MessageRequest messageRequest) {
        return messageService.updateMessage(messageId, messageRequest);
    }

    @SendTo("/chat")
    @MessageMapping("/chat.deleteMessage/{messageId}")
    public void deleteMessage(@PathVariable Long messageId,
                              @Payload String token) {
        messageService.deleteMessage(token, messageId);
    }
}
