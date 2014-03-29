/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import colonialants.TerrainLocation.Direction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 *
 * @author George McDaid
 */
public class Ant {
    
    TerrainLocation destination = null;
    TerrainLocation origin = null;
    Random r = new Random();
    int SIZE = 20;
    Location position;
    Direction intendedBearing = Direction.NORTH;
    private String[] textures;
    private double ANT_SPEED = .08;
    boolean carryingFood = false;

    private void randomWalk() {
    }

    public enum State{
        MOVING, IDLE
    }
    
    State state;
    
    public Ant(){
        this.state = State.IDLE;
    }
    
    public Ant(Point p){
        position = new Location(p);
        this.state = State.IDLE;
    }
    
    public Ant(Point p, TerrainLocation t, String[] tex){
        position = new Location(p);
        this.state = State.IDLE;
        origin = t;
        textures = tex;
    }
    
    public Ant(Point p, int size){
        position = new Location(p, size);
        this.state = State.IDLE;
    }
    
    public Rectangle getLocation(){
        return position.getScreenLocaiton();
    }
    
    void onClockTick(int delta) {
        update(delta);
    }
    
    public void update(int delta){
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
                walk(delta);
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
        HashMap<Direction,TerrainLocation> list = origin.getNeighbors();
        HashMap<Integer, Direction> choices = new HashMap<Integer, Direction>();
        Iterator it = list.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            if(((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("anthill")){
                //System.out.println();
                choices.put(count, (Direction) pairs.getKey());
                count++;
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
        if(count > 0){
            int choice = r.nextInt(count);
            intendedBearing = choices.get(choice);
            destination = list.get(intendedBearing);
            state = State.MOVING;
        }
        
    }
    
    public void walk(int delta){
        if(isMoving()){
            
            //primMove();
            advancedMove(delta);
            
            if(position.equalRectagle(destination.getScreenLocaiton())){
                origin = destination;
                state = State.IDLE;
            }
        }
    }
    
    public void advancedMove(int delta){
        ANT_SPEED = .08+Math.random()*.05;
        Rectangle dest = destination.getScreenLocaiton();
        Rectangle curr = position.getScreenLocaiton();
        int tol = (int)(Math.sqrt(dest.width *dest.width + dest.height * dest.height))/8;
        double deltaDistance = ANT_SPEED * (double) delta;

        double distance = Math.sqrt(Math.pow(dest.x - curr.x, 2.0)
                        + Math.pow(dest.y - curr.y, 2.0));
        double factor = deltaDistance / distance;

        curr.x += (int) (factor * (dest.x - curr.x));
        curr.y += (int) (factor * (dest.y - curr.y));
        curr.x = Math.abs(curr.x - dest.x) < tol ? dest.x : curr.x;
        curr.y = Math.abs(curr.y - dest.y) < tol ? dest.y : curr.y;
    }
    
    public void primMove(){
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
    }
    
    public TerrainLocation getOrigin(){
        return this.origin;
    }
    
    public String getTexture() {
		String tex = textures[intendedBearing.value];
		return tex;
	}

    public void setTexture(String name, Direction dir) {
            textures[dir.value] = name;
    }
    
    public Rectangle getScreenPosition(){
        return position.getScreenLocaiton();
    }
    
    public void setCarrying(){
        carryingFood = true;
    }
    
    public void stopCarrying(){
        carryingFood = false;
    }
    
    public boolean getCarryingStatus(){
        return this.carryingFood;
    }    

}
