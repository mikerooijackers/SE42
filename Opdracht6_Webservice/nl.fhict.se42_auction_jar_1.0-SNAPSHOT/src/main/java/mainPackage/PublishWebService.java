/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainPackage;

import javax.xml.ws.Endpoint;
import web.Auction;
import web.Registration;

/**
 *
 * @author Laurens
 */
public class PublishWebService {

    
    private static final String urlAuction = "http://localhost:8081/Auction";
    private static final String urlRegistration = "http://localhost:8081/Registration";
    public static void main(String[] args) {
        Endpoint.publish(urlAuction, new Auction());
        Endpoint.publish(urlRegistration,new Registration());
    }
    
}
