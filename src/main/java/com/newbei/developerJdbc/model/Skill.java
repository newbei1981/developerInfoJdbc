package com.newbei.developerJdbc.model;

public class Skill {
    private long id;
    private String name;

    public Skill() {}

    public Skill(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Skill: "  + id +") = "+
                "  " + name + '\'';
    }
}
