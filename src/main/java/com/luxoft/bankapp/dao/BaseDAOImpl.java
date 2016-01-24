package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.DAOException;
import org.h2.jdbc.JdbcConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by LChlebda on 2016-01-20.
 */
public class BaseDAOImpl implements BaseDAO{

    private Connection con = null;
    private String databasePathHome = "jdbc:h2:C:\\Users\\Łukasz\\IdeaProjects\\BankApplication\\BankAppliactionDB";
    private String databasePathWork = "jdbc:h2:C:\\Users\\LChlebda\\IdeaProjects\\BankApplication\\BankAppliactionDB";

    public Connection getConnection() {
        return con;
    }

    @Override
    public Connection openConnection() throws DAOException {
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(databasePathHome,
                    "sa",
                    ""
            );
            return con;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        }
    }

    @Override
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
