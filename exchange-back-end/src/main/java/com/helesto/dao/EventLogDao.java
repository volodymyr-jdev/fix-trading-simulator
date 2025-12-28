package com.helesto.dao;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.helesto.model.EventLogEntity;

@ApplicationScoped
public class EventLogDao {

    @Inject
    EntityManager em;

    public List<EventLogEntity> listEventLog(String sendercompid) {

        TypedQuery<EventLogEntity> query = em.createNamedQuery("EventLog.findAllBySenderCompID", EventLogEntity.class);

        query.setParameter("sendercompid", sendercompid);

        return query.getResultList();

    }

}
