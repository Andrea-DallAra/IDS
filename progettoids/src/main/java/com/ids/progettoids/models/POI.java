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
    private  List<Content> mediaList;
    private String comune;
 /**
  * @param nome il nome del POI
  * @param coordinate la coordinate del POI
  * @param descrizione la descrizione del POI
  * @param _mediaList la lista dei contenuti del POI
  * @param comune il comune del POI
  */

    public POI(String nome, Coordinate coordinate, String descrizione, List<Content> _mediaList, String _comune) {
        this.nome = nome;
        this.coordinate = coordinate;
        this.descrizione = descrizione;
        this.comune = _comune;
        if(this.mediaList == null)
        {
          this.mediaList = new ArrayList<>();
        }
        setMediaList(_mediaList);
    }

    /**
     * Metodo che aggiunge i contenuti alla la lista dei contenuti
     * @param _mediaList
     */
   private void setMediaList(List<Content> _mediaList)
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
        POIutils.creaPOI(poi.nome, poi.coordinate, poi.descrizione, poi.mediaList, poi.getComune(), daApprovare);
    }
    public String getComune() {
        return comune;
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
