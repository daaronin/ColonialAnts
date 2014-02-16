/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author George McDaid
 */
public class Environment {
    
    //Needs to be a multiple of 2 for now
    private int spaceSize = 20;
    
    private int dimension = 36;
    
    private Location[][] locations;
    
    //Normally this will be a swarm holding all ants, sadly we must wait for Krish Fish (unless I get impatient)
    //Envionment has a Swarm
    private Ant ant;
    private CommonScents scent;
    
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
                locations[i][j] = new Location((int)(spaceSize*(i)+(spaceSize/2)),(int)(spaceSize*(j)+(spaceSize/2)));
                Random r = new Random();
                if(r.nextInt(100)<5){
                    locations[i][j].setTerrain((Terrain) new Leaf());
                }else{
                    locations[i][j].setTerrain((Terrain) new Sand());
                }
                //System.out.print("("+locations[i][j].getX()+","+locations[i][j].getY()+") ");
            }
            //System.out.println();
        }
    }
    
    public void addTestAnt(){
        ant = new Ant(locations[0][0]);
    }
    
    public Ant getTestAnt(){
        return ant;
    }
    
    public void addTestScent(){
        scent = new CommonScents(locations[4][4]);
    }
    
    public CommonScents getTestScent(){
        return scent;
    }
    
    public Location[][] getLocations(){
        return locations;
    }
    
    public ArrayList<Location> getSquare(Location location){
        ArrayList<Location> square  = new ArrayList<Location>();
        
        int ox = location.getX();
        int oy = location.getY();

        System.out.println("*********************");
        for(int i = -1;i<=1;i++){
            int x = (int)((ox-spaceSize/2)/spaceSize);
            x = x+i;
            if(x>=0&&x<dimension){
                for(int j = -1;j<=1;j++){
                    int y = (int)((oy-spaceSize/2)/spaceSize);
                    y = y+j;
                    if(y>=0&&y<dimension){
                        if((i==0)&&(j==0)){
                            continue;
                        }
                        square.add(locations[x][y]);
                        System.out.println(locations[x][y].getX()+" "+locations[x][y].getY());
                    }
                }
            }
        }
        System.out.println("*********************");
        
        return square;
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
