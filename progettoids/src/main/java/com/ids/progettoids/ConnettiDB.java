package com.ids.progettoids;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnettiDB {
    /**
     * Stringa di connessione per MySQL e credenziali
     */ 
    private static final String CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/CamerinoMapsDB?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    /**
     * Restituisce un oggetto Connection per la connessione al database.
     * Il metodo si occupa di caricare il driver MySQL e creare la connessione
     * al database utilizzando le credenziali indicate. Se la connessione
     * fallisce, il metodo restituisce null.
     * @return un oggetto Connection per la connessione al database o null
     * in caso di fallimento.
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
           
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
