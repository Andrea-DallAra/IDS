package com.ids.progettoids.models;

import java.util.ArrayList;

public class POI {
    
    private String nome;
    private Coordinate coordinate;
    private String descrizione;
    private Content media;
     /**
     * Factory Method: Builder
     */
    public POI(String _nome, Coordinate _coordinate, String _descrizione,Content _media)
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
    public void setMedia(Content media) {
        this.media = media;
    }
    public static void VediPOI(int id) 
    {
        if(id == -1) //ritorno tutti i poi
        {
            
        }
    }

    public static void VediItinerario( ArrayList<POI> POI)
    {
    
    }
}

