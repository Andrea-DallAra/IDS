package com.ids.progettoids.Views;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Curatore;
import com.ids.progettoids.utils.ContentUtils;
import com.ids.progettoids.utils.ContentWithId;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("ApprovaContenuto")
public class ApprovaContenutoView extends VerticalLayout {
    private Grid<ContentWithId> contentGrid;

    public ApprovaContenutoView() {
        contentGrid = new Grid<>(ContentWithId.class);

        List<HashMap<Integer, Content>> listaContentDaApprovare = ContentUtils.getAllContentdaApprovare();
        List<ContentWithId> listaContentWithID = listaContentDaApprovare.stream()
                .map(map -> {
                    Integer id = map.keySet().iterator().next();
                    Content content = map.values().iterator().next();
                    return new ContentWithId(id, content);
                })
                .collect(Collectors.toList());
        contentGrid.setItems(listaContentWithID);

        TextField idContentDaApprovare = new TextField("id del content da approvare");
        idContentDaApprovare.setPlaceholder("Inserisci l'id del content da approvare");

        Button submitButton = new Button("Approva Content", e -> {
            if (idContentDaApprovare.isEmpty()) {
                Notification.show("Devi compilare il campo id", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                int id = Integer.parseInt(idContentDaApprovare.getValue());
                Content contentApprovato = ContentUtils.getContentdaApprovare(id);

                Curatore curatore = new Curatore(SessioneUtente.utente.getNome(), SessioneUtente.utente.getCognome(), SessioneUtente.utente.getEmail(), SessioneUtente.utente.getPassword(), SessioneUtente.utente.getUsername());
                curatore.ApprovaContent(contentApprovato);

                Notification.show("Content approvato con successo", 3000, Notification.Position.MIDDLE);
            } catch (NumberFormatException err) {
                Notification.show("Errore di formato", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore generico", 3000, Notification.Position.MIDDLE);
            }
        });

        add(contentGrid, idContentDaApprovare, submitButton);
    }
}


