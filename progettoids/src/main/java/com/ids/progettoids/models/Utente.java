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
import com.ids.progettoids.Interfacce.UtenteInterfaccia;


public abstract class Utente implements UtenteInterfaccia {
    String username = "";
    String password = "";
    String email = "";
    String nome = "";
    String cognome = "";
    List<Ruolo> ruoli = new ArrayList<>();

    public Utente(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       email = _email;
       password = _password;
       nome = _nome;
       cognome = _cognome;
       username = _username;
       
    }
 
    public void SalvaRuoliDB(String username) {
        Connection con = ConnettiDB.getConnection();
    
        if (con == null) {
            System.out.println("Connessione al database fallita.");
            return;
        }
    
        // aggiorna ruoli
        String query = "UPDATE Ruoli SET Gestore = ?, Contributore = ?, Curatore = ?, Animatore = ?, Turista = ?,  ContributoreAutenticato = ? WHERE idUtente = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            // Imposta 1 se il ruolo e' presente, 0 se non lo e'
            stmt.setInt(1, ruoli.contains(Ruolo.Gestore) ? 1 : 0);
            stmt.setInt(2, ruoli.contains(Ruolo.Contributore) ? 1 : 0);
            stmt.setInt(3, ruoli.contains(Ruolo.Curatore) ? 1 : 0);
            stmt.setInt(4, ruoli.contains(Ruolo.Animatore) ? 1 : 0);
            stmt.setInt(5, ruoli.contains(Ruolo.Turista) ? 1 : 0);
            stmt.setString(6, username);
    
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
                    if (this instanceof Contributore) {
                        ((Contributore) this).setAutenticato(true);
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
    
    public boolean Login(String username, String password) {
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

    public boolean Registrazione(String nome, String cognome, String email, String password, String username)
    {
        
        password = hashPassword(password, "chiave");
        return true;
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


}
