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
    
    private Location location;
    
    public enum State{
        MOVING, IDLE
    }
    
    State state;
    
    public Ant(){
        location = new Location();
        this.state = State.MOVING;
    }
    
    public Ant(Location location){
        this.location = location;
    }
    
    public Location getLocation(){
        return location;
    }
    
    public void move(Location[] square){
        if(state != State.MOVING){
            state = State.MOVING;
        }
    }

}
