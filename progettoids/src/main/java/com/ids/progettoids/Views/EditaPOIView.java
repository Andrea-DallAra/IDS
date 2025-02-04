package com.ids.progettoids.Views;

import java.util.List;



import com.ids.progettoids.models.Coordinate;
import com.ids.progettoids.models.POI;

import com.ids.progettoids.utils.EditaUtils;
import com.ids.progettoids.utils.POIutils;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("EditaPOI") 
public class EditaPOIView extends VerticalLayout{
   public EditaPOIView() {
    Grid<POI> poiGrid = new Grid<>(POI.class);
    List<POI> listaPOI = POIutils.getPOI(null);
    poiGrid.setItems(listaPOI);

    TextField poiNameField = new TextField("Nome del POI da modificare");
    poiNameField.setPlaceholder("Inserisci il nome del POI da modificare");
    POI[] poidaEditareArray = new POI[1];

    TextField poiNameFieldEditato = new TextField("Nome del POI");
    poiNameFieldEditato.setPlaceholder("Inserisci il nuovo nome del POI");

    TextField latitudeFieldEditato = new TextField("Latitudine del POI");
    latitudeFieldEditato.setPlaceholder("Inserisci la latitudine");

    TextField longitudeFieldEditato = new TextField("Longitudine del POI");
    longitudeFieldEditato.setPlaceholder("Inserisci la longitudine");

    TextField poiDescrizioneEditato = new TextField("Descrizione del POI");
    poiDescrizioneEditato.setPlaceholder("Inserisci la descrizione del POI");

    
    

    Button submitButton = new Button("Edita POI", e -> {
        if (poiNameField.isEmpty() || poiNameFieldEditato.isEmpty() || latitudeFieldEditato.isEmpty() ||
            longitudeFieldEditato.isEmpty() || poiDescrizioneEditato.isEmpty()) {
            Notification.show("Tutti i campi devono essere compilati", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
           
            List<POI> poiList = POIutils.getPOI(poiNameField.getValue());
            if (poiList.isEmpty()) {
                Notification.show("POI non trovato", 3000, Notification.Position.MIDDLE);
                return;
            }
            poidaEditareArray[0] = poiList.get(0);

            
            double latitude = Double.parseDouble(latitudeFieldEditato.getValue());
            double longitude = Double.parseDouble(longitudeFieldEditato.getValue());


           
            POI nuovoPOI = new POI(
                poiNameFieldEditato.getValue(),
                new Coordinate(latitude, longitude),
                poiDescrizioneEditato.getValue(),
                poidaEditareArray[0].getMediaList()
            );

            
            EditaUtils.EditaPOI(poidaEditareArray[0], nuovoPOI);

            Notification.show("POI editato con successo", 3000, Notification.Position.MIDDLE);

        } catch (NumberFormatException err) {
            Notification.show("Errore di formato nei campi numerici", 3000, Notification.Position.MIDDLE);
        } catch (Exception error) {
            Notification.show("Errore durante l'aggiornamento del POI", 3000, Notification.Position.MIDDLE);
        }
    });

    add(poiGrid, poiNameField, poiNameFieldEditato, latitudeFieldEditato, longitudeFieldEditato, poiDescrizioneEditato,  submitButton);
}

}
