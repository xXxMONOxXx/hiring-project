package by.mishastoma.companyservice.repository;

import by.mishastoma.companyservice.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
