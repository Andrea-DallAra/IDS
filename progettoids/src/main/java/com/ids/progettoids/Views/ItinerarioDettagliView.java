package com.ids.progettoids.Views;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.models.Turista;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.List;

@Route("itinerarioDettagli")
@PageTitle("Dettagli Itinerario")
public class ItinerarioDettagliView extends VerticalLayout implements HasUrlParameter<String> {

    private Grid<POI> poiGrid = new Grid<>(POI.class);

    public ItinerarioDettagliView() {
        setAlignItems(Alignment.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String itinerarioId) {
        if (itinerarioId == null || !itinerarioId.matches("\\d+")) {
            add(new Paragraph("Itinerario non trovato. ID non valido: " + itinerarioId));
            return;
        }

        int id = Integer.parseInt(itinerarioId);
        List<Itinerario> itinerari = ItinerarioUtils.getItinerario(id);

        if (itinerari.isEmpty()) {
            add(new Paragraph("Itinerario non trovato con ID: " + id));
            return;
        }

        Itinerario itinerario = itinerari.get(0);

        add(new Paragraph("ID Itinerario: " + itinerario.getIdItinerario()));

        poiGrid.setItems(itinerario.getListaPOI());
        poiGrid.removeAllColumns();
        poiGrid.addColumn(POI::getNome).setHeader("Nome POI");

        add(poiGrid);

       
        Button backButton = new Button("Torna alla lista", clickEvent ->
                getUI().ifPresent(ui -> ui.navigate("itinerarioList"))
        );
        add(backButton);

    
        if (SessioneUtente.utente != null && SessioneUtente.utente.getRuolo().contains(Ruolo.Turista)) {
            Button salvaItinerarioButton = new Button("Salva Itinerario", clickEvent -> addItinerariDaSalvare(itinerario.getIdItinerario()));
            add(salvaItinerarioButton);

            
        }
    }
    
   
    private void addItinerariDaSalvare(int itinerario) {
        
        Turista pass = new Turista();
        pass.SetUsername(SessioneUtente.utente.getUsername());
        pass.setAutenticato(true);
        pass.salvaItinerario(itinerario);
        Notification.show("Itinerario salvato", 3000, Notification.Position.MIDDLE);

    }
 
}
