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
    
    private State state;
    
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
    
    public void changeDest(ArrayList<GridLocation> square){
        if(!isMoving()){
            state = State.MOVING;
        }
        
        for(int i = 0;i<square.size();i++){
            if(!(square.get(i).getTerrain() instanceof AntHill)){
                square.remove(i);
            }
        }
        
        int pick = r.nextInt(square.size());

        destination = square.get(pick);
        
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
