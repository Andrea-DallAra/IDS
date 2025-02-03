package com.ids.progettoids.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.models.POI;

public class ItinerarioUtils {
     public static ArrayList<Itinerario> getItinerario(int id) {
        ArrayList<Itinerario> listaItinerari = new ArrayList<>();

        String sql = (id == -1) ? "SELECT * FROM Itinerario" : "SELECT * FROM Itinerario WHERE idItinerario = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (id != -1) {
                pstmt.setInt(1, id);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idItinerario = rs.getInt("idItinerario");
                String listaPOIStr = rs.getString("ListaPOI");

                ArrayList<POI> listaPOI = parsePOIList(listaPOIStr);

                Itinerario itinerario = new Itinerario(idItinerario, listaPOI);
                listaItinerari.add(itinerario);
            }

        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli itinerari: " + e.getMessage());
        }

        return listaItinerari;
    }

   
    public static ArrayList<POI> parsePOIList(String listaPOIStr) {
        ArrayList<POI> listaPOI = new ArrayList<>();

        if (listaPOIStr != null && !listaPOIStr.isEmpty()) {
            String[] nomiPOI = listaPOIStr.split(",");
            for (String nome : nomiPOI) {
                listaPOI.addAll(POIutils.getPOI(nome.trim()));
            }
        }

        return listaPOI;
    }

    public static void creaItinerario(ArrayList<POI> listaPOI, boolean daApprovare) {
         String sql = "";
        if(!daApprovare)
        {
         sql = "INSERT INTO Itinerario (ListaPOI) VALUES (?)";
        }
        else{ sql = "INSERT INTO Itinerario_DaApprovare ( ListaPOI) VALUES (?)";}

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            

            StringBuilder sb = new StringBuilder();

            for (POI poi : listaPOI) {
                sb.append(poi.getNome()).append(",");
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            pstmt.setString(1, sb.toString());

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'itinerario: " + e.getMessage());
        }
    }
   

    public static void EliminaItinerario(int idItinerario) {
        String sql = "DELETE FROM Itinerario WHERE idItinerario = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idItinerario);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione dell'itinerario: " + e.getMessage());
        }
    }

    public static ArrayList<Itinerario> getAllItineraridaApprovare() {
        ArrayList<Itinerario> listaItinerario = new ArrayList<>();
        String sql = "SELECT * FROM Itinerari_DaApprovare";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idItinerario=rs.getInt("idItinerario");
                String listaPOIStringa = rs.getString("ListaPOI");
                ArrayList<POI> listaPOI=parsePOIList(listaPOIStringa);
                Itinerario itinerario = new Itinerario(idItinerario,listaPOI);
                listaItinerario.add(itinerario);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dell'Itinerario: " + e.getMessage());
        }

        return listaItinerario;
    }

    public static Itinerario getItinerariodaApprovare(int idItinerario) {
        String sql = "SELECT * FROM Itinerari_DaApprovare WHERE idItinerario = ?";
        ArrayList<POI> listaPOI=new ArrayList<>();
        Itinerario itinerario = new Itinerario(idItinerario,listaPOI);
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String listaPOIStringa = rs.getString("ListaPOI");
                listaPOI=parsePOIList(listaPOIStringa);
                itinerario = new Itinerario(idItinerario, listaPOI);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dell'Itinerario: " + e.getMessage());
        }
        return itinerario;
    }
}