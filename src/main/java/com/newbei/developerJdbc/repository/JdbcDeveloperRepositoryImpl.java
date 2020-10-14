package com.newbei.developerJdbc.repository;

import com.newbei.developerJdbc.model.Developer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.newbei.developerJdbc.Properties.*;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository{

    private AccountRepository accountRepository = new JdbcAccountRepositoryImpl();
    private SpecificDeveloperSkillRepository skillRepository = new SpecificDeveloperSkillRepositoryImpl();
    private Connection connection = null;
    private PreparedStatement statement = null;


    @Override
    public Developer save(Developer developer){
        String sqlInsertDeveloper = "insert into developer(name, accountId) values (?,?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlInsertDeveloper,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,developer.getName());
            statement.setLong(2,developer.getAccount().getId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    developer.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Saving failed, no ID obtained.");
                }
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
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        String sqlUpdateDeveloperName = "update developer set name = ? where id = ? ";
        try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                statement = connection.prepareStatement(sqlUpdateDeveloperName);
                statement.setString(1,developer.getName());
                statement.setLong(2,developer.getId());
                statement.executeUpdate();
                skillRepository.updateAll(developer.getSkills(),developer.getId());
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
        return developer;
    }

    @Override
    public List<Developer> getAll() {
        String sqlGetAllDeveloper = "select * from developer ";
        Statement statement = null;
        List<Developer> tempListDeveloper = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlGetAllDeveloper);
            while(rs.next()){
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setAccount(accountRepository.getById(rs.getLong("accountId")));
                developer.setSkills(skillRepository.getAll(developer.getId()));
                tempListDeveloper.add(developer);
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
        return tempListDeveloper;
    }

    @Override
    public Developer getById(Long aLong) {
        String sqlGetDeveloper = "select * from developer where id = ? ";
        Developer developer = new Developer();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlGetDeveloper);
            statement.setLong(1,aLong);
            ResultSet rs = statement.executeQuery();
            rs.first();
            developer.setId(rs.getLong("id"));
            developer.setName(rs.getString("name"));
            developer.setAccount(accountRepository.getById(rs.getLong("accountId")));
            developer.setSkills(skillRepository.getAll(developer.getId()));
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
        return developer;
    }

    @Override
    public Developer deleteById(Long aLong) {
        Developer developer = getById(aLong);
        accountRepository.deleteById(developer.getAccount().getId());
        skillRepository.deleteById(aLong);
        return null;
    }

    public long authentication(String login, String password){
        List<Developer> developers = getAll();
        long accountId = accountRepository.authentication(login,password);
        if (accountId != -1) {
            for (Developer developer:developers){
                if (developer.getAccount().getId() == accountId) return developer.getId();
            }
        }
        return -1;
    }
}
