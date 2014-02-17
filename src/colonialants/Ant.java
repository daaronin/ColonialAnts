/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author George McDaid
 */
public class Ant {
    
    private FineLocation location;
    private GridLocation destination = null;
    Random r = new Random();
    
    public enum State{
        MOVING, IDLE
    }
    
    State state;
    
    public Ant(){
        location = new FineLocation();
        this.state = State.IDLE;
    }
    
    public Ant(FineLocation location){
        this.location = location;
        this.state = State.IDLE;
    }
    
    public Location getLocation(){
        return location;
    }
    
    public boolean isMoving(){
        return state==State.MOVING;
    }
    
    public void changeDest(ArrayList<Location> square){
        if(!isMoving()){
            state = State.MOVING;
        }
        
        int pick = r.nextInt(square.size()-1);

        destination = new GridLocation(square.get(pick).getX(),square.get(pick).getY());
        
    }
    
    public void update(){
        if(isMoving()){
            int x = destination.getX() - location.getX();
            int y = destination.getY() - location.getY();
            
            if(x!=0){
                if(x>0){
                    location.incX();
                }else{
                    location.decX();
                }
            } 
            
            if(y!=0){
                if(y>0){
                    location.incY();
                }else{
                    location.decY();
                }
            }
            
            if(location.equals(destination)){
                state = State.IDLE;
            }
        }
    }

}
