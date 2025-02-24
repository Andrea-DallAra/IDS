package com.ids.progettoids.models;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.utils.POIutils;

public class POI {

    /**
     * Classe che rappresenta un Poi
     */
    private String nome;
    private Coordinate coordinate;
    private String descrizione;
    private List<Content> mediaList;
    private String comune;
 /**
  * @param nome il nome del POI
  * @param coordinate la coordinate del POI
  * @param descrizione la descrizione del POI
  * @param _mediaList la lista dei contenuti del POI
  * @param comune il comune del POI
  */

    private POI(Builder builder) {
            this.nome = builder.nome;
            this.coordinate = builder.coordinate;
            this.descrizione = builder.descrizione;
            this.mediaList = builder.mediaList != null ? builder.mediaList : new ArrayList<>();
            this.comune = builder.comune;
        }
    
    public static class Builder {
        private String nome;
        private Coordinate coordinate;
        private String descrizione;
        private List<Content> mediaList;
        private String comune;

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
            return this;
        }

        public Builder setDescrizione(String descrizione) {
            this.descrizione = descrizione;
            return this;
        }

        public Builder setMediaList(List<Content> mediaList) {
            for (Content contenuto : mediaList) {
                if(!mediaList.contains(contenuto)){
                    mediaList.add(contenuto);
                }
           }
           return this;
        }

        public Builder setComune(String comune) {
            this.comune = comune;
            return this;
        }

        public POI build() {
            return new POI(this);
        }
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
   
    public String getComune() {
        return comune;
    }

    public static void CreaPOI(POI poi, boolean daApprovare) {
        POIutils.creaPOI(poi.nome, poi.coordinate, poi.descrizione, poi.mediaList, poi.getComune(), daApprovare);
    }
    
    @Override
    public String toString() {
        return "POI{" +
                "nome='" + nome + '\'' +
                ", coordinate=" + coordinate +
                ", descrizione='" + descrizione + '\'' +
                ", media=" + mediaList.toString() +
                ", comune='" + comune + '\'' +
                '}';
    }
    

  
    
}
