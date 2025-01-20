package com.ids.progettoids.models;

import java.util.Date;

import com.ids.progettoids.utils.ContentUtils;

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
    public int getIdContent() {
    return ContentUtils.getIdContent(this.media, this.data, this.autore, this.descrizione);
}

public String getMedia() {
    return media;
}
public Date getData() {
    return data;
}
public String getAutore() {
    return autore;
}
public String getDescrizione() {
    return descrizione;
}
    public static void CreaContent(Content content, boolean daApprovare)
    {
        ContentUtils.creaContent(content.media, content.data, content.autore, content.descrizione, daApprovare);
    }

}
