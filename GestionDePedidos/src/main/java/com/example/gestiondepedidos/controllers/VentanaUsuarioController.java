package com.example.gestiondepedidos.controllers;

import com.example.gestiondepedidos.Main;
import com.example.gestiondepedidos.domain.Sesion;
import com.example.gestiondepedidos.domain.pedido.Pedido;

import com.example.gestiondepedidos.domain.pedido.PedidoDAO;
import com.example.gestiondepedidos.domain.usuario.Usuario;
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
 * Controlador de la ventana Pedidos de un usuario
 */
public class VentanaUsuarioController implements Initializable {

    PedidoDAO pedidoDAO;

    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cFecha;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cUsuario;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cTotal;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cId;
    @FXML
    private MenuItem btnCerrarSesion;
    @FXML
    private MenuItem btnNuevoPedido;
    @FXML
    private MenuItem btnDetalles;
    @FXML
    private MenuItem btnEliminar;
    @FXML
    private MenuItem btnEditarPedido;
    @FXML
    private Label labelpedidos;
    @FXML
    private TableView<Pedido> tablausuario;

    @Override
    public void initialize( URL url , ResourceBundle resourceBundle ) {

        pedidoDAO = new PedidoDAO( );

        Sesion.setNuevoProducto( false );
        rellenarTabla( );

        tablausuario.getSelectionModel( ).selectedItemProperty( ).addListener( ( observableValue , pedido , t1 ) -> {
            Sesion.setClickPedido( t1 );
        } );

    }

    /**
     * Llenar la tabla de valores de la base de datos
     */
    private void rellenarTabla( ) {
        Usuario usuario = Sesion.getUsuarioActual( );
        pedidoDAO = new PedidoDAO( );
        List<Pedido> pedidosDeUser = pedidoDAO.pedidosdelusuario( usuario );

        //Cambiar Titulo
        labelpedidos.setText( "Pedidos de " + usuario.getNombre( ) );
        //Rellenar la tabla
        cId.setCellValueFactory( ( fila ) -> {
            Long id = fila.getValue( ).getId( );
            return new SimpleStringProperty( id.toString( ) );
        } );
        cFecha.setCellValueFactory( ( fila ) -> {
            String fecha = fila.getValue( ).getFecha( );
            return new SimpleStringProperty( fecha );
        } );
        cUsuario.setCellValueFactory( ( fila ) -> {
            String nombre = Sesion.getUsuarioActual( ).getNombre( );
            return new SimpleStringProperty( nombre );
        } );
        cTotal.setCellValueFactory( ( fila ) -> {
            String total = String.valueOf(fila.getValue( ).getTotal( ));
            return new SimpleStringProperty( total );
        } );
        ObservableList<Pedido> observableList = FXCollections.observableArrayList( );
        observableList.addAll( pedidosDeUser );
        tablausuario.setItems( observableList );
    }

    /**
     * Función cerrar sesión
     */
    @javafx.fxml.FXML
    public void cerrarsesion( ) {
        Sesion.logout();
        Main.loadFXML( "login.fxml" , "Iniciar Sesión" );
    }

    /**
     * Función detalles del pedido
     */
    @javafx.fxml.FXML
    public void detallespedido( ) {
        if (Sesion.getClickPedido( ) != null) Main.loadFXML( "ventana-pedido.fxml" , "Detalles del pedido" );
    }

    /**
     * Función editar pedido
     */
    @javafx.fxml.FXML
    public void editarpedido( ) {
        if(Sesion.getClickPedido() != null){
            Sesion.setNuevoPedido( false );
            Main.loadFXML( "modificarpedido.fxml" , "Editar pedido" );
        }
    }

    /**
     * Función eliminar pedido
     */
    @FXML
    public void eliminarpedido( ) {
        if (Sesion.getClickPedido( ) != null) {
            pedidoDAO.delete( Sesion.getClickPedido( ) );
            this.rellenarTabla( );
        }
    }

    /**
     * Función añadir pedido
     */
    @FXML
    public void nuevopedido( ) {
        Sesion.setNuevoPedido( true );
        Main.loadFXML( "modificarpedido.fxml" , "Añadir pedido" );
    }

}