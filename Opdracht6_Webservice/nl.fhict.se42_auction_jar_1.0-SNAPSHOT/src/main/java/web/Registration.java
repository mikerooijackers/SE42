/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import auction.domain.User;
import auction.service.RegistrationMgr;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Laurens
 */
@WebService
public class Registration {
     final EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    final EntityManager em = emf.createEntityManager();
   RegistrationMgr registrationMgr = new RegistrationMgr(em);
    /**
     * Web service operation
     */
    @WebMethod(operationName = "registerUser")
    public User registerUser(String email) {
       User u = registrationMgr.registerUser(email);
       return u;
        
    }
    @WebMethod(operationName = "getUser")
    public User getUser(String email){
      User u = registrationMgr.getUser(email);
      return u;
    }
    
}
