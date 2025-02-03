package com.ids.progettoids.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("EditaElemento") 
public class EditaElementoView extends VerticalLayout{
    public EditaElementoView() {
        Button editaPOI = new Button("Vai alla pagina per editare un POI", e -> {
            getUI().ifPresent(ui -> ui.navigate("/EditaPOI"));});
        Button editaItinerario = new Button("Vai alla pagina per editare un Itinerario", e -> {
            getUI().ifPresent(ui -> ui.navigate("/EditaItinerario"));});
        Button editContent = new Button("Vai alla pagina per editare un Content", e -> {
            getUI().ifPresent(ui -> ui.navigate("/EditaContent"));});    
        add(editaPOI, editaItinerario,editContent);
    }
}
