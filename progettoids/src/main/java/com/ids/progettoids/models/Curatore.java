package com.ids.progettoids.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
public class Curatore extends Utente {

    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Curatore);
        
    }

    public Curatore(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       super(_nome, _cognome, _email, _password, _username);
       AggiungiRuolo();
    }

  public void ApprovaPOI(POI poi) {
    String sqlElimina = "DELETE FROM POI_DaApprovare WHERE Nome = ? AND Coordinate = ? AND Descrizione = ? AND idContent = ?";
    String sqlInserisci = "INSERT INTO POI (Nome, Coordinate, Descrizione, idContent) VALUES (?, ?, ?, ?)";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmtElimina = conn.prepareStatement(sqlElimina);
         PreparedStatement pstmtInserisci = conn.prepareStatement(sqlInserisci)) {

        pstmtElimina.setString(1, poi.getNome());
        pstmtElimina.setString(2, poi.getCoordinate().toString());
        pstmtElimina.setString(3, poi.getDescrizione());
        pstmtElimina.setInt(4, poi.getMedia().getIdContent());

        pstmtInserisci.setString(1, poi.getNome());
        pstmtInserisci.setString(2, poi.getCoordinate().toString());
        pstmtInserisci.setString(3, poi.getDescrizione());
        pstmtInserisci.setInt(4, poi.getMedia().getIdContent());

        pstmtElimina.executeUpdate();
        pstmtInserisci.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante l'approvazione del POI: " + e.getMessage());
    }
}
    
public void ApprovaItinerari(Itinerario itinerario) {
    String sqlElimina = "DELETE FROM Itinerari_DaApprovare WHERE idItinerario = ?";
    String sqlInserisci = "INSERT INTO Itinerari (idItinerario, ListaPOI) VALUES (?, ?)";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmtElimina = conn.prepareStatement(sqlElimina);
         PreparedStatement pstmtInserisci = conn.prepareStatement(sqlInserisci)) {

        pstmtElimina.setInt(1, itinerario.getIdItinerario());

        pstmtInserisci.setInt(1, itinerario.getIdItinerario());

        pstmtInserisci.setString(2, itinerario.getListaPOI().toString());

        pstmtElimina.executeUpdate();
        pstmtInserisci.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante l'approvazione dell'itinerario: " + e.getMessage());
    }
}

public void ApprovaContent(Content content) {
    String sqlElimina = "DELETE FROM Content_DaApprovare WHERE idContent = ?";
    String sqlInserisci = "INSERT INTO Content (idContent, Media, Data, Autore, Descrizione) VALUES (?, ?)";

    try (Connection conn = ConnettiDB.getConnection();        
         PreparedStatement pstmtElimina = conn.prepareStatement(sqlElimina);
         PreparedStatement pstmtInserisci = conn.prepareStatement(sqlInserisci)) {

        pstmtElimina.setInt(1, content.getIdContent());
        pstmtInserisci.setInt(1, content.getIdContent());
        pstmtInserisci.setString(2, content.getMedia());
        pstmtInserisci.setString(3, content.getData().toString());
        pstmtInserisci.setString(4, content.getAutore());
        pstmtInserisci.setString(5, content.getDescrizione());
        
        pstmtElimina.executeUpdate();
        pstmtInserisci.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante l'approvazione del contenuto: " + e.getMessage());
    }
}
}
