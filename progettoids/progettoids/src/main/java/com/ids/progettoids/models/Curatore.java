package com.ids.progettoids.models;

import com.ids.progettoids.Ruolo;
public class Curatore extends Utente {

    @Override
    public void AggiungiRuolo() 
    {
       
        ruoli.add(Ruolo.Curatore);
        
    }

    public Curatore(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       super(_nome, _cognome, _email, _password, _username);
       AggiungiRuolo();
    }
    

}
