package com.ids.progettoids.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("aggiungiItinerario")
public class AggiungiItinerarioView extends VerticalLayout {
    private Grid<POI> poiGrid;
    private TextField selectedPOIField;

    public AggiungiItinerarioView() {
        poiGrid = new Grid<>(POI.class);
        poiGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        
        List<POI> allPOIs = POIutils.getPOI(null); 
        poiGrid.setItems(allPOIs);

        
        selectedPOIField = new TextField("Itinerario composto da:");
        selectedPOIField.setReadOnly(true);
        selectedPOIField.setPlaceholder("Seleziona i POI dalla lista");

        
        poiGrid.asMultiSelect().addSelectionListener(event -> {
            List<POI> selectedPOIs = new ArrayList<>(event.getAllSelectedItems());
            String poiNames = selectedPOIs.stream()
                    .map(POI::getNome)
                    .collect(Collectors.joining(", "));
            selectedPOIField.setValue(poiNames);
        });

       
        Button submitButton = new Button("Crea Itinerario", event -> {
            ArrayList<POI> selectedPOIs = new ArrayList<>(poiGrid.getSelectedItems());

            if (selectedPOIs.isEmpty()) {
                Notification.show("Seleziona almeno un POI!", 3000, Notification.Position.MIDDLE);
                return;
            }

            boolean daApprovare = false;
            SessioneUtente.utente.CaricaRuoli(SessioneUtente.utente.getUsername());

            if (SessioneUtente.utente.getRuolo().contains(Ruolo.Contributore) &&
                !SessioneUtente.utente.getRuolo().contains(Ruolo.Curatore)) {
                if (!SessioneUtente.utente.getRuolo().contains(Ruolo.ContributoreAutenticato)) {
                    daApprovare = true;
                }
            }

            ItinerarioUtils.creaItinerario(selectedPOIs, daApprovare);
            Notification.show("Itinerario creato con successo!", 3000, Notification.Position.MIDDLE);
        });

        add(poiGrid, selectedPOIField, submitButton);
    }
}
