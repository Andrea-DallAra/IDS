package com.ids.progettoids.Views;

import java.time.LocalDate;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.utils.ContentUtils;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.time.LocalDate;

@Route("CreaContenuto")
public class CreaContenutoView extends VerticalLayout implements HasUrlParameter<String> {

    private boolean daApprovare = false;
    private TextField poiField = new TextField("POI");

    public CreaContenutoView() {
        setAlignItems(Alignment.CENTER);

       
        if (SessioneUtente.utente.getRuolo().contains(Ruolo.Contributore) &&
            !SessioneUtente.utente.getRuolo().contains(Ruolo.Curatore)) {
            Contributore contributorePass = (Contributore) SessioneUtente.utente;
            if (!contributorePass.isAutenticato()) {
                daApprovare = true;
            }
        }
        TextField postField = new TextField("Post");
        TextArea descriptionField = new TextArea("Descrizione");

        Button submitButton = new Button("Crea Contenuto", event -> {
            String postText = postField.getValue();
            String descriptionText = descriptionField.getValue();
            String poiName = poiField.getValue();
            String todayDate = LocalDate.now().toString();
            
            ContentUtils.creaContent(postText, todayDate, descriptionText, todayDate, daApprovare);
            POIutils.collegaContent(new Content(postText, todayDate, descriptionText, todayDate), poiName, daApprovare);
            
            Notification.show("Post creato con successo", 3000, Notification.Position.MIDDLE);
        });

        add(postField, descriptionField, poiField, submitButton);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String poiName) {
        if (poiName != null && !poiName.isEmpty()) {
            poiField.setValue(poiName);
        }
    }
}
