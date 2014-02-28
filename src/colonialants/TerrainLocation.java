/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import org.lwjgl.util.Point;

/**
 *
 * @author George McDaid
 */
public class TerrainLocation extends Location{
    
    //protected Location[] neighbors = new Block[8];
    
    private Terrain t;
    Point gridPoint;
    
    public TerrainLocation(){
        
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
     
    
     
     /**
     *
     * @return
     */
    @Override
    public String toString(){
        return t.toString();
    }
    
}
