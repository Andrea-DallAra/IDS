package com.ids.progettoids.models;

import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.ConnettiDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Itinerario {

    private int idItinerario;
    private ArrayList<POI> listaPOI;

    public Itinerario(int idItinerario, ArrayList<POI> listaPOI) {
        this.idItinerario = idItinerario;
        this.listaPOI = listaPOI;
    }

    public int getIdItinerario() {
        return idItinerario;
    }

    public ArrayList<POI> getListaPOI() {
        return listaPOI;
    }

    /**
     * Recupera uno o più itinerari.
     * Se id == -1, restituisce tutti gli itinerari.
     */
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
}
