package com.arm.CreditManagerWorkstation.repository;

import com.arm.CreditManagerWorkstation.model.Request;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RequestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Request request) {
        Session session = entityManager.unwrap(Session.class);
        session.getSession().save(request);
    }
}