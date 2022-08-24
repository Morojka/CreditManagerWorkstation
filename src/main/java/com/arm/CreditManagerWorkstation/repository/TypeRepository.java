package com.arm.CreditManagerWorkstation.repository;

import com.arm.CreditManagerWorkstation.model.Type;
import org.hibernate.Session;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TypeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Type> findByType(@Param("type") String queryType) {
        Session session = entityManager.unwrap(Session.class);
        return session.getSession().createQuery("FROM Type where type = :queryType", Type.class).setParameter("queryType", queryType).getResultList();
    }
}