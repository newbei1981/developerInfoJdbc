package com.newbei.developerJdbc.controller;

import com.newbei.developerJdbc.model.Skill;
import com.newbei.developerJdbc.repository.JdbcSkillRepositoryImpl;
import com.newbei.developerJdbc.repository.SkillRepository;
import com.newbei.developerJdbc.repository.SpecificDeveloperSkillRepository;
import com.newbei.developerJdbc.repository.SpecificDeveloperSkillRepositoryImpl;

import java.util.Set;

import static com.newbei.developerJdbc.view.SkillView.*;
import static com.newbei.developerJdbc.view.StaticUtility.*;

public class SkillController {

    private SkillRepository skillRepository = new JdbcSkillRepositoryImpl();
    private SpecificDeveloperSkillRepository specSkillDev = new SpecificDeveloperSkillRepositoryImpl();

    public Set<Skill> createSkills(long idDev, Set<Skill> skillSet){
        byte input;
        do {
            mainHeader(skillRepository.getAll().toString());
            input = selectMenuItems(SKILLS_MENU);
            switch (input) {
                case 1:{ skillSet = saveAbsentSkills(skillSet);
                    break;}
                case 2:{ skillSet = updateUserSkills(idDev, skillSet);
                    break;}
                case 3:{ skillSet = deleteUserSkill(skillSet);
                    break;}
            }
        } while (input != 0);
        return skillSet;
    }

    public Set<Skill> saveAbsentSkills(Set<Skill> userSkillSet){
        String input;
        do {
            absentHeader(skillRepository.getAll().toString());
            input = userInput.get();
            if (input.charAt(0) != '0') {
                Skill absentSkill = skillRepository.save(new Skill(input));
                userSkillSet.add(absentSkill);
            }
        }
        while (input.charAt(0) != '0');
        System.out.println(userSkillSet.toString());
        return userSkillSet;
    }

    public Set<Skill> deleteUserSkill(Set<Skill> userSkillSet){
        deleteHeader();
        if (userInput.get().toUpperCase().charAt(0) == 'Y') {
            try{
                deleteYesView();
                Skill skill = skillRepository.getById(Long.parseLong(userInput.get()));
                userSkillSet.remove(skill);
            } catch (Exception exception) {
                System.out.println("You have entered incorrect data. Please repeat!");
            }
        }
        return userSkillSet;
    }

    public Set<Skill> updateUserSkills(long idDev,Set<Skill> userSkillSet){
        if (userSkillSet == null) updateHeader(skillRepository.getAll().toString());
            else updateHeader(specSkillDev.getAll(idDev).toString());
        String input;
        do {
            input = userInput.get();
            if (input.charAt(0) != '0') {
                userSkillSet.add(skillRepository.getById(Long.parseLong(input)));
            }
        }
        while (input.charAt(0) != '0');
        return userSkillSet;
    }
}