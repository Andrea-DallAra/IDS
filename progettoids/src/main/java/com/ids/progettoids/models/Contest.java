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

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<Content> getContents() {
        return contents;
    }    
    
}