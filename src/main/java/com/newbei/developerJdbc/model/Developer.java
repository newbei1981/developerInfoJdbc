package com.newbei.developerJdbc.model;

import java.util.Set;

public class Developer {
    private long id;
    private String name;
    private Account account;
    private Set<Skill> skills;

    public Developer(){};

    public Developer(long id, String name, Account account, Set<Skill> skills) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.skills = skills;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

}
