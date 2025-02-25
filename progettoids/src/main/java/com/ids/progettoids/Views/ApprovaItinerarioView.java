package com.ids.progettoids.Views;

import java.util.List;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Curatore;
import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.models.Utente;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("ApprovaItinerario") 
public class ApprovaItinerarioView extends VerticalLayout {
    private Grid<Itinerario> itinerarioGrid = new Grid<>(Itinerario.class);
    private TextField selectedItinerarioField = new TextField("Itinerario Selezionato");

    public ApprovaItinerarioView() {
        List<Itinerario> listaItinerariDaApprovare = ItinerarioUtils.getAllItineraridaApprovare();
        itinerarioGrid.setItems(listaItinerariDaApprovare);
        itinerarioGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

      
        selectedItinerarioField.setReadOnly(true);
        selectedItinerarioField.setPlaceholder("Seleziona un Itinerario dalla lista");

       
        itinerarioGrid.asSingleSelect().addValueChangeListener(event -> {
            Itinerario selectedItinerario = event.getValue();
            if (selectedItinerario != null) {
                selectedItinerarioField.setValue(String.valueOf(selectedItinerario.getIdItinerario())); 
            } else {
                selectedItinerarioField.clear();
            }
        });

        Curatore curatore = (Curatore) new Utente.Builder()
            .setUsername(SessioneUtente.utente.getUsername())
            .setRuoli(SessioneUtente.utente.getRuolo())
            .setTipo(Ruolo.Curatore)
            .build();

       
        Button submitButton = new Button("Approva Itinerario", e -> {
            if (selectedItinerarioField.isEmpty()) {
                Notification.show("Seleziona un Itinerario dalla lista", 3000, Notification.Position.MIDDLE);
                return;
            }

            int itinerarioId = Integer.parseInt(selectedItinerarioField.getValue());
            Itinerario itinerarioDaApprovare = ItinerarioUtils.getItinerariodaApprovare(itinerarioId);
            if (itinerarioDaApprovare == null) {
                Notification.show("Errore: Itinerario non trovato", 3000, Notification.Position.MIDDLE);
                return;
            }

            curatore.ApprovaItinerari(itinerarioDaApprovare);

            Notification.show("Itinerario approvato con successo", 3000, Notification.Position.MIDDLE);
        });

        Button submitButtonRifiuta = new Button("Rifiuta Itinerario", e -> {
            if (selectedItinerarioField.isEmpty()) {
                Notification.show("Seleziona un Itinerario dalla lista", 3000, Notification.Position.MIDDLE);
                return;
            }

            int itinerarioId = Integer.parseInt(selectedItinerarioField.getValue());
            Itinerario itinerarioDaRifiutare = ItinerarioUtils.getItinerariodaApprovare(itinerarioId);
            if (itinerarioDaRifiutare == null) {
                Notification.show("Errore: Itinerario non trovato", 3000, Notification.Position.MIDDLE);
                return;
            }

            curatore.deleteItinerarioDaApprovare(itinerarioId);

            Notification.show("Itinerario rifiutato con successo", 3000, Notification.Position.MIDDLE);
        });

        add(itinerarioGrid, selectedItinerarioField, submitButton,submitButtonRifiuta);
    }
}
