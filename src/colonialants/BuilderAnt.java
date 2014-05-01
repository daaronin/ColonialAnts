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
public class BuilderAnt extends Ant{
    
    public BuilderAnt(Point p, TerrainLocation t, String[] tex){
        super(p, t, tex);            
    }

    public BuilderAnt(Point p, TerrainLocation t, String[] tex, int life){
        super(p, t, tex, life);            
    }
    
    public BuilderAnt(Point p, String[] tex){
        super(p, tex);            
    }
    
    @Override
    public void changeDestination(int delta){
        HashMap<TerrainLocation.Direction,TerrainLocation> list = origin.getNeighbors();
        HashMap<Integer, TerrainLocation.Direction> choices = new HashMap<Integer, TerrainLocation.Direction>();
        HashMap<Integer, Double> probs = new HashMap<Integer, Double>();
        Iterator it = list.entrySet().iterator();
        int count = 0;
        int bestmove = 100;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            if (!carryingDirt){
                if(((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("anthill")){
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 85.0);
                    bestmove = count;
                    count++;
                } else if(((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("stream")){
                    //BAD MOVE
                } else if (((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("sand")){
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 75.0);
                    count++;                    
                } else if (((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("leaf")){
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 55.0);
                    count++;                    
                }
            } else {
                if(((TerrainLocation)pairs.getValue()).getScent().getDangerIntensity() > 0){
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 100.0);
                    count++;
                } else {
                    if(((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("anthill")){
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 25.0);
                        count++;
                    } else if(((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("stream")){
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 85.0);
                        bestmove = count;
                        count++;
                    } else if (((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("sand")){
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 75.0);
                        count++;                    
                    } else if (((TerrainLocation)pairs.getValue()).getTerrain().getTexture().equals("leaf")){
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 55.0);
                        count++;                    
                    }
                }
            }
        }
        if (count > 0) {
            int choice = 0;
            double bestdiff = 100;
            for (int i = 0; i < probs.size(); i++) {
                double prob = probs.get(i);
                double diff = 100 - r.nextInt((int) prob);
                /*if (bestdiff == 100) {
                 choice = i;
                 bestdiff = diff;
                 //System.out.println(bestdiff);
                 }*/
                if (bestdiff > diff) {
                    choice = i;
                    bestdiff = diff;
                }
            }
            if (bestmove != 100) {
                choice = bestmove;
            }

            intendedBearing = choices.get(choice);
            destination = list.get(intendedBearing);
            state = State.MOVING;
            lowerLifeSpan(1);
        }

    }
    
    @Override
    public String toString(){
        return "builder";
    }
}
