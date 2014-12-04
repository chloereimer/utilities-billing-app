/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.cr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author chloe
 */
@ManagedBean
@SessionScoped
public class Main {
    @PersistenceContext(unitName = "utilities-billing-appPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private String meter_id;
    private Integer reading_id;

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
    
    public Reading getReading(){
        
        Reading reading = (Reading) (em.createNamedQuery("Reading.findById").setParameter( "id", reading_id ).getSingleResult());
        
        Date referenceDate = reading.getReadingDate();
        Calendar c = Calendar.getInstance(); 
        c.setTime(referenceDate); 
        c.add(Calendar.MONTH, -1);
        Date previousDate = c.getTime();
        
        Meter meter = this.getMeter();
        
        Reading previousReading = (Reading) (em.createNamedQuery("Reading.findByReadingDateAndMeterId").setParameter( "readingDate", previousDate ).setParameter("meterId", meter ).getSingleResult());
        reading.setPreviousReading( previousReading );
        
        return reading;
    }
    
    public void setReadingId(Integer reading_id){
        this.reading_id = reading_id;
    }
    
    public Collection<Reading> getReadings(){
        Meter meter = this.getMeter();
        Collection<Reading> readings = meter.getReadingCollection();
        
        Reading previousReading = null;
        for (Reading reading : readings) {
            
            if( previousReading != null ){
                reading.setPreviousReading( previousReading );
            }
            
            previousReading = reading;
            
        }
        
        return readings;
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
