package com.ids.progettoids.modelsInterface;

import java.util.Map;

public interface GestoreInterface {
    
    /**
     * Metodo per ottenere le richieste di cambio di ruolo dal database.
     * @return le richieste di cambio di ruolo come un Map<String, String> 
     */
    public Map<String, String> getRichiesteCambioRuolo();
    
    /**
     * Metodo per modificare il ruolo di un utente nel database.
     * @param username l'utente da modificare
     * @param _ruolo il ruolo da aggiungere
     */
    public void EditaRuolo(String username, String _ruolo);


    /**
     * Metodo per eliminare un ruolo dall'utente dal database.
     * @param username l'utente a cui rimuovere un ruolo
     */
    public void removeRuoloFromDatabase(String username);
}
