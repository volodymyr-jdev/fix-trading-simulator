package com.helesto.dao;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import com.helesto.dto.OrderDto;
import com.helesto.model.OrderEntity;
import com.helesto.socket.OrderSocket;

@ApplicationScoped
public class OrderDao {

    @Inject
    EntityManager em;

    @Inject
	OrderSocket orderSocket;

    public List<OrderEntity> listOrders() {

        TypedQuery<OrderEntity> query = em.createNamedQuery("Orders.findAll", OrderEntity.class);

        return query.getResultList();

    }

    public Optional<OrderEntity> readByClOrdID(int clOrdID) {

        Query query = em.createNamedQuery("Orders.findByClOrdID");

        query.setParameter("clOrdID", clOrdID);

        try {
            return Optional.of((OrderEntity) query.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }

    }

    public void persistOrder(OrderEntity order) {
        em.persist(order);
        orderSocket.broadcast(new OrderDto(order));
    }

    public void updateOrder(OrderEntity order) {
        em.merge(order);
        orderSocket.broadcast(new OrderDto(order));
    }

}
