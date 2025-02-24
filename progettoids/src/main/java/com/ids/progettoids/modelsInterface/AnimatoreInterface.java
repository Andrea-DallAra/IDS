package com.ids.progettoids.modelsInterface;

import java.util.List;

import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Contest;

public interface AnimatoreInterface {
    
    /**
     * Metodo per creare un contest
     * @param contest il contest da creare
     */
    public void creaContest(Contest contest);
    
    /**
     * Metodo per gestire un contest
    * @param contest il contest da gestire
    * @return la lista dei contenuti
    */
    public List<Content> gestisciContest(Contest contest);
    
    
    /**
     * Metodo per dichiarare il vincitore
     * @param usernameVincitore 
     * @param contestNome
     * @return la stringa di dichiarazione
     */
    public String dichiaraVincitore(String usernameVincitore , String contestNome);
}
