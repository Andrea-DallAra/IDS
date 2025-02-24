package com.ids.progettoids.modelsInterface;

import com.ids.progettoids.models.Content;

public interface TuristaInteface {
    /**
     * Metodo per salvare un itinerario nel database
     * @param idItinerario
     */
    public void salvaItinerario(int idItinerario);


    /**
     * Metodo per aggiungere un contenuto
     * @param content
     */
    public void aggiungiContenuto(Content content);


    /**
     * Metodo per salvare un POI
     * @param poiName
     */
    public void salvaPOI(String poiName);
}
