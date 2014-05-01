/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialants;

import static colonialants.Utility.*;
import java.io.Serializable;

/**
 *
 * @author Dan Ford
 */
public class CommonScents implements Serializable{

    private double ReturnIntensity = 0;
    private double FoodIntensity = 0;

    private String[] textures;

    private Ant rlayer = null;
    private Ant flayer = null; 

    private static double EVAP_RATE = .03125;

    public CommonScents() {
        this.ReturnIntensity = 0;
        this.FoodIntensity = 0;
    }

    public CommonScents(String[] tex) {
        this.ReturnIntensity = 0;
        this.FoodIntensity = 0;
        textures = tex;
    }

    public CommonScents(int ReturnIntensity, int FoodIntensity) {
        this.ReturnIntensity = ReturnIntensity;
        this.FoodIntensity = FoodIntensity;
    }

    void onClockTick(int delta) {
        //O(delta);
        update(delta);
    }

    public void update(int delta) {
        lowerFoodIntensity();
        lowerReturnIntensity();
    }

    public double getReturnIntensity() {
        return ReturnIntensity;
    }

    public double getFoodIntensity() {
        return FoodIntensity;
    }

    public void raiseReturnIntensity(double increase) {
        this.ReturnIntensity += increase;

        if (this.ReturnIntensity > (1 / EVAP_RATE)) {
            this.ReturnIntensity = (int) (1 / EVAP_RATE);
        }

    }

    public void raiseFoodIntensity(double increase) {
        this.FoodIntensity += increase;

        if (this.FoodIntensity > (1 / EVAP_RATE)) {
            this.FoodIntensity = (int) (1 / EVAP_RATE);
        }

    }

    public void lowerReturnIntensity() {
        if (rlayer != null && !rlayer.isMoving()) {

            this.ReturnIntensity = (ReturnIntensity * (1 - EVAP_RATE));
        }else if(rlayer == null){
            this.ReturnIntensity = (ReturnIntensity * (1-(EVAP_RATE*.0588)));
        }
        

        if (this.ReturnIntensity < EVAP_RATE) {
            this.ReturnIntensity = 0;
        }

    }

    public void lowerFoodIntensity() {
        if (flayer != null && !flayer.isMoving()) {
            
            this.FoodIntensity = ((FoodIntensity * (1 - EVAP_RATE)));

        }else if(flayer == null){
            this.FoodIntensity = ((FoodIntensity * (1 - (EVAP_RATE*.0588))));
        }
        
        if (this.FoodIntensity < EVAP_RATE) {
            this.FoodIntensity = 0;
        }
    }

    public void resetReturnIntensity() {
        this.ReturnIntensity = 0;
    }

    public void resetFoodIntensity() {
        this.FoodIntensity = 0;
    }

    public void alterScent(String type, double increase, Ant layer) {
        //int block = (location.getX() / 20) + dimension * (location.getY() / 20);
        if ("return".equals(type)) {
            raiseReturnIntensity(increase);
            this.rlayer = layer;
        } else if ("food".equals(type)) {
            raiseFoodIntensity(increase);
            this.flayer = layer;
        }
    }

    public String getTexture() {

        String tex = textures[16];

        if (ReturnIntensity == 0 && FoodIntensity == 0) {
            tex = textures[16];
        } else if (ReturnIntensity > FoodIntensity) {
            if (ReturnIntensity <= (int) (.25 * (1 / EVAP_RATE) + .5) && ReturnIntensity > 0) {
                tex = textures[8];
            } else if (ReturnIntensity <= (int) (.5 * (1 / EVAP_RATE) + .5) && ReturnIntensity > (int) (.25 * (1 / EVAP_RATE) + .5)) {
                tex = textures[9];
            } else if (ReturnIntensity <= (int) (.75 * (1 / EVAP_RATE) + .5) && ReturnIntensity > (int) (.5 * (1 / EVAP_RATE) + .5)) {
                tex = textures[10];
            } else if (ReturnIntensity > (int) (.75 * (1 / EVAP_RATE) + .5)) {
                tex = textures[11];
            }
        } else if (ReturnIntensity < FoodIntensity) {
            if (FoodIntensity <= (int) (.25 * (1 / EVAP_RATE) + .5) && FoodIntensity > 0) {
                tex = textures[12];
            } else if (FoodIntensity <= (int) (.5 * (1 / EVAP_RATE) + .5) && FoodIntensity > (int) (.25 * (1 / EVAP_RATE) + .5)) {
                tex = textures[13];
            } else if (FoodIntensity <= (int) (.75 * (1 / EVAP_RATE) + .5) && FoodIntensity > (int) (.5 * (1 / EVAP_RATE) + .5)) {
                tex = textures[14];
            } else if (FoodIntensity > (int) (.75 * (1 / EVAP_RATE) + .5)) {
                tex = textures[15];
            }
        }

        return tex;
    }

    public void setTexture(String name, int type) {
        textures[type] = name;
    }

    public Ant getRlayer() {
        return rlayer;
    }

    public void setRlayer(Ant rlayer) {
        this.rlayer = rlayer;
    }

    public Ant getFlayer() {
        return flayer;
    }

    public void setFlayer(Ant flayer) {
        this.flayer = flayer;
    }

    public void setReturnIntensity(double ReturnIntensity) {
        this.ReturnIntensity = ReturnIntensity;
    }

    public void setFoodIntensity(double FoodIntensity) {
        this.FoodIntensity = FoodIntensity;
    }

    public static void setEVAP_RATE(double EVAP_RATE) {
        CommonScents.EVAP_RATE = EVAP_RATE;
    }    
}
