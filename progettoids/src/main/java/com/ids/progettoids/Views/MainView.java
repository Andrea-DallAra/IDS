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
            if(SessioneUtente.utente.getRuolo().contains(Ruolo.Turista))
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
        if(!bottoneInseritoCuratore) 
        {
            Button inserisciPoi = new Button("Vai alla pagina Inserisci POI", e -> {
            getUI().ifPresent(ui -> ui.navigate("/InserisciPOI"));});

            Button itinerario = new Button("Vai alla pagina  Aggiungi Itinerario", e -> {
                getUI().ifPresent(ui -> ui.navigate("/aggiungiItinerario"));});
                Button contenuto = new Button("Vai alla pagina Crea Contenuto", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/CreaContenuto"));});
                add(inserisciPoi,itinerario, contenuto);
                Button gestisciReport = new Button("Vai alla pagina Gestisci Report", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/GestisciReport"));});
                add(inserisciPoi,itinerario, contenuto,gestisciReport);
                bottoneInseritoCuratore = true;
        }
    }
    public void BottoniTurista()
    {
        if(!bottoneInseritoTurista) 
        {
            Button report = new Button("Vai alla pagina Report", e -> {
            getUI().ifPresent(ui -> ui.navigate("/Report"));});

            Button visPOI = new Button("Visuallizza POI", e -> {
                getUI().ifPresent(ui -> ui.navigate("/poiList"));});

                Button visItinerario = new Button("Visuallizza Itinierari", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/itinerarioList"));});
            add(report, visPOI, visItinerario);
            
        
    }
    
    }
}