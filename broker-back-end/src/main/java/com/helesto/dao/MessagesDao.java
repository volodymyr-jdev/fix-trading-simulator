package com.helesto.dao;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.helesto.model.MessagesEntity;

@ApplicationScoped
public class MessagesDao {

    @Inject
    EntityManager em;

    public List<MessagesEntity> listMessages(String sendercompid) {

        TypedQuery<MessagesEntity> query = em.createNamedQuery("Messages.findAllBySenderCompID", MessagesEntity.class);

        query.setParameter("sendercompid", sendercompid);

        return query.getResultList();

    }

}
