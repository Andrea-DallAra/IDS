package com.ids.progettoids.models;

import java.util.Date;

public class Content {
    private String media;
    private Date data;
    private String autore;
    private String descrizione;

    /**
     * Factory Method: Builder
     */
    public Content(String media, Date data, String autore, String descrizione) {
        setMedia(media);
        setAutore(autore);
        setData(data);
        setDescrizione(descrizione);
        }
    public void setAutore(String autore) {
        this.autore = autore;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public void setMedia(String media) {
        this.media = media;
    }
    
}
