package com.ids.progettoids.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.modelsInterface.UtenteInterface;
import com.ids.progettoids.utils.UtenteUtils;

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

    protected Utente(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.nome = builder.nome;
        this.cognome = builder.cognome;
        this.ruoli = builder.ruoli;
    }
    public static class Builder {
        private String username;
        private String password;
        private String email;
        private String nome;
        private String cognome;
        private List<Ruolo> ruoli = new ArrayList<>();
        private Ruolo tipo;
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setCognome(String cognome) {
            this.cognome = cognome;
            return this;
        }

        public Builder setRuoli(List<Ruolo> ruoli) {
           
            for (Ruolo ruolo : ruoli) {
                if (!this.ruoli.contains(ruolo)) {
                    this.ruoli.add(ruolo);
                }
            }
            return this;
        }
        public Builder setTipo(Ruolo _daInstanziare) {
            this.tipo = _daInstanziare;
            return this;
        }
        public Utente build() {

            if(tipo == null) return new Utente(this);
            switch (tipo) {
                case Gestore:
                    return new Gestore(this);
                case Curatore:
                    return new Curatore(this);
                case Animatore: 
                    return new Animatore(this);
                case Turista:
                    return new Turista(this);
                case Contributore:
                    return new Contributore(this);
                default:
                    return new Utente(this);
            }
        }
    }
   
  
   /**
     * Metodo per cambiare il ruolo di un utente e salvarlo nel database.
     * 
     * @param username Nome utente
     * @param _ruolo Nuovo ruolo da assegnare
     */
    @Override
    public void CambiaRuolo(String username, String _ruolo) {
       UtenteUtils.CambiaRuolo(username, _ruolo) ;
    }
    /**
     * Salva i ruoli dell'utente nel database.
     * Se i ruoli esistono già, li aggiorna, altrimenti li inserisce.
     * 
     * @param username Nome utente
     */
    @Override
    public void SalvaRuoliDB(String username) {
       UtenteUtils.SalvaRuoliDB(username);
    }
    /**
     * Inserisce i ruoli dell'utente nel database.
     * @param username
     * @param ruoliUtente
     */
    @Override
    public void insertRuoli(String username, List<Ruolo> ruoliUtente) {
       UtenteUtils.insertRuoli(username, ruoliUtente);
    }
    /**
     * Carica i ruoli dell'utente dal database.
     * @param username
     */
    @Override
    public void CaricaRuoli(String username) {
        setRuoli(UtenteUtils.CaricaRuoli(username));  
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
        boolean log =  UtenteUtils.Login(username, password);
        if(log) CaricaRuoli(username);
        return log;
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

      return UtenteUtils.Registrazione(nome, cognome, email, password, username);
    }
 
    /**
     * Metodo che aggiunge turista come ruolo di default 
     * ad un utente quando viene registrato
     * @param username
     */
    @Override
    public void AggiungiTurista(String username) {
      UtenteUtils.AggiungiTurista(username);
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
    public void AggiungiRuolo() {}
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
        UtenteUtils.Report(chiave, tipo, descrizione);
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
    public void setRuoli(List<Ruolo> ruoli) {
           
        for (Ruolo ruolo : ruoli) {
            if (!this.ruoli.contains(ruolo)) {
                this.ruoli.add(ruolo);
            }
        
    }

}
}
