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
    /**
     * Metodo che recupera uno o piu' itinerari.
     * Se id == -1, restituisce tutti gli itinerari.
     * @param id l'id dell'itinerario da recuperare, -1 per tutti
     * @return una lista di Itinerario
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

   
    /**
     * Metodo che, dato una stringa contenente i nomi dei POI,
     * restituisce una lista di POI contenenti i POI corrispondenti.
     * Se la stringa e' vuota, restituisce una lista vuota.
     * @param listaPOIStr la stringa contenente i nomi dei POI
     * @return una lista di POI
     */
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



    /**
     * Metodo che crea un itinerario con i POI nella listaPOI.
     * Se daApprovare == true, l'itinerario viene creato nella tabella Itinerario_DaApprovare, altrimenti nella tabella Itinerario.
     * @param listaPOI la lista di POI da inserire nell'itinerario
     * @param daApprovare true se l'itinerario deve essere creato nella tabella Itinerario_DaApprovare, false altrimenti
     */
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

            if(listaPOI.size() >1)
            {
            for (POI poi : listaPOI) {
                sb.append(poi.getNome()).append(",");
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            }
            else sb.append(listaPOI.get(0).getNome());

            pstmt.setString(1, sb.toString());

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'itinerario: " + e.getMessage());
        }
    }
   


/**
 * Metodo per eliminare un itinerario dal database.
 * 
 * @param idItinerario l'identificativo dell'itinerario da eliminare
 */

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

    /**
     * Metodo che recupera tutti gli itinerari da approvare
     * @return lista di Itinerario
     */
    public static ArrayList<Itinerario> getAllItineraridaApprovare() {
        ArrayList<Itinerario> listaItinerario = new ArrayList<>();
        String sql = "SELECT * FROM Itinerario_DaApprovare";

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

    /**
     * Metodo che recupera un itinerario da approvare.
     * @param idItinerario l'id dell'itinerario da recuperare.
     * @return l'itinerario con l'id specificato, oppure null se non esiste.
     */
    public static Itinerario getItinerariodaApprovare(int idItinerario) {
        String sql = "SELECT * FROM Itinerario_DaApprovare WHERE idItinerario = ?";
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