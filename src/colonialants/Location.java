/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class Location {

    protected int x = 0;
    protected int y = 0;
            
    public Location(){
        
    }
    
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean equals(Location l){
        return (this.getX()==l.getX())&&(this.getY()==l.getY());
    }

}
