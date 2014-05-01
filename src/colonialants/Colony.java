/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import colonialants.Environment.AntType;
import java.util.ArrayList;

/**
 *
 * @author George McDaid
 */
public class Colony {
    
    private ArrayList<Ant> ants;
    
    private int foodCount;
    private int leafCount;
    private int scoreCount;
    
    private int gatherers;
    private int builders;

    public enum ColLoc{
        TR, TL, BR, BL
    }
    
    private final ColLoc location;
    private final int dimension;
    
    public Colony(){
        location = ColLoc.TL;
        dimension = 15;
        foodCount = 0;
        ants = new ArrayList<Ant>();
    }
    
    public ColLoc getLocation(){
        return location;
    }
    
    public int getDimension(){
        return dimension;
    }
    
    public void addFood(){
        foodCount += 5;
    }
    
    public void removeFood(){
        foodCount -= 1;
    }
    
    public void addFood(int amount){
        foodCount += amount;
    }
    
    public void removeFood(int amount){
        foodCount -= amount;
    }
    
    public int getFoodCount(){
        return foodCount;
    }
    
    public void addAntCount(AntType type){
        if(type == AntType.GATHERER){
            gatherers++;
        }else if(type == AntType.BUILDER){
            builders++;
        }
    }
    
    public void lowerAntCount(AntType type){
        if(type == AntType.GATHERER){
            gatherers--;
        }else if(type == AntType.BUILDER){
            builders--;
        }
    }
    
    public int getAntCount(AntType type){
        if(type == AntType.GATHERER){
            return gatherers;
        }else if(type == AntType.BUILDER){
            return builders;
        }
        return 0;
    }
    
    public int getPopulation(){
        return gatherers + builders;
    }
    
    public void addLeafCount(){
        leafCount++;
    }
    
    public int getLeafCount(){
        return leafCount;
    }
    
    public void lowerLeafCount(){
        leafCount--;
    }
    
    public int getScore() {
        return scoreCount;
    }
    
    public void addScore(int points){
        scoreCount += points;
    }
    
    public void lowerScore(int points){
        scoreCount -= points;
    }
    
    public ArrayList<Ant> getAnts(){
        return ants;
    }
    
    public void setAnts(ArrayList<Ant> ants){
        this.ants = ants;
    }

    public void resetColony(){
        ants = new ArrayList<Ant>();
        foodCount = 100;
        leafCount = 0;
        scoreCount = 0;

        gatherers = 0;
        builders = 0;
    }
}
