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
    
    int SCENT_UNIT = 25;
    
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
       this.ReturnIntensity += SCENT_UNIT;
    }
    
    public void raiseFoodIntensity(){
       this.FoodIntensity += SCENT_UNIT;
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
        String tex = textures[16];
        if (ReturnIntensity > FoodIntensity){
            if (ReturnIntensity <= 2*SCENT_UNIT && ReturnIntensity > 0){
                tex = textures[8];
            } else if(ReturnIntensity <= 4*SCENT_UNIT && ReturnIntensity > 2*SCENT_UNIT){
                tex = textures[9];
            }  else if(ReturnIntensity <= 6*SCENT_UNIT && ReturnIntensity > 4*SCENT_UNIT){
                tex = textures[10];
            }  else if(ReturnIntensity > 6*SCENT_UNIT){
                tex = textures[11];
            }
        }else {
            if (FoodIntensity <= 2*SCENT_UNIT && FoodIntensity > 0){
                tex = textures[12];
            } else if(FoodIntensity <= 4*SCENT_UNIT && FoodIntensity > 2*SCENT_UNIT){
                tex = textures[13];
            }  else if(FoodIntensity <= 6*SCENT_UNIT && FoodIntensity > 4*SCENT_UNIT){
                tex = textures[14];
            }  else if(FoodIntensity > 6*SCENT_UNIT){
                tex = textures[15];
            }
        }
        
        return tex;
    }

    public void setTexture(String name, int type) {
            textures[type] = name;
    }   
}
