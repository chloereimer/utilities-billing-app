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
    @NamedQuery(name = "Reading.findByReadingDate", query = "SELECT r FROM Reading r WHERE r.readingDate = :readingDate"),
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
