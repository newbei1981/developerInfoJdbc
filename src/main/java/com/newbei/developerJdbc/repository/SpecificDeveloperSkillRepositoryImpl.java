package com.newbei.developerJdbc.repository;

import com.newbei.developerJdbc.model.Skill;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static com.newbei.developerJdbc.Properties.*;

public class SpecificDeveloperSkillRepositoryImpl implements SpecificDeveloperSkillRepository{

    private Connection connection = null;
    private PreparedStatement statement = null;
    private final SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

    @Override
    public Set<Skill> saveAll(long idDev, Set<Skill> skillSet) {
        String sqlSaveDevSkill = "insert into skillsdeveloper(developerId,skillId) values (?,?)";
        for (Skill skill:skillSet){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                statement = connection.prepareStatement(sqlSaveDevSkill);
                statement.setLong(1,idDev);
                statement.setLong(2,skill.getId());
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
        }
        return getAll(idDev);
    }

    @Override
    public Set<Skill> getAll(long id) {
        Set<Skill> skills = new HashSet<>();
        String sqlAllSkillsSpecificDeveloper = "select skillId from skillsdeveloper where developerId = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlAllSkillsSpecificDeveloper);
            statement.setLong(1,id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Skill skill = new Skill();
                skill.setId(rs.getLong("skillId"));
                skill.setName(skillRepository.getById(rs.getLong("skillId")).getName());
                skills.add(skill);
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
        return skills;
    }

    @Override
    public void updateAll(Set<Skill> skillsNew, long id) {
        Set<Skill> skillsOld = getAll(id);
           boolean flag;
           for (Skill skillOld : skillsOld) {
               flag = false;
               for (Skill skillNew : skillsNew) {
                   if (skillNew.getId() == skillOld.getId()) {
                       flag = true;
                   }
               }
               if ( flag ) {
                   String sqlDeleteByIdSkill = "delete from skillsdeveloper where developerId = ? and skillId = ?";
                   try {
                       Class.forName("com.mysql.jdbc.Driver");
                       connection = DriverManager.getConnection(URL, USER, PASSWORD);
                       statement = connection.prepareStatement(sqlDeleteByIdSkill);
                       statement.setLong(1, id);
                       statement.setLong(2, skillOld.getId());
                       statement.executeUpdate();
                       skillsNew.remove(skillOld);
                   } catch (ClassNotFoundException | SQLException e) {
                       e.printStackTrace();
                   } finally {
                       try {
                           statement.close();
                           connection.close();
                       } catch (SQLException thr) {
                           thr.printStackTrace();
                       }
                   }
               }
       }
        if (skillsNew != null ) saveAll(id, skillsNew);
    }

    @Override
    public void deleteById(long id) {
        String sqlDeleteDeveloperSkills = "delete from skillsdeveloper where developerId = ? ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlDeleteDeveloperSkills);
            statement.setLong(1,id);
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
    }


}
