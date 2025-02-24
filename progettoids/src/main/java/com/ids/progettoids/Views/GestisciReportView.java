package com.ids.progettoids.Views;

import java.util.List;
import java.util.stream.Collectors;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.models.Curatore;
import com.ids.progettoids.models.Report;
import com.ids.progettoids.models.Utente;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("GestisciReport")
public class GestisciReportView extends VerticalLayout{

    private Curatore curatore;
    public GestisciReportView() {
        curatore= (Curatore) new Utente.Builder()
        .setUsername(SessioneUtente.utente.getUsername())
        .setRuoli(SessioneUtente.utente.getRuolo())
        .setTipo(Ruolo.Curatore)
        .build();
     
        List<Report> reports=curatore.GetReports();
        Grid<Report> grid= new Grid<>(Report.class);
        grid.setItems(reports);
        TextField reportDaCancellare = new TextField("Vuoi eliminare un report?");
        List<String> chiaviReport=reports.stream().map(Report::getChiave).collect(Collectors.toList());
        reportDaCancellare.setPlaceholder("Inserisci la chiave del report che vuoi cancellare");
        Button subtimButton = new Button("Elimina Elemento", e -> {
            if (reportDaCancellare.getValue().isEmpty() || !chiaviReport.contains(reportDaCancellare.getValue())) {
                Notification.show("Devi compilare il campo report da cancellare o la chiave del report deve essere presente nella griglia", 3000, Notification.Position.MIDDLE);
                return;
            }
            Report report= curatore.getReportFromChiave(reportDaCancellare.getValue());
            curatore.EliminaReport(report);
        });
        Button cancellaReport = new Button("Elimina Report", e -> {
            if (reportDaCancellare.getValue().isEmpty() || !chiaviReport.contains(reportDaCancellare.getValue())) {
                Notification.show("Devi compilare il campo report da cancellare o la chiave del report deve essere presente nella griglia", 3000, Notification.Position.MIDDLE);
                return;
            }
            Report report= curatore.getReportFromChiave(reportDaCancellare.getValue());
            curatore.EliminaSegnalazione(report.getChiave());
        });
        add(grid,reportDaCancellare,subtimButton, cancellaReport);
    }
}
