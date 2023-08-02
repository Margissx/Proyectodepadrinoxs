package com.example.demo1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    public static Connection conMySqlServerDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
            return null;
        }
    }
    public static Connection sqlDBConection()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Connection con = DriverManager.getConnection("jdbc:sqlserver://nombre_del_servidor:puerto;database=javaproject;", "root", "root");
            //Connection con = DriverManager.getConnection("jdbc:sqlserver://.;integratedSecurity=true;databaseName=javaproject;port=1433;");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=javaproject;integratedSecurity=false", "dmGowther", "Rome2015");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
            return null;
        }
    }
}
