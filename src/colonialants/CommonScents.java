/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import org.eclipse.swt.graphics.Rectangle;

/**
 *
 * @author Dan Ford
 */
public class CommonScents {
    
    public int ReturnIntensity = 0;
    public int FoodIntensity = 0;
    private String[] textures;
       
    public CommonScents(){
        this.ReturnIntensity = 0;
        this.FoodIntensity = 0;
    }
    
    public CommonScents(String[] tex){
        this.ReturnIntensity = 0;
        this.FoodIntensity = 0;
        textures = tex;
    }
    
    public CommonScents(int ReturnIntensity, int FoodIntensity){
        this.ReturnIntensity = ReturnIntensity;
        this.FoodIntensity = FoodIntensity;
    }
    
     void onClockTick(int delta) {
        update(delta);
    }
    
    public void update(int delta){
        lowerFoodIntensity();
        lowerReturnIntensity();
    }
    
    public int getReturnIntensity(){
        return ReturnIntensity;
    }
    
    public int getFoodIntensity(){
        return FoodIntensity;
    }
    
    public void setReturnIntensity(int ReturnIntensity){
       this.ReturnIntensity = ReturnIntensity;
    }
    
    public void setFoodIntensity(int FoodIntensity){
       this.FoodIntensity = FoodIntensity;
    }
    
    public void raiseReturnIntensity(){
       this.ReturnIntensity += 50;
       if (FoodIntensity < ReturnIntensity){
           setTexture("pheromoneReturn",9);
       }
    }
    
    public void raiseFoodIntensity(){
       this.FoodIntensity += 50;
       setTexture("pheromoneFood",8);
    }
    
    public void lowerReturnIntensity(){
       if (this.ReturnIntensity > 0){
            this.ReturnIntensity--;
       }
    }
    
    public void lowerFoodIntensity(){
       if (this.FoodIntensity > 0){
            this.FoodIntensity--;
       }
    }
    
    public void resetReturnIntensity(){
       this.ReturnIntensity = 0;
    }
    
    public void resetFoodIntensity(){
       this.FoodIntensity = 0;
    }
    
    public String getTexture() {
        if (ReturnIntensity > FoodIntensity){
		String tex = textures[9];
		return tex;
        } else if (FoodIntensity > ReturnIntensity){
            String tex = textures[8];
		return tex;
        } else {
            String tex = textures[10];
		return tex;
        }
    }

    public void setTexture(String name, int type) {
            textures[type] = name;
    }   
}
