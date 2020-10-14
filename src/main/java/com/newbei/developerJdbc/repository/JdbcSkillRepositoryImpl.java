package com.newbei.developerJdbc.repository;

import com.newbei.developerJdbc.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.newbei.developerJdbc.Properties.*;

public class JdbcSkillRepositoryImpl implements SkillRepository{

        private Connection connection = null;
        private PreparedStatement statement = null;

        @Override
        public Skill save(Skill skill){
           long id = check(skill.getName());
           if (id==0){
            String sqlSaveSkill = "insert into skills(name) values (?)";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                statement = connection.prepareStatement(sqlSaveSkill,Statement.RETURN_GENERATED_KEYS);
                statement.setString(1,skill.getName());
                statement.executeUpdate();
                skill.setId(check(skill.getName()));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException thr) {
                    thr.printStackTrace();
                }
            }
           } else {
               System.out.println("This skill is always present!");
               skill.setId(id);
            }
           return skill;
        }

        @Override
        public Skill update(Skill skill) {
            return null;
        }

        @Override
        public List<Skill> getAll() {
            String sqlGetAllSkills = "select * from skills ";
            Statement statement = null;
            List<Skill> tempListSkills = new ArrayList<>();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlGetAllSkills);
                while(rs.next()){
                    Skill skill = new Skill();
                    skill.setId(rs.getLong("id"));
                    skill.setName(rs.getString("name"));
                    tempListSkills.add(skill);
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException thr) {
                    thr.printStackTrace();
                }
            }
            return tempListSkills;
        }

        @Override
        public Skill getById(Long aLong) {
            Skill skill = new Skill();
            String sqlGetByIdSkill = "select * from skills where id = ? ";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                statement = connection.prepareStatement(sqlGetByIdSkill);
                statement.setLong(1,aLong);
                ResultSet rs = statement.executeQuery();
                rs.first();
                skill.setId(rs.getLong("id"));
                skill.setName(rs.getString("name"));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException thr) {
                    thr.printStackTrace();
                }
            }
            return skill;
        }

    private final long check(String name){
        List<Skill> allSkills = getAll();
        for(Skill sk:allSkills){
            if (sk.getName().equals(name)) return sk.getId();
        }
        return 0;
    }

        //Only for Administrator property
        @Override
        public Skill deleteById(Long aLong) {
            Skill skill = getById(aLong);
            String sqlDeleteByIdSkill = "delete from skills where id = ? ";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                statement = connection.prepareStatement(sqlDeleteByIdSkill);
                statement.setLong(1,aLong);
                statement.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException thr) {
                    thr.printStackTrace();
                }
            }
            return skill;
        }

}
