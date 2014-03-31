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
public class Colony {
    
    int foodCount;
    int antCount;
    int leafCount;
    int scoreCount;

    public enum ColLoc{
        TR, TL, BR, BL
    }
    
    private final ColLoc location;
    private final int dimension;
    
    public Colony(){
        location = ColLoc.TL;
        dimension = 15;
        foodCount = 0;
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
    
    public void addAntCount(){
        antCount++;
    }
    
    public void lowerAntCount(){
        antCount--;
    }
    
    public int getAntCount(){
        return antCount;
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
    

}
