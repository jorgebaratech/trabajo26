package com.example.gestiondepedidos.domain;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utilidades de hibernate
 */
@Log
public class HibernateUtil {
    private static SessionFactory sf = null;

    static {
        try {
            Configuration cfg = new Configuration();
            cfg.configure();
            sf = cfg.buildSessionFactory();
            log.info("¡SessionFactory creada con exito!");
        } catch(Exception e){
            log.severe("Error al crear el SessionFactory");
        }
    }

    /**
     * Sesion Factory
     * @return Devuelve la instancia de una sesion factory de hibernate
     */
    public static SessionFactory getSessionFactory(){
        return sf;
    }
}

