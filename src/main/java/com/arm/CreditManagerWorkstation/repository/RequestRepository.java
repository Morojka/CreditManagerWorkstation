package com.arm.CreditManagerWorkstation.repository;

import com.arm.CreditManagerWorkstation.model.Request;
import org.hibernate.Session;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RequestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Request request) {
        Session session = entityManager.unwrap(Session.class);
        session.getSession().save(request);
    }

    public Request findById(@Param("id") Long query) {
        Session session = entityManager.unwrap(Session.class);
        return session.getSession().createQuery("FROM Request where id = :query", Request.class).setParameter("query", query).getSingleResult();
    }
}