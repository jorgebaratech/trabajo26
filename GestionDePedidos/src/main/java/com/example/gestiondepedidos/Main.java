package com.example.gestiondepedidos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Aplicacion principal
 */
public class Main extends Application {
    @Getter
    @Setter
    private static Stage myStage;
    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login!");
        stage.setMinHeight( 500 );
        stage.setMinWidth( 830 );
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cargar escena y cambiar título
     * @param fxml nombre de la escena
     * @param titulo Nombre del título
     */
    public static void loadFXML(String fxml, String titulo){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            myStage.setTitle(titulo);
            myStage.setScene(scene);
            myStage.show();
        } catch (IOException e) {
            System.out.println("Error, no carga "+fxml);
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo main
     * @param args no se usan argumentos
     */
    public static void main(String[] args) {
        launch();
    }
}