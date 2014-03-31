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
    public static int EVAP_RATE = 1;
    
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
        lowerFoodIntensity(delta);
        lowerReturnIntensity(delta);
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
    }
    
    public void raiseFoodIntensity(){
       this.FoodIntensity += 50;
    }
    
    public void lowerReturnIntensity(int delta){
       if (this.ReturnIntensity > 0){
            this.ReturnIntensity-= EVAP_RATE;
       }
    }
    
    public void lowerFoodIntensity(int delta){
       if (this.FoodIntensity > 0){
            this.FoodIntensity-= EVAP_RATE;
       }
    }
    
    public void resetReturnIntensity(){
       this.ReturnIntensity = 0;
    }
    
    public void resetFoodIntensity(){
       this.FoodIntensity = 0;
    }
    
    public void alterScent(TerrainLocation location, String type, int delta){
        //int block = (location.getX() / 20) + dimension * (location.getY() / 20);
        if ("return".equals(type)) {
            location.getScent().raiseReturnIntensity();
        } else if ("food".equals(type)){
            location.getScent().raiseFoodIntensity();
        }
    }
    
    public String getTexture() {
        String tex = textures[16];
        if (ReturnIntensity > FoodIntensity){
            if (ReturnIntensity <= 50 && ReturnIntensity > 0){
                tex = textures[8];
            } else if(ReturnIntensity <= 100 && ReturnIntensity > 50){
                tex = textures[9];
            }  else if(ReturnIntensity <= 150 && ReturnIntensity > 100){
                tex = textures[10];
            }  else if(ReturnIntensity > 150){
                tex = textures[11];
            }
        }else {
            if (FoodIntensity <= 50 && FoodIntensity > 0){
                tex = textures[12];
            } else if(FoodIntensity <= 100 && FoodIntensity > 50){
                tex = textures[13];
            }  else if(FoodIntensity <= 150 && FoodIntensity > 100){
                tex = textures[14];
            }  else if(FoodIntensity > 150){
                tex = textures[15];
            }
        }
        
        return tex;
    }

    public void setTexture(String name, int type) {
            textures[type] = name;
    }   
}
