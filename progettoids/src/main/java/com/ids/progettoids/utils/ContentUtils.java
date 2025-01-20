package com.ids.progettoids.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ids.progettoids.ConnettiDB;

public class ContentUtils {
    
    public static int getIdContent(String media, Date data, String autore, String descrizione) {
    { int idContent = -1;
    String sql = "SELECT idContent FROM Content WHERE media = ? AND data = ? AND autore = ? AND descrizione = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, media);
        pstmt.setString(2, data.toString());
        pstmt.setString(3, autore);
        pstmt.setString(4, descrizione);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            idContent = rs.getInt("idContent");
        }
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante il recupero dell'idContent: " + e.getMessage());
    }

    return idContent;}
    }

    public static void creaContent(String media, Date data, String autore, String descrizione, boolean daApprovare) {
        String sql = "";
        if(!daApprovare){
         sql = "INSERT INTO Content (MediaUrl, Data, Autore, Descrizione) VALUES (?, ?, ?, ?)"; 
        }
        else
        {
         sql = "INSERT INTO Content_DaApprovare (MediaUrl, Data, Autore, Descrizione) VALUES (?, ?, ?, ?)";
        }
        
        try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, media);
        pstmt.setString(2, data.toString());
        pstmt.setString(3, autore);
        pstmt.setString(4, descrizione);

        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {    
        System.err.println("Errore durante la creazione del Content: " + e.getMessage());
    }
    }
}

