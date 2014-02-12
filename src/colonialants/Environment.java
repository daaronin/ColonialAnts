/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.Random;

/**
 *
 * @author George McDaid
 */
public class Environment {
    
    private int spaceSize = 20;
    
    private int dimension = 35;
    
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
                locations[i][j] = new Location((int)(spaceSize*(i+1)+(spaceSize/2)),(int)(spaceSize*(j+1)+(spaceSize/2)));
                Random r = new Random();
                if(r.nextInt(100)<5){
                    locations[i][j].setTerrain((Terrain) new Leaf());
                }else{
                    locations[i][j].setTerrain((Terrain) new Sand());
                }
                System.out.print("("+locations[i][j].getX()+","+locations[i][j].getY()+") ");
            }
            System.out.println();
        }
    }
    
    public Location[][] getLocations(){
        return locations;
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
    
    public int getSpaceSize(){
        return spaceSize;
    }
}
