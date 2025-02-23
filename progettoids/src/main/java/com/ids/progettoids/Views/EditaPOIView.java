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
public class EditaPOIView extends VerticalLayout {
    private Grid<POI> poiGrid = new Grid<>(POI.class);
    private TextField poiNameField = new TextField("POI Selezionato");
    private TextField poiNameFieldEditato = new TextField("Nome del POI");
    private TextField latitudeFieldEditato = new TextField("Latitudine del POI");
    private TextField longitudeFieldEditato = new TextField("Longitudine del POI");
    private TextField poiDescrizioneEditato = new TextField("Descrizione del POI");
    private POI selectedPOI;

    public EditaPOIView() {
        List<POI> listaPOI = POIutils.getPOI(null);
        poiGrid.setItems(listaPOI);
        poiGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        poiNameField.setReadOnly(true);
        poiNameFieldEditato.setPlaceholder("Inserisci il nuovo nome del POI");
        latitudeFieldEditato.setPlaceholder("Inserisci la latitudine");
        longitudeFieldEditato.setPlaceholder("Inserisci la longitudine");
        poiDescrizioneEditato.setPlaceholder("Inserisci la descrizione del POI");

        poiGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedPOI = event.getValue();
            if (selectedPOI != null) {
                poiNameField.setValue(selectedPOI.getNome());
                poiNameFieldEditato.setValue(selectedPOI.getNome());
                latitudeFieldEditato.setValue(String.valueOf(selectedPOI.getCoordinate().getLatitudine()));
                longitudeFieldEditato.setValue(String.valueOf(selectedPOI.getCoordinate().getLongitudine()));
                poiDescrizioneEditato.setValue(selectedPOI.getDescrizione());
            }
        });

        Button submitButton = new Button("Edita POI", e -> {
            if (selectedPOI == null) {
                Notification.show("Seleziona un POI dalla lista", 3000, Notification.Position.MIDDLE);
                return;
            }

            if (poiNameFieldEditato.isEmpty() || latitudeFieldEditato.isEmpty() ||
                longitudeFieldEditato.isEmpty() || poiDescrizioneEditato.isEmpty()) {
                Notification.show("Tutti i campi devono essere compilati", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                double latitude = Double.parseDouble(latitudeFieldEditato.getValue());
                double longitude = Double.parseDouble(longitudeFieldEditato.getValue());

                POI nuovoPOI = new POI(
                    poiNameFieldEditato.getValue(),
                    new Coordinate(latitude, longitude),
                    poiDescrizioneEditato.getValue(),
                    selectedPOI.getMediaList(),
                    selectedPOI.getComune() 
                );

                EditaUtils.EditaPOI(selectedPOI, nuovoPOI);

                Notification.show("POI editato con successo", 3000, Notification.Position.MIDDLE);

            } catch (NumberFormatException err) {
                Notification.show("Errore di formato nei campi numerici", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore durante l'aggiornamento del POI", 3000, Notification.Position.MIDDLE);
            }
        });

        add(poiGrid, poiNameField, poiNameFieldEditato, latitudeFieldEditato, longitudeFieldEditato, poiDescrizioneEditato, submitButton);
    }
}
