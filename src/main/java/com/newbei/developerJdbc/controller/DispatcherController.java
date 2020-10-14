package com.newbei.developerJdbc.controller;

import com.newbei.developerJdbc.model.Account;
import com.newbei.developerJdbc.model.Developer;
import com.newbei.developerJdbc.model.Skill;
import com.newbei.developerJdbc.repository.SpecificDeveloperSkillRepository;
import com.newbei.developerJdbc.repository.SpecificDeveloperSkillRepositoryImpl;

import java.util.HashSet;
import java.util.Set;

import static com.newbei.developerJdbc.view.StaticUtility.*;


public class DispatcherController {
    private AccountController accountController = new AccountController();
    private SkillController skillsController = new SkillController();
    private DeveloperController developerController = new DeveloperController();
    private SpecificDeveloperSkillRepository specSkillDev = new SpecificDeveloperSkillRepositoryImpl();

    private Developer developer = new Developer();
    private Account account = new Account();

    public void menuFlow() {
        byte choice = selectMenuItems(MAIN_MENU);
        if ((choice > 0) && (choice < MAIN_MENU.size())) {
            if (choice == 1) {
                logInAndUpdate();
            } else if (choice == 2) {
                registration();
            }
        }
    }

    public void registration() {
        developer.setAccount(accountController.saveAccount());
        developer = developerController.saveNewDeveloper(developer);
        developer.setSkills(skillsController.createSkills(developer.getId(), new HashSet<>()));
        developerController.update(developer);
    }

    public void logInAndUpdate() {
        developer = developerController.developerAuthentication();
        account = developer.getAccount();
        System.out.println("Select the field which you want to change?");
        byte choice = selectMenuItems(DEVELOPER_CARD_FIELDS);
        switch (choice) {
            case 1: {
                developer = developerController.updateNameDeveloper(developer);
                break;
            }
            case 2: {
                accountController.updateAccountLogin(account);
                break;
            }
            case 3: {
                accountController.updateAccountPassword(account);
                break;
            }
            case 4: {
                Set<Skill> skillSet = developer.getSkills();
                developer.setSkills(skillsController.createSkills(developer.getId(),skillSet));
                developerController.update(developer);
                break;
            }
            case 5: {
                developerController.deleteAccountAndSkill(developer);
                break;
            }
        }
    }
}
