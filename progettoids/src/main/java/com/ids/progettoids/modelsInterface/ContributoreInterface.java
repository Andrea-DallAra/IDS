package com.ids.progettoids.modelsInterface;

import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Contest;

public interface ContributoreInterface {

    /**
     * Metodo per partecipare ad un contest
     * @param contest il contest a cui si vuole partecipare
     * @param content il contenuto da caricare
     */
    public void partecipaContest(Contest contest, Content content);
}
