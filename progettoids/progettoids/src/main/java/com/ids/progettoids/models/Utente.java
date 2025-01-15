package com.ids.progettoids.models;

import java.util.ArrayList;
import java.util.List;

import com.ids.progettoids.Ruolo;
import com.ids.progettoids.Interfacce.UtenteInterfaccia;

public abstract class Utente implements UtenteInterfaccia {
    String username = "";
    String password = "";
    String email = "";
    String nome = "";
    String cognome = "";
    List<Ruolo> ruoli = new ArrayList<>();

    public Utente(String _nome, String _cognome , String _email, String _password, String _username) 
    {
       email = _email;
       password = _password;
       nome = _nome;
       cognome = _cognome;
       username = _username;
       
    }
}
