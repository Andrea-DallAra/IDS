package com.ids.progettoids.Views;

import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Gestore;
import com.ids.progettoids.models.Utente;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("CambioRuoloView")
public class CambioRuoloView extends VerticalLayout {


    private Select<Ruolo> ruoloSelect;
    private Button aggiungiRuoloButton;

    public CambioRuoloView(){
        Gestore gestore = (Gestore) new Utente.Builder()
            .setUsername(SessioneUtente.utente.getUsername())
            .setRuoli(SessioneUtente.utente.getRuolo())
            .setTipo(Ruolo.Curatore)
            .build();
    
        TextField usernameField = new TextField("Username dell'utente a cui vuoi cambiare il ruolo");
        Button submitButton = new Button("Cerca Username", e -> {
            if (usernameField.isEmpty()) {
                Notification.show("Devi compilare il campo nome", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            Utente utente= new Utente.Builder()
                .setUsername(usernameField.getValue())
                .build();
            utente.CaricaRuoli(utente.getUsername());
            List<Ruolo> ruoliUtente= utente.getRuolo();
            List<Ruolo> ruoliMancanti = getNotRuoli(ruoliUtente);
            
            if (ruoloSelect != null) {
                remove(ruoloSelect);
            }
            if (aggiungiRuoloButton != null) {
                remove(aggiungiRuoloButton);
            }
            
            ruoloSelect = new Select<>();
            ruoloSelect.setLabel("Seleziona il ruolo da assegnare");
            ruoloSelect.setItems(ruoliMancanti);
            
            aggiungiRuoloButton = new Button("Aggiungi ruolo", e2 -> {
                if(ruoliMancanti.contains(ruoloSelect.getValue())){
                    gestore.EditaRuolo(usernameField.getValue(), ruoloSelect.getValue().name());
                    Notification.show("Ruolo aggiunto con successo");
                }else{
                    Notification.show("Ruolo non valido");
                }
            });
            
            add(ruoloSelect, aggiungiRuoloButton);
        });
         
        add(usernameField,submitButton);
    }

    private List<Ruolo> getNotRuoli(List<Ruolo> ruoliUtente) {
        Ruolo[] ruoli=Ruolo.values();
        List<Ruolo> notRuoli=new ArrayList<>();
        for (Ruolo ruolo : ruoli) {
            if (!ruoliUtente.contains(ruolo)) {
                notRuoli.add(ruolo);
            }
        }
        return notRuoli;
    }
    
}
