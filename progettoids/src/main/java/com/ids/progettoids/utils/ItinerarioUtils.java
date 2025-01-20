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

   
    private static ArrayList<POI> parsePOIList(String listaPOIStr) {
        ArrayList<POI> listaPOI = new ArrayList<>();

        if (listaPOIStr != null && !listaPOIStr.isEmpty()) {
            String[] nomiPOI = listaPOIStr.split(",");
            for (String nome : nomiPOI) {
                listaPOI.addAll(POIutils.getPOI(nome.trim()));
            }
        }

        return listaPOI;
    }

    public static void creaItinerario(int idItinerario, ArrayList<POI> listaPOI, boolean daApprovare) {
         String sql = "";
        if(!daApprovare)
        {
         sql = "INSERT INTO Itinerario (idItinerario, ListaPOI) VALUES (?, ?)";
        }
        else{ sql = "INSERT INTO Itinerario_DaApprovare (idItinerario, ListaPOI) VALUES (?, ?)";}

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idItinerario);

            StringBuilder sb = new StringBuilder();

            for (POI poi : listaPOI) {
                sb.append(poi.getNome()).append(",");
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            pstmt.setString(2, sb.toString());

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'itinerario: " + e.getMessage());
        }
    }
    }
