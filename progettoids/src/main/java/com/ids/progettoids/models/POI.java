package com.ids.progettoids.models;
import java.util.ArrayList;

import com.ids.progettoids.utils.POIutils;

public class POI {

    private String nome;
    private Coordinate coordinate;
    private String descrizione;
    private Content media;

 
    public POI(String _nome, Coordinate _coordinate, String _descrizione, Content _media) {
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

  
    public String getNome() {
        return nome;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public Content getMedia() {
        return media;
    }
 
    public static ArrayList<POI> VediPOI(String nome) {
      
            return POIutils.getPOI(nome);
        
    }


    public static void CreaPOI(POI poi, boolean daApprovare) {
        POIutils.creaPOI(poi.nome, poi.coordinate, poi.descrizione, poi.media, daApprovare);
    }
  
    
}
