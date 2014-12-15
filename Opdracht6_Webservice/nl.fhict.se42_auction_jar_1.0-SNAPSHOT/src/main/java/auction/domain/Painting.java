/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auction.domain;

import javax.persistence.Entity;

/**
 *
 * @author Laurens
 */
@Entity
public class Painting extends Item {
    
    String title;
    String painter;

    public Painting() {
    }

    public Painting(String Title, String Painter){
        this.title = Title;
        this.painter = Painter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }
    
}
