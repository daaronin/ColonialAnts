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
        this.pixelLocation.x = x;
    }
    
    public void setY(int y) {
        this.pixelLocation.y = y;
    }
      
    
    public void incX(){
        pixelLocation.x++;
    }
    
    public void incY(){
        pixelLocation.y++;
    }
    
    public void decX(){
        pixelLocation.x--;
    }
    
    public void decY(){
        pixelLocation.y--;
    }
    
    
}
