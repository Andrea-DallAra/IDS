package com.ids.progettoids.Views;

import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.POIutils;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("poiList")
public class POIListView extends VerticalLayout {

    private Grid<POI> poiGrid = new Grid<>(POI.class);
    private TextField searchField = new TextField("Cerca POI per nome");

    public POIListView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

       
        poiGrid.setColumns("nome");

      
        List<POI> listaPOI = POIutils.getPOI(null);
        poiGrid.setItems(listaPOI);

       
        searchField.setPlaceholder("Inserisci nome...");
        searchField.addValueChangeListener(event -> {
            String searchTerm = event.getValue();
            List<POI> filteredList = POIutils.getPOI(searchTerm);
            poiGrid.setItems(filteredList);
        });

      
        poiGrid.asSingleSelect().addValueChangeListener(event -> {
            POI selectedPOI = event.getValue();
            if (selectedPOI != null) {
                getUI().ifPresent(ui -> ui.navigate("poiDettagli/" + selectedPOI.getNome()));
            }
        });

        add(searchField, poiGrid);
    }
}
