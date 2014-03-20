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
        Iterator it = list.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            //if(((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("anthill")){
                //System.out.println();
                choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                count++;
            //}
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
