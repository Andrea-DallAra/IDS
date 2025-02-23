package com.ids.progettoids.Views;

import java.util.ArrayList;
import java.util.List;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("PoiSalvati")
public class POISalvatiView extends VerticalLayout {
    private Grid<POI> poiGrid = new Grid<>(POI.class);

    public POISalvatiView(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        List<String> listaNomiPOISalvati=POIutils.getNomiPOISalvati(SessioneUtente.utente.getUsername());
        List<POI> listaPOI=new ArrayList<>();
        for (String s : listaNomiPOISalvati) {
            listaPOI.add(POIutils.getPOI(s).get(0));
        }
        poiGrid.setItems(listaPOI);
        poiGrid.asSingleSelect().addValueChangeListener(event -> {
            POI selectedPOI = event.getValue();
            if (selectedPOI != null) {
                getUI().ifPresent(ui -> ui.navigate("poiDettagli/" + selectedPOI.getNome()));
            }
        });
        add(poiGrid); 
    }
}
