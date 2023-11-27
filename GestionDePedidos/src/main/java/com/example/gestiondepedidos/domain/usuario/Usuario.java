package com.example.gestiondepedidos.domain.usuario;

import com.example.gestiondepedidos.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String contraseña;
    private String email;

    @OneToMany (mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos;

}