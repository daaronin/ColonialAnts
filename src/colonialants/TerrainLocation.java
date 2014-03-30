/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.HashMap;
import java.util.Random;
import org.eclipse.swt.graphics.Point;



/**
 *
 * @author George McDaid
 */
public class TerrainLocation extends Location{
    
    protected TerrainLocation[] neighbors = new TerrainLocation[8];
    HashMap<Direction, TerrainLocation> options = new HashMap<Direction, TerrainLocation>();
    CommonScents scent = new CommonScents();
    
    private Terrain t;
    private int resources = 0;
   
    public static enum Direction {
		NORTH(0), NORTHEAST(1), EAST(2),
		SOUTHEAST(3), 
		SOUTH(4), SOUTHWEST(5), WEST(6),
		NORTWEST(7);
		
		public int value;
		
		private Direction(int bearing){
			this.value = bearing;
		}
	}
    
    public TerrainLocation(){
        
    }
    
    public TerrainLocation(Point p, int DIMENSION){
        super(p, DIMENSION);
    }
    
    public TerrainLocation(int x, int y){
        super(x,y);
    }
    
    public void setScent(CommonScents scent){
        this.scent = scent;
    }
    
    public CommonScents getScent(){
        return scent;
    }
    
     public Terrain getTerrain(){
        return t;
    }
     
     public void setTerrain(Terrain t){
        this.t = t;
    }
     
     public void addNeighbor(TerrainLocation loc, Direction dir){
        neighbors[dir.value] = loc;
        options.put(dir, loc);
    }
     
    public TerrainLocation getNeighbor(Direction dir){
        return neighbors[dir.value];
    }

    public HashMap<Direction, TerrainLocation> getNeighbors(){
        return options;
    }
     
    void onClockTick(int delta) {
        scent.onClockTick(delta);
    }
    
    public void setResources(int resources){
        this.resources = resources;
    }
    
    public void takeResources(int amount){
        this.resources-=amount;
    }
    
    public int getResources(){
        return this.resources;
    }
        
     
     /**
     *
     * @return
     */
    @Override
    public String toString(){
        return t.toString();
    }
    
}
