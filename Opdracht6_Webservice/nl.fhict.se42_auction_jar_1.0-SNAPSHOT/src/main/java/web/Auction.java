/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.RegistrationMgr;
import auction.service.SellerMgr;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.Money;

/**
 *
 * @author Laurens
 */
@WebService
public class Auction {
    
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    final EntityManager em = emf.createEntityManager();
    
     SellerMgr sellerMgr = new SellerMgr(em);
     AuctionMgr auctionMgr = new AuctionMgr(em);

    @WebMethod(operationName = "getItem")
    public Item getItem(Long id) {
        Item item = auctionMgr.getItem(id);
        return item;
    }

    @WebMethod(operationName = "findItemByDescription")
    public List<Item> findItemByDescription(String description) {
        List<Item> li = auctionMgr.findItemByDescription(description);
        return li;
    }

    @WebMethod(operationName = "newBid")
    public Bid newBid(Item item, User buyer, Money amount) {
        Bid bid = auctionMgr.newBid(item, buyer, amount);
        return bid;
    }

    @WebMethod(operationName = "offerItem")
    public Item offerItem(User seller, Category cat, String description) {
        Item item = sellerMgr.offerItem(seller, cat, description);
        return item;
    }

    @WebMethod(operationName = "revokeItem")
    public boolean revokeItem(Item item) {
        return sellerMgr.revokeItem(item);
    }
}
