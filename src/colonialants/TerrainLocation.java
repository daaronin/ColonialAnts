/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.ArrayList;
import org.eclipse.swt.graphics.Point;



/**
 *
 * @author George McDaid
 */
public class TerrainLocation extends Location{
    
    protected TerrainLocation[] neighbors = new TerrainLocation[8];
    
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
    }
     
    public TerrainLocation getNeighbor(Direction dir){
        return neighbors[dir.value];
    }

    public ArrayList<TerrainLocation> getNeighbors(){
        ArrayList<TerrainLocation> neighbor = new ArrayList<TerrainLocation>();
        for(int i = 0;i<neighbors.length;i++){
            if(neighbors[i] != null){
                neighbor.add(neighbors[i]);
            }
        }
        return neighbor;
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
