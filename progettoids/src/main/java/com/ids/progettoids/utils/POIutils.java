package com.ids.progettoids.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Coordinate;
import com.ids.progettoids.models.POI;

public class POIutils {

   
    /**
     * Recupera un elenco di POI.
     *
     * Se {@code nome} == null, restituisce tutti i POI presenti nel database.
     * Altrimenti, restituisce il POI con {@code nome} (se presente).
     *
     * @param nome il nome del POI da cercare
     * @return un elenco di POI
     */
    public static ArrayList<POI> getPOI(String nome) {
        ArrayList<POI> listaPOI = new ArrayList<>();
        String sql = (nome == null) ? "SELECT * FROM POI" : "SELECT * FROM POI WHERE Nome = ?";
    
    
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
    
            if (nome != null) {
                pstmt.setString(1, nome);
            }
    
    
            ResultSet rs = pstmt.executeQuery();
    
    
            while (rs.next()) {
                String nomePOI = rs.getString("Nome");
                String coordinateStr = rs.getString("Coordinate");
                String descrizione = rs.getString("Descrizione");
                String comune = rs.getString("Comune"); 
                String idContent = rs.getString("idContent");
                List<Integer> idContentList = new ArrayList<>();
                List<Content> media = new ArrayList<>();
                idContentList = parseStringList(idContent);
                if (!idContentList.isEmpty()) {
                if (!idContentList.isEmpty()) {
                    for (Integer idInteger : idContentList) {
                        media.add(getContent(idInteger));
                    }
                }
    
    
                String[] coordinateSplit = coordinateStr.split(",");
                Coordinate coordinate = new Coordinate(
                        Double.parseDouble(coordinateSplit[0].trim()),
                        Double.parseDouble(coordinateSplit[1].trim())
                );
    
                POI poi = new POI.Builder()
                    .setNome(nomePOI)
                    .setCoordinate(coordinate)
                    .setDescrizione(descrizione)
                    .setMediaList(media)
                    .setComune(comune)
                    .build(); 
                listaPOI.add(poi);
            }
        }
        conn.close(); 
    } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei POI: " + e.getMessage());
        }
    
    
        return listaPOI;
    }
    
    

/**
 * Metodo per parsare una stringa di interi separati da virgole
 * @param stringList la stringa da parsare
 * @return un elenco di interi
 */
   private static List<Integer> parseStringList(String stringList) {
    if (stringList == null || stringList.trim().isEmpty()) {
        return new ArrayList<>(); 
    }

    return Arrays.stream(stringList.split(","))
                 .map(String::trim)             
                 .filter(s -> !s.isEmpty())   
                 .map(Integer::parseInt)        
                 .collect(Collectors.toList());

    }

    /**
     * Recupera un elenco di POI da approvare.
     * @return un elenco di POI
     */
    public static ArrayList<POI> getAllPOIdaApprovare() {
        ArrayList<POI> listaPOI = new ArrayList<>();
        String sql = "SELECT * FROM POI_DaApprovare";
    
    
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
    
    
            while (rs.next()) {
                String nomePOI = rs.getString("Nome");
                String coordinateStr = rs.getString("Coordinate");
                String descrizione = rs.getString("Descrizione");
                String comune = rs.getString("Comune");
                String idContent = rs.getString("idContent");
                List<Integer> idContentList = new ArrayList<>();
                List<Content> media = new ArrayList<>();
                idContentList = parseStringList(idContent);
                if (!idContentList.isEmpty()) {
                if (!idContentList.isEmpty()) {
                    for (Integer idInteger : idContentList) {
                        media.add(getContent(idInteger));
                    }
                }
    
    
                String[] coordinateSplit = coordinateStr.split(",");
                Coordinate coordinate = new Coordinate(
                        Double.parseDouble(coordinateSplit[0].trim()),
                        Double.parseDouble(coordinateSplit[1].trim())
                );
    
                POI poi = new POI.Builder()
                    .setNome(nomePOI)
                    .setCoordinate(coordinate)
                    .setDescrizione(descrizione)
                    .setMediaList(media)
                    .setComune(comune)
                    .build(); 
    

                listaPOI.add(poi);
            }
            
        }
        conn.close();
    } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei POI: " + e.getMessage());
        }
    
    
        return listaPOI;
    }
    

   
    
    

   
    
    public static POI getPOIdaApprovare(String nome) {
        String sql = "SELECT * FROM POI_DaApprovare WHERE Nome =?";
        POI poi= new POI.Builder()
            .setNome(nome)
            .build();
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();
    
    
            while (rs.next()) {
                String nomePOI = rs.getString("Nome");
                String coordinateStr = rs.getString("Coordinate");
                String descrizione = rs.getString("Descrizione");
                String comune = rs.getString("Comune");

                
                String[] coordinateSplit = coordinateStr.split(",");
                Coordinate coordinate = new Coordinate(
                        Double.parseDouble(coordinateSplit[0].trim()),
                        Double.parseDouble(coordinateSplit[1].trim())
                );
                poi = new POI.Builder()
                    .setNome(nomePOI)
                    .setCoordinate(coordinate)
                    .setDescrizione(descrizione)
                    .setMediaList(new ArrayList<>())
                    .setComune(comune)
                    .build();
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei POI: " + e.getMessage());
        }
        return poi;
    }
    
    

  

    /**
     * Recupera un Content dal database.
     * @param idContent l'id del Content da cercare
     * @return il Content con l'id specificato, null se non esiste
     */
    public static Content getContent(int idContent) {
        Content content = null;
        String sql = "SELECT * FROM Content WHERE idContent = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idContent);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String mediaUrl = rs.getString("MediaUrl");
                String data = rs.getString("Data");
                String autore = rs.getString("Autore");
                String descrizione = rs.getString("Descrizione");
                content = new Content.Builder()
                    .setMedia(mediaUrl)
                    .setData(data)
                    .setAutore(autore)
                    .setDescrizione(descrizione).build();
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero del Content: " + e.getMessage());
        }

        return content;
    }

    /**
     * Crea un POI nel database.
     * @param nome il nome del POI
     * @param coordinate le coordinate del POI
     * @param descrizione la descrizione del POI
     * @param media l'elenco dei contenuti del POI
     * @param daApprovare true se il POI deve essere creato nella tabella POI_DaApprovare, false altrimenti
     */
   public static void creaPOI(String nome, Coordinate coordinate, String descrizione, List<Content> media, String comune , boolean daApprovare) {
    String sql;
    if (!daApprovare) {
        sql = "INSERT INTO POI (Nome, Coordinate, Descrizione, idContent, Comune) VALUES (?, ?, ?, ?, ?)";
        sql = "INSERT INTO POI (Nome, Coordinate, Descrizione, idContent, Comune) VALUES (?, ?, ?, ?, ?)";
    } else {
        sql = "INSERT INTO POI_DaApprovare (Nome, Coordinate, Descrizione, idContent, Comune) VALUES (?, ?, ?, ?, ?)";
        sql = "INSERT INTO POI_DaApprovare (Nome, Coordinate, Descrizione, idContent, Comune) VALUES (?, ?, ?, ?, ?)";
    }

    try (Connection conn = ConnettiDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, nome);
        pstmt.setString(2, coordinate.toString());
        pstmt.setString(3, descrizione);
        pstmt.setString(4, "0");  
        pstmt.setString(5,  comune);
        pstmt.setString(5,  comune);

        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Errore durante la creazione del POI: " + e.getMessage());
    }
}


    /**
     * Elimina un POI dal database.
     * @param poi il nome del POI da eliminare
     */
    public static void EliminaPOI(String poi) {
     
        String sql = "DELETE FROM POI WHERE Nome = ?";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, poi);

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del POI: " + e.getMessage());
        }
    }

    public static void collegaContent(Content content, String poi, boolean daApprovare) {
        int idContent = 0;
    
        if (!daApprovare) {
            idContent = ContentUtils.getIdContent(content.getMedia(), content.getData(), content.getAutore(), content.getDescrizione());
        } else {
            idContent = -ContentUtils.getIdContentDaApprovare(content.getMedia(), content.getData(), content.getAutore(), content.getDescrizione());
        }
    
        String sqlPOI = "UPDATE POI SET idContent = ? WHERE Nome = ?";
        
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmtPOI = conn.prepareStatement(sqlPOI)) {
    
            
            String idContentsString = UnisciId(poi);
    
          
            if (!idContentsString.isEmpty()) {
                List<String> idList = new ArrayList<>(Arrays.asList(idContentsString.split(",")));
                if (!idList.contains(String.valueOf(idContent))) {
                    idList.add(String.valueOf(idContent));
                }
                idContentsString = String.join(",", idList);
            } else {
                idContentsString = String.valueOf(idContent); 
            }
    
            pstmtPOI.setString(1, idContentsString);
            pstmtPOI.setString(2, poi);
            pstmtPOI.executeUpdate();
    
            conn.close();

            removeIfPresentZero(poi);
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'idContent nei POI: " + e.getMessage());
        }
    }

    private static void removeIfPresentZero(String poi){
        String query1 = "SELECT idContent FROM POI WHERE Nome = ?";
        String query2 = "UPDATE POI SET idContent = ? WHERE Nome = ?";
        String idContent = "";
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query1);
             PreparedStatement stmt2 = conn.prepareStatement(query2)) {
    
            stmt.setString(1, poi);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                idContent = rs.getString("idContent");
            }
            if (idContent.startsWith("0") && !idContent.equals("0")) {
                idContent = idContent.substring(2);
            }
            stmt2.setString(1, idContent);
            stmt2.setString(2, poi);
            stmt2.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Metodo per ottenere l'idContent attuale di un POI specifico.
     * @param poi il nome del POI
     * @return la stringa contenente gli id separati da virgole
     */
    private static String UnisciId(String poi) {
        String query = "SELECT idContent FROM POI WHERE Nome = ?";
        String idContent = "";
    
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, poi);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                idContent = rs.getString("idContent");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idContent != null && idContent.startsWith("0") && !idContent.equals("0")) {
            idContent = idContent.substring(2);
        }
        
        return (idContent == null || idContent.trim().isEmpty()) ? "" : idContent;
    }
    
    public static List<String> getNomiPOISalvati(String username) {
        String sql = "SELECT listaPOI FROM POI_Salvati WHERE username = ?";
        List<String> nomiPOI = new ArrayList<>();
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nomiString = rs.getString("listaPOI");
                if (nomiString != null){
                    if(nomiString.startsWith("[") && nomiString.endsWith("]")){
                        nomiString = nomiString.substring(1, nomiString.length() - 1);
                    }
                    nomiString=nomiString.replaceAll("\\s", "");
                    nomiPOI.addAll(Arrays.asList(nomiString.split(",")));
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dei POI: " + e.getMessage());
        }
        return nomiPOI;
    }
   
  
}