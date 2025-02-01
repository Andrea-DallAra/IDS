package com.ids.progettoids.Views;

import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("itinerarioList")
public class ItinerarioListView extends VerticalLayout {

    private Grid<Itinerario> itinerarioGrid = new Grid<>(Itinerario.class);
    private TextField searchField = new TextField("Cerca Itinerario per ID");

    public ItinerarioListView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

       
        itinerarioGrid.setColumns("idItinerario");

     
        List<Itinerario> listaItinerari = ItinerarioUtils.getItinerario(-1);
        itinerarioGrid.setItems(listaItinerari);

     
        searchField.setPlaceholder("Inserisci ID...");
        searchField.addValueChangeListener(event -> {
            String searchTerm = event.getValue();
            if (searchTerm.matches("\\d+")) {
                int id = Integer.parseInt(searchTerm);
                List<Itinerario> filteredList = ItinerarioUtils.getItinerario(id);
                itinerarioGrid.setItems(filteredList);
            }
        });

        
        itinerarioGrid.asSingleSelect().addValueChangeListener(event -> {
            Itinerario selectedItinerario = event.getValue();
            if (selectedItinerario != null) {
                getUI().ifPresent(ui -> ui.navigate("itinerarioDettagli/" + selectedItinerario.getIdItinerario()));
            }
        });
        add(searchField, itinerarioGrid);
    }
}
