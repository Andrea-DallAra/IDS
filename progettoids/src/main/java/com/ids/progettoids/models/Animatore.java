package com.ids.progettoids.models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.utils.ContentUtils;
public class Animatore extends Utente {
    
    
    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Animatore);
        
    }

    public Animatore(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       super(_nome, _cognome, _email, _password, _username);
       AggiungiRuolo();
    }

    public void creaContest(Contest contest){
        String sql = "INSERT INTO Contest (nome,descrizione) VALUES (?,?)";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contest.getNome());
            pstmt.setString(2, contest.getDescrizione());
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Contest: " + e.getMessage());
        }
    }


    public List<Content> gestisciContest(Contest contest){
        List<Content> contents = new ArrayList<>();
        String sql = "SELECT * FROM Contest where nome=?";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contest.getNome());  
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String content=rs.getString("listaContent");
                String[] contentSplit = content.split(",");
                for (String s : contentSplit) {
                    contents.add(ContentUtils.getContent(Integer.parseInt(s)));
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Contest: " + e.getMessage());
        }
        return contents;
    }

    
    public void dichiaraVincitore(String usernameVincitore , String contestNome){
        System.out.println("Il vincitore del contest "+contestNome+" &egrave "+usernameVincitore);
    }

    

}
