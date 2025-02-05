package com.ids.progettoids.models;

public class Report {
    
    /**
     * Classe che rappresenta un report
     */
    String chiave;
    String tipo;
    String descrizione;

    /**
     * @param chiave chiave dell'elemento segnalato
     * @param tipo tipo dell'elemento segnalato (POI, Itinerario, Content)
     * @param descrizione descrizione dell'elemento segnalato
     */
    public Report(String chiave, String tipo, String descrizione) {
        this.chiave = chiave;
        this.tipo = tipo;
        this.descrizione = descrizione;
    } 
    public String getTipo() {
        return tipo;
    }  
    public String getChiave() {
        return chiave;
    }
    public String getDescrizione() {
        return descrizione;
    }
}
