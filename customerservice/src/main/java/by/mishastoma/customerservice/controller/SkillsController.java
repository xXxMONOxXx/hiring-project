package by.mishastoma.customerservice.controller;

import by.mishastoma.customerservice.dto.SkillResponse;
import by.mishastoma.customerservice.service.SkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillsController {

    private final SkillsService skillsService;
    private final RabbitTemplate rabbitTemplate;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SkillResponse> getAllSkills() {
        rabbitTemplate.addAfterReceivePostProcessors();
        return skillsService.getAllSkills();
    }
}
