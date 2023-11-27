package com.example.gestiondepedidos.domain.item;

import com.example.gestiondepedidos.domain.pedido.Pedido;
import com.example.gestiondepedidos.domain.producto.Producto;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Clase que presenta un com.example.gestiondepedidos.domain.com.example.gestiondepedidos.domain.pedido.pedido.item en la ap`licacion de pedidos
 * Esta clase almacena información sobre el ítem, incluyendo su identificación, código de com.example.gestiondepedidos.domain.pedido.pedido al que pertenece, producto asociado y cantidad.
 */
@Data
@Entity
@Table (name = "item")
public class Item implements Serializable {
    /**
     * Identificador unico del com.example.gestiondepedidos.domain.com.example.gestiondepedidos.domain.pedido.pedido.item
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Codigo de com.example.gestiondepedidos.domain.pedido.pedido al que pertenece el com.example.gestiondepedidos.domain.com.example.gestiondepedidos.domain.pedido.pedido.item
     */
    @ManyToOne
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    /**
     * Producto asociado con el com.example.gestiondepedidos.domain.com.example.gestiondepedidos.domain.pedido.pedido.item
     */
    @ManyToOne
    @JoinColumn(name = "producto")
    private Producto producto;

    /**
     * Cantidad de productos en el com.example.gestiondepedidos.domain.com.example.gestiondepedidos.domain.pedido.pedido.item
     */
    private Integer cantidad;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", pedido_id=" + pedido.getId() +
                ", producto_id=" + producto.getId() +
                ", cantidad=" + cantidad +
                '}';
    }
}