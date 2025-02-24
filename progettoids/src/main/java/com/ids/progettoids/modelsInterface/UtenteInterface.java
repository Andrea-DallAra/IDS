package com.ids.progettoids.modelsInterface;

import java.util.List;

import com.ids.progettoids.Ruolo;

public interface UtenteInterface {
    /**
     * Metodo per cambiare il ruolo di un utente e salvarlo nel database.
     * 
     * @param username Nome utente
     * @param _ruolo Nuovo ruolo da assegnare
     */
    public void CambiaRuolo(String username, String _ruolo);

    /**
     * Salva i ruoli dell'utente nel database.
     * Se i ruoli esistono già, li aggiorna, altrimenti li inserisce.
     * 
     * @param username Nome utente
     */
    public void SalvaRuoliDB(String username);

    /**
     * Inserisce i ruoli dell'utente nel database.
     * @param username
     * @param ruoliUtente
     */
    public void insertRuoli(String username, List<Ruolo> ruoliUtente);

    /**
     * Carica i ruoli dell'utente dal database.
     * @param username
     */
    public void CaricaRuoli(String username);

    /**
     * Metodo per autenticare un utente confrontando la password con l'hash salvato.
     * 
     * @param username Nome utente
     * @param password Password dell'utente
     * @return true se il login ha successo, false altrimenti
     */
    public boolean Login(String username, String password);

    /**
     * Metodo per registrare un nuovo utente nel database.
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @param username
     * @return
     */
    public boolean Registrazione(String nome, String cognome, String email, String password, String username);

    /**
     * Metodo che aggiunge turista come ruolo di default 
     * ad un utente quando viene registrato
     * @param username
     */
    public void AggiungiTurista(String username);

    /**
     * Metodo che aggiunge un ruolo all'utente
     */
    public void AggiungiRuolo(Ruolo _ruolo);

    public List<Ruolo> getRuolo();

    /**
     * Metodo che aggiunge un report al database
     * @param chiave
     * @param tipo
     * @param descrizione
     */
    public void Report(String chiave, String tipo, String descrizione);
    
}
