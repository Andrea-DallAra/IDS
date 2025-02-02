package com.ids.progettoids.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
public class Gestore extends Utente {

    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Gestore);
        
    }

    public Gestore(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       super(_nome, _cognome, _email, _password, _username);
       AggiungiRuolo();
    }


      public Map<String, String> getRichiesteCambioRuolo() {
        // Query per recuperare username e ruolo dalla tabella RichiediRuolo
        String sql = "SELECT username, ruolo FROM RichiediRuolo";
        
        // Creazione della HashMap per memorizzare i dati
        Map<String, String> richiesteRuoli = new HashMap<>();

        // Connessione al database e recupero dati
        try (Connection conn =ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Itero sui risultati e li inserisco nella HashMap
            while (rs.next()) {
                String username = rs.getString("username").trim();  // Elimino eventuali spazi
                String ruolo = rs.getString("ruolo");
                richiesteRuoli.put(username, ruolo);
            }

            System.out.println("Richieste caricate con successo!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero delle richieste: " + e.getMessage());
        }

        return richiesteRuoli;
    }
    //da collegare con l'hud
    public void EditaRuolo(String username, String _ruolo) 
    {
        Utente nuovoUtente = new Utente(username, null, null, null, null);
        nuovoUtente.AggiungiRuolo(Ruolo.valueOf(_ruolo));
        nuovoUtente.SalvaRuoliDB(username);
    }

    public void removeRuoloFromDatabase(String username) {
        String sql = "DELETE FROM RichiediRuolo WHERE username = ?";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del Content: " + e.getMessage());
        }
    }
    

}
