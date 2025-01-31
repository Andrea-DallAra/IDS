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
    public void BottoniGestore() 
    {
        Button navigateToPageButton = new Button("Vai alla pagina Admin", e -> {
            getUI().ifPresent(ui -> ui.navigate("/admin"));});
            add(navigateToPageButton);
    }
    boolean bottoneInserito = false;
    public void BottoniContributore()
    {
        if(!bottoneInserito) 
        {
            Button inserisciPoi = new Button("Vai alla pagina Inserisci POI", e -> {
            getUI().ifPresent(ui -> ui.navigate("/InserisciPOI"));});

            Button itinerario = new Button("Vai alla pagina Aggiungi Itinerario", e -> {
                getUI().ifPresent(ui -> ui.navigate("/aggiungiItinerario"));});
          
                Button contenuto = new Button("Vai alla pagina Crea Contenuto", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/CreaContenuto"));});
                add(inserisciPoi,itinerario, contenuto);


            bottoneInserito = true;
        }
    }
    public void BottoniAnimatore(){}
    public void BottoniCuratore()
    {
        if(!bottoneInserito) 
        {
            Button inserisciPoi = new Button("Vai alla pagina Inserisci POI", e -> {
            getUI().ifPresent(ui -> ui.navigate("/InserisciPOI"));});

            Button itinerario = new Button("Vai alla pagina  Aggiungi Itinerario", e -> {
                getUI().ifPresent(ui -> ui.navigate("/aggiungiItinerario"));});
                Button contenuto = new Button("Vai alla pagina Crea Contenuto", e -> {
                    getUI().ifPresent(ui -> ui.navigate("/CreaContenuto"));});
                add(inserisciPoi,itinerario, contenuto);
            bottoneInserito = true;
        }
    }
    public void BottoniTurista(){}
    
}