/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class FineLocation extends Location{
    
    public FineLocation(){
        
    }
    
    public FineLocation(int x, int y){
        super(x,y);
    }
    
    public void setX(int x) {
        this.p.x = x;
    }
    
    public void setY(int y) {
        this.p.y = y;
    }
      
    
    public void incX(){
        p.x++;
    }
    
    public void incY(){
        p.y++;
    }
    
    public void decX(){
        p.x--;
    }
    
    public void decY(){
        p.y--;
    }
    
    
}
