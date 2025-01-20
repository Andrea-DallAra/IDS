package com.ids.progettoids.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.utils.ContentUtils;
import com.ids.progettoids.utils.EditaUtils;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.POIutils;
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
public void EditaElemento(POI base, POI editato) {
    EditaUtils.EditaElemento(base, editato);
}
public void EditaElemento(Content base, Content editato) {
    EditaUtils.EditaElemento(base, editato);
}
public void EditaElemento(Itinerario base, Itinerario editato) {
    EditaUtils.EditaElemento(base, editato);
}

ArrayList<POI> reportPOI = new ArrayList<>();
ArrayList<Content> reportContent = new ArrayList<>();
ArrayList<Itinerario> reportItinerario = new ArrayList<>();
ArrayList<Report> reports = new ArrayList<>();
public void VisualizzaReport()
{
    reportPOI.clear();
    reportContent.clear();
    reportItinerario.clear();
    reports.clear();
    reports = GetReports();
    //da collegare all'hud
 
    for (Report r : reports) 
    {
        switch (r.getTipo()) {
            case "POI":
               
              
                reportPOI.add(  POI.VediPOI(r.getChiave()).get(0));
                break;
            case "Content":
               
                reportContent.add(   ContentUtils.getContent( Integer.parseInt(r.getChiave())));
                break;
            case "Itinerario":
            
            
               reportItinerario.add( ItinerarioUtils.getItinerario(Integer.parseInt(r.getChiave())).get(0));
                break;

            default:

                break;
        }

    }
}
public void EliminaReport(Report r)
{
    switch (r.getTipo()) {
        case "POI":
           
            POIutils.EliminaPOI(r.getChiave());
            EliminaSegnalazione(r.getChiave());
            break;
        case "Content":
           ContentUtils.EliminaContent(Integer.parseInt( r.getChiave()));
           EliminaSegnalazione(r.getChiave());
            break;
        case "Itinerario":
        
           ItinerarioUtils.EliminaItinerario(Integer.parseInt( r.getChiave())); 
           EliminaSegnalazione(r.getChiave());       
            break;

        default:

            break;
    }
}
public void EliminaSegnalazione(String chiave)
{
    String sql = "DELETE FROM Report WHERE chiave = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, chiave);

        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante l'eliminazione della segnalazione: " + e.getMessage());
    }
}
private ArrayList<Report> GetReports()
{
  //prendi i valori della tabella report e li metti in una lista
  String sql = "SELECT * FROM Report";
  ArrayList<Report> reports = new ArrayList<>();
  try (Connection conn = ConnettiDB.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(sql);
       ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
          String chiave = rs.getString("chiave");
          String descrizione = rs.getString("Descrizione");
          String tipo = rs.getString("Tipo");
          Report report = new Report( chiave, tipo,descrizione);
          reports.add(report);
      }
      conn.close();
  } catch (SQLException e) {
      System.err.println("Errore durante la ricerca dei report: " + e.getMessage());
  }
  return reports;

}
}
