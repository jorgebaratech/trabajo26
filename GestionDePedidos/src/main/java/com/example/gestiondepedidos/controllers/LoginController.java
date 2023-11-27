package com.example.gestiondepedidos.controllers;

import com.example.gestiondepedidos.Main;
import com.example.gestiondepedidos.domain.usuario.UsuarioDAO;
import com.example.gestiondepedidos.domain.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la ventana de login
 */
@Data
public class LoginController implements Initializable {

    @FXML
    private Label info;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button txtEntrar;
    @FXML
    private TextField txtUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUsuario.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEntrar.fire();
            }
        });
        txtPassword.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEntrar.fire();
            }
        });
    }

    /**
     * Botón Entrar
     */
    @FXML
    private void entrar() {

        UsuarioDAO dao = new UsuarioDAO();
        try{
            if (dao.usuariocorrecto(txtUsuario.getText(), txtPassword.getText())) {
                Sesion.setUsuarioActual(dao.cargarlogin(txtUsuario.getText(), txtPassword.getText() ));

                Main.loadFXML("ventana-usuario.fxml", "Pedidos de " + Sesion.getUsuarioActual().getNombre());
            } else {
                txtUsuario.setText("");
                txtPassword.setText("");
                info.setText("Incorrecto");
            }

        } catch (Exception e){
            txtUsuario.setText("");
            txtPassword.setText("");
            info.setText("Error de conexión");
            System.out.println(e.getMessage());
        }
    }

}
