package com.ids.progettoids.Views;

import com.ids.progettoids.models.Animatore;
import com.ids.progettoids.models.Contest;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("CreaContest")
public class CreaContestView extends VerticalLayout{
    public CreaContestView() {
        TextField contestNome = new TextField("Nome del Contest");
        contestNome.setPlaceholder("Inserisci il nome del contest");

        TextField descrizione = new TextField("Descrizione del Contest");
        descrizione.setPlaceholder("Inserisci la descrizione");

        Button submitButton = new Button("Crea Contest", e -> {
            if (contestNome.isEmpty()) {
                Notification.show("Devi compilare il campo nome", 3000, Notification.Position.MIDDLE);
                return;
            }
            Animatore animatore= new Animatore();
            animatore.SetUsername(SessioneUtente.utente.getUsername());
            animatore.creaContest(new Contest(contestNome.getValue(), descrizione.getValue()));
            Notification.show("Contest creato con successo", 3000, Notification.Position.MIDDLE);
        });

        add(contestNome, descrizione, submitButton);
    }
}
