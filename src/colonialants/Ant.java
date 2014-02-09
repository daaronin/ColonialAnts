
package colonialants;

/**
 *
 * @author George McDaid
 */
public class Ant {
    
    Location location;
    
    public Ant(){
        location = new Location();
    }
    
    public Ant(int x, int y){
        location = new Location(x,y);
    }

}
