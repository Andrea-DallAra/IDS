package com.ids.progettoids.Views;

import java.util.ArrayList;
import java.util.List;

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
public class EditaItinerarioView extends VerticalLayout{
    public EditaItinerarioView() {
        Grid<Itinerario> contentGrid = new Grid<>(Itinerario.class);
        List<Itinerario> listaItinerari=ItinerarioUtils.getItinerario(-1);
        contentGrid.setItems(listaItinerari);
        TextField idItinerarioField = new TextField("Id dell'itinerario da modificare");
        idItinerarioField.setPlaceholder("Inserisci l'id dell'itinerario da da modificare");
        TextField nomiPOIFieldEditato = new TextField("Nomi dei POI da inserire nell'itinerario intervallati da ,");
        nomiPOIFieldEditato.setPlaceholder("Inserisci i nomi dei POI da inserire nell'itinerario intervallati da ,");
        Button submitButton = new Button("EditaItinerario", e -> {
            if (idItinerarioField.isEmpty() || nomiPOIFieldEditato.isEmpty() ) {
                Notification.show("Tutti i campi devono essere compilati", 3000, Notification.Position.MIDDLE);
                return;
            }
            try {
                Itinerario oldItinerario=ItinerarioUtils.getItinerario(Integer.parseInt(idItinerarioField.getValue())).get(0);
                ArrayList<POI> listaPOI=ItinerarioUtils.parsePOIList(nomiPOIFieldEditato.getValue());
                Itinerario newItinerario= new Itinerario(oldItinerario.getIdItinerario(), listaPOI);
                EditaUtils.EditaItinerario(oldItinerario,newItinerario);
                Notification.show("Itinerario editato con successo", 3000, Notification.Position.MIDDLE);
            } catch (NumberFormatException err) {
                Notification.show("Errore di formato", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore generico", 3000, Notification.Position.MIDDLE);
            }
        });
        add(contentGrid,idItinerarioField,nomiPOIFieldEditato,submitButton);
    }
}
