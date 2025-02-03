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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("CambioRuoloView")
public class CambioRuoloView extends VerticalLayout {

    private Gestore gestore;

    public CambioRuoloView(){
        gestore=new Gestore(SessioneUtente.utente.getNome(), SessioneUtente.utente.getCognome(),SessioneUtente.utente.getEmail(),SessioneUtente.utente.getPassword(),SessioneUtente.utente.getUsername());
        TextField usernameField = new TextField("Username dell'utente a cui vuoi cambiare il ruolo");
        Utente utente= new Utente(usernameField.getValue(), "", "", "", "");
        utente.CaricaRuoli(utente.getUsername());
        List<Ruolo> ruoliUtente= utente.getRuolo();
        Button submitButton = new Button("Cerca Username", e -> {
            if (usernameField.isEmpty()) {
                Notification.show("Devi compilare il campo nome", 3000, Notification.Position.MIDDLE);
                return;
            }
            List<Ruolo> ruoliMancanti = getNotRuoli(ruoliUtente);
            TextField ruoloField = new TextField("Ruolo da assegnare tra "+ruoliMancanti.toString());
            Button aggiungiRuoloButton = new Button("Aggiungi ruolo", e2 -> {
                if(ruoliMancanti.contains(Ruolo.valueOf(ruoloField.getValue()))){
                    gestore.EditaRuolo(usernameField.getValue(), ruoloField.getValue());
                    Notification.show("Ruolo aggiunto con successo");
                }else{
                    Notification.show("Ruolo non valido");
                }
            });
            add(ruoloField, aggiungiRuoloButton);
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
