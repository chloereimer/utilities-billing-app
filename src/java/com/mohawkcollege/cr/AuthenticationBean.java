/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mohawkcollege.cr;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.misc.BASE64Encoder;

/**
 *
 * @author chloe
 */
@ManagedBean
@SessionScoped
public class AuthenticationBean {
    
    private String username;
    private String password;
    private String streetNumber;
    private String streetName;
    private String meterId;
    private int privilege;
    @PersistenceContext(unitName = "utilities-billing-appPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    public String getUsername() {
        return username;
    }
    
    public String getUsernameFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return session.getAttribute("user").toString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public AuthenticationBean() {
    }
    
    public String doRegistration(){
        
        String hash = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes("UTF-8"));
            byte raw[] = md.digest();
            hash = (new BASE64Encoder()).encode(raw);
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Meter meter = (Meter) (em.createNamedQuery("Meter.findById").setParameter( "id", meterId ).getSingleResult());
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(hash);
        account.setPrivilege(privilege);
        account.setMeterId(meter);
        
        boolean committed = false;
        try{
            utx.begin();
            em.persist(account);
            utx.commit();
            committed = true;
        } catch( Exception e ){
            return "login";
        }
        
        if( privilege >= 1 ){
            return "admin/index?faces-redirect=true";
        } else {
            return "user/index?faces-redirect=true";
        }
    }
    
    public String doLogin(){
        
        Account account = null;
        
        try{
            account = (Account) (em.createNamedQuery("Account.findByUsername").setParameter( "username", username ).getSingleResult());
            this.setUsername( account.getUsername() );
            this.setPrivilege( account.getPrivilege() );
            this.setMeterId( account.getMeterId().getId() );
        } catch (Exception e){
            return "login";
        }
        
        if (account != null){
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            session.setAttribute("user", username);
            session.setAttribute("privilege", privilege);
            session.setAttribute("meterId", meterId);
        } else {
            return "login";
        }
        
        if( privilege >= 1 ){
            return "admin/index?faces-redirect=true";
        } else {
            return "user/index?faces-redirect=true";
        }
        
    }
    
    public String doLogout(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        return "login";
    }
    
    public boolean getIsLoggedIn(){
        
        boolean isLoggedIn = false;
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        
        if( session.getAttribute("user") != null && session.getAttribute("privilege") != null ){
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
        }
        
        return isLoggedIn;
        
    }
    
    public boolean getIsNotLoggedIn(){
        return !this.getIsLoggedIn();
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
