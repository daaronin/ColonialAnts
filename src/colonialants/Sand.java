/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

/**
 *
 * @author George McDaid
 */
public class Sand implements Terrain{
    
    String texture;
    
    public Sand(String texture){
        this.texture = texture;
    }
    
    @Override
    public String toString(){
        String s = "O";
        
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
