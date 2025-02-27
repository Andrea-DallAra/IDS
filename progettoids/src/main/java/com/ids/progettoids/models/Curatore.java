package com.ids.progettoids.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.modelsInterface.CuratoreInterface;
import com.ids.progettoids.utils.ContentUtils;
import com.ids.progettoids.utils.EditaUtils;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.POIutils;
public class Curatore extends Utente implements CuratoreInterface{

    ArrayList<POI> reportPOI = new ArrayList<>();
    ArrayList<Content> reportContent = new ArrayList<>();
    ArrayList<Itinerario> reportItinerario = new ArrayList<>();
    ArrayList<Report> reports = new ArrayList<>();
    /**
     * Classe che rappresenta un curatore
     * Gestisce le azioni del curatore
     */
    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Curatore);
        
    }
    protected Curatore(Utente.Builder builder) {
        super(builder);
        AggiungiRuolo();
    }

    /**
     * Metodo per approvare un POI
     * @param poi il POI da approvare
     */
    @Override
 public void ApprovaPOI(POI poi) {
    String sqlElimina = "DELETE FROM POI_DaApprovare WHERE Nome = ?";
    String sqlInserisci = "INSERT INTO POI (Nome, Coordinate, Descrizione, idContent) VALUES (?, ?, ?, ?)";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmtElimina = conn.prepareStatement(sqlElimina);
         PreparedStatement pstmtInserisci = conn.prepareStatement(sqlInserisci)) {

        pstmtInserisci.setString(1, poi.getNome());
        pstmtInserisci.setString(2, poi.getCoordinate().toString());
        pstmtInserisci.setString(3, poi.getDescrizione());
        pstmtInserisci.setString(4, "0");

        pstmtInserisci.executeUpdate(); 

        pstmtElimina.setString(1, poi.getNome());
        pstmtElimina.executeUpdate(); 

        conn.close();

    } catch (SQLException e) {
        System.err.println("Errore durante l'approvazione del POI: " + e.getMessage());
    }
}

    /**
     * Metodo per approvare un itinerario
     * @param itinerario l'itinerario da approvare
     */
    @Override
    public void ApprovaItinerari(Itinerario itinerario) {
    String sqlElimina = "DELETE FROM Itinerario_DaApprovare WHERE idItinerario = ?";
    String sqlInserisci = "INSERT INTO Itinerario (ListaPOI) VALUES (?)";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmtElimina = conn.prepareStatement(sqlElimina);
         PreparedStatement pstmtInserisci = conn.prepareStatement(sqlInserisci)) {

        pstmtElimina.setInt(1, itinerario.getIdItinerario());

   

        pstmtInserisci.setString(1, itinerario.getListaPOI().toString());

        pstmtInserisci.executeUpdate();
        
        pstmtElimina.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante l'approvazione dell'itinerario: " + e.getMessage());
    }
}

/**
 * Metodo per approvare un content
 * @param content il content da approvare
 * @param idContent l'id del content
 */
    @Override
    public void ApprovaContent(Content content, int idContent) {
    String sqlElimina = "DELETE FROM Content_DaApprovare WHERE idContent = ?";
    String sqlInserisci = "INSERT INTO Content ( MediaUrl, Data, Autore, Descrizione) VALUES (?, ?, ?, ?)";

    try (Connection conn = ConnettiDB.getConnection();        
         PreparedStatement pstmtElimina = conn.prepareStatement(sqlElimina);
         PreparedStatement pstmtInserisci = conn.prepareStatement(sqlInserisci)) {

        pstmtElimina.setInt(1,  idContent);
      
        pstmtInserisci.setString(1, content.getMedia());
        pstmtInserisci.setString(2, content.getData());
        pstmtInserisci.setString(3, content.getAutore());
        pstmtInserisci.setString(4, content.getDescrizione());
        
        pstmtElimina.executeUpdate();
        pstmtInserisci.executeUpdate();
        conn.close();
       int idNuovo = ContentUtils.getIdContent(content.getMedia(), content.getData(), content.getAutore(), content.getDescrizione());
        AggiornaId(idNuovo, idContent);    
    } catch (SQLException e) {
        System.err.println("Errore durante l'approvazione del contenuto: " + e.getMessage());
    }
}

        public void deletePOIDaApprovare(String nome){
            String sql = "DELETE FROM POI_DaApprovare WHERE Nome = ?";

            try (Connection conn = ConnettiDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nome);

                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.err.println("Errore durante l'eliminazione del POI da approvare: " + e.getMessage());
            }
        }

        public void deleteItinerarioDaApprovare(int idItinerario){
            String sql = "DELETE FROM Itinerario_DaApprovare WHERE idItinerario = ?";
            try (Connection conn = ConnettiDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, idItinerario);

                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.err.println("Errore durante l'eliminazione dell'itinerario da approvare: " + e.getMessage());
            }
        }


        public void deleteContentDaApprovare(int idContent){
            String sql = "DELETE FROM Content_DaApprovare WHERE idContent = ?";
            try (Connection conn = ConnettiDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, idContent);

                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.err.println("Errore durante l'eliminazione del POI da approvare: " + e.getMessage());
            }
        }

    @Override
    public void AggiornaId(int _idNuovo, int _idVecchio)
{
    String  sqlAggiornaId = "UPDATE POI SET idContent = ? WHERE idContent = ?";
    try (Connection conn = ConnettiDB.getConnection();        
    PreparedStatement pstmtAggiorna = conn.prepareStatement(sqlAggiornaId))
 {

    pstmtAggiorna.setInt(1,  _idNuovo);
    pstmtAggiorna.setInt(2,  _idVecchio);
  
    pstmtAggiorna.executeUpdate();
   conn.close();
   
} catch (SQLException e) {
   System.err.println("Errore durante l'aggiornamento dell'id: " + e.getMessage());
}
}

    @Override
    public void EditaPOI(POI base, POI editato) {
    EditaUtils.EditaPOI(base, editato);
}
    @Override
    public void EditaContent(Content base, Content editato) {
    EditaUtils.EditaContent(base, editato);
}
    @Override
    public void EditaItinerario(Itinerario base, Itinerario editato) {
    EditaUtils.EditaItinerario(base, editato);
}


    @Override
    public void VisualizzaReport()
{
    reportPOI.clear();
    reportContent.clear();
    reportItinerario.clear();
    reports.clear();
    reports = GetReports();
  
 
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

    @Override
    public Report getReportFromChiave(String chiave) {
    String sql = "SELECT * FROM Report WHERE chiave = ?";
    Report report = new Report(chiave, "", "");

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, chiave); 
        ResultSet rs = pstmt.executeQuery(); 

        while (rs.next()) {
            String descrizione = rs.getString("Descrizione");
            String tipo = rs.getString("Tipo");
            report = new Report(chiave, tipo, descrizione);
        }

        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante la ricerca dei report: " + e.getMessage());
    }

    return report;
}

    @Override
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
    @Override
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
    @Override
    public ArrayList<Report> GetReports()
{
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
