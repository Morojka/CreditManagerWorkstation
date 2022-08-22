package com.arm.CreditManagerWorkstation.repository;

import com.arm.CreditManagerWorkstation.model.User;
import org.hibernate.Session;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User findByLogin(@Param("user") String query) {
        Session session = entityManager.unwrap(Session.class);
        return session.getSession().createQuery("FROM User where login = :query", User.class).setParameter("query", query).getSingleResult();
    }

    @Transactional
    public void save(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.getSession().save(user);
    }
}