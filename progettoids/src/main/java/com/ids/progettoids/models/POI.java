package com.ids.progettoids.models;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.utils.POIutils;

public class POI {

    private String nome;
    private Coordinate coordinate;
    private String descrizione;
    private  List<Content> mediaList;

 
   /* public POI(String _nome, Coordinate _coordinate, String _descrizione, Content _media) {
        setNome(_nome);
        setCoordinate(_coordinate);
        setDescrizione(_descrizione);
        setMedia(_media);
    } */
    public POI(String nome, Coordinate coordinate, String descrizione, List<Content> _mediaList) {
        this.nome = nome;
        this.coordinate = coordinate;
        this.descrizione = descrizione;
         
        if(this.mediaList == null)
        {
          this.mediaList = new ArrayList<>();
        }
        setMediaList(_mediaList);
    }

 /*   public void setMedia(Content content) {
        if (this.mediaList == null) {
            this.mediaList = new ArrayList<>(); 
        }
        this.mediaList.add(content);
    } */
   public void setMediaList(List<Content> _mediaList)
   {
       for (Content contenuto : _mediaList) {
            if(!mediaList.contains(contenuto)){
                mediaList.add(contenuto);
            }
       }
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


  
    public String getNome() {
        return nome;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public List<Content> getMediaList() {
        return mediaList;
    }
 
    public static ArrayList<POI> VediPOI(String nome) {
      
            return POIutils.getPOI(nome);
        
    }
   


    public static void CreaPOI(POI poi, boolean daApprovare) {
        POIutils.creaPOI(poi.nome, poi.coordinate, poi.descrizione, poi.mediaList, daApprovare);
    }

    @Override
    public String toString() {
        return "POI{" +
                "nome='" + nome + '\'' +
                ", coordinate=" + coordinate +
                ", descrizione='" + descrizione + '\'' +
                ", media=" + mediaList.toString() +
                '}';
    }

  
    
}
