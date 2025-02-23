package com.ids.progettoids.Views;

import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.utils.ItinerarioUtils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("ItinerariSalvati")
public class ItinerariSalvatiView extends VerticalLayout {
    private Grid<Itinerario> itinerarioGrid = new Grid<>(Itinerario.class);

    public ItinerariSalvatiView(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        List<Integer> listaIdItinerariSalvati = ItinerarioUtils.getIdItinerariSalvati(SessioneUtente.utente.getUsername());
        List<Itinerario> listaItinerari = new ArrayList<>();
        for(int i=0;i<listaIdItinerariSalvati.size();i++){
            listaItinerari.add(ItinerarioUtils.getItinerario(listaIdItinerariSalvati.get(i)).get(0));
        }
        itinerarioGrid.setItems(listaItinerari);
        itinerarioGrid.asSingleSelect().addValueChangeListener(event -> {
            Itinerario selectedItinerario = event.getValue();
            if (selectedItinerario != null) {
                getUI().ifPresent(ui -> ui.navigate("itinerarioDettagli/" + selectedItinerario.getIdItinerario()));
            }
        });
        add(itinerarioGrid);        
    }

}
