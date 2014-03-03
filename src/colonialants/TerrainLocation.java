/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.HashMap;
import org.eclipse.swt.graphics.Point;



/**
 *
 * @author George McDaid
 */
public class TerrainLocation extends Location{
    
    protected TerrainLocation[] neighbors = new TerrainLocation[8];
    HashMap<Direction, TerrainLocation> options = new HashMap<Direction, TerrainLocation>();
    
    private Terrain t;
    
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
     
    
    
        
     
     /**
     *
     * @return
     */
    @Override
    public String toString(){
        return t.toString();
    }
    
}
