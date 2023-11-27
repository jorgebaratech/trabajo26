package com.example.gestiondepedidos.domain.pedido;

import com.example.gestiondepedidos.domain.item.Item;
import com.example.gestiondepedidos.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private Long total;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;


    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<Item> items;

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", usuario=" + usuario.getNombre() +
                ", total=" + total + '\'' +
                ", items=" + items +
                '}';
    }
}