package com.ids.progettoids.Views;

import java.util.Map;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Gestore;
import com.ids.progettoids.models.Utente;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route("RichiesteCambioRuoloView")
public class RichiesteCambioRuoloView extends VerticalLayout {
    
    private Gestore gestore;

    public RichiesteCambioRuoloView(){
        gestore= (Gestore) new Utente.Builder()
        .setUsername(SessioneUtente.utente.getUsername())
        .setRuoli(SessioneUtente.utente.getRuolo())
        .setTipo(Ruolo.Gestore)
        .build();
        Map<String,String> richieste=gestore.getRichiesteCambioRuolo();
        Grid<String> richiesteGrid = new Grid<>();
        richiesteGrid.addColumn(new ComponentRenderer<>(
            username -> {
                Span span = new Span(username);
                span.setWidth("100%");
                return span;
            }
        )).setHeader("Username");
        richiesteGrid.addColumn(new ComponentRenderer<>(
            username -> {
                Span span = new Span(richieste.get(username));
                span.setWidth("100%");
                return span;
            }
        )).setHeader("Ruolo");
        richiesteGrid.setItems(richieste.keySet());
        Button accettaButton = new Button("Accetta");
        Button rifiutaButton = new Button("Rifiuta");
        
        add(richiesteGrid,accettaButton,rifiutaButton);

        accettaButton.addClickListener(event -> {
            String username = richiesteGrid.getSelectedItems().iterator().next();
            String ruolo = richieste.get(username);
            gestore.EditaRuolo(username, ruolo);
            richieste.remove(username);
            richiesteGrid.getDataProvider().refreshAll();
            gestore.removeRuoloFromDatabase(username);
        });
        rifiutaButton.addClickListener(event -> {
            String username = richiesteGrid.getSelectedItems().iterator().next();
            richieste.remove(username);
            richiesteGrid.getDataProvider().refreshAll();
            gestore.removeRuoloFromDatabase(username);
        });
    }

    
}
