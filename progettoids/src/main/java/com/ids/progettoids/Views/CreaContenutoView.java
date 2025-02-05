package com.ids.progettoids.Views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Contributore;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.utils.ContentUtils;
import com.ids.progettoids.utils.POIutils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@Route("CreaContenuto")
public class CreaContenutoView extends VerticalLayout implements HasUrlParameter<String> {

    private boolean daApprovare = false;
    private ComboBox<String> poiComboBox = new ComboBox<>("Seleziona un POI");

    public CreaContenutoView() {
        setAlignItems(Alignment.CENTER);

       
        if (SessioneUtente.utente.getRuolo().contains(Ruolo.Contributore) &&
            !SessioneUtente.utente.getRuolo().contains(Ruolo.Curatore)) {
            Contributore contributorePass = new Contributore();
            contributorePass.SetUsername(SessioneUtente.utente.getUsername());
            contributorePass.CaricaRuoli(SessioneUtente.utente.getUsername());
            if (!contributorePass.isAutenticato()) {
                daApprovare = true;
            }
        }

       
        List<String> poiList = new ArrayList();
        for ( POI poi: POIutils.getPOI(null)) {
            poiList.add( poi.getNome());
        }
        poiComboBox.setItems(poiList);
        poiComboBox.setAllowCustomValue(false);  
        poiComboBox.setPlaceholder("Cerca un POI...");
        poiComboBox.setClearButtonVisible(true); 

       
        TextField postField = new TextField("Post");
        TextArea descriptionField = new TextArea("Descrizione");

       
        Button submitButton = new Button("Crea Contenuto", event -> {
            String postText = postField.getValue();
            String descriptionText = descriptionField.getValue();
            String poiName = poiComboBox.getValue(); 
            String todayDate = LocalDate.now().toString();

            if (poiName == null || poiName.isEmpty()) {
                Notification.show("Seleziona un POI!", 3000, Notification.Position.MIDDLE);
                return;
            }

           
            ContentUtils.creaContent(postText, todayDate, SessioneUtente.utente.getUsername(), descriptionText, daApprovare);
            POIutils.collegaContent(new Content(postText, todayDate, SessioneUtente.utente.getUsername(), descriptionText), poiName, daApprovare);

            Notification.show("Post creato con successo!", 3000, Notification.Position.MIDDLE);
        });

    
        add(postField, descriptionField, poiComboBox, submitButton);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String poiName) {
        if (poiName != null && !poiName.isEmpty()) {
            poiComboBox.setValue(poiName); 
        }
    }
}
