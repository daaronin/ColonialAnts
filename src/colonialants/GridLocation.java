/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class GridLocation extends Location{
    
    private Terrain t;
    
    public GridLocation(){
        
    }
    
    public GridLocation(int x, int y){
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
