package com.ids.progettoids.Views;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        

        Button loginButton = new Button("Vai al login", e -> {
            getUI().ifPresent(ui -> ui.navigate("/login"));});
            add(loginButton);
        Button visPOI = new Button("Visualizza POI", e -> {
            getUI().ifPresent(ui -> ui.navigate("/poiList"));});
        Button visItinerario = new Button("Visualizza Itinierari", e -> {
            getUI().ifPresent(ui -> ui.navigate("/itinerarioList"));});
        add(visPOI, visItinerario);

         
    
       if(SessioneUtente.utente != null){
          
            if(SessioneUtente.utente.getRuolo().contains(Ruolo.Gestore))
            {
               BottoniGestore();
            }   
              
            if(SessioneUtente.utente.getRuolo().contains(Ruolo.Contributore))
            {
                BottoniContributore();
             
            }
            if(SessioneUtente.utente.getRuolo().contains(Ruolo.Curatore))
            {
                BottoniCuratore();
            }
            if(SessioneUtente.utente.getRuolo().contains(Ruolo.Animatore))
            {
                BottoniAnimatore();
            }
            if(SessioneUtente.utente.getRuolo().contains(Ruolo.Turista) || SessioneUtente.utente.getRuolo().contains(Ruolo.TuristaAutenticato))
            {
                BottoniTurista();
            }
            
        }
            
                

    }
    boolean bottoneInseritoGestore = false;
    boolean bottoneInseritoContributore = false;
    boolean bottoneInseritoAnimatore = false;
    boolean bottoneInseritoTurista = false;
    boolean bottoneInseritoCuratore = false;
    public void BottoniGestore() 
    {
        if(!bottoneInseritoGestore) 
        {
        Button cambiareRuoli = new Button("Vai alla pagina delle richieste di cambi ruolo", e -> {
            getUI().ifPresent(ui -> ui.navigate("/RichiesteCambioRuoloView"));});
        Button richiesteCambioRuolo = new Button("Vai alla pagina per i cambi ruolo", e -> {
            getUI().ifPresent(ui -> ui.navigate("/CambioRuoloView"));});
            add(cambiareRuoli,richiesteCambioRuolo);
            bottoneInseritoGestore=true;
        }
    }
    
    public void BottoniContributore()
    {
        if(!bottoneInseritoContributore) 
        {
            Button inserisciPoi = new Button("Vai alla pagina Inserisci POI", e -> {
            getUI().ifPresent(ui -> ui.navigate("/InserisciPOI"));});

            Button itinerario = new Button("Vai alla pagina Aggiungi Itinerario", e -> {
                getUI().ifPresent(ui -> ui.navigate("/aggiungiItinerario"));});
          
                Button contenuto = new Button("Vai alla pagina Crea Contenuto", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/CreaContenuto"));});
                add(inserisciPoi,itinerario, contenuto);


                bottoneInseritoContributore = true;
        }
    }
    public void BottoniAnimatore(){
        if(!bottoneInseritoAnimatore) 
        {
            Button creaContest = new Button("Vai alla pagina Crea Contest", e -> {
            getUI().ifPresent(ui -> ui.navigate("/CreaContest"));});
            Button gestisciContest = new Button("Vai alla pagina Gestisci Contest", e -> {
                getUI().ifPresent(ui -> ui.navigate("/GestisciContest"));});
            add(creaContest,gestisciContest);
            bottoneInseritoAnimatore=true;
        }
    }
    public void BottoniCuratore()
    {
        if(!bottoneInseritoContributore) 
        {
            Button inserisciPoi = new Button("Vai alla pagina Inserisci POI", e -> {
                getUI().ifPresent(ui -> ui.navigate("/InserisciPOI"));});
                Button itinerario = new Button("Vai alla pagina  Aggiungi Itinerario", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/aggiungiItinerario"));});
                Button contenuto = new Button("Vai alla pagina Crea Contenuto", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/CreaContenuto"));});
                add(inserisciPoi,itinerario, contenuto);
            
                bottoneInseritoContributore = true;
            }
        if(!bottoneInseritoCuratore) 
        {
           
            Button approvaPoi = new Button("Vai alla pagina Approva POI", e -> {
                getUI().ifPresent(ui -> ui.navigate("/ApprovaPOI"));});
            Button approvaItinerario = new Button("Vai alla pagina  Approva Itinerario", e -> {
                getUI().ifPresent(ui -> ui.navigate("/ApprovaItinerario"));});
            Button approvaContenuto = new Button("Vai alla pagina Approva Contenuto", e -> {
                getUI().ifPresent(ui -> ui.navigate("/ApprovaContenuto"));});
            Button gestisciReport = new Button("Vai alla pagina Gestisci Report", e -> {
                getUI().ifPresent(ui -> ui.navigate("/GestisciReport"));});
            Button editaElemento = new Button("Vai alla pagina per editare un elemento", e -> {
                getUI().ifPresent(ui -> ui.navigate("/EditaElemento"));});
                
                add(approvaPoi,approvaItinerario,approvaContenuto, gestisciReport,editaElemento);
                bottoneInseritoCuratore = true;
        }
    }
    public void BottoniTurista()
    {
        if(!bottoneInseritoTurista) 
        {
            Button report = new Button("Vai alla pagina Report", e -> {
                getUI().ifPresent(ui -> ui.navigate("/Report"));});
                Button richiediRuolo = new Button("Vai alla pagina per la richiesta di nuovo ruolo", e -> {
                    getUI().ifPresent(ui-> ui.navigate("/NewRuoloView"));});
              /*  Button visPOI = new Button("Visualizza POI", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/poiList"));});
                Button visItinerario = new Button("Visualizza Itinierari", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/itinerarioList"));});
                add(report, visPOI, visItinerario); */
                Button partecipaContest = new Button("Vai alla pagina per partecipare a un contest", e -> {
                    getUI().ifPresent(ui-> ui.navigate("/PartecipaContest"));});
                add(report,richiediRuolo,partecipaContest);
                bottoneInseritoTurista=true;
        }
    
    }
}