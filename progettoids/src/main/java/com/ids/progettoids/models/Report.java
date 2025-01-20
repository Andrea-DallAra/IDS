package com.ids.progettoids.models;

public class Report {
    
    String chiave;
    String tipo;
    String descrizione;

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
