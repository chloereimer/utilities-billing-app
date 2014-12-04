/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.cr;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chloe
 */
@Entity
@Table(name = "READINGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reading.findAll", query = "SELECT r FROM Reading r"),
    @NamedQuery(name = "Reading.findById", query = "SELECT r FROM Reading r WHERE r.id = :id"),
    @NamedQuery(name = "Reading.findByReadingDateAndMeterId", query = "SELECT r FROM Reading r WHERE r.readingDate = :readingDate AND r.meterId = :meterId"),
    @NamedQuery(name = "Reading.findByAmount", query = "SELECT r FROM Reading r WHERE r.amount = :amount")})
public class Reading implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "READING_DATE")
    @Temporal(TemporalType.DATE)
    private Date readingDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private int amount;
    @JoinColumn(name = "METER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Meter meterId;
    @Transient
    private Reading previousReading;

    public Reading() {
    }

    public Reading(Integer id) {
        this.id = id;
    }

    public Reading(Integer id, Date readingDate, int amount) {
        this.id = id;
        this.readingDate = readingDate;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Meter getMeterId() {
        return meterId;
    }

    public void setMeterId(Meter meterId) {
        this.meterId = meterId;
    }
    
    public void setPreviousReading(Reading previousReading){
        this.previousReading = previousReading;
    }
    
    public Reading getPreviousReading(){
        return previousReading;
    }
    
    public float getAmountUsed(){
        float amountUsed = 0.0F;
        if( previousReading != null ){
            amountUsed = (float) ((amount - previousReading.amount) / 50 );
        }
        return amountUsed;
    }
    
    public float getBill(){
        return ( ( this.getLowAmount() * 0.5F ) + ( this.getMediumAmount() * 0.55F ) + ( this.getHighAmount() * 0.6F ));
    }
    
    public float getLowAmount(){
        float totalAmount = this.getAmountUsed();
        if( totalAmount < 5 ){
            return totalAmount;
        } else {
            return 5;
        }
    }
    
    public float getMediumAmount(){
        float totalAmount = this.getAmountUsed();
        if( totalAmount - 5 < 25 ){
            return totalAmount - 5;
        } else {
            return 25;
        }
    }
    
    public float getHighAmount(){
        float totalAmount = this.getAmountUsed();
        if( totalAmount - 30 > 0 ){
            return totalAmount - 30;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reading)) {
            return false;
        }
        Reading other = (Reading) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mohawkcollege.cr.Reading[ id=" + id + " ]";
    }
    
}
