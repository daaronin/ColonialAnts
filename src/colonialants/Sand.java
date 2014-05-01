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
public class Sand implements Terrain, Serializable{
    
    String texture;
    
    public Sand(String texture){
        this.texture = texture;
    }
    
    @Override
    public String toString(){
        return "sand";
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
