/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 *
 * @author George McDaid
 */
public class Location {

    public static final int LOCATION_DIMENSION = 20;
       
    protected Point gridLocation;
    
    protected Rectangle pixelLocation = new Rectangle(0, 0, 
			LOCATION_DIMENSION, LOCATION_DIMENSION);
            
    public Location(){
        
    }
    
    public Location(int x, int y){
        gridLocation = new Point(x, y);
        
        pixelLocation.x = gridLocation.y * pixelLocation.width;
	pixelLocation.y = gridLocation.x * pixelLocation.height;
    }
    
    public Location(Point p){
        gridLocation = p;
        
        pixelLocation.x = gridLocation.y * pixelLocation.width;
	pixelLocation.y = gridLocation.x * pixelLocation.height;
    }
    
    public int getX() {
        return pixelLocation.x;
    }

    public int getY() {
        return pixelLocation.y;
    }
    
    public Rectangle getBoundry(){
        return pixelLocation;
    }
    
    public boolean equals(Location l){
        return (this.getX()==l.getX())&&(this.getY()==l.getY());
    }

}
