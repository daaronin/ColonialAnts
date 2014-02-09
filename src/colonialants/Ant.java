/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class Ant {
    
    Location location;
    
    public Ant(){
        location = new Location();
    }
    
    public Ant(int x, int y){
        location = new Location(x,y);
    }

}
