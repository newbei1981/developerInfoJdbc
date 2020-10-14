package com.newbei.developerJdbc.repository;

import com.newbei.developerJdbc.model.Skill;

import java.util.Set;

public interface SpecificDeveloperSkillRepository {
    Set<Skill> saveAll(long idDev, Set<Skill> skillSet);
    Set<Skill> getAll(long id);
    void updateAll(Set<Skill> skillsNew, long id);
    void deleteById(long id);
}
