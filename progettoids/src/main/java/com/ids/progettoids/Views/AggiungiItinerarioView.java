package com.ids.progettoids.Views;

import java.util.ArrayList;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("aggiungiItinerario")
public class AggiungiItinerarioView extends VerticalLayout {

    public AggiungiItinerarioView() {
        TextField itinerarioField = new TextField("Nome Itinerario");
        TextField poiField = new TextField("Inserisci POI (separati da virgola)");

        Button submitButton = new Button("Crea Itinerario", event -> {
            String nomeItinerario = itinerarioField.getValue();
            String listaPOI = poiField.getValue();

            if (nomeItinerario.isEmpty() || listaPOI.isEmpty()) {
                Notification.show("Inserisci tutti i campi", 3000, Notification.Position.MIDDLE);
                return;
            }

            boolean daApprovare = false;
            ArrayList<POI> parsedPOIList = parsePOIList(listaPOI);
            SessioneUtente.utente.CaricaRuoli(SessioneUtente.utente.getUsername());
            if(SessioneUtente.utente.getRuolo().contains(Ruolo.Contributore) && !SessioneUtente.utente.getRuolo().contains(Ruolo.Curatore))
            {
                if(!SessioneUtente.utente.getRuolo().contains(Ruolo.ContributoreAutenticato))
                {
                    daApprovare = true;
                }
            }
            ItinerarioUtils.creaItinerario(parsedPOIList, daApprovare);
            Notification.show("Itinerario creato con successo!", 3000, Notification.Position.MIDDLE);
        });

        add(itinerarioField, poiField, submitButton);
    }

    private static ArrayList<POI> parsePOIList(String listaPOIStr) {
        ArrayList<POI> listaPOI = new ArrayList<>();

        if (listaPOIStr != null && !listaPOIStr.isEmpty()) {
            String[] nomiPOI = listaPOIStr.split(",");
            for (String nome : nomiPOI) {
                listaPOI.addAll(POIutils.getPOI(nome.trim()));
            }
        }

        return listaPOI;
    }
}
