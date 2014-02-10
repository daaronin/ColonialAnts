/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class Environment {
    
    private int dimension = 10;
    
    private Location[][] locations;
    
    public Environment(){
        locations = new Location[dimension][dimension];
    }
    
    public Environment(int dimension){
        this.dimension = dimension;
        locations = new Location[dimension][dimension];
    }
    
    public void initEmptyField(){
        for(int i = 0;i < dimension;i++){
            for(int j = 0;j < dimension;j++){
                locations[i][j] = new Location(i,j);
                locations[i][j].setTerrain((Terrain) new Sand());
            }
        }
    }
    
    public Location[] getSquare(Location location){
        
        return null;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        String s = "";
        for(int i = 0;i < dimension;i++){
            for(int j = 0;j < dimension;j++){
                System.out.print(locations[j][i].toString() + " ");
            }
            System.out.println();
            
        }
        return s;
    }
}
