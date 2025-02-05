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

    /**
     * Classe che rappresenta un gestore
     * Gestisce le azioni del gestore
     */
    @Override
    public void AggiungiRuolo() 
    {    
        ruoli.add(Ruolo.Gestore);     
    }
    public Gestore() 
    {

    }
    public Gestore(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       super(_nome, _cognome, _email, _password, _username);
       AggiungiRuolo();
    }

/**
 * Metodo per ottenere le richieste di cambio di ruolo dal database.
 * @return le richieste di cambio di ruolo come un Map<String, String> 
 */
      public Map<String, String> getRichiesteCambioRuolo() {
        
        String sql = "SELECT username, ruolo FROM RichiediRuolo";
        
       
        Map<String, String> richiesteRuoli = new HashMap<>();

        
        try (Connection conn =ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            
            while (rs.next()) {
                String username = rs.getString("username").trim();  
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
    /**
     * Metodo per modificare il ruolo di un utente nel database.
     * @param username l'utente da modificare
     * @param _ruolo il ruolo da aggiungere
     */
    public void EditaRuolo(String username, String _ruolo) 
    {
        Utente nuovoUtente = new Utente();
        nuovoUtente.SetUsername(username);
        nuovoUtente.AggiungiRuolo(Ruolo.valueOf(_ruolo));
        nuovoUtente.SalvaRuoliDB(username);
    }
/**
 * Metodo per eliminare un ruolo dall'utente dal database.
 * @param username l'utente a cui rimuovere un ruolo
 */
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
