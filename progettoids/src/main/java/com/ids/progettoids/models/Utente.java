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
import com.ids.progettoids.Ruolo;
import com.ids.progettoids.modelsInterface.UtenteInterface;

/**
 * Classe che rappresenta un utente
 * Gestisce la registrazione, autenticazione e gestione dei ruoli degli utenti.
 */
public class Utente  implements UtenteInterface {
    String username = "";
    String password = "";
    String email = "";
    String nome = "";
    String cognome = "";
    List<Ruolo> ruoli = new ArrayList<>();


  

 /**  
     * @param _username Nome utente
     * @param _nome Nome dell'utente
     * @param _cognome Cognome dell'utente
     * @param _email Email dell'utente
     * @param _password Password dell'utente
     */
    public Utente( String _username,String _nome, String _cognome , String _email, String _password) 
    {
       setEmail(_email);
       setPassword(_password);
       setNome(_nome);
       setcognome(_cognome);
       SetUsername(_username);
       
    }
    public Utente() {}
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setcognome(String cognome) {
        this.cognome = cognome;
    }
    
   /**
     * Metodo per cambiare il ruolo di un utente e salvarlo nel database.
     * 
     * @param username Nome utente
     * @param _ruolo Nuovo ruolo da assegnare
     */
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
    /**
     * Salva i ruoli dell'utente nel database.
     * Se i ruoli esistono già, li aggiorna, altrimenti li inserisce.
     * 
     * @param username Nome utente
     */
    @Override
    public void SalvaRuoliDB(String username) {
        Connection con = ConnettiDB.getConnection();
    
        if (con == null) {
            System.out.println("Connessione al database fallita.");
            return;
        }
    
        this.CaricaRuoli(username);
        List<Ruolo> ruoliUtente = this.getRuolo();
        
        String query = "UPDATE Ruoli SET Gestore = ?, Contributore = ?, Curatore = ?, Animatore = ?, " +
                       "Turista = ?, ContributoreAutenticato = ?, TuristaAutenticato = ? WHERE idUtente = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            
            stmt.setInt(1, ruoliUtente.contains(Ruolo.Gestore) ? 1 : 0);
            stmt.setInt(2, ruoliUtente.contains(Ruolo.Contributore) ? 1 : 0);
            stmt.setInt(3, ruoliUtente.contains(Ruolo.Curatore) ? 1 : 0);
            stmt.setInt(4, ruoliUtente.contains(Ruolo.Animatore) ? 1 : 0);
            stmt.setInt(5, ruoliUtente.contains(Ruolo.Turista) ? 1 : 0);
            stmt.setInt(6, ruoliUtente.contains(Ruolo.ContributoreAutenticato) ? 1 : 0);
            stmt.setInt(7, ruoliUtente.contains(Ruolo.TuristaAutenticato) ? 1 : 0);
            stmt.setString(8, username);
    
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Ruoli aggiornati correttamente per l'utente: " + username);
            } else {
               insertRuoli(this.username, ruoliUtente);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Errore durante il salvataggio dei ruoli: " + e.getMessage());
        }
    }
    /**
     * Inserisce i ruoli dell'utente nel database.
     * @param username
     * @param ruoliUtente
     */
    @Override
    public void insertRuoli(String username, List<Ruolo> ruoliUtente) {
        String query = "INSERT INTO Ruoli (idUtente, Gestore, Contributore, Curatore, Animatore, Turista, ContributoreAutenticato, TuristaAutenticato) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnettiDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            for (int i = 0; i < Ruolo.values().length; i++) {
                stmt.setInt(i + 2, ruoliUtente.contains(Ruolo.values()[i]) ? 1 : 0);
            }
            stmt.executeUpdate();
            System.out.println("Ruoli inseriti correttamente per l'utente: " + username);
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento dei ruoli: " + e.getMessage());
        }
    }
    /**
     * Carica i ruoli dell'utente dal database.
     * @param username
     */
    @Override
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
               
                if (rs.getInt("ContributoreAutenticato") == 1) {
                    if (!ruoli.contains(Ruolo.Contributore)) {
                        ruoli.add(Ruolo.Contributore);
                    }
                    ruoli.add(Ruolo.ContributoreAutenticato);
                }
                
                    ruoli.add(Ruolo.Turista);
                    
                    ruoli.add(Ruolo.TuristaAutenticato);
             
                System.out.println("Ruoli caricati correttamente per l'utente: " + username);
            } else {
                System.out.println("Nessun ruolo trovato per l'utente: " + username);
            }
    
            con.close();
        } catch (SQLException e) {
            System.out.println("Errore durante il caricamento dei ruoli: " + e.getMessage());
        }
    }
     /**
     * Metodo per autenticare un utente confrontando la password con l'hash salvato.
     * 
     * @param username Nome utente
     * @param password Password dell'utente
     * @return true se il login ha successo, false altrimenti
     */
    @Override
    public  boolean Login(String username, String password) {
        Connection con = ConnettiDB.getConnection();
      
    
        String query = "SELECT password FROM utenti WHERE username = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password");
                
                
                String inputHash = hashPassword(password, "chiave");
             
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
/**
 * Metodo per registrare un nuovo utente nel database.
 * @param nome
 * @param cognome
 * @param email
 * @param password
 * @param username
 * @return
 */
    @Override
    public boolean Registrazione(String nome, String cognome, String email, String password, String username) {

        if (utenteEsiste(username, email)) {
            System.out.println("L'utente con username: " + username + " o email: " + email + " è già registrato.");
            return false;
        }
      
        password = hashPassword(password, "chiave");

       
        String sql = "INSERT INTO Utenti (username, email, nome, cognome, password) VALUES (?, ?, ?, ?, ?)";
        
        
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
    /**
     * Metodo per controllare se un utente esiste nel database.
     * @param username
     * @param email
     * @return
     */
    private boolean utenteEsiste(String username, String email) {
        String checkQuery = "SELECT COUNT(*) FROM Utenti WHERE username = ? OR email = ?";
    
        try (Connection conn = ConnettiDB.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
    
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();
    
            if (rs.next() && rs.getInt(1) > 0) {
                return true; 
            }
    
        } catch (SQLException e) {
            System.err.println("Errore nel controllo esistenza utente: " + e.getMessage());
        }
    
        return false; 
    }
    /**
     * Metodo che aggiunge turista come ruolo di default 
     * ad un utente quando viene registrato
     * @param username
     */
    @Override
    public void AggiungiTurista(String username) {
        Connection con = ConnettiDB.getConnection();
    
        if (con == null) {
            System.out.println("Connessione al database fallita.");
            return;
        }
    
        
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
    

   /**
     * Genera un hash della password utilizzando SHA-256.
     * 
     * @param password Password in chiaro
     * @param chiave Chiave di sicurezza aggiuntiva
     * @return Stringa hash della password
     */
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
    
    /**
     * metodo da sovrascrivere nelle classi figlie
     */
    public void AggiungiRuolo() {
       
    }
    /**
     * Metodo che aggiunge un ruolo all'utente
     */
    @Override
    public void AggiungiRuolo(Ruolo _ruolo) {
       ruoli.add(_ruolo);
    }
    @Override
    public List<Ruolo> getRuolo() {
        return ruoli;
    }
    /**
     * Metodo che aggiunge un report al database
     * @param chiave
     * @param tipo
     * @param descrizione
     */
    @Override
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
    public void SetUsername(String _username){
        username = _username;
    }
}
