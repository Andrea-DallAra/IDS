package com.ids.progettoids;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnettiDB {
    
    private static final String CONNECTION_STRING = "jdbc:sqlserver://localhost;databaseName=master;integratedSecurity=true;";
    
    
    public static Connection getConnection() {
        Connection con = null;
        try {
               
            con = DriverManager.getConnection(CONNECTION_STRING);
            
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database: " + e.getMessage());
        } 

            
    if (con == null) {
        System.out.println("Connessione al database fallita.");
        
    }
        return con;
    }
}
