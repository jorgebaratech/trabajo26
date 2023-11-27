package com.example.gestiondepedidos.domain.pedido;

import com.example.gestiondepedidos.domain.DAO;
import com.example.gestiondepedidos.domain.HibernateUtil;
import com.example.gestiondepedidos.domain.item.Item;
import com.example.gestiondepedidos.domain.producto.Producto;
import com.example.gestiondepedidos.domain.usuario.Usuario;
import lombok.extern.java.Log;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Session;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    /**
     * DAO para consultas relacionadas con los pedidos
     */
    @Log
    public class PedidoDAO implements DAO<Pedido> {
        @Override
        public ArrayList<Pedido> getAll( ) {
            return null;
        }

        @Override
        public Pedido get( Long id ) {
            return null;
        }

        /**
         * Guardar un nuevo pedido en la bd
         * @param data Pedido nuevo
         * @return Devuelve el nuevo pedido
         */
        @Override
        public Pedido save( Pedido data ) {

            Pedido salida = null;
            try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
                Transaction t = s.beginTransaction( );
                s.persist( data );
                t.commit( );
                salida = data;
            } catch ( Exception e ) {
                log.severe( "Error, no se ha guardado. " + data.toString( ) );
            }
            return salida;
        }

        @Override
        public void update( Pedido data ) {

        }

        /**
         * Borrar un pedido
         * @param data Pedido a borrar
         */
        @Override
        public void delete( Pedido data ) {

            HibernateUtil.getSessionFactory().inTransaction(s -> {
                Query<Item> q = s.createQuery( "from Item where pedido =: ped" , Item.class );
                q.setParameter( "ped" , data );
                List<Item> items = q.getResultList( );
                Pedido i = s.get( Pedido.class , data.getId( ) );
                items.forEach( it -> s.remove( it ) );

                s.remove( i );
            });
        }

        /**
         * Pedidos que corresponde al usuario
         * @param usuario Usuario
         * @return Lista de pedidos de usuario
         */
        public List<Pedido> pedidosdelusuario( Usuario usuario ) {

            List<Pedido> salida = new ArrayList<>( );
            try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
                Query<Usuario> q = s.createQuery( "from Usuario where id =: id" , Usuario.class );
                q.setParameter( "id" , usuario.getId( ) );
                salida = q.getSingleResult( ).getPedidos( );
            }
            for (Pedido pedido : salida) {
                Double total = 0.0;
                for (Item item : pedido.getItems( )) {
                    total = total + item.getCantidad( ) * item.getProducto( ).getPrecio( );
                }
                DecimalFormat formato = new DecimalFormat( "#0.00" );
                //pedido.setTotal(Long.valueOf(formato.format( total )));
                pedido.setTotal(0L);
            }
            return salida;
        }

        /**
         * Items que corresponden a un pedido
         * @param pedidoPulsado Pedido
         * @return Items que corresponden a un pedido
         */
        public List<Item> detallesdelpedido( Pedido pedidoPulsado ) {
            List<Item> result;
            try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
                Query<Pedido> q = s.createQuery( "from Pedido where id =: id" , Pedido.class );
                q.setParameter( "id" , pedidoPulsado.getId( ) );
                result = new ArrayList<>( q.getSingleResult( ).getItems( ) );
            }
            return result;
        }

        /**
         * Lista de productos
         * @return Lista de productos de la base de datos
         */
        public List<String> listaproductos( ) {
            List<String> resultado = new ArrayList<String>( );
            try ( Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
                Query<String> q = s.createQuery( "select distinct p.nombre from Producto p" , String.class );
                resultado = q.getResultList( );
            }

            return resultado;
        }

        /**
         * Añadir nuevo item a un pedido
         * @param ped Pedido a añadir el item
         * @param cant Cantidad de producto que se añade
         * @param prod Producto que corresponde al item
         */
        public void insertaritemapedido( Pedido ped , Integer cant , Producto prod ) {
            try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
                Transaction t = s.beginTransaction( );
                //Crear u nuevo item
                Item item = new Item( );
                item.setCantidad( cant );
                item.setPedido( ped );
                item.setProducto( prod );
                s.persist( item );
                t.commit( );
            } catch ( Exception e ) {
                log.severe( "Error" );
            }
        }

        /**
         * Buscar un producto en un pedido
         * @param nombreProducto Nombre del producto
         * @param pedido Pedido
         * @return Producto si está o no
         */
        public Producto buscarproducto( String nombreProducto , Pedido pedido ) {
            Producto producto = null;
            try ( org.hibernate.Session s = HibernateUtil.getSessionFactory( ).openSession( ) ) {
                Query<Producto> q = s.createQuery(
                        "select i.producto from Item i where i.producto.nombre =: nombre and i.pedido.id =: idPedido" , Producto.class );
                q.setParameter( "nombre" , nombreProducto );
                q.setParameter( "idPedido" , pedido.getId( ) );
                producto = q.getSingleResultOrNull( );
            }
            return producto;
        }

        /**
         * Comprueba si el producto esta en el pedido
         * @param nombreProducto Nombre del producto
         * @param pedido Pedido
         * @return Comprobamos si existe
         */
        public boolean comprobarproductoenpedido( String nombreProducto , Pedido pedido ) {
            return buscarproducto( nombreProducto , pedido ) != null;
        }

        /**
         * Actualiza la fecha del pedido pulsado
         */
        public void actualizarfecha(Pedido pedido){
            HibernateUtil.getSessionFactory().inTransaction(s -> {
                Pedido p = s.get( Pedido.class , pedido.getId() );
                p.setFecha( LocalDate.now().toString() );
            });
        }
    }
