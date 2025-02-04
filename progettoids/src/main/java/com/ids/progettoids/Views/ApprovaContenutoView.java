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
    private TextField selectedContentField = new TextField("Contenuto Selezionato");

    public ApprovaContenutoView() {
        contentGrid = new Grid<>(ContentWithId.class);
        contentGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        List<HashMap<Integer, Content>> listaContentDaApprovare = ContentUtils.getAllContentdaApprovare();
        List<ContentWithId> listaContentWithID = listaContentDaApprovare.stream()
                .map(map -> {
                    Integer id = map.keySet().iterator().next();
                    Content content = map.values().iterator().next();
                    return new ContentWithId(id, content);
                })
                .collect(Collectors.toList());
        contentGrid.setItems(listaContentWithID);

       
        selectedContentField.setReadOnly(true);
        selectedContentField.setPlaceholder("Seleziona un contenuto dalla lista");

       
        contentGrid.asSingleSelect().addValueChangeListener(event -> {
            ContentWithId selectedContent = event.getValue();
            if (selectedContent != null) {
                selectedContentField.setValue(String.valueOf(selectedContent.getId())); // Display ID
            } else {
                selectedContentField.clear();
            }
        });

       
        Button submitButton = new Button("Approva Content", e -> {
            if (selectedContentField.isEmpty()) {
                Notification.show("Seleziona un contenuto dalla lista", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                int id = Integer.parseInt(selectedContentField.getValue());
                Content contentApprovato = ContentUtils.getContentdaApprovare(id);

                if (contentApprovato == null) {
                    Notification.show("Errore: Contenuto non trovato", 3000, Notification.Position.MIDDLE);
                    return;
                }

                Curatore curatore = new Curatore(SessioneUtente.utente.getNome(), 
                                                 SessioneUtente.utente.getCognome(), 
                                                 SessioneUtente.utente.getEmail(), 
                                                 SessioneUtente.utente.getPassword(), 
                                                 SessioneUtente.utente.getUsername());
                
                curatore.ApprovaContent(contentApprovato, id);

                Notification.show("Content approvato con successo", 3000, Notification.Position.MIDDLE);
            } catch (NumberFormatException err) {
                Notification.show("Errore di formato", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore generico", 3000, Notification.Position.MIDDLE);
            }
        });

        add(contentGrid, selectedContentField, submitButton);
    }
}
