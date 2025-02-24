package com.ids.progettoids.Views;

import java.util.ArrayList;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Coordinate;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("InserisciPOI") 
public class InserisciPOIView extends VerticalLayout {

    public InserisciPOIView() {
     
        TextField poiNameField = new TextField("Nome del POI");
        poiNameField.setPlaceholder("Inserisci il nome del POI");

        TextField latitudeField = new TextField("Latitudine del POI");
        latitudeField.setPlaceholder("Inserisci la latitudine");

        TextField longitudeField = new TextField("Longitudine del POI");
        longitudeField.setPlaceholder("Inserisci la longitudine");

        TextField poiDescrizione = new TextField("Descrizione del POI");
        poiDescrizione.setPlaceholder("Inserisci la descrizione del POI");

        TextField comuneField = new TextField("Comune");
        comuneField.setPlaceholder("Inserisci il comune del POI");

        Button submitButton = new Button("Crea POI", e -> {
            if (poiNameField.isEmpty() || latitudeField.isEmpty() || longitudeField.isEmpty() || poiDescrizione.isEmpty() || comuneField.isEmpty()) {
                Notification.show("Tutti i campi devono essere compilati", 3000, Notification.Position.MIDDLE);
                return;
            }

            String name = poiNameField.getValue();
            double latitude = Double.parseDouble(latitudeField.getValue());
            double longitude = Double.parseDouble(longitudeField.getValue());
            String description = poiDescrizione.getValue();
            String comune = comuneField.getValue();

            POI poi = new POI.Builder()
                    .setNome(name)
                    .setCoordinate(new Coordinate(latitude, longitude))
                    .setDescrizione(description)
                    .setMediaList(new ArrayList<>())
                    .setComune(comune)
                    .build();
            boolean daApprovare = false;
            
            if (SessioneUtente.utente.getRuolo().contains(Ruolo.Contributore) && !SessioneUtente.utente.getRuolo().contains(Ruolo.Curatore)) {
                if (!SessioneUtente.utente.getRuolo().contains(Ruolo.ContributoreAutenticato)) {
                    daApprovare = true;
                }
            }
            
            POI.CreaPOI(poi, daApprovare);
            Notification.show("POI creato con successo", 3000, Notification.Position.MIDDLE);
        });

        add(poiNameField,comuneField, latitudeField, longitudeField, poiDescrizione,  submitButton);
    }
}
