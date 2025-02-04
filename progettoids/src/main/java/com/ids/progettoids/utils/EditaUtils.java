package com.ids.progettoids.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.models.POI;

public class EditaUtils {
    
  public static void EditaPOI(POI base, POI editato) {
    String sql = "UPDATE POI SET Nome = ?, Coordinate = ?, Descrizione = ? WHERE Nome = ? AND Coordinate = ? AND Descrizione = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        
      
      
        pstmt.setString(1, editato.getNome());
        pstmt.setString(2, editato.getCoordinate().toString());
        pstmt.setString(3, editato.getDescrizione());
  

       
        pstmt.setString(4, base.getNome());
        pstmt.setString(5, base.getCoordinate().toString());
        pstmt.setString(6, base.getDescrizione());
     

        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Errore durante la modifica del POI: " + e.getMessage());
    }
}



public static void EditaContent(Content base, Content editato) {
    String sql = "UPDATE Content SET MediaUrl = ?, data = ?, autore = ?, descrizione = ? WHERE idContent = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, editato.getMedia());
        pstmt.setString(2, editato.getData());
        pstmt.setString(3, editato.getAutore());
        pstmt.setString(4, editato.getDescrizione());

        pstmt.setInt(5, base.getIdContent());

        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante la modifica del Content: " + e.getMessage());
    }
}

public static void EditaItinerario(Itinerario base, Itinerario editato) {
    String sql = "UPDATE Itinerario SET listaPOI = ? where idItinerario = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        pstmt.setString(1, editato.getListaPOI().toString());
        pstmt.setInt(2, base.getIdItinerario());
       
        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante la modifica dell'itinerario: " + e.getMessage());
    }
}
}
