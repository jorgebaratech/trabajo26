package com.example.gestiondepedidos.controllers;

import com.example.gestiondepedidos.Main;
import com.example.gestiondepedidos.domain.Sesion;
import com.example.gestiondepedidos.domain.item.Item;
import com.example.gestiondepedidos.domain.pedido.PedidoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la vista Detalles de un pedido
 */
public class VentanaPedidoController implements Initializable
{

    @FXML
    private TableColumn<Item,String>  cNombre;
    @FXML
    private TableColumn<Item,String>  cPrecio;
    @FXML
    private TableColumn<Item,String>  cCantidad;
    @FXML
    private MenuItem btnCerrarSesion;
    @FXML
    private MenuItem btnVolver;
    @FXML
    private TableView<Item> tablapedidos;
    @FXML
    private Label labeldetalles;

    /**
     * Inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PedidoDAO dao = new PedidoDAO();
        List<Item> items = dao.detallesdelpedido(Sesion.getClickPedido());

        labeldetalles.setText("Pedido número " + Sesion.getClickPedido().getId());

        cNombre. setCellValueFactory( (fila) -> {
            String nombre = fila.getValue().getProducto().getNombre();
            return new SimpleStringProperty(nombre);
        });
        cCantidad. setCellValueFactory( (fila) -> {
            int cantidad = fila.getValue().getCantidad();
            return new SimpleStringProperty(Integer.toString(cantidad));
        });
        cPrecio. setCellValueFactory( (fila) -> {
            double precio = fila.getValue().getProducto().getPrecio();
            return new SimpleStringProperty(Double.toString(precio));
        });
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        observableList.addAll(items);
        tablapedidos.setItems(observableList);
    }

    /**
     * Función botón atrás
     */
    @javafx.fxml.FXML
    public void cerrarsesion( ) {
        Sesion.logout();
        Main.loadFXML( "login.fxml" , "Iniciar Sesión" );
    }

    /**
     * Función botón logout
     */
    @FXML
    public void volver() {
        Main.loadFXML("ventana-usuario.fxml", "Pedidos de " + Sesion.getUsuarioActual().getNombre());
    }
}