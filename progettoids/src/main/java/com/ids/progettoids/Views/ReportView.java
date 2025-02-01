package com.ids.progettoids.Views;

import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("Report")
public class ReportView extends VerticalLayout {

    public ReportView() {
      
        TextField objectNameField = new TextField("Nome dell'elemento da segnalare");

       
        ComboBox<String> categoryDropdown = new ComboBox<>("Categoria");
        categoryDropdown.setItems("POI", "Itinerario", "Content");
        categoryDropdown.setPlaceholder("Seleziona una categoria");

      
        TextArea descriptionArea = new TextArea("Descrizione");
        descriptionArea.setPlaceholder("Inserisci la descrizione del problema...");

        Button reportButton = new Button("Invia Report", event -> {
            sendReport(objectNameField.getValue(), categoryDropdown.getValue(), descriptionArea.getValue());
        });

        
        add(objectNameField, categoryDropdown, descriptionArea, reportButton);
    }

    private void sendReport(String daReportare, String tipo, String descrizione) {
        if (daReportare.isEmpty() || tipo == null || descrizione.isEmpty()) {
            return; 
        }

      SessioneUtente.utente.Report(daReportare, tipo, descrizione);
          Notification.show("Elemento segnalato", 3000, Notification.Position.MIDDLE);
    }
}
