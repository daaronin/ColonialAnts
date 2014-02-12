/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class Location {

    private int x = 0;
    private int y = 0;
    
    private Terrain t;
    
    public Location(){
        
    }
    
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
