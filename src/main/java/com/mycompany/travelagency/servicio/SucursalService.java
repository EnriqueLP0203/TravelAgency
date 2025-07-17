package com.mycompany.travelagency.servicio;

import com.mycompany.travelagency.modelo.Sucursal;

import javax.persistence.*;
import java.util.List;

/**
 * Servicio para gestionar las operaciones CRUD de Sucursal.
 */
public class SucursalService {

    private final EntityManagerFactory emf;

    public SucursalService() {
        emf = Persistence.createEntityManagerFactory("TravelAgency");
    }

    public void guardarSucursal(Sucursal sucursal) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(sucursal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar sucursal: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Sucursal> obtenerTodasLasSucursales() {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Sucursal> query = em.createQuery(
                    "SELECT s FROM Sucursal s", Sucursal.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Sucursal buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public void actualizarSucursal(Sucursal sucursal) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(sucursal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar sucursal: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void eliminarSucursal(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            Sucursal sucursal = em.find(Sucursal.class, id);
            if (sucursal != null) {
                em.getTransaction().begin();
                em.remove(sucursal);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar sucursal: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
