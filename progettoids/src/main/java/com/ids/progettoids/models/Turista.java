package com.ids.progettoids.models;

import com.ids.progettoids.Ruolo;
public class Turista extends Utente {

    private boolean autenticato;
    public boolean isAutenticato() {
        return autenticato;
    }
    public void setAutenticato(boolean autenticato) {
        this.autenticato = autenticato;
    }
    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Turista);
        
    }

    public Turista(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       super(_nome, _cognome, _email, _password, _username);
       AggiungiRuolo();
    }
    

}
