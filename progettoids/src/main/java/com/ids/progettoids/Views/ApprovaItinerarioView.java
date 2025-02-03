package com.ids.progettoids.Views;

import java.util.List;

import com.ids.progettoids.models.Curatore;
import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("ApprovaItinerario") 
public class ApprovaItinerarioView extends VerticalLayout{
    private Grid<Itinerario> itinerarioGrid = new Grid<>(Itinerario.class);
    
    public ApprovaItinerarioView() {
        List<Itinerario> listaItinerariDaApprovare=ItinerarioUtils.getAllItineraridaApprovare();
        itinerarioGrid.setItems(listaItinerariDaApprovare);
        TextField idItinerarioDaApprovare = new TextField("id dell'itinerario da approvare");
        idItinerarioDaApprovare.setPlaceholder("Inserisci l'id dell'itinerario da approvare");
        Button submitButton = new Button("Approva Itinerario", e -> {
            if (idItinerarioDaApprovare.isEmpty()) {
                Notification.show("Devi compilare il campo id", 3000, Notification.Position.MIDDLE);
                return;
            }
            Itinerario itinerarioApprovato=ItinerarioUtils.getItinerariodaApprovare(Integer.parseInt(idItinerarioDaApprovare.getValue()));
            Curatore curatore=new Curatore(SessioneUtente.utente.getNome(), SessioneUtente.utente.getCognome(), SessioneUtente.utente.getEmail(), SessioneUtente.utente.getPassword(), SessioneUtente.utente.getUsername());
            curatore.ApprovaItinerari(itinerarioApprovato);
            Notification.show("Itinerario approvato con successo", 3000, Notification.Position.MIDDLE);
        });
        add(itinerarioGrid,idItinerarioDaApprovare,submitButton);
    }
}
