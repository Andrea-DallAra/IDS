package com.ids.progettoids;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnettiDB {
    // Stringa di connessione per MySQL
    private static final String CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/CamerinoMapsDB?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Carica il driver MySQL (opzionale con JDBC 4+)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Crea la connessione
            con = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            System.out.println("Connessione al database riuscita!");
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL non trovato: " + e.getMessage());
        }

        if (con == null) {
            System.out.println("Connessione al database fallita.");
        }

        return con;
    }
}
