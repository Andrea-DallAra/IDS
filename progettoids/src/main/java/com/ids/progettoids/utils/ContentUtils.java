package com.ids.progettoids.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.models.Content;

public class ContentUtils {
    
    /**
     * Recupera l'idContent dal database
     * @param media 
     * @param data
     * @param autore
     * @param descrizione
     * @return idContent
     */
    public static int getIdContent(String media, String data, String autore, String descrizione) {
    { int idContent = -1;
    String sql = "SELECT idContent FROM Content WHERE mediaUrl = ? AND data = ? AND autore = ? AND descrizione = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, media);
        pstmt.setString(2, data);
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
    /**
     * Recupera l'idContent di un Content da approvare dal database
     * @param media
     * @param data
     * @param autore
     * @param descrizione
     * @return idContent
     */
    public static int getIdContentDaApprovare(String media, String data, String autore, String descrizione) {
        { int idContent = -1;
        String sql = "SELECT idContent FROM Content_DaApprovare WHERE mediaUrl = ? AND data = ? AND autore = ? AND descrizione = ?";
    
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, media);
            pstmt.setString(2, data);
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
        /**
         * Metodo per creare un content
         * @param media
         * @param data
         * @param autore
         * @param descrizione
         * @param daApprovare 
         */
    public static void creaContent(String media, String data, String autore, String descrizione, boolean daApprovare) {
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
        pstmt.setString(2, data);
        pstmt.setString(3, autore);
        pstmt.setString(4, descrizione);

        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {    
        System.err.println("Errore durante la creazione del Content: " + e.getMessage());
    }
    }

    /**
     * Recupera un Content dal database dato l'id
     * @param idContent
     * @return il content con l'id passato
     */
    public static Content getContent(int idContent) {
        Content content = null;
        String sql = "SELECT * FROM Content WHERE idContent = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContent);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                content = new Content(null,null,null,null);                
                content.setMedia(rs.getString("MediaUrl"));
                content.setData(rs.getString("Data"));
                content.setAutore(rs.getString("Autore"));
                content.setDescrizione(rs.getString("Descrizione"));
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Content: " + e.getMessage());
        }

        return content;
    }

    /**
     * Elimina un Content dal database
     * @param idContent
    */
    public static void EliminaContent(int idContent) {
        String sql = "DELETE FROM Content WHERE idContent = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContent);

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del Content: " + e.getMessage());
        }
    }

    /**
     * Recupera tutti i Content dal database
     * @return i content con gli id ArrayList<HashMap<Integer,Content>>
     */
    public static ArrayList<HashMap<Integer,Content>> getAllContent() {
        ArrayList<HashMap<Integer,Content>> listaContent = new ArrayList<>();
        String sql = "SELECT * FROM Content";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                
                String media = rs.getString("MediaUrl");
                String data = rs.getString("Data");
                String autore = rs.getString("Autore");
                String descrizione = rs.getString("Descrizione");
                int idContent = rs.getInt("idContent");
                Content content = new Content(media, data, autore, descrizione);
                HashMap<Integer, Content> map = new HashMap<>();
                map.put(idContent, content);
                listaContent.add(map);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Content: " + e.getMessage());
        }
        return listaContent;
    }

    /**
     * Recupera tutti i Content da approvare dal database
     * @return i content con gli id ArrayList<HashMap<Integer,Content>>
     */
    public static ArrayList<HashMap<Integer,Content>> getAllContentdaApprovare() {
        ArrayList<HashMap<Integer,Content>> listaContent = new ArrayList<>();
        String sql = "SELECT * FROM Content_DaApprovare";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                
                String media = rs.getString("MediaUrl");
                String data = rs.getString("Data");
                String autore = rs.getString("Autore");
                String descrizione = rs.getString("Descrizione");
                int idContent = rs.getInt("idContent");
                Content content = new Content(media, data, autore, descrizione);
                HashMap<Integer, Content> map = new HashMap<>();
                map.put(idContent, content);
                listaContent.add(map);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Content: " + e.getMessage());
        }
        return listaContent;
    }

    /**
     * Recupera un Content da approvare dal database
     * @param idContent
     * @return il content da approvare con l'id passato
     */
    public static Content getContentdaApprovare(int idContent) {
        String sql = "SELECT * FROM Content_DaApprovare WHERE idContent = ?";
        Content content = new Content("", "", "", "");
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContent);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String media = rs.getString("MediaUrl");
                String data = rs.getString("Data");
                String autore = rs.getString("Autore");
                String descrizione = rs.getString("Descrizione");
                content = new Content(media, data, autore, descrizione);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Content: " + e.getMessage());
        }
        return content;
    }
    /**
     * Recupera tutti i Content di un utente
     * @param username
     * @return i content di un utente
     */
    public static List<Content> getUserContents(String username) {
        List<Content> contents = new ArrayList<>();
        String sql = "SELECT * FROM Content WHERE Autore = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String media = rs.getString("MediaUrl");
                String data = rs.getString("Data");
                String autore = rs.getString("Autore");
                String descrizione = rs.getString("Descrizione");
                Content content = new Content(media, data, autore, descrizione);
                contents.add(content);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Content: " + e.getMessage());
        }

        return contents;
    }
}

