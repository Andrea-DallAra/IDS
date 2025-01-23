package com.ids.progettoids.models;

import java.util.ArrayList;
import java.util.List;


public class Contest{
    private String nome;
    private String descrizione;
    private List<Content> contents;

    public Contest(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        contents = new ArrayList<>();
    }

    String getNome() {
        return nome;
    }

    String getDescrizione() {
        return descrizione;
    }

    List<Content> getContents() {
        return contents;
    }    
    
}