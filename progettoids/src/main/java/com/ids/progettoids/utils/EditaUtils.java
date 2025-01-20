package com.ids.progettoids.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.models.POI;

public class EditaUtils {
    
   public static void EditaElemento(POI base, POI editato) {
    String sql = "UPDATE POI SET Nome = ?, Coordinate = ?, Descrizione = ?, idContent = ? WHERE Nome = ? AND Coordinate = ? AND Descrizione = ? AND idContent = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, editato.getNome());
        pstmt.setString(2, editato.getCoordinate().toString());
        pstmt.setString(3, editato.getDescrizione());
        pstmt.setInt(4, editato.getMedia().getIdContent());

        pstmt.setString(5, base.getNome());
        pstmt.setString(6, base.getCoordinate().toString());
        pstmt.setString(7, base.getDescrizione());
        pstmt.setInt(8, base.getMedia().getIdContent());

        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante la modifica del POI: " + e.getMessage());
    }
}

public static void EditaElemento(Content base, Content editato) {
    String sql = "UPDATE Content SET media = ?, data = ?, autore = ?, descrizione = ? WHERE idContent = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, editato.getMedia());
        pstmt.setString(2, editato.getData().toString());
        pstmt.setString(3, editato.getAutore());
        pstmt.setString(4, editato.getDescrizione());

        pstmt.setInt(5, base.getIdContent());

        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante la modifica del Content: " + e.getMessage());
    }
}

public static void EditaElemento(Itinerario base, Itinerario editato) {
    String sql = "UPDATE Itinerari SET idItinerario = ?, listaPOI = ? where idItinerario = ?";

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, editato.getIdItinerario());
        pstmt.setString(2, editato.getListaPOI().toString());
        pstmt.setInt(3, base.getIdItinerario());
       
        pstmt.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        System.err.println("Errore durante la modifica dell'itinerario: " + e.getMessage());
    }
}
}
