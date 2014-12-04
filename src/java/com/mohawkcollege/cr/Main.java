/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.cr;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author chloe
 */
@ManagedBean
@RequestScoped
public class Main {
    @PersistenceContext(unitName = "utilities-billing-appPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private String meter_id;

    /**
     * Creates a new instance of Main
     */
    public Main() {
    }
    
    public Collection<Meter> getMeters(){
        Collection<Meter> meters = (Collection<Meter>) (em.createNamedQuery("Meter.findAll").getResultList());
        return meters; 
    }
    
    public Meter getMeter(){
        Meter meter = (Meter) (em.createNamedQuery("Meter.findById").setParameter( "id", meter_id ).getSingleResult());
        return meter;
    }
    
    public void setMeterId(String meter_id){
        this.meter_id = meter_id;
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
}
