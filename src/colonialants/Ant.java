/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialants;

import colonialants.TerrainLocation.Direction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 *
 * @author George McDaid
 */
public class Ant implements Serializable{

    TerrainLocation destination = null;
    TerrainLocation origin = null;
    
    static int antCount = 0;
    
    private int id;
    
    int SIZE = 20;
    Location position;
    Direction intendedBearing = Direction.NORTH;
    private double ANT_SPEED = .08;
    boolean carryingFood = false;
    private int ANT_LIFESPAN = 2000;
    private double less_lay = .005;
    
    State state;
    Random r = new Random();
    private String[] textures;
    private double RP_LEVEL = 32;
    private double FP_LEVEL = 32;

    public enum State {
        MOVING(0), IDLE(1);
        
        private int value;
        
        private State(int value) {
            this.value = value;
        }
        
        public static State fromValue(int value){
            switch(value){
                case 0:
                    return State.MOVING;
                case 1:
                    return State.IDLE;
                default: 
                    return State.IDLE;
            }
        }
        
    }

    public Ant() {
        this.state = State.IDLE;
        this.ANT_LIFESPAN = ANT_LIFESPAN;
        id = antCount;
        antCount++;
    }

    public Ant(Point p) {
        this();
        position = new Location(p);
    }

    public Ant(Point p, TerrainLocation t, String[] tex) {
        this();
        position = new Location(p);
        origin = t;
        textures = tex;
    }
    
    public Ant(Point p, String[] tex) {
        this();
        position = new Location(p);
        textures = tex;
    }

    public Ant(Point p, int size) {
        this();
        position = new Location(p, size);
    }

    public Rectangle getLocation() {
        return position.getScreenLocation();
    }

    void onClockTick(int delta, boolean snapMovement) {
        update(delta, snapMovement);
    }

    public void update(int delta, boolean snapMovement) {
        //Ant has to stop to think
        if (!isMoving()) {
            // Ant contemplates a move
            changeDestination(delta);
                // Turns in the direction to move;
            // The turn can be made linear
            //setBearing(intendedBearing);
        }
        if (isMoving()) {
                // Ant with a purpose
            //snapMove(getScreenPosition());
            walk(delta, snapMovement);
        }
        
        if (getOrigin().getTerrain() instanceof Stream){
                lowerLifeSpan(5000);
            }

    }

    public boolean isMoving() {
        return state == State.MOVING;
    }

    public void changeDest(ArrayList<TerrainLocation> square) {
        if (!isMoving()) {
            state = State.MOVING;
        }

        for (int i = 0; i < square.size(); i++) {
            if (!(square.get(i).getTerrain() instanceof AntHill)) {
                square.remove(i);
                i = 0;
            }
        }

        int pick = r.nextInt(square.size());

        destination = square.get(pick);

        destination.toString();

    }

    public void changeDestination(int delta) {
        HashMap<Direction, TerrainLocation> list = origin.getNeighbors();
        HashMap<Integer, Direction> choices = new HashMap<Integer, Direction>();
        Iterator it = list.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            if (((TerrainLocation) pairs.getValue()).getTerrain().getTexture().equals("anthill")) {
                //System.out.println();
                choices.put(count, (Direction) pairs.getKey());
                count++;
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
        if (count > 0) {
            int choice = r.nextInt(count);
            intendedBearing = choices.get(choice);
            destination = list.get(intendedBearing);
            state = State.MOVING;
        }

    }

    public void walk(int delta, boolean snapMovement) {
        if (isMoving()) {
            //primMove();
            if (snapMovement) {
                snapMove();
            } else {
                advancedMove(delta);
            }
            if (position.equalRectangle(destination.getScreenLocation())) {
                origin = destination;
                state = State.IDLE;
            }
        }
    }

    private void snapMove() {
        Rectangle dest = destination.getScreenLocation();
        Rectangle curr = position.getScreenLocation();

        curr.x = dest.x;
        curr.y = dest.y;
    }

    public void advancedMove(int delta) {
        ANT_SPEED = .08 + Math.random() * .05;
        Rectangle dest = destination.getScreenLocation();
        Rectangle curr = position.getScreenLocation();
        int tol = (int) (Math.sqrt(dest.width * dest.width + dest.height * dest.height)) / 8;
        double deltaDistance = ANT_SPEED * (double) delta;

        double distance = Math.sqrt(Math.pow(dest.x - curr.x, 2.0)
                + Math.pow(dest.y - curr.y, 2.0));
        double factor = deltaDistance / distance;

        curr.x += (int) (factor * (dest.x - curr.x));
        curr.y += (int) (factor * (dest.y - curr.y));
        curr.x = Math.abs(curr.x - dest.x) < tol ? dest.x : curr.x;
        curr.y = Math.abs(curr.y - dest.y) < tol ? dest.y : curr.y;
    }

    public void primMove() {
        int x = destination.getX() - position.getScreenLocation().x;
        int y = destination.getY() - position.getScreenLocation().y;

        if (x != 0) {
            if (x > 0) {
                position.incX();
            } else {
                position.decX();
            }
        }

        if (y != 0) {
            if (y > 0) {
                position.incY();
            } else {
                position.decY();
            }
        }
    }

    public TerrainLocation getOrigin() {
        return this.origin;
    }
    
    public TerrainLocation getDestination() {
        return this.destination;
    }

    public String getTexture() {
        String tex = textures[intendedBearing.value];
        return tex;
    }

    public void setTexture(String name, Direction dir) {
        textures[dir.value] = name;
    }

    public Rectangle getScreenPosition() {
        return position.getScreenLocation();
    }

    public void setCarrying() {
        if (!carryingFood) {
            carryingFood = true;
            getOrigin().takeResources(1);
        }
    }

    public void stopCarrying() {
        carryingFood = false;
    }

    public boolean getCarryingStatus() {
        return this.carryingFood;
    }

    public void setLifeSpan(int lifespan) {
        this.ANT_LIFESPAN = lifespan;
    }

    public void lowerLifeSpan(int amount) {
        ANT_LIFESPAN -= amount;
        if (ANT_LIFESPAN <= 0) {
            //o("An Ant Died!!");
        }
    }

    public int getLifeSpan() {
        return ANT_LIFESPAN;
    }

    public double getRP_LEVEL() {
        return RP_LEVEL;
    }

    public void decRP_LEVEL() {
        if (RP_LEVEL > 0) {
            RP_LEVEL = RP_LEVEL * (1 - less_lay);
        }
    }

    public double getFP_LEVEL() {
        return FP_LEVEL;
    }

    public void decFP_LEVEL() {
        if (FP_LEVEL > 0) {
            FP_LEVEL = FP_LEVEL * (1 - less_lay);
        }
    }

    public void resetLevels() {
        FP_LEVEL = 32;
        RP_LEVEL = 32;
    }
    
    public int getID(){
        return id;
    }
    
    public int getBearing(){
        return intendedBearing.value;
    }
    
    public int getState(){
        return state.value;
    }
    
    public void setDestination(TerrainLocation destination) {
        this.destination = destination;
    }

    public void setOrigin(TerrainLocation origin) {
        this.origin = origin;
    }

    public void setPosition(Location position) {
        this.position = position;
    }

    public void setIntendedBearing(Direction intendedBearing) {
        this.intendedBearing = intendedBearing;
    }

    public void setCarryingFood(boolean carryingFood) {
        this.carryingFood = carryingFood;
    }

    public void setANT_LIFESPAN(int ANT_LIFESPAN) {
        this.ANT_LIFESPAN = ANT_LIFESPAN;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setRP_LEVEL(double RP_LEVEL) {
        this.RP_LEVEL = RP_LEVEL;
    }

    public void setFP_LEVEL(double FP_LEVEL) {
        this.FP_LEVEL = FP_LEVEL;
    }
    
    public void setID(int id){
        this.id = id;
        antCount--;
    }
    
    @Override
    public String toString(){
        return "ant";
    }

}
