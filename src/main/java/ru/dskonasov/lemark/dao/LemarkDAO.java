/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dskonasov.lemark.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.dskonasov.lemark.entity.LemarkEntity;

/**
 *
 * @author Dmitriy Konasov
 */
public class LemarkDAO<T extends LemarkEntity> {
    private EntityManager em;
    private Class cls;
    public LemarkDAO(Class cls, EntityManager em){
        this.cls = cls;
        this.em = em;
    }
    public long save(T entity){
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);
        T updatedEntity = (T)session.merge(entity);
        em.getTransaction().commit();
        return updatedEntity.getId();
    }
    
    public T getById (long id){
        Session session = em.unwrap(Session.class);
        return (T) session.get(this.cls, id);
    }
    
    public void delete(T entity){
        Session session = em.unwrap(Session.class);
        session.delete(entity);
    }
    
    public List<T> getAll(){
        Session session = em.unwrap(Session.class);
        return session.createCriteria(cls).list();
    }
    
    public List<T> get(Map<String, ? extends Object> query){
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(cls);
        for (String column: query.keySet()){
            criteria.add(Restrictions.eq(column, query.get(column)));
        }
        return criteria.list();
    }
}
