package com.ids.progettoids.models;


import com.ids.progettoids.utils.ContentUtils;

public class Content {
    private String media;
    private String data;
    private String autore;
    private String descrizione;
    
    /**
     * Factory Method: Builder
     */
    public Content(String media, String data, String autore, String descrizione) {
        setMedia(media);
        setAutore(autore);
        setData(data);
        setDescrizione(descrizione);
        }
    public void setAutore(String autore) {
        this.autore = autore;
    }
    public void setData(String data) {
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
public String getData() {
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

    @Override
    public String toString() {
        return "Content{" +
                "media='" + media + '\'' +
                ", data='" + data + '\'' +
                ", autore='" + autore + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }

}
