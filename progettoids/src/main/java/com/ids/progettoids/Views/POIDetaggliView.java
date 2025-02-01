package com.ids.progettoids.Views;

import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.POIutils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
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
    }
}
