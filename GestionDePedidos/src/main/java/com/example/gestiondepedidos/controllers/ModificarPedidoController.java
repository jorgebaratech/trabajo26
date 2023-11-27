package com.example.gestiondepedidos.controllers;

import com.example.gestiondepedidos.Main;
import com.example.gestiondepedidos.domain.Sesion;
import com.example.gestiondepedidos.domain.item.Item;
import com.example.gestiondepedidos.domain.item.ItemDAO;
import com.example.gestiondepedidos.domain.pedido.Pedido;
import com.example.gestiondepedidos.domain.pedido.PedidoDAO;
import com.example.gestiondepedidos.domain.producto.Producto;
import com.example.gestiondepedidos.domain.producto.ProductoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador de la vista Editar un pedido
 */
public class ModificarPedidoController implements Initializable {

    private static PedidoDAO pedidoDAO;
    private static ItemDAO itemDAO;
    private static ProductoDAO productoDAO;

    @FXML
    private ComboBox<String> comboboxNombre;
    @FXML
    private Spinner<Integer> spinnerCantidad;
    @FXML
    private Button btnEditarPedido;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableColumn<Item,String> cNombre;
    @FXML
    private TableColumn<Item,String> cCantidad;
    @FXML
    private TableColumn<Item,String> cPrecio;
    @FXML
    private MenuItem btnNuevoPedido;
    @FXML
    private MenuItem btnCerrarSesion;
    @FXML
    private MenuItem btnEliminar;
    @FXML
    private MenuItem btnVolver;
    @FXML
    private Label labelmodificar;
    @FXML
    private TableView<Item> tablapedidos;
    @FXML
    private VBox menuizquierda;

    public void initialize( URL url , ResourceBundle resourceBundle ) {
        pedidoDAO = new PedidoDAO( );
        itemDAO = new ItemDAO( );
        productoDAO = new ProductoDAO( );
        if (Sesion.isNuevoPedido( )) {
            Pedido nuevoPedido = new Pedido(  );
            nuevoPedido.setUsuario( Sesion.getUsuarioActual() );
            nuevoPedido.setFecha( LocalDate.now().toString() );
            pedidoDAO.save( nuevoPedido );
            Sesion.setClickPedido( nuevoPedido );
            //Cambiar titulo
            labelmodificar.setText( "Añadir pedido " );
        }
        else{
            //Rellenar la tabla
            rellenarTabla( );
            //Cambiar titulo
            if (Sesion.getClickPedido()!=null) labelmodificar.setText( "Editar pedido " + Sesion.getClickPedido( ).getId( )  );

        }

        //listener de la tabla
        nuevolistenertabla( );

        //Rellenar comboBox
        comboboxNombre.getItems( ).addAll( pedidoDAO.listaproductos( ) );

    }
    private void nuevolistenertabla( ) {
        tablapedidos.getSelectionModel( ).selectedItemProperty( ).addListener( ( observableValue , producto , t1 ) -> {
            menuizquierda.setDisable( false );
            if (t1 != null) Sesion.setClickItem((Item) t1);
            comboboxNombre.setValue( Sesion.getClickItem( ).getProducto( ).getNombre( ) );
            spinnerCantidad.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 1 , 1000 , Sesion.getClickItem( ).getCantidad( ) , 1 ) );
            btnEditarPedido.setText( "Editar" );
            comboboxNombre.setDisable( true );
        } );
    }
    private void rellenarTabla( ) {
        List<Item> items = pedidoDAO.detallesdelpedido( Sesion.getClickPedido( ) );
        cNombre.setCellValueFactory( ( fila ) -> {
            String nombre = fila.getValue( ).getProducto( ).getNombre( );
            return new SimpleStringProperty( nombre );
        } );
        cCantidad.setCellValueFactory( ( fila ) -> {
            int cantidad = fila.getValue( ).getCantidad( );
            return new SimpleStringProperty( Integer.toString( cantidad ) );
        } );
        cPrecio.setCellValueFactory( ( fila ) -> {
            double precio = fila.getValue( ).getProducto( ).getPrecio( );
            return new SimpleStringProperty( Double.toString( precio ) );
        } );
        ObservableList<Item> observableList = FXCollections.observableArrayList( );
        observableList.addAll( items );
        tablapedidos.setItems( observableList );
    }
    @FXML
    public void volver() {
        Main.loadFXML("ventana-usuario.fxml", "Pedidos de " + Sesion.getUsuarioActual().getNombre());
    }
    @javafx.fxml.FXML
    public void cerrarsesion( ) {
        Sesion.logout();
        Main.loadFXML( "login.fxml" , "Iniciar Sesión" );
    }
    @FXML
    public void editarpedido( ) {
        try {
            if (Sesion.isNuevoProducto( )) {
                if (pedidoDAO.comprobarproductoenpedido( comboboxNombre.getValue( ) , Sesion.getClickPedido( ) )) {
                    Item item = itemDAO.itemPedidoNombre( Sesion.getClickPedido( ) , comboboxNombre.getValue( ) );
                    modificarItem( spinnerCantidad.getValue( ) + item.getCantidad( ) );
                } else {
                    Producto producto = productoDAO.productonombre( comboboxNombre.getValue( ) );
                    pedidoDAO.insertaritemapedido( Sesion.getClickPedido( ) , spinnerCantidad.getValue( ) , producto );
                }
                Sesion.setNuevoProducto( false );
            } else {
                modificarItem( spinnerCantidad.getValue( ) );
            }
            pedidoDAO.actualizarfecha(Sesion.getClickPedido());
            this.rellenarTabla( );
            this.menuizquierda.setDisable( true );
            this.tablapedidos.setDisable( false );

        } catch ( NumberFormatException e ) {

            System.out.println( e.getMessage( ) );
        }
    }
    /**
     * Modifica cantidad de ítem
     * @param cantidad Nueva cantidad del item
     */
    private void modificarItem( Integer cantidad ) {
        Item itemModificado = itemDAO.itemPedidoNombre( Sesion.getClickPedido( ) ,
                comboboxNombre.getValue( ) );
        Producto productoModificado = productoDAO.productonombre( comboboxNombre.getValue( ) );
        itemModificado.setCantidad( cantidad );
        itemModificado.setProducto( productoModificado );
        itemDAO.update( itemModificado );
    }
    /**
     * Función Añadir
     */
    @FXML
    public void nuevopedido( ) {
        Sesion.setNuevoProducto( true );
        comboboxNombre.setDisable( false );
        menuizquierda.setDisable( false );
        tablapedidos.setDisable( true );
        btnEditarPedido.setText( "Añadir" );
        comboboxNombre.setValue(String.valueOf(comboboxNombre.getItems( ).getClass( )));
        spinnerCantidad.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 1 , 1000 , 1 , 1 ) );
    }
    /**
     * Función eliminar
     */
    @FXML
    public void eliminarpedido( ) {
        if (Sesion.getClickItem( ) != null) {
            itemDAO.delete( Sesion.getClickItem( ) );
            this.rellenarTabla( );
            this.menuizquierda.setDisable( true );
        }
    }
    /**
     * Función cancelar
     */
    @FXML
    public void cancelarpedido( ) {
        this.menuizquierda.setDisable( true );
        this.tablapedidos.setDisable( false );
    }

}