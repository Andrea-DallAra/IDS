package com.ids.progettoids.Views;
import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Utente;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

@Route("NewRuoloView")
public class RichiestaNuovoRuoloView extends VerticalLayout{
    private Select<Ruolo> ruoloSelect;
    private Button aggiungiRuoloButton;

    public RichiestaNuovoRuoloView(){
        Utente utente= new Utente(SessioneUtente.utente.getUsername(), SessioneUtente.utente.getNome(),SessioneUtente.utente.getCognome(),SessioneUtente.utente.getEmail(),SessioneUtente.utente.getPassword());
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
        ruoloSelect.setLabel("Seleziona il ruolo da richiedere");
        ruoloSelect.setItems(ruoliMancanti);
        
        aggiungiRuoloButton = new Button("Richiedi ruolo", e2 -> {
            if(ruoliMancanti.contains(ruoloSelect.getValue())){
                utente.CambiaRuolo(utente.getUsername(), ruoloSelect.getValue().toString());
                Notification.show("Ruolo richiesto con successo");
            }else{
                Notification.show("Ruolo non valido");
            }
        });
        
        add(ruoloSelect, aggiungiRuoloButton);
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
