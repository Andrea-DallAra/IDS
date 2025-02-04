package com.ids.progettoids.Views;

import java.util.List;
import com.ids.progettoids.models.Curatore;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("ApprovaPOI") 
public class ApprovaPOIView extends VerticalLayout {
    private Grid<POI> poiGrid = new Grid<>(POI.class);
    private TextField selectedPOIField = new TextField("POI Selezionato");

    public ApprovaPOIView() {
        List<POI> listaPOIDaApprovare = POIutils.getAllPOIdaApprovare();
        poiGrid.setItems(listaPOIDaApprovare);
        poiGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        
        selectedPOIField.setReadOnly(true);
        selectedPOIField.setPlaceholder("Seleziona un POI dalla lista");

    
        poiGrid.asSingleSelect().addValueChangeListener(event -> {
            POI selectedPOI = event.getValue();
            if (selectedPOI != null) {
                selectedPOIField.setValue(selectedPOI.getNome());
            } else {
                selectedPOIField.clear();
            }
        });

      
        Button submitButton = new Button("Approva POI", e -> {
            if (selectedPOIField.isEmpty()) {
                Notification.show("Seleziona un POI dalla lista", 3000, Notification.Position.MIDDLE);
                return;
            }

            POI poiDaApprovare = POIutils.getPOIdaApprovare(selectedPOIField.getValue());
            if (poiDaApprovare == null) {
                Notification.show("Errore: POI non trovato", 3000, Notification.Position.MIDDLE);
                return;
            }

            Curatore curatore = new Curatore();
            curatore.SetUsername(SessioneUtente.utente.getUsername());
            curatore.ApprovaPOI(poiDaApprovare);

            Notification.show("POI approvato con successo", 3000, Notification.Position.MIDDLE);
        });

        add(poiGrid, selectedPOIField, submitButton);
    }
}
