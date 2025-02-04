package com.ids.progettoids.Views;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.models.Content;
import com.ids.progettoids.utils.ContentUtils;
import com.ids.progettoids.utils.ContentWithId;
import com.ids.progettoids.utils.EditaUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("EditaContent") 
public class EditaContentView extends VerticalLayout {
    private Grid<ContentWithId> contentGrid = new Grid<>(ContentWithId.class);
    private TextField idContentField = new TextField("ID del Content");
    private TextField mediaFieldEditato = new TextField("Media del Content");
    private TextField autoreFieldEditato = new TextField("Autore del Content");
    private TextField contentDescrizioneEditato = new TextField("Descrizione del Content");
    private DatePicker datePickerEditato = new DatePicker("Data");
    private Content selectedContent;

    public EditaContentView() {
        List<HashMap<Integer, Content>> listaContent = ContentUtils.getAllContent();
        List<ContentWithId> listaContentWithID = listaContent.stream()
                .map(map -> {
                    Integer id = map.keySet().iterator().next();
                    Content content = map.values().iterator().next();
                    return new ContentWithId(id, content);
                })
                .collect(Collectors.toList());
        contentGrid.setItems(listaContentWithID);
        contentGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        idContentField.setReadOnly(true);
        idContentField.setPlaceholder("Seleziona un content dalla lista");
        mediaFieldEditato.setPlaceholder("Inserisci il media del content");
        autoreFieldEditato.setPlaceholder("Inserisci l'autore del content");
        contentDescrizioneEditato.setPlaceholder("Inserisci la descrizione del content");
        datePickerEditato.setPlaceholder("Seleziona la data");

        contentGrid.asSingleSelect().addValueChangeListener(event -> {
            ContentWithId selected = event.getValue();
            if (selected != null) {
                selectedContent = selected.getContent();
                idContentField.setValue(String.valueOf(selected.getId()));
                mediaFieldEditato.setValue(selectedContent.getMedia());
                autoreFieldEditato.setValue(selectedContent.getAutore());
                contentDescrizioneEditato.setValue(selectedContent.getDescrizione());
                datePickerEditato.setValue(java.time.LocalDate.parse(selectedContent.getData()));
            }
        });

        Button submitButton = new Button("Edita Content", e -> {
            if (selectedContent == null) {
                Notification.show("Seleziona un content dalla lista", 3000, Notification.Position.MIDDLE);
                return;
            }

            if (mediaFieldEditato.isEmpty() || autoreFieldEditato.isEmpty() || contentDescrizioneEditato.isEmpty() || datePickerEditato.isEmpty()) {
                Notification.show("Tutti i campi devono essere compilati", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                Content newContent = new Content(
                    mediaFieldEditato.getValue(),
                    datePickerEditato.getValue().toString(),
                    autoreFieldEditato.getValue(),
                    contentDescrizioneEditato.getValue()
                );

                EditaUtils.EditaContent(selectedContent, newContent);
                Notification.show("Content editato con successo", 3000, Notification.Position.MIDDLE);
            } catch (NumberFormatException err) {
                Notification.show("Errore di formato", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore generico", 3000, Notification.Position.MIDDLE);
            }
        });

        add(contentGrid, idContentField, mediaFieldEditato, autoreFieldEditato, contentDescrizioneEditato, datePickerEditato, submitButton);
    }
}
