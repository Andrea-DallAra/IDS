package com.ids.progettoids.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.modelsInterface.ContributoreInterface;

public class Contributore extends Utente implements ContributoreInterface{

    /**
     * Classe che rappresenta un contributore
     * Gestisce le azioni del contributore
     */
    private boolean autenticato;
    public boolean isAutenticato() {
        if(ruoli.contains(Ruolo.ContributoreAutenticato)) autenticato = true;
        return autenticato;
    }
    public void setAutenticato(boolean autenticato) {
        this.autenticato = autenticato;
    }
    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Contributore);
        
    }

    protected Contributore(Utente.Builder builder) {
        super(builder);
        
        AggiungiRuolo();
    }
    /**
     * Metodo per partecipare ad un contest
     * @param contest il contest a cui si vuole partecipare
     * @param content il contenuto da caricare
     */
    @Override
    public void partecipaContest(Contest contest, Content content){
        String sql1 = "SELECT listaContent FROM Contest WHERE nome = ?";
        String sql2 = "UPDATE Contest SET listaContent = ? WHERE nome = ?";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, contest.getNome());
            ResultSet rs = pstmt.executeQuery();
            List<Integer> contentsIdList = new ArrayList<>();
            if (rs.next()) {
                String stringaContentId=rs.getString("listaContent");
                String[] contentSplit = stringaContentId.split(",");
                for (String s : contentSplit) {
                    contentsIdList.add(Integer.valueOf(s));
                }
            }
            contentsIdList.add(content.getIdContent()); 
            pstmt2.setString(1, contentsIdList.toString());
            pstmt2.setString(2, contest.getNome());
            pstmt2.executeUpdate();
            conn.close();
            }catch (SQLException e) {
            System.err.println("Errore durante l'inserimento nel contest: " + e.getMessage());
        }
    }

}
