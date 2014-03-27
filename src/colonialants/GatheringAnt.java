/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.swt.graphics.Point;

/**
 *
 * @author George McDaid
 */
public class GatheringAnt extends Ant{

    public GatheringAnt(Point p, TerrainLocation t, String[] tex){
        super(p, t, tex);            
    }
    
    @Override
    public void changeDestination(){
        HashMap<TerrainLocation.Direction,TerrainLocation> list = origin.getNeighbors();
        HashMap<Integer, TerrainLocation.Direction> choices = new HashMap<Integer, TerrainLocation.Direction>();
        HashMap<Integer, Double> probs = new HashMap<Integer, Double>();
        Iterator it = list.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            
            //food pheromone and carrying food
            if(((TerrainLocation)pairs.getValue()).getScent().getFoodIntensity() > 0 && carryingFood){
                choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                probs.put(count, 10.0);
                count++;
            //food pheromone and carrying food
            } else if(((TerrainLocation)pairs.getValue()).getScent().getFoodIntensity() > 0 && !carryingFood){
                choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                probs.put(count, 100.0);
                count++;
            //return pheromone and carrying food
            } else if(((TerrainLocation)pairs.getValue()).getScent().getReturnIntensity() > 0 && carryingFood){
                choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                probs.put(count, 100.0);
                count++;
            //return pheromone and not carrying food
            } else if(((TerrainLocation)pairs.getValue()).getScent().getReturnIntensity() > 0 && !carryingFood){
                choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                probs.put(count, 10.0);
                count++;
            //food pheromone and not carrying food
            } else if(((TerrainLocation)pairs.getValue()).getScent().getReturnIntensity() == 0 
                    && ((TerrainLocation)pairs.getValue()).getScent().getFoodIntensity() == 0 && !carryingFood){
                choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                probs.put(count, 20.0);
                count++;
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
        if(count > 0){
            int choice = r.nextInt(count);
            intendedBearing = choices.get(choice);
            destination = list.get(intendedBearing);
            state = State.MOVING;
        }

    }
    
}
