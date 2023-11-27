package com.example.gestiondepedidos.domain.usuario;

import com.example.gestiondepedidos.domain.DAO;
import com.example.gestiondepedidos.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {


    @Override
    public List<Usuario> getAll() {
        List<Usuario> result = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = s.createQuery("from Usuario", Usuario.class);
            result = q.getResultList();
        }
        return result;
    }


    @Override
    public Usuario get(Long id) {
        return null;
    }

    @Override
    public Usuario save(Usuario data) {
        return null;
    }

    @Override
    public void update(Usuario data) {

    }

    @Override
    public void delete(Usuario data) {

    }
    public boolean usuariocorrecto(String user, String pass) {
        return cargarlogin(user,pass) != null;
    }
    public Usuario cargarlogin(String user, String pass) {
        Usuario result = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Usuario> q = session.createQuery("from Usuario where nombre=:u and contraseña=:p", Usuario.class);
            q.setParameter("u",user);
            q.setParameter("p",pass);

            try {
                result = q.getSingleResult();
            }catch (Exception e){
                System.out.println(e.getMessage());

            }
        }
        return result;
    }
}
