/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialants;

import java.io.Serializable;
import java.util.HashMap;
import org.eclipse.swt.graphics.Point;

/**
 *
 * @author George McDaid
 */
public class TerrainLocation extends Location implements Serializable{

    static int terrainLocCount = 0;
    
    private int id;
    
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

        private Direction(int bearing) {
            this.value = bearing;
        }
        
        public static Direction fromValue(int value){
            switch(value){
                case 0:
                    return Direction.NORTH;
                case 1:
                    return Direction.NORTHEAST;
                case 2:
                    return Direction.EAST;
                case 3:
                    return Direction.SOUTHEAST;
                case 4:
                    return Direction.SOUTH;
                case 5:
                    return Direction.SOUTHWEST;
                case 6:
                    return Direction.WEST;
                case 7:
                    return Direction.NORTWEST;
                default: 
                    return Direction.NORTH;
            }
        }
    }

    public TerrainLocation(Point p, int DIMENSION) {
        super(p, DIMENSION);
        id = terrainLocCount;
        terrainLocCount++;
    }
    
    public TerrainLocation(){
        
    }

    public void setScent(CommonScents scent) {
        this.scent = scent;
    }

    public CommonScents getScent() {
        return scent;
    }

    public Terrain getTerrain() {
        return t;
    }

    public void setTerrain(Terrain t) {
        this.t = t;
    }

    public void addNeighbor(TerrainLocation loc, Direction dir) {
        neighbors[dir.value] = loc;
        options.put(dir, loc);
    }

    public TerrainLocation getNeighbor(Direction dir) {
        return neighbors[dir.value];
    }

    public HashMap<Direction, TerrainLocation> getNeighbors() {
        return options;
    }

    void onClockTick(int delta) {
        scent.onClockTick(delta);
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public void takeResources(int amount) {
        this.resources -= amount;
    }

    public int getResources() {
        return this.resources;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return t.toString();
    }

    public int getID(){
        return id;
    }
}
