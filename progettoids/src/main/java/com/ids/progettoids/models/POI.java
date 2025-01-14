package com.ids.progettoids.models;

public class POI {
    
    private String nome;
    private Coordinate coordinate;
    private String descrizione;
    private String media;
     /**
     * Factory Method: Builder
     */
    public POI(String _nome, Coordinate _coordinate, String _descrizione,String _media)
    {
        setNome(_nome);
        setCoordinate(_coordinate);
        setDescrizione(_descrizione);
        setMedia(_media);
    }
    void setNome(String nome) {
        this.nome = nome;
    }

    void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public void setMedia(String media) {
        this.media = media;
    }
    public static void VediPOI()
    {
    
    }

    public static void VediItinerario()
    {
    
    }
}

