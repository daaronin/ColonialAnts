/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author Dan
 */
public class Leaf implements Terrain {
    
    String texture;
    
    public Leaf(String texture){
        this.texture = texture;
    }
    
    @Override
    public String toString(){
        String s = "L";
        
        return s;
    }
    
    @Override
    public String getTexture() {
        return texture;
    }

    @Override
    public void setTexture(String s) {
        texture = s;
    }
}
