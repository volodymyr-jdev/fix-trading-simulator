package com.helesto.dao;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import com.helesto.model.SessionsEntity;

@ApplicationScoped
public class SessionsDao {

    @Inject
    EntityManager em;

    public Optional<SessionsEntity> readSession(String sendercompid) {

        Query query = em.createNamedQuery("Sessions.findBySenderCompID");

        query.setParameter("sendercompid", sendercompid);

        try {
            return Optional.of((SessionsEntity) query.getSingleResult());

        } catch (NoResultException nre) {
            return Optional.empty();

        }

    }

}
