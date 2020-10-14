package com.newbei.developerJdbc.controller;


import com.newbei.developerJdbc.model.Developer;
import com.newbei.developerJdbc.repository.JdbcDeveloperRepositoryImpl;

import java.util.HashSet;

import static com.newbei.developerJdbc.view.DeveloperView.*;

public class DeveloperController {

   private JdbcDeveloperRepositoryImpl developerRepository = new JdbcDeveloperRepositoryImpl();

    public Developer developerAuthentication(){
        String login, password = "";
        long id = -1;
        do {
            login = saveOrUpdateRecord("Login");
            password = saveOrUpdateRecord("Password");
            id = developerRepository.authentication(login, password);
            if (id == -1) {
                printAuthenticationError();
            }
        } while (id == -1) ;
        return  developerRepository.getById(id);
    }

    public Developer saveNewDeveloper(Developer developer){
        developer.setName(saveOrUpdateRecord("Name"));
        return developerRepository.save(developer);
    }

    public Developer update(Developer developer){
        return developerRepository.update(developer);
    }

    public Developer updateNameDeveloper(Developer developer){
        developer.setName(saveOrUpdateRecord(" Name ", developer.getName()));
        return update(developer);
    }


    public Developer deleteAccountAndSkill(Developer developer){
        if (deleteChoice()) {
            developerRepository.deleteById(developer.getId());
        }
        return developerRepository.getById(developer.getId());
    }
}
