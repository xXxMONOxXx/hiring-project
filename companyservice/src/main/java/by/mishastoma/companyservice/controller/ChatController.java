package by.mishastoma.companyservice.controller;

import by.mishastoma.companyservice.dto.MessageRequest;
import by.mishastoma.companyservice.dto.MessageResponse;
import by.mishastoma.companyservice.service.MessageService;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    @ResponseBody
    @GetMapping("/messages/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponse> getMessages(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                             @PathVariable Long applicationId) {
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
