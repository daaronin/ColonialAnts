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
    public enum ColLoc{
        TR, TL, BR, BL
    }
    
    private final ColLoc location;
    private final int dimension;
    
    public Colony(){
        location = ColLoc.TL;
        dimension = 15;
    }
    
    public ColLoc getLocation(){
        return location;
    }
    
    public int getDimension(){
        return dimension;
    }
}
