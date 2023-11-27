module com.example.gestiondepedidos {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.gestiondepedidos.domain.usuario;
    opens com.example.gestiondepedidos.domain.pedido;
    opens com.example.gestiondepedidos.domain.item;
    opens com.example.gestiondepedidos.domain.producto;
    opens com.example.gestiondepedidos to javafx.fxml;
    exports com.example.gestiondepedidos;
    exports com.example.gestiondepedidos.controllers;
    opens com.example.gestiondepedidos.controllers to javafx.fxml;
}