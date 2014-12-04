/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.cr;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chloe
 */
@Entity
@Table(name = "FEES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fee.findAll", query = "SELECT f FROM Fee f"),
    @NamedQuery(name = "Fee.findById", query = "SELECT f FROM Fee f WHERE f.id = :id"),
    @NamedQuery(name = "Fee.findByFeeType", query = "SELECT f FROM Fee f WHERE f.feeType = :feeType"),
    @NamedQuery(name = "Fee.findByAmount", query = "SELECT f FROM Fee f WHERE f.amount = :amount")})
public class Fee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "FEE_TYPE")
    private String feeType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private float amount;

    public Fee() {
    }

    public Fee(Integer id) {
        this.id = id;
    }

    public Fee(Integer id, String feeType, float amount) {
        this.id = id;
        this.feeType = feeType;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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
        if (!(object instanceof Fee)) {
            return false;
        }
        Fee other = (Fee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mohawkcollege.cr.Fee[ id=" + id + " ]";
    }
    
}
