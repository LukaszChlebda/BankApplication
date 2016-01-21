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

    public Connection con = null;

    public Connection getConnection() {
        return con;
    }

    @Override
    public Connection openConnection() throws DAOException {
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:C:\\Users\\LChlebda\\IdeaProjects\\BankApplication\\BankAppliactionDataBase",
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
