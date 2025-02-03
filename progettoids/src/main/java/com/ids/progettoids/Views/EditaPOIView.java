package com.ids.progettoids.Views;

import java.util.List;

import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Coordinate;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.EditaUtils;
import com.ids.progettoids.utils.POIutils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
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
        POI poidaEditare=POIutils.getPOI(poiNameField.getValue()).get(0);
        
        TextField poiNameFieldEditato = new TextField("Nome del POI");
        poiNameFieldEditato.setPlaceholder("Inserisci il nome del POI da modificare");
        TextField latitudeFieldEditato = new TextField("Latitudine del POI");
        latitudeFieldEditato.setPlaceholder("Inserisci la latitudine");

        TextField longitudeFieldEditato = new TextField("Longitudine del POI");
        longitudeFieldEditato.setPlaceholder("Inserisci la longitudine");

        TextField poiDescrizioneEditato = new TextField("Descrizione del POI");
        poiDescrizioneEditato.setPlaceholder("Inserisci la descrizione del POI");

        DatePicker datePickerEditato = new DatePicker("Data");
        datePickerEditato.setPlaceholder("Seleziona la data");

        
        Button submitButton = new Button("EditaPOI", e -> {
            if (poiNameField.isEmpty() || poiNameFieldEditato.isEmpty() || latitudeFieldEditato.isEmpty() || longitudeFieldEditato.isEmpty() || poiDescrizioneEditato.isEmpty() || datePickerEditato.isEmpty()) {
                Notification.show("Tutti i campi devono essere compilati", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                String pass = datePickerEditato.toString();
                double latitude = Double.parseDouble(latitudeFieldEditato.getValue());
                double longitude = Double.parseDouble(longitudeFieldEditato.getValue());
                Content oldContent= poidaEditare.getMedia();
                Content newContent = new Content(oldContent.getMedia(), pass, oldContent.getAutore(), oldContent.getDescrizione());
                POI nuovoPOI= new POI(poiNameFieldEditato.getValue(), new Coordinate(latitude, longitude), poiDescrizioneEditato.getValue(), newContent);
                EditaUtils.EditaPOI(poidaEditare, nuovoPOI);
                Notification.show("POI editato con successo", 3000, Notification.Position.MIDDLE);
            } catch (NumberFormatException err) {
                Notification.show("Errore di formato", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore generico", 3000, Notification.Position.MIDDLE);
            }
        });
        add(poiGrid,poiNameField,poiNameFieldEditato,latitudeFieldEditato,longitudeFieldEditato,poiDescrizioneEditato,datePickerEditato,submitButton);
    }
}
