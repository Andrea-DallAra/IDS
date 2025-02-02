package com.ids.progettoids.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.ids.progettoids.ConnettiDB;
import com.ids.progettoids.Interfacce.UtenteInterfaccia;
import com.ids.progettoids.Ruolo;


public class Utente implements UtenteInterfaccia {
    String username = "";
    String password = "";
    String email = "";
    String nome = "";
    String cognome = "";
    List<Ruolo> ruoli = new ArrayList<>();

    public Utente( String _username,String _nome, String _cognome , String _email, String _password) 
    {
       email = _email;
       password = _password;
       nome = _nome;
       cognome = _cognome;
       username = _username;
       
    }
    public Utente() {}
    @Override
    public void CambiaRuolo(String username, String _ruolo) {
        String sql = "INSERT INTO RichiediRuolo (username, ruolo) VALUES (?, ?)";

        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            
            pstmt.setString(1, username);
            pstmt.setString(2, _ruolo);

          
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Inserimento riuscito: ruolo richiesto per " + username);
            } else {
                System.out.println("Nessun dato inserito.");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento: " + e.getMessage());
        }
    }

    public void SalvaRuoliDB(String username) {
        Connection con = ConnettiDB.getConnection();
    
        if (con == null) {
            System.out.println("Connessione al database fallita.");
            return;
        }
    
        Utente utente=new Utente(username, null, null, null, null);
        utente.CaricaRuoli(username);
        List<Ruolo> ruoliUtente = utente.getRuolo();
        String query = "UPDATE Ruoli SET Gestore = ?, Contributore = ?, Curatore = ?, Animatore = ?, Turista = ?,  ContributoreAutenticato = ? WHERE idUtente = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            // Imposta 1 se il ruolo e' presente, 0 se non lo e'
            stmt.setInt(1, ruoliUtente.contains(Ruolo.Gestore) ? 1 : 0);
            stmt.setInt(2, ruoliUtente.contains(Ruolo.Contributore) ? 1 : 0);
            stmt.setInt(3, ruoliUtente.contains(Ruolo.Curatore) ? 1 : 0);
            stmt.setInt(4, ruoliUtente.contains(Ruolo.Animatore) ? 1 : 0);
            stmt.setInt(5, ruoliUtente.contains(Ruolo.Turista) ? 1 : 0);
            stmt.setInt(6, ruoliUtente.contains(Ruolo.ContributoreAutenticato) ? 1 : 0);
            stmt.setString(7, username);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Ruoli aggiornati correttamente per l'utente: " + username);
            } else {
                System.out.println("Nessuna riga aggiornata. L'utente potrebbe non esistere.");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Errore durante il salvataggio dei ruoli: " + e.getMessage());
        }
    }
    
    public void CaricaRuoli(String username) {
        Connection con = ConnettiDB.getConnection();
        String query = "SELECT Gestore, Contributore, Curatore, Animatore, Turista, ContributoreAutenticato FROM Ruoli WHERE idUtente = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                if (rs.getInt("Gestore") == 1) {
                    ruoli.add(Ruolo.Gestore);
                }
                if (rs.getInt("Contributore") == 1) {
                    ruoli.add(Ruolo.Contributore);
                }
                if (rs.getInt("Curatore") == 1) {
                    ruoli.add(Ruolo.Curatore);
                }
                if (rs.getInt("Animatore") == 1) {
                    ruoli.add(Ruolo.Animatore);
                }
                if (rs.getInt("Turista") == 1) {
                    ruoli.add(Ruolo.Turista);
                }
                if (rs.getInt("ContributoreAutenticato") == 1) {
                    if (ruoli.contains(Ruolo.Contributore)) {
                        Contributore contributore = new Contributore(this.nome, this.cognome, this.email, this.password, this.username);
                        contributore.setAutenticato(true);   
                    }
                }
    
                System.out.println("Ruoli caricati correttamente per l'utente: " + username);
            } else {
                System.out.println("Nessun ruolo trovato per l'utente: " + username);
            }
    
            con.close();
        } catch (SQLException e) {
            System.out.println("Errore durante il caricamento dei ruoli: " + e.getMessage());
        }
    }
    
    public  boolean Login(String username, String password) {
        Connection con = ConnettiDB.getConnection();
      
    
        String query = "SELECT password FROM utenti WHERE username = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password");
                
                
               // String inputHash = hashPassword(password, "chiave");
               String inputHash = password;
                con.close();
                if (inputHash.equals(storedHash)) {
                    System.out.println("Login riuscito."); 
                    CaricaRuoli(username);
                    return true;
                } else {
                    System.out.println("Password errata.");
                    return false;
                }
            } else {
                con.close();
                System.out.println("Username non trovato.");
                return false;
            }
            
        } catch (SQLException e) {
           
            System.out.println("Errore durante il login: " + e.getMessage());
            return false;
        }
       
    }

    public boolean Registrazione(String nome, String cognome, String email, String password, String username) {

        if (utenteEsiste(username, email)) {
            System.out.println("L'utente con username: " + username + " o email: " + email + " è già registrato.");
            return false;
        }
        // Hash della password
      //  password = hashPassword(password, "chiave");

        // Query di inserimento
        String sql = "INSERT INTO Utenti (username, email, nome, cognome, password) VALUES (?, ?, ?, ?, ?)";
        
        // Connessione e inserimento
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, nome);
            pstmt.setString(4, cognome);
            pstmt.setString(5, password);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Registrazione completata per l'utente: " + username);
               
                AggiungiTurista(username);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione: " + e.getMessage());
        }

        return false;
    }
    private boolean utenteEsiste(String username, String email) {
        String checkQuery = "SELECT COUNT(*) FROM Utenti WHERE username = ? OR email = ?";
    
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
    
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();
    
            if (rs.next() && rs.getInt(1) > 0) {
                return true;  // L'utente esiste già
            }
    
        } catch (SQLException e) {
            System.err.println("Errore nel controllo esistenza utente: " + e.getMessage());
        }
    
        return false; // L'utente non esiste
    }
    
    public void AggiungiTurista(String username) {
        Connection con = ConnettiDB.getConnection();
    
        if (con == null) {
            System.out.println("Connessione al database fallita.");
            return;
        }
    
        // Prima verifica se l'utente esiste già nella tabella Ruoli
        String checkQuery = "SELECT COUNT(*) FROM Ruoli WHERE idUtente = ?";
        String insertQuery = "INSERT INTO Ruoli (idUtente, Gestore, Contributore, Curatore, Animatore, Turista, ContributoreAutenticato) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
    
            if (count == 0) { 
                try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, username);
                    insertStmt.setInt(2,0);
                    insertStmt.setInt(3,  0);
                    insertStmt.setInt(4, 0);
                    insertStmt.setInt(5,0);
                    insertStmt.setInt(6, 1 );
                    insertStmt.setInt(7, 0);
    
                    int rowsInserted = insertStmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Ruoli inseriti correttamente per l'utente: " + username);
                    } else {
                        System.out.println("Errore durante l'inserimento dei ruoli.");
                    }
                }
            } else {
                System.out.println("L'utente " + username + " ha già un record nella tabella Ruoli.");
            }
    
        } catch (SQLException e) {
            System.out.println("Errore durante il salvataggio dei ruoli: " + e.getMessage());
        }
    }
    

    // SHA-256
    public static String hashPassword(String password, String chiave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(chiave.getBytes()); 
            byte[] hashedPassword = md.digest(password.getBytes());

            
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Qualcosa e' andato storto", e);
        }
    }
    @Override
    public void AggiungiRuolo() {
       
    }
    public void AggiungiRuolo(Ruolo _ruolo) {
       ruoli.add(_ruolo);
    }
    public List<Ruolo> getRuolo() {
        return ruoli;
    }
    public void Report(String chiave, String tipo, String descrizione) {
        String sql = "INSERT INTO Report (Chiave, Tipo, Descrizione) VALUES (?, ?, ?)";
    
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, chiave);
            pstmt.setString(2, tipo);
            pstmt.setString(3, descrizione);
    
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento del report: " + e.getMessage());
        }
    }

    public String getNome(){
        return nome;
    }
    
    public String getCognome(){
        return cognome;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }
}
