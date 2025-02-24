package com.ids.progettoids.models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.modelsInterface.AnimatoreInterface;
import com.ids.progettoids.utils.ContentUtils;
public class Animatore extends Utente implements AnimatoreInterface{
    
    /**
     * Classe che rappresenta un animatore
     * Gestisce le azioni dell'animatore
     */
    
    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Animatore);
        
    }
    protected Animatore(Utente.Builder builder) {
        super(builder);
        AggiungiRuolo();
    }

    /**
     * Metodo per creare un contest
     * @param contest il contest da creare
     */
    @Override
    public void creaContest(Contest contest){
        String sql = "INSERT INTO contest (nome, descrizione, listaContent) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contest.getNome());
            pstmt.setString(2, contest.getDescrizione());
            pstmt.setString(3, null); 
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Contest: " + e.getMessage());
        }
    }

 /**
  * Metodo per gestire un contest
  * @param contest il contest da gestire
  * @return la lista dei contenuti
  */
    @Override
    public List<Content> gestisciContest(Contest contest){
        List<Content> contents = new ArrayList<>();
        String sql = "SELECT * FROM Contest where nome=?";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contest.getNome());  
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String content=rs.getString("listaContent");
                content = content.replaceAll("[\\[\\]]", "").trim();
                String[] contentSplit = content.split(",");
                for (String s : contentSplit) {
                    s = s.trim();
                    contents.add(ContentUtils.getContent(Integer.parseInt(s)));
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Contest: " + e.getMessage());
        }
        return contents;
    }

    /**
     * Metodo per dichiarare il vincitore
     * @param usernameVincitore 
     * @param contestNome
     * @return la stringa di dichiarazione
     */
    @Override
    public String dichiaraVincitore(String usernameVincitore , String contestNome){
        
        return "Il vincitore del contest "+contestNome+"  e' "+ usernameVincitore;
    }

    

}
