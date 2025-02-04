package com.ids.progettoids.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Contest;

public class ContestUtils {
    
    public static void participateInContest(Contest contest, Content content, String username) {
        String sql1 = "SELECT listaContent FROM Contest WHERE nome = ?;";
        String sql2 = "UPDATE Contest SET listaContent = ? WHERE nome = ?;";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, contest.getNome());
            ResultSet rs = pstmt.executeQuery();
            List<Integer> contentsIdList = new ArrayList<>();
            if (rs.next()) {
                String stringaContentId = rs.getString("listaContent");
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
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento nel contest: " + e.getMessage());
        }
    }
    public static List<Contest> getAllContests() {
        List<Contest> contests = new ArrayList<>();
        String sql = "SELECT * FROM Contest;";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Contest contest = new Contest(rs.getString("nome"), rs.getString("descrizione"));
         
                contests.add(contest);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dei Contest: " + e.getMessage());
        }
        return contests;
}
}
