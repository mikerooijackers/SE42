
package web;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Auction", targetNamespace = "http://web/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Auction {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns web.Bid
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "newBid", targetNamespace = "http://web/", className = "web.NewBid")
    @ResponseWrapper(localName = "newBidResponse", targetNamespace = "http://web/", className = "web.NewBidResponse")
    @Action(input = "http://web/Auction/newBidRequest", output = "http://web/Auction/newBidResponse")
    public Bid newBid(
        @WebParam(name = "arg0", targetNamespace = "")
        Item arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        User arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        Money arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns web.Item
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "offerItem", targetNamespace = "http://web/", className = "web.OfferItem")
    @ResponseWrapper(localName = "offerItemResponse", targetNamespace = "http://web/", className = "web.OfferItemResponse")
    @Action(input = "http://web/Auction/offerItemRequest", output = "http://web/Auction/offerItemResponse")
    public Item offerItem(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Category arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "revokeItem", targetNamespace = "http://web/", className = "web.RevokeItem")
    @ResponseWrapper(localName = "revokeItemResponse", targetNamespace = "http://web/", className = "web.RevokeItemResponse")
    @Action(input = "http://web/Auction/revokeItemRequest", output = "http://web/Auction/revokeItemResponse")
    public boolean revokeItem(
        @WebParam(name = "arg0", targetNamespace = "")
        Item arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<web.Item>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findItemByDescription", targetNamespace = "http://web/", className = "web.FindItemByDescription")
    @ResponseWrapper(localName = "findItemByDescriptionResponse", targetNamespace = "http://web/", className = "web.FindItemByDescriptionResponse")
    @Action(input = "http://web/Auction/findItemByDescriptionRequest", output = "http://web/Auction/findItemByDescriptionResponse")
    public List<Item> findItemByDescription(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns web.Item
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getItem", targetNamespace = "http://web/", className = "web.GetItem")
    @ResponseWrapper(localName = "getItemResponse", targetNamespace = "http://web/", className = "web.GetItemResponse")
    @Action(input = "http://web/Auction/getItemRequest", output = "http://web/Auction/getItemResponse")
    public Item getItem(
        @WebParam(name = "arg0", targetNamespace = "")
        Long arg0);

}
