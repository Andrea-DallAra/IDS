package com.ids.progettoids.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.modelsInterface.TuristaInteface;
import com.ids.progettoids.utils.ContentUtils;

public class Turista extends Utente implements TuristaInteface{

    /**
     * Classe che rappresenta un turista
     * Gestisce le azioni del turista.
     */
    private boolean autenticato;

    public boolean isAutenticato() {
        return autenticato;
    }

    public void setAutenticato(boolean autenticato) {
        this.autenticato = autenticato;
    }

    @Override
    public void AggiungiRuolo() {

        ruoli.add(Ruolo.Turista);

    }
  protected Turista(Utente.Builder builder) {
        super(builder);
        AggiungiRuolo();
    }

    /**
     * Metodo per salvare un itinerario nel database
     * @param idItinerario
     */
    @Override
    public void salvaItinerario(int idItinerario) {
        if (this.isAutenticato()) {
            String selectSql = "SELECT idItinerari FROM ItinerariSalvati WHERE username = ?";
            String updateSql = "UPDATE ItinerariSalvati SET idItinerari = ? WHERE username = ?";
            String insertSql = "INSERT INTO ItinerariSalvati (username, idItinerari) VALUES (?, ?)";
    
            try (Connection conn = ConnettiDB.getConnection();
                 PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
    
                selectStmt.setString(1, this.username);
                ResultSet rs = selectStmt.executeQuery();
    
                List<Integer> idItinerariList = new ArrayList<>();
    
                if (rs.next()) {
                   
                    String existingIds = rs.getString("idItinerari");
                    if (existingIds != null && !existingIds.isEmpty()) {
                        String[] idArray = existingIds.replace("[", "").replace("]", "").split(",");
                        for (String id : idArray) {
                            idItinerariList.add(Integer.parseInt(id.trim()));
                        }
                    }
    
                   
                    if (!idItinerariList.contains(idItinerario)) {
                        idItinerariList.add(idItinerario);
                        String newIdList = idItinerariList.toString();
                        
                        updateStmt.setString(1, newIdList);
                        updateStmt.setString(2, this.username);
                        int rowsUpdated = updateStmt.executeUpdate();
                        System.out.println("Rows updated: " + rowsUpdated);
                    }
                } else {
                 
                    insertStmt.setString(1, this.username);
                    insertStmt.setString(2, "[" + idItinerario + "]");
                    int rowsInserted = insertStmt.executeUpdate();
                    System.out.println("Rows inserted: " + rowsInserted);
                }
    
                conn.close();
            } catch (SQLException e) {
                System.err.println("Errore durante il salvataggio dell'itinerario: " + e.getMessage());
            }
        }
    }
    


    @Override
    public void aggiungiContenuto(Content content) {
        ContentUtils.creaContent(content.getMedia(), content.getData(), content.getAutore(), content.getDescrizione(), this.autenticato);
    }
    @Override
    public void salvaPOI( String poiName) {
        String selectSql = "SELECT listaPOI FROM poi_salvati WHERE username = ?";
        String updateSql = "UPDATE poi_salvati SET listaPOI = ? WHERE username = ?";
        String insertSql = "INSERT INTO poi_salvati (username, listaPOI) VALUES (?, ?)";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            selectStmt.setString(1, this.username);
            ResultSet rs = selectStmt.executeQuery();

            List<String> poiList = new ArrayList<>();

            if (rs.next()) {
                
                String existingPOIs = rs.getString("listaPOI");
                if (existingPOIs != null && !existingPOIs.isEmpty()) {
                    String[] poiArray = existingPOIs.replace("[", "").replace("]", "").split(",");
                    for (String poi : poiArray) {
                        poiList.add(poi.trim());
                    }
                }

                
                if (!poiList.contains(poiName)) {
                    poiList.add(poiName);
                    String newPoiList = poiList.toString();
                    
                    updateStmt.setString(1, newPoiList);
                    updateStmt.setString(2, this.username);
                    int rowsUpdated = updateStmt.executeUpdate();
                    System.out.println("Rows updated: " + rowsUpdated);
                }
            } else {
                
                insertStmt.setString(1, this.username);
                insertStmt.setString(2, "[" + poiName + "]");
                int rowsInserted = insertStmt.executeUpdate();
                System.out.println("Rows inserted: " + rowsInserted);
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio del POI: " + e.getMessage());
        }
    }
}