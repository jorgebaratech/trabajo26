package com.example.gestiondepedidos.domain.item;

import com.example.gestiondepedidos.domain.DAO;
import com.example.gestiondepedidos.domain.HibernateUtil;
import com.example.gestiondepedidos.domain.pedido.Pedido;
import com.example.gestiondepedidos.domain.producto.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDAO implements DAO<Item> {

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public Item get(Long id) {
        return null;
    }

    @Override
    public Item save(Item data) {
        return null;
    }

    /**
     * Actualizar un Item
     * @param data item modificado
     */
    @Override
    public void update(Item data) {

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Transaction t = s.beginTransaction();
            Item nuevoItem = s.get(Item.class, data.getId());
            Producto nuevoProducto = s.get(Producto.class, data.getProducto().getId());
            nuevoItem.setCantidad(data.getCantidad());
            nuevoItem.setProducto(nuevoProducto);
            t.commit();
        }
    }

    /**
     * Borrar un item
     * @param data Item a borrar
     */
    @Override
    public void delete(Item data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Item i = session.get(Item.class, data.getId());
            session.remove(i);
        });
    }

    /**
     * Devuelve el Item si existe un producto en un pedido
     * @param p Pedido en el cual buscar
     * @param nombreProducto Nombre del producto a buscr
     * @return Item del pedido con ese producto o null si no existe
     */
    public Item itemPedidoNombre(Pedido p, String nombreProducto){
        Item result = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Item> q = s.createQuery("from Item i where i.producto.nombre =: n and i.pedido.id=: idPedido",Item.class);
            q.setParameter("n",nombreProducto);
            q.setParameter("idPedido",p.getId());
            result = q.getSingleResultOrNull();
        }
        return result;
    }
}