package com.ids.progettoids.Views;

import com.ids.progettoids.models.Utente;
import com.ids.progettoids.utils.SessioneUtente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView() {
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        Button loginButton = new Button("Login", event -> {
            login(usernameField.getValue(), passwordField.getValue());
        });

        add(usernameField, passwordField, loginButton);

        Button signIn = new Button("Registrati", e -> {
            getUI().ifPresent(ui -> ui.navigate("/signin"));});
            add(signIn);
    }

    private void login(String username, String password) {
         Utente pass = new Utente();
       if( pass.Login(username, password))
       {
          SessioneUtente.utente = pass;
          getUI().ifPresent(ui -> ui.navigate(""));
       }
    }
}
