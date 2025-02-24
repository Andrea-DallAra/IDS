package com.ids.progettoids.models;


import com.ids.progettoids.utils.ContentUtils;

public class Content {
    /**
     * Classe che rappresenta un content
     */
    private String media;
    private String data;
    private String autore;
    private String descrizione;

    private Content(Builder builder) {
        this.media = builder.media;
        this.data = builder.data;
        this.autore = builder.autore;
        this.descrizione = builder.descrizione;
    }

    /**
     * Factory Method: Builder
     */
    public static class Builder {
        private String media;
        private String data;
        private String autore;
        private String descrizione;

        public Builder setMedia(String media) {
            this.media = media;
            return this;
        }

        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        public Builder setAutore(String autore) {
            this.autore = autore;
            return this;
        }

        public Builder setDescrizione(String descrizione) {
            this.descrizione = descrizione;
            return this;
        }

        public Content build() {
            return new Content(this);
        }
    }

    public int getIdContent() {
        if (this.media == null) return 0;
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

    public static void CreaContent(Content content, boolean daApprovare) {
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
