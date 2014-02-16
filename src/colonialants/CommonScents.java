/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author Dan Ford
 */
public class CommonScents {
    
    private Location location;
    
    public enum Direction{
        NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, NONE
    }
    
    Direction direction;
    
    public CommonScents(){
        location = new Location();
        this.direction = Direction.NONE;
    }
    
    public CommonScents(Location location){
        this.location = location;
        this.direction = Direction.NONE;
    }
    
    public CommonScents(Location location, Direction direction){
        this.location = location;
        this.direction = direction;
    }
    
    public Location getLocation(){
        return location;
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public void setDirection(Direction direction){
       this.direction = direction;
    }
    
}
