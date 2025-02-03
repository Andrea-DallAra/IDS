package com.ids.progettoids.Views;

import com.ids.progettoids.models.POI;
import com.ids.progettoids.models.Turista;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.List;

@Route("poiDettagli")
@PageTitle("Dettagli POI")
public class POIDetaggliView extends VerticalLayout implements HasUrlParameter<String> {

    public POIDetaggliView() {
        setAlignItems(Alignment.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String poiName) {
        if (poiName == null || poiName.isEmpty()) {
            add(new Paragraph("POI non trovato."));
            return;
        }

        List<POI> poiList = POIutils.getPOI(poiName);
        if (poiList.isEmpty()) {
            add(new Paragraph("POI non trovato."));
            return;
        }

        POI poi = poiList.get(0);

        add(new Paragraph("Nome: " + poi.getNome()));
        add(new Paragraph("Descrizione: " + poi.getDescrizione()));
        add(new Paragraph("Coordinate: " + poi.getCoordinate()));

       
        Button backButton = new Button("Torna alla lista", event2 ->
            getUI().ifPresent(ui -> ui.navigate("poiList"))
        );
        add(backButton);

        Button aggiungiContenutoButton = new Button("Aggiungi Contenuto", event2 ->
        getUI().ifPresent(ui -> ui.navigate("CreaContenuto/" + poi.getNome())));
         add(aggiungiContenutoButton);

            Button salvaPOIButton = new Button("Salva POI", event2 -> salvaPOI(poi.getNome()));
            add(salvaPOIButton);
        
    }

    private void salvaPOI(String poiName) {
        Turista pass = new Turista();
        pass.SetUsername(SessioneUtente.utente.getUsername());
        pass.setAutenticato(true);
        pass.salvaPOI(poiName);
        Notification.show("POI salvato", 3000, Notification.Position.MIDDLE);
    }
}
