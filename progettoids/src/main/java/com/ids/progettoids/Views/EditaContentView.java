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
public class EditaContentView extends VerticalLayout{
    public EditaContentView() {
    Grid<ContentWithId> contentGrid = new Grid<>(ContentWithId.class);
        List<HashMap<Integer, Content>> listaContent = ContentUtils.getAllContent();
        List<ContentWithId> listaContentWithID = listaContent.stream()
                .map(map -> {
                    Integer id = map.keySet().iterator().next();
                    Content content = map.values().iterator().next();
                    return new ContentWithId(id, content);
                })
                .collect(Collectors.toList());
        contentGrid.setItems(listaContentWithID);
        

        TextField idContentField = new TextField("Id del content da modificare");
        idContentField.setPlaceholder("Inserisci l'id del content da da modificare");
        
        TextField mediaFieldEditato = new TextField("Media del content");
        mediaFieldEditato.setPlaceholder("Inserisci il media del content");
        TextField autoreFieldEditato = new TextField("Autore del content");
        autoreFieldEditato.setPlaceholder("Inserisci autore del content");
        TextField contentDescrizioneEditato = new TextField("Descrizione del content");
        contentDescrizioneEditato.setPlaceholder("Inserisci la descrizione del content");
        DatePicker datePickerEditato = new DatePicker("Data");
        datePickerEditato.setPlaceholder("Seleziona la data");
        Button submitButton = new Button("EditaContent", e -> {
            if (idContentField.isEmpty() || mediaFieldEditato.isEmpty() || autoreFieldEditato.isEmpty() || contentDescrizioneEditato.isEmpty() || datePickerEditato.isEmpty()) {
                Notification.show("Tutti i campi devono essere compilati", 3000, Notification.Position.MIDDLE);
                return;
            }
            try {
                Content oldcontent= ContentUtils.getContent(Integer.parseInt(idContentField.getValue()));
                String pass = datePickerEditato.getValue().toString();
                Content newContent = new Content(mediaFieldEditato.getValue(), pass, autoreFieldEditato.getValue(), contentDescrizioneEditato.getValue());
                EditaUtils.EditaContent(oldcontent, newContent);
                Notification.show("Content editato con successo", 3000, Notification.Position.MIDDLE);
            } catch (NumberFormatException err) {
                Notification.show("Errore di formato", 3000, Notification.Position.MIDDLE);
            } catch (Exception error) {
                Notification.show("Errore generico", 3000, Notification.Position.MIDDLE);
            }
        });
        add(contentGrid,idContentField,mediaFieldEditato,autoreFieldEditato,contentDescrizioneEditato,datePickerEditato,submitButton);
    }
}
