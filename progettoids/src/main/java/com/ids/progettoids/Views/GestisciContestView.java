package com.ids.progettoids.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.models.Animatore;
import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Contest;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("GestisciContest")
public class GestisciContestView extends VerticalLayout{

    private Animatore animatore;
    private List<String> autori = new ArrayList<>();

   public GestisciContestView() {
        animatore=new Animatore(SessioneUtente.utente.getNome(), SessioneUtente.utente.getCognome(),SessioneUtente.utente.getEmail(),SessioneUtente.utente.getPassword(),SessioneUtente.utente.getUsername());
        TextField contestNome = new TextField("Nome del Contest");
        contestNome.setPlaceholder("Inserisci il nome del Contest");
        Grid<Content> grid= new Grid<>(Content.class);
        Button apriListaContent = new Button("Apri Contest", e -> {
            if (contestNome.isEmpty()) {
                Notification.show("Devi compilare il campo nome", 3000, Notification.Position.MIDDLE);
                return;
            }
            List<Content> contenuti=animatore.gestisciContest(new Contest(contestNome.getValue(), ""));
            autori=contenuti.stream().map(Content::getAutore).collect(Collectors.toList());
            grid.setItems(contenuti);
        });

        TextField vincitore = new TextField("Inserisci il nome del vincitore");
        vincitore.setPlaceholder("Inserisci il nome del vincitore");
        Button dichiaraVincitore = new Button("Dichiara vincitore", e -> {
            if (vincitore.isEmpty() || !autori.contains(vincitore.getValue())) {
                Notification.show("Devi compilare il campo nome del vincitore o il vincitore deve essere presente nella griglia", 3000, Notification.Position.MIDDLE);
                return;
            }
            animatore.dichiaraVincitore(vincitore.getValue(), contestNome.getValue());
        });
        add(contestNome,apriListaContent,grid,vincitore,dichiaraVincitore);
    }
}

