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

    private int x = 0;
    private int y = 0;
    
    public Location(){
        //I think this should init to the origin of the colony
    }
    
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
