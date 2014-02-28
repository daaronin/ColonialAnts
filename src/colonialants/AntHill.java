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
public class AntHill implements Terrain{

    String texture;
    
    @Override
    public String toString(){
        return "AH";
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
