package com.ids.progettoids.Views;

import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.POIutils;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route("poiList")
public class POIListView extends VerticalLayout {

    private Grid<POI> poiGrid = new Grid<>(POI.class);
    private TextField searchField = new TextField("Cerca POI per nome");
    private TextField comuneSearchField = new TextField("Cerca POI per comune");

    public POIListView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

       
        poiGrid.setColumns("nome", "comune"); 

      
        List<POI> listaPOI = POIutils.getPOI(null);
        poiGrid.setItems(listaPOI);

      
        searchField.setPlaceholder("Inserisci nome...");
        
      
        comuneSearchField.setPlaceholder("Inserisci comune...");

      
        searchField.addValueChangeListener(event -> filterPOI());
        comuneSearchField.addValueChangeListener(event -> filterPOI());

        
        poiGrid.asSingleSelect().addValueChangeListener(event -> {
            POI selectedPOI = event.getValue();
            if (selectedPOI != null) {
                getUI().ifPresent(ui -> ui.navigate("poiDettagli/" + selectedPOI.getNome()));
            }
        });

        add(searchField, comuneSearchField, poiGrid);
    }

    private void filterPOI() {
        String searchTerm = searchField.getValue().toLowerCase();
        String comuneTerm = comuneSearchField.getValue().toLowerCase();

        List<POI> filteredList = POIutils.getPOI(null).stream()
                .filter(poi -> (searchTerm.isEmpty() || poi.getNome().toLowerCase().contains(searchTerm)) &&
                               (comuneTerm.isEmpty() || poi.getComune().toLowerCase().contains(comuneTerm)))
                .collect(Collectors.toList());

        poiGrid.setItems(filteredList);
    }
}
