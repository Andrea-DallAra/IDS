package com.ids.progettoids.Views;

import com.ids.progettoids.models.Utente;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("signin")
public class RegistrazioneView extends VerticalLayout {

    public RegistrazioneView() {
        TextField usernameField = new TextField("Username");
        TextField emailField = new TextField("Email");
        TextField nome = new TextField("Nome");
        TextField cognome = new TextField("Cognome");
        PasswordField passwordField = new PasswordField("Password");

        Button signinButton = new Button("Registrati", event -> {
            SignIn(
               nome.getValue(), cognome.getValue(), emailField.getValue(), usernameField.getValue(), passwordField.getValue());
        });

        add(usernameField, passwordField,emailField, nome, cognome, signinButton);
    }

    private void SignIn(String nome, String cognome, String email,  String username ,String password) {
         Utente pass = new Utente.Builder().build();
       if( pass.Registrazione(nome, cognome, email, password, username))
       {
          SessioneUtente.utente = new Utente.Builder()
          .setUsername(pass.getUsername())
          .setRuoli(pass.getRuolo())
          .build();
          getUI().ifPresent(ui -> ui.navigate(""));
       }
    }
}
