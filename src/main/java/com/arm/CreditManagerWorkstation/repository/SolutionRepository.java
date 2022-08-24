package com.arm.CreditManagerWorkstation.repository;

import com.arm.CreditManagerWorkstation.model.Solution;
import org.hibernate.Session;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class SolutionRepository {

    private final Boolean MAKE_AUTO_SOLUTION = Boolean.TRUE;

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Solution solution) {
        Session session = entityManager.unwrap(Session.class);
        if(MAKE_AUTO_SOLUTION == Boolean.TRUE) {
            if(Math.round(Math.random()) == 1) {
                solution.setApproved(Boolean.TRUE);
                solution.setDays_amount((int)(Math.random() * 366) + 1);
                solution.setCredit_amount((int)(Math.random() * (200000 - 1)) + 1);
            } else {
                solution.setApproved(Boolean.FALSE);
            }
        }
        session.getSession().save(solution);
    }

    //Transactional annotation prevents race condition
    //it happens without it
    @Transactional
    public void update(Solution solution) {
        Session session = entityManager.unwrap(Session.class);
        session.getSession().persist(solution);
        session.getSession().update(solution);
    }

    public Solution findById(@Param("id") Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.getSession().createQuery("FROM Solution where id = :query", Solution.class).setParameter("query", id).getSingleResult();
    }
}
