package com.ids.progettoids.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Coordinate;
import com.ids.progettoids.models.POI;

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
                String idContent = rs.getString("idContent");
                List<Integer> idContentList = new ArrayList<>();
                List<Content> media = new ArrayList<>();
                idContentList = parseStringList(idContent);
                if(!idContentList.isEmpty() ) {
                    for (Integer idInteger : idContentList) {
                        media.add(getContent(idInteger));
                    }
                }
               
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
   private static List<Integer> parseStringList(String stringList) {
    if (stringList == null || stringList.trim().isEmpty()) {
        return new ArrayList<>(); 
    }

    return Arrays.stream(stringList.split(","))
                 .map(String::trim)             
                 .filter(s -> !s.isEmpty())   
                 .map(Integer::parseInt)        
                 .collect(Collectors.toList());

    }

    public static ArrayList<POI> getAllPOIdaApprovare() {
        ArrayList<POI> listaPOI = new ArrayList<>();
        String sql = "SELECT * FROM POI_DaApprovare";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String nomePOI = rs.getString("Nome");
                String coordinateStr = rs.getString("Coordinate");
                String descrizione = rs.getString("Descrizione");
                String idContent = rs.getString("idContent");
                List<Integer> idContentList = new ArrayList<>();
                List<Content> media = new ArrayList<>();
                idContentList = parseStringList(idContent);
                if(!idContentList.isEmpty() ) {
                    for (Integer idInteger : idContentList) {
                        media.add(getContent(idInteger));
                    }
                }
                
              

               
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

    public static POI getPOIdaApprovare(String nome) {
        String sql = "SELECT * FROM POI_DaApprovare WHERE Nome =?";
        POI poi = new POI(nome, null, null,new ArrayList<>());
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))           
             {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nomePOI = rs.getString("Nome");
                String coordinateStr = rs.getString("Coordinate");
                String descrizione = rs.getString("Descrizione");

                

               
                String[] coordinateSplit = coordinateStr.split(",");
                Coordinate coordinate = new Coordinate(
                        Double.parseDouble(coordinateSplit[0].trim()),
                        Double.parseDouble(coordinateSplit[1].trim())
                );
               
                poi = new POI(nomePOI, coordinate, descrizione, new ArrayList<>());
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei POI: " + e.getMessage());
        }
        return poi;
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

   public static void creaPOI(String nome, Coordinate coordinate, String descrizione, List<Content> media, boolean daApprovare) {
    String sql;
    if (!daApprovare) {
        sql = "INSERT INTO POI (Nome, Coordinate, Descrizione, idContent) VALUES (?, ?, ?, ?)";
    } else {
        sql = "INSERT INTO POI_DaApprovare (Nome, Coordinate, Descrizione, idContent) VALUES (?, ?, ?, ?)";
    }

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, nome);
        pstmt.setString(2, coordinate.toString());
        pstmt.setString(3, descrizione);
        pstmt.setString(4, "0");  
        

        pstmt.executeUpdate();
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

    public static void collegaContent(Content content , String poi, boolean daApprovare) {
      int idContent = 0;
        if(!daApprovare){
         idContent = ContentUtils.getIdContent(content.getMedia(), content.getData(), content.getAutore(), content.getDescrizione());
          }
          else
          {
            idContent =-ContentUtils.getIdContentDaApprovare(content.getMedia(), content.getData(), content.getAutore(), content.getDescrizione());

          }
        String  sqlPOI = "UPDATE POI SET idContent = ? WHERE Nome = ?";
      
        
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmtPOI = conn.prepareStatement(sqlPOI))
              {
            
            pstmtPOI.setInt(1, idContent);
            pstmtPOI.setString(2, poi);
            pstmtPOI.executeUpdate();
            
            
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'idContent nei POI: " + e.getMessage());
        }
    }
}