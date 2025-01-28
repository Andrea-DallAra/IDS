package com.ids.progettoids.Views;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Coordinate;
import com.ids.progettoids.models.POI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("") 
public class MainView extends VerticalLayout {

    public MainView() {
        // Campo di testo per inserire il nome del POI
        TextField poiNameField = new TextField("Nome del POI");
        poiNameField.setPlaceholder("Inserisci il nome del POI");
        TextField poiCoordinate = new TextField("Coordinate del POI");
        poiCoordinate.setPlaceholder("Inserisci le Coordinate del POI");
        TextField poiDescrizione = new TextField("Descrizione del POI");
         poiDescrizione.setPlaceholder("Inserisci la descrizione del POI");
        // Bottone per inviare il comando
        Button submitButton = new Button("Cerca POI", e -> {
            Content nullo = new Content(null, null, null, null);
        
            POI.CreaPOI(new POI(poiNameField.getValue(),new Coordinate(20, 0), poiDescrizione.getValue(), nullo ), false); 
        });
        add(poiNameField, submitButton);
    }    
}