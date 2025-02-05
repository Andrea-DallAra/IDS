package com.ids.progettoids.models;

import com.ids.progettoids.utils.ItinerarioUtils;

import java.util.ArrayList;

public class Itinerario {

    /**
     * classe che rappresenta un itinerario
     */
    private int idItinerario;
    private ArrayList<POI> listaPOI;

    public Itinerario(int idItinerario, ArrayList<POI> listaPOI) {
        this.idItinerario = idItinerario;
        this.listaPOI = listaPOI;
    }

    public int getIdItinerario() {
        return idItinerario;
    }

    public ArrayList<POI> getListaPOI() {
        return listaPOI;
    }

    /**
     * Metodo che recupera uno o più itinerari.
     * Se id == -1, restituisce tutti gli itinerari.
     */
   public static ArrayList<Itinerario> getItinerari(int id) {
        return ItinerarioUtils.getItinerario(id);
    }

    public static void CreaItinerario(Itinerario itinerario, boolean daApprovare) {
        ItinerarioUtils.creaItinerario( itinerario.listaPOI, daApprovare);
    }
}
