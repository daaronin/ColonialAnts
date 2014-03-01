/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.ArrayList;
import java.util.Random;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 *
 * @author George McDaid
 */
public class Ant {
    
    private TerrainLocation destination = null;
    private TerrainLocation origin = null;
    Random r = new Random();
    int SIZE = 20;
    Location position;

    private void randomWalk() {
    }

    
    
    public enum State{
        MOVING, IDLE
    }
    
    private State state;
    
    public Ant(){
        this.state = State.IDLE;
    }
    
    public Ant(Point p){
        position = new Location(p);
        this.state = State.IDLE;
    }
    
    public Ant(Point p, TerrainLocation t){
        position = new Location(p);
        this.state = State.IDLE;
        origin = t;
    }
    
    public Ant(Point p, int size){
        position = new Location(p, size);
        this.state = State.IDLE;
    }
    
    public Rectangle getLocation(){
        return position.getScreenLocaiton();
    }
    
    void onClockTick() {
        update();
    }
    
    public void update(){
         //Ant has to stop to think
        if (!isMoving()){
                // Ant contemplates a move
                changeDestination();
                // Turns in the direction to move;
                // The turn can be made linear
                //setBearing(intendedBearing);
        }  
        if (isMoving()){
                // Ant with a purpose
                //snapMove(getScreenPosition());
                walk();
        }
         
    }   
    
    
    public boolean isMoving(){
        return state==State.MOVING;
    }
    
    public void changeDest(ArrayList<TerrainLocation> square){
        if(!isMoving()){
            state = State.MOVING;
        }
        
        for(int i = 0;i<square.size();i++){
            if(!(square.get(i).getTerrain() instanceof AntHill)){
                square.remove(i);
                i = 0;
            }
        }
        
        int pick = r.nextInt(square.size());

        destination = square.get(pick);
        
        destination.toString();
        
    }
    
    
    public void changeDestination(){
        ArrayList<TerrainLocation> list = origin.getNeighbors();
        destination = list.get(r.nextInt(list.size()));
        state = State.MOVING;
    }
    
    public void walk(){
        if(isMoving()){
            int x = destination.getX() - position.getScreenLocaiton().x;
            int y = destination.getY() - position.getScreenLocaiton().y;
            
            if(x!=0){
                if(x>0){
                    position.incX();
                }else{
                    position.decX();
                }
            } 
            
            if(y!=0){
                if(y>0){
                    position.incY();
                }else{
                    position.decY();
                }
            }
            
            if(position.equalRectagle(destination.getScreenLocaiton())){
                origin = destination;
                state = State.IDLE;
            }
        }
    }
    
    public Rectangle getScreenPosition(){
        return position.getScreenLocaiton();
    }

}
