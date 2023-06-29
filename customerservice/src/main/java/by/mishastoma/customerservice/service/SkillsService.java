package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.SkillResponse;
import by.mishastoma.customerservice.model.Skill;
import by.mishastoma.customerservice.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillsService {

    private final SkillRepository skillRepository;

    private final ModelMapper modelMapper;

    public List<SkillResponse> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills
                .stream()
                .map(skill -> modelMapper.map(skill, SkillResponse.class))
                .toList();
    }
}
