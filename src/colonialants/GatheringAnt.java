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
public class GatheringAnt extends Ant {

    public GatheringAnt(Point p, TerrainLocation t, String[] tex) {
        super(p, t, tex);
    }

    public GatheringAnt(Point p, TerrainLocation t, String[] tex, int life) {
        super(p, t, tex, life);
    }
    
    public GatheringAnt(Point p, String[] tex) {
        super(p, tex);
    }
    
    @Override
    public void changeDestination(int delta) {
        HashMap<TerrainLocation.Direction, TerrainLocation> list = origin.getNeighbors();
        HashMap<Integer, TerrainLocation.Direction> choices = new HashMap<Integer, TerrainLocation.Direction>();
        HashMap<Integer, Double> probs = new HashMap<Integer, Double>();
        Iterator it = list.entrySet().iterator();
        int count = 0;
        int bestmove = 100;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            
            if (!(((TerrainLocation) pairs.getValue()).getTerrain() instanceof Stream)){
                //stronger food pheromone
                if (!carryingFood) {
                    if (((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() > 0) {
                        if (((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() > getOrigin().getScent().getFoodIntensity()) {
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 75.0);
                            count++;
                        } else {
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 55.0);
                            count++;
                        }
                    } else if (((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() > 0 && ((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() == 0) {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 10.0);
                        count++;
                    } else if (((TerrainLocation) pairs.getValue()).getTerrain() instanceof Leaf) {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 100.0);
                        bestmove = count;
                        count++;
                    } else {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 35.0);
                        count++;
                    }
                } else if (carryingFood) {
                    if (((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() > 0) {
                        if (((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() > getOrigin().getScent().getReturnIntensity()) {
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 75.0);
                            count++;
                        } else {
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 55.0);
                            count++;
                        }
                    } else if (((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() > 0 && ((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() == 0) {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 10.0);
                        count++;
                    } else if (((TerrainLocation) pairs.getValue()).getTerrain() instanceof AntHill) {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 100.0);
                        bestmove = count;
                        count++;
                    } else {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 35.0);
                        count++;
                    }
                }
            } else if ((((TerrainLocation) pairs.getValue()).getTerrain() instanceof Stream)) {
                //ALERT COLONY OF WATER
                addFear();
            } else {
                
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

    //@Override
    public void changeDestinationWorking(int delta) {
        HashMap<TerrainLocation.Direction, TerrainLocation> list = origin.getNeighbors();
        HashMap<Integer, TerrainLocation.Direction> choices = new HashMap<Integer, TerrainLocation.Direction>();
        HashMap<Integer, Double> probs = new HashMap<Integer, Double>();
        Iterator it = list.entrySet().iterator();
        int count = 0;
        int bestmove = 100;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();

            //stronger food pheromone
            if (!carryingFood) {
                if (((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() > 0) {
                    if (((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() < getOrigin().getScent().getFoodIntensity()) {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 65.0);
                        count++;
                    } else {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 55.0);
                        count++;
                    }
                } else if (((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() > 0 && ((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() == 0) {
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 10.0);
                    count++;
                } else if (((TerrainLocation) pairs.getValue()).getTerrain() instanceof Leaf) {
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 100.0);
                    bestmove = count;
                    count++;
                } else {
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 25.0);
                    count++;
                }
            } else if (carryingFood) {
                if (((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() > 0) {
                    if (((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() > getOrigin().getScent().getReturnIntensity()) {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 65.0);
                        count++;
                    } else {
                        choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                        probs.put(count, 55.0);
                        count++;
                    }
                } else if (((TerrainLocation) pairs.getValue()).getScent().getFoodIntensity() > 0 && ((TerrainLocation) pairs.getValue()).getScent().getReturnIntensity() == 0) {
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 10.0);
                    count++;
                } else if (((TerrainLocation) pairs.getValue()).getTerrain() instanceof AntHill) {
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 100.0);
                    bestmove = count;
                    count++;
                } else {
                    choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                    probs.put(count, 25.0);
                    count++;
                }
            }
            //it.remove(); // avoids a ConcurrentModificationException
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
        return "gatherer";
    }
}
