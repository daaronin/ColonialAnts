/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import org.eclipse.swt.graphics.Point;

/**
 *
 * @author George McDaid
 */
public class Location {

    protected Point p;
            
    public Location(){
        
    }
    
    public Location(int x, int y){
        p = new Point(x, y);
    }
    
    public int getX() {
        return p.x;
    }

    public int getY() {
        return p.y;
    }
    
    public Point getPoint(){
        return p;
    }
    
    public boolean equals(Location l){
        return (this.getX()==l.getX())&&(this.getY()==l.getY());
    }

}
