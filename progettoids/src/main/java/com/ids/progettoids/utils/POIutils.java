package com.ids.progettoids.utils;


import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Coordinate;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.ConnettiDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class POIutils {

   
    public static ArrayList<POI> getPOI(String nome) {
        ArrayList<POI> listaPOI = new ArrayList<>();
        String sql = (nome == null) ? "SELECT * FROM POI" : "SELECT * FROM POI WHERE Nome = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (nome != null) {
                pstmt.setString(1, nome);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nomePOI = rs.getString("Nome");
                String coordinateStr = rs.getString("Coordinate");
                String descrizione = rs.getString("Descrizione");
                int idContent = rs.getInt("idContent");

                Content media = getContent(idContent);

               
                String[] coordinateSplit = coordinateStr.split(",");
                Coordinate coordinate = new Coordinate(
                        Double.parseDouble(coordinateSplit[0].trim()),
                        Double.parseDouble(coordinateSplit[1].trim())
                );

                POI poi = new POI(nomePOI, coordinate, descrizione, media);
                listaPOI.add(poi);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei POI: " + e.getMessage());
        }

        return listaPOI;
    }

  
    public static Content getContent(int idContent) {
        Content content = null;
        String sql = "SELECT * FROM Content WHERE idContent = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContent);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String mediaUrl = rs.getString("MediaUrl");
                String data = rs.getString("Data");
                String autore = rs.getString("Autore");
                String descrizione = rs.getString("Descrizione");
                

                content = new Content(mediaUrl, data, autore, descrizione);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Content: " + e.getMessage());
        }

        return content;
    }

    public static void creaPOI(String nome, Coordinate coordinate, String descrizione, Content media, boolean daApprovare) {
        String sql= "";
        if(!daApprovare){
             sql = "INSERT INTO POI (Nome, Coordinate, Descrizione, idContent) VALUES (?, ?, ?, ?)";  

        }
        else
        {
             sql = "INSERT INTO POI_DaApprovare (Nome, Coordinate, Descrizione, idContent) VALUES (?, ?, ?, ?)";  

        }

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {        

            pstmt.setString(1, nome);
            pstmt.setString(2, coordinate.toString());
            pstmt.setString(3, descrizione);
            pstmt.setInt(4, media.getIdContent());

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione del POI: " + e.getMessage());
        }
    }

    public static void EliminaPOI(String poi) {
     
        String sql = "DELETE FROM POI WHERE Nome = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, poi);

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del POI: " + e.getMessage());
        }
    }
}
