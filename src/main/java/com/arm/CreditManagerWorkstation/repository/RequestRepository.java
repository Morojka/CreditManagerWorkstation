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

    public List<Request> searchByName(String search) {
        Session session = entityManager.unwrap(Session.class);
        String query = "%" + search + "%";
        return session.getSession().createQuery("SELECT r FROM Request r where r.full_name LIKE :query", Request.class)
                .setParameter("query", query)
                .getResultList();
    }

    public List<Request> searchByPassport(String search) {
        Session session = entityManager.unwrap(Session.class);
        String query = "%" + search + "%";
        return session.getSession().createQuery("SELECT r FROM Request r where r.passport LIKE :query", Request.class)
                .setParameter("query", query)
                .getResultList();
    }

    public List<Request> searchByPhone(String search) {
        Session session = entityManager.unwrap(Session.class);
        String query = "%" + search + "%";
        return session.getSession().createQuery("SELECT r FROM Request r where r.phone_number LIKE :query", Request.class)
                .setParameter("query", query)
                .getResultList();
    }

    public List<Request> getAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.getSession().createQuery("FROM Request", Request.class).getResultList();
    }

    //Returns all requests that has solution connected
    //Boolean signed attr parameter is responsible for whether signed solutions or all will be returned
    public List<Request> whereHasSolution(Boolean signed) {
        Session session = entityManager.unwrap(Session.class);
        String subQuery = signed ? " where rs.signed = true" : "";
        return session.getSession().createQuery("select r FROM Request r JOIN r.solution as rs" + subQuery, Request.class).getResultList();
    }
}