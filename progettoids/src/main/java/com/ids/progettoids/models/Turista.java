package com.ids.progettoids.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.utils.ContentUtils;

public class Turista extends Utente {

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

    public Turista(String _nome, String _cognome, String _email, String _password, String _username) {
        super(_nome, _cognome, _email, _password, _username);
        AggiungiRuolo();
    }

    public void salvaItinerari(List<Integer> idItinerari) {
        if (this.isAutenticato()) {
            String sql = "INSERT INTO ItinerariSalvati (username, idItinerario) VALUES (?, ?)";
            try (Connection conn = ConnettiDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, this.username);
                pstmt.setString(2, idItinerari.toString());
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.err.println("Errore durante il salvataggio dell'itinerario: " + e.getMessage());
            }
        }
    }

    public void salvaPOI(List<Integer> idPOI) {
        if (this.isAutenticato()) {
            String sql = "INSERT INTO POI_Salvati (username, ListaPOI) VALUES (?, ?)";
            try (Connection conn = ConnettiDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, this.username);
                pstmt.setString(2, idPOI.toString());
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.err.println("Errore durante il salvataggio del POI: " + e.getMessage());
            }
        }
    }

    public void aggiungiContenuto(Content content) {
        ContentUtils.creaContent(content.getMedia(), content.getData(), content.getAutore(), content.getDescrizione(), true);
    }
}