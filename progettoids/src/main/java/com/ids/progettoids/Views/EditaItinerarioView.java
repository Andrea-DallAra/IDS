package com.ids.progettoids.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.EditaUtils;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("EditaItinerario") 
public class EditaItinerarioView extends VerticalLayout {
    private Grid<Itinerario> itinerarioGrid = new Grid<>(Itinerario.class);
    private TextField idItinerarioField = new TextField("ID dell'Itinerario");
    private TextField nomiPOIFieldEditato = new TextField("Nomi dei POI da inserire");
    private Itinerario selectedItinerario;

    public EditaItinerarioView() {
        List<Itinerario> listaItinerari = ItinerarioUtils.getItinerario(-1);
        itinerarioGrid.setItems(listaItinerari);
        itinerarioGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        idItinerarioField.setReadOnly(true);
        idItinerarioField.setPlaceholder("Seleziona un itinerario dalla lista");
        nomiPOIFieldEditato.setPlaceholder("Inserisci i nomi dei POI separati da ,");

        itinerarioGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedItinerario = event.getValue();
            if (selectedItinerario != null) {
                idItinerarioField.setValue(String.valueOf(selectedItinerario.getIdItinerario()));
                nomiPOIFieldEditato.setValue(
                    selectedItinerario.getListaPOI().stream()
                        .map(POI::getNome)
                        .collect(Collectors.joining(", "))
                );
            }
        });

        Button submitButton = new Button("Edita Itinerario", e -> {
            if (selectedItinerario == null) {
                Notification.show("Seleziona un itinerario dalla lista", 3000, Notification.Position.MIDDLE);
                return;
            }

            if (nomiPOIFieldEditato.isEmpty()) {
                Notification.show("Inserisci almeno un POI", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                ArrayList<POI> listaPOI = ItinerarioUtils.parsePOIList(nomiPOIFieldEditato.getValue());
                Itinerario newItinerario = new Itinerario(selectedItinerario.getIdItinerario(), listaPOI);

                EditaUtils.EditaItinerario(selectedItinerario, newItinerario);
                Notification.show("Itinerario editato con successo", 3000, Notification.Position.MIDDLE);
            } catch (NumberFormatException err) {
                Notification.show("Errore di formato", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore generico", 3000, Notification.Position.MIDDLE);
            }
        });

        add(itinerarioGrid, idItinerarioField, nomiPOIFieldEditato, submitButton);
    }
}
