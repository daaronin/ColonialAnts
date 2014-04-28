/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.io.Serializable;

/**
 *
 * @author George McDaid
 */
public class Stream implements Terrain, Serializable{
    
    String texture;
    
    @Override
    public String toString(){
        String s = "S";
        
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
