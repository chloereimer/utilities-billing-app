/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.cr;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author chloe
 */
@Entity
@Table(name = "METERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meter.findAll", query = "SELECT m FROM Meter m"),
    @NamedQuery(name = "Meter.findById", query = "SELECT m FROM Meter m WHERE m.id = :id"),
    @NamedQuery(name = "Meter.findByAccountId", query = "SELECT m FROM Meter m WHERE m.accountId = :accountId"),
    @NamedQuery(name = "Meter.findByStreetNumber", query = "SELECT m FROM Meter m WHERE m.streetNumber = :streetNumber"),
    @NamedQuery(name = "Meter.findByStreetName", query = "SELECT m FROM Meter m WHERE m.streetName = :streetName")})
public class Meter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "ID")
    private String id;
    @Column(name = "ACCOUNT_ID")
    private Integer accountId;
    @Size(max = 8)
    @Column(name = "STREET_NUMBER")
    private String streetNumber;
    @Size(max = 64)
    @Column(name = "STREET_NAME")
    private String streetName;
    @OneToMany(mappedBy = "meterId")
    private Collection<Reading> readingCollection;

    public Meter() {
    }

    public Meter(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @XmlTransient
    public Collection<Reading> getReadingCollection() {
        return readingCollection;
    }

    public void setReadingCollection(Collection<Reading> readingCollection) {
        this.readingCollection = readingCollection;
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
        if (!(object instanceof Meter)) {
            return false;
        }
        Meter other = (Meter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mohawkcollege.cr.Meter[ id=" + id + " ]";
    }
    
}
