package com.example.gestiondepedidos.domain;

import com.example.gestiondepedidos.domain.item.Item;
import com.example.gestiondepedidos.domain.pedido.Pedido;
import com.example.gestiondepedidos.domain.usuario.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa las variables de sesion
 */
@Data
public class Sesion {

    /**
     * Usuario actual
     */
    @Setter
    @Getter
    private static Usuario usuarioActual;

    /**
     * Pedido pulsado
     */
    @Setter
    @Getter
    private static Pedido clickPedido;

    /**
     * Item pulsado
     */
    @Setter
    @Getter
    private static Item clickItem;

    /**
     * Comprobamos si estamos añadiendo un producto o modificándolo
     */
    @Setter
    @Getter
    private static boolean NuevoProducto;

    /**
     * Comprobamos si estamos añadiendo un pedido o modificándolo
     */
    @Setter
    @Getter
    private static boolean NuevoPedido;

    /**
     * Cerrar sesión
     */
    public static void logout(){
        Sesion.setClickPedido( null );
        Sesion.setUsuarioActual( null );
        Sesion.setClickItem( null );
    }



}