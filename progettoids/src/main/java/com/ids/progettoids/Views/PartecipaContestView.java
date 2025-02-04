package com.ids.progettoids.Views;

import java.util.List;


import com.ids.progettoids.models.Contest;

import com.ids.progettoids.models.Content;
import com.ids.progettoids.utils.ContestUtils;
import com.ids.progettoids.utils.ContentUtils;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;



@Route("PartecipaContest") 
public class PartecipaContestView extends VerticalLayout {
    private ComboBox<Contest> contestDropdown = new ComboBox<>("Seleziona un Contest");
    private ComboBox<Content> contentDropdown = new ComboBox<>("Seleziona il tuo Content");
    private Button submitButton = new Button("Partecipa al Contest");

    public PartecipaContestView() {
       
        List<Contest> contests = ContestUtils.getAllContests();
        contestDropdown.setItems(contests);
        contestDropdown.setItemLabelGenerator(Contest::getNome);
        contestDropdown.setPlaceholder("Cerca un contest...");
        contestDropdown.setClearButtonVisible(true);

        List<Content> userContents = ContentUtils.getUserContents(SessioneUtente.utente.getUsername());
        contentDropdown.setItems(userContents);
        contentDropdown.setItemLabelGenerator(Content::getMedia);
        contentDropdown.setPlaceholder("Seleziona il tuo contenuto...");
        contentDropdown.setClearButtonVisible(true);

        submitButton.addClickListener(e -> {
            Contest selectedContest = contestDropdown.getValue();
            Content selectedContent = contentDropdown.getValue();

            if (selectedContest == null || selectedContent == null) {
                Notification.show("Seleziona un contest e un contenuto", 3000, Notification.Position.MIDDLE);
                return;
            }

            ContestUtils.participateInContest(selectedContest, selectedContent, SessioneUtente.utente.getUsername());
            Notification.show("Partecipazione registrata con successo!", 3000, Notification.Position.MIDDLE);
        });

        add(contestDropdown, contentDropdown, submitButton);
    }
}
