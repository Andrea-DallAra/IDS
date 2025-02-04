package com.ids.progettoids.Views;

import java.util.List;

import com.ids.progettoids.models.Curatore;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("ApprovaPOI") 
public class ApprovaPOIView extends VerticalLayout{
    private Grid<POI> poiGrid = new Grid<>(POI.class);
    
    public ApprovaPOIView() {
        List<POI> listaPOIDaApprovare=POIutils.getAllPOIdaApprovare();
        poiGrid.setItems(listaPOIDaApprovare);
        TextField nomePOIDaApprovare = new TextField("Nome del POI da approvare");
        nomePOIDaApprovare.setPlaceholder("Inserisci il nome del POI");
        Button submitButton = new Button("Approva POI", e -> {
            if (nomePOIDaApprovare.isEmpty()) {
                Notification.show("Devi compilare il campo nome", 3000, Notification.Position.MIDDLE);
                return;
            }
            POI poiDaApprovare=POIutils.getPOIdaApprovare(nomePOIDaApprovare.getValue());
            Curatore curatore=new Curatore();
            curatore.SetUsername(SessioneUtente.utente.getUsername());
            curatore.ApprovaPOI(poiDaApprovare);
            Notification.show("POI approvato con successo", 3000, Notification.Position.MIDDLE);
        });
        add(poiGrid,nomePOIDaApprovare,submitButton);
    }
    
}
