package com.newbei.developerJdbc.repository;

import com.newbei.developerJdbc.model.Account;
import com.newbei.developerJdbc.model.AccountStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.newbei.developerJdbc.Properties.*;

public class JdbcAccountRepositoryImpl implements AccountRepository{

    private Connection connection = null;
    private PreparedStatement statement = null;

    @Override
    public Account save(Account account) {
        String sqlInsertAccount = "insert into account(login, password, account_status) values (?,?,?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlInsertAccount,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,account.getLogin());
            statement.setString(2,account.getPassword());
            statement.setString(3,account.getAccountStatus().toString());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong(1));
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
        return account;
    }

    @Override
    public Account update(Account account) {
        String sqlUpdateAccount = "update account set login = ?, password = ?, account_status = ? where id = ? ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlUpdateAccount);
            statement.setString(1,account.getLogin());
            statement.setString(2,account.getPassword());
            statement.setString(3,account.getAccountStatus().toString());
            statement.setLong(4,account.getId());
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
        return account;
    }

    @Override
    public List<Account> getAll() {
        String sqlGetAllAccount = "select * from account ";
        Statement statement = null;
        List<Account> tempListAccount = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlGetAllAccount);
            while(rs.next()){
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setPassword(rs.getString("password"));
                account.setLogin(rs.getString("login"));
                account.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));
                tempListAccount.add(account);
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
        return tempListAccount;
    }

    @Override
    public Account getById(Long aLong) {
        Account account = new Account();
        String sqlGetByIdAccount = "select * from account where id = ? ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlGetByIdAccount);
            statement.setLong(1,aLong);
            ResultSet rs = statement.executeQuery();
            rs.first();
            account.setId(rs.getLong("id"));
            account.setPassword(rs.getString("password"));
            account.setLogin(rs.getString("login"));
            account.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));
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
        return account;
    }

    @Override
    public Account deleteById(Long aLong) {
        Account tempAccountById = getById(aLong);
        Account returnAccount = new Account(tempAccountById.getId(),tempAccountById.getLogin(),tempAccountById.getPassword(),tempAccountById.getAccountStatus());
        tempAccountById.setPassword("");
        tempAccountById.setLogin("");
        tempAccountById.setAccountStatus(AccountStatus.DELETED);
        update(tempAccountById);
        return returnAccount;
    }

    @Override
    public long authentication(String login, String password){
        String sqlAuthentication = "select * from account where login = ? and password = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.prepareStatement(sqlAuthentication);
            statement.setString(1,login);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            if (!rs.next() ) {
                return -1;
            } else {
                return rs.getLong("id");
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
        return -1;
    }
}
