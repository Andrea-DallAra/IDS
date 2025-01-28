package com.ids.progettoids.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("") 
public class MainView extends VerticalLayout {

    public MainView() {
       
        Button navigateToInserisciPOIButton = new Button("Vai a Inserisci POI", e -> {
            getUI().ifPresent(ui -> ui.navigate("/InserisciPOI"));
        });

        add(navigateToInserisciPOIButton);
    }
}