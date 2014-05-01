/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialants;

import colonialants.Ant.State;
import colonialants.Colony.ColLoc;
import colonialants.TerrainLocation.Direction;
import static colonialants.Utility.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.eclipse.swt.graphics.Point;

/**
 *
 * @author George McDaid
 */
public class Environment {

    //Needs to be a multiple of 2 for now
    private final int spaceSize = 20;

    private int dimension = 32;
    
    private TerrainLocation[][] terrain;

    private int SPAWN_RATE = 2;
    private int FOOD_RATE = 200;
    private int LIFE_SPAN = 2000;
    
    private Colony colony;
    private int population;
    Random r = new Random();
    double PRODUCTION_RATE = .1;
    private int leavestoset = 0;
    private boolean snapMovement = false;
    private int antBalance = 100;

    
    private boolean spawnStarted = false;
    
    public static String[] gatherertex = {
        "antNorth",
        "antNorthEast",
        "antEast",
        "antSouthEast",
        "antSouth",
        "antSouthWest",
        "antWest",
        "antNorthWest",
        "pheromoneReturn1",
        "pheromoneReturn2",
        "pheromoneReturn3",
        "pheromoneReturn4",
        "pheromoneFood1",
        "pheromoneFood2",
        "pheromoneFood3",
        "pheromoneFood4",
        "pheromoneNone",
        "pheromoneDanger"
    };
    
    public static String[] buildertex = {
      "builderAntNorth",
        "builderAntNorthEast",
        "builderAntEast",
        "builderAntSouthEast",
        "builderAntSouth",
        "builderAntSouthWest",
        "builderAntWest",
        "builderAntNorthWest",
        "pheromoneReturn1",
        "pheromoneReturn2",
        "pheromoneReturn3",
        "pheromoneReturn4",
        "pheromoneFood1",
        "pheromoneFood2",
        "pheromoneFood3",
        "pheromoneFood4",
        "pheromoneNone",
        "pheromoneDanger"
    };

    public enum AntType {

        GATHERER, BUILDER
    }

    public Environment() {
        terrain = new TerrainLocation[dimension][dimension];
        colony = new Colony();
        population = 150;
        

        Date d = new Date();
        initLog("src\\" + String.valueOf(d.getTime()) + ".csv");
    }
    
    public TerrainLocation[][] getTerrain(){
        return terrain;
    }

    private void initAntHill() {
        int is = 0;
        int ib = 0;

        int js = 0;
        int jb = 0;

        if (colony.getLocation() == ColLoc.TR) {
            is = dimension - colony.getDimension();
            ib = dimension;

            js = 0;
            jb = 0 + colony.getDimension();
        } else if (colony.getLocation() == ColLoc.TL) {
            is = 0;
            ib = 0 + colony.getDimension();

            js = 0;
            jb = 0 + colony.getDimension();
        } else if (colony.getLocation() == ColLoc.BR) {
            is = dimension - colony.getDimension();
            ib = dimension;

            js = dimension - colony.getDimension();
            jb = dimension;
        } else if (colony.getLocation() == ColLoc.BL) {
            is = 0;
            ib = 0 + colony.getDimension();

            js = dimension - colony.getDimension();
            jb = dimension;
        }

        for (int i = is; i < ib; i++) {
            for (int j = js; j < jb; j++) {
                terrain[i][j].setTerrain((Terrain) new AntHill("anthill"));
            }
        }
    }

    public void initEmptyField() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                terrain[i][j] = new TerrainLocation(new Point(i, j), spaceSize);
                Random r = new Random();
                if (r.nextInt(100) < 3) {
                    terrain[i][j].setTerrain((Terrain) new Leaf("leaf"));
                    terrain[i][j].setResources(FOOD_RATE);
                    getColony().addLeafCount();
                } else if (r.nextInt(8191) == 1337) {
                    terrain[i][j].setTerrain((Terrain) new Leaf("redleaf"));
                    terrain[i][j].setResources(1000);
                    getColony().addLeafCount();
                } else {
                    terrain[i][j].setTerrain((Terrain) new Sand("sand"));
                }

                //System.out.print("("+locations[i][j].getX()+","+locations[i][j].getY()+") ");
            }
            //System.out.println();
        }

        initAntHill();
        getColony().addFood(100);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.setNeighbors(terrain[i][j]);
            }
        }

        //this.dumpNeighborList();
    }

    public void dumpNeighborList() {

        String list = "";
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                TerrainLocation[] nbrs = getNeighbors(terrain[i][j]);
                for (TerrainLocation blk : nbrs) {
                    list += (blk.toString() + " ");
                }
                list += "|\n";
            }
            list += "||\n";

        }
        System.out.println(list);
    }

    public TerrainLocation[] getNeighbors(TerrainLocation block) {

        ArrayList<TerrainLocation> squares = new ArrayList<TerrainLocation>();

        Point loc = block.getIdices();

        for (int i = -1; i <= 1; i++) {

            int x = loc.x + i;
            if (x >= 0 && x < dimension) {
                for (int j = -1; j <= 1; j++) {
                    if ((i == 0) && (j == 0)) {
                        continue;
                    }

                    int y = loc.y + j;
                    if (y >= 0 && y < dimension) {
                        squares.add(terrain[x][y]);
                    }
                }
            }
        }
        TerrainLocation[] blk = new TerrainLocation[squares.size()];
        for (int i = 0; i < squares.size(); i++) {
            blk[i] = (TerrainLocation) squares.get(i);
        }
        return blk;
    }

    public void addTestAnts() {
        ArrayList<Ant> ants = colony.getAnts();
        for (int i = 0; i < (population); i++) {
            int j = r.nextInt(100);
            if (j < 10) {
                ants.add(new GatheringAnt(new Point(0, 0), terrain[0][0], gatherertex));
            } else {
                ants.add(new Ant(new Point(0, 0), terrain[0][0], gatherertex));
            }
        }
        //ants[dimension-1] = new Ant(new Point(dimension-1, dimension-1), terrain[(dimension-1)][(dimension-1)]);
    }

    public void addAnt(AntType TYPE) {
        ArrayList<Ant> ants = colony.getAnts();
        //if(colony.getAntCount() < population){
        if (TYPE == AntType.GATHERER) {
            ants.add(new GatheringAnt(new Point(0, 0), terrain[0][0], gatherertex, LIFE_SPAN));
            colony.addAntCount(AntType.GATHERER);
        } else if (TYPE == AntType.BUILDER) {
            ants.add(new BuilderAnt(new Point(0, 0), terrain[0][0], buildertex, LIFE_SPAN));
            colony.addAntCount(AntType.BUILDER);
        } else {
            ants.add(new Ant(new Point(0, 0), terrain[0][0], gatherertex, LIFE_SPAN));
        }
        //}

    }

    public ArrayList<Ant> getTestAnts() {
        return colony.getAnts();
    }

    public void initScents() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                terrain[i][j].setScent(new CommonScents(gatherertex));
                //scents.add(new CommonScents(terrain[i][j],tex));
            }
        }
    }

    public TerrainLocation[][] getLocations() {
        return terrain;
    }
    
    public void setSquare(TerrainLocation location, int x, int y){
        terrain[x][y] = location;
    }
    
    public void setNeighbors(TerrainLocation block) {

        Point coords = block.getIdices();

        for (int xoff = -1; xoff <= 1; xoff++) {

            int x = coords.x + xoff;

            if (x >= 0 && x < dimension) {

                for (int yoff = -1; yoff <= 1; yoff++) {

                    if ((xoff == 0) && (yoff == 0)) // Don't count current block
                    {
                        continue;
                    }

                    int y = coords.y + yoff;
                    if (y >= 0 && y < dimension) {

                        Direction dir = null;
                        switch (xoff) {
                            case -1:
                                if (yoff == -1) {
                                    dir = Direction.NORTWEST;
                                }
                                if (yoff == 0) {
                                    dir = Direction.NORTH;
                                }
                                if (yoff == 1) {
                                    dir = Direction.NORTHEAST;
                                }
                                break;
                            case 1:
                                if (yoff == -1) {
                                    dir = Direction.SOUTHWEST;
                                }
                                if (yoff == 0) {
                                    dir = Direction.SOUTH;
                                }
                                if (yoff == 1) {
                                    dir = Direction.SOUTHEAST;
                                }
                                break;
                            default:
                                if (yoff == -1) {
                                    dir = Direction.WEST;
                                }
                                if (yoff == 1) {
                                    dir = Direction.EAST;
                                }
                        }
                        block.addNeighbor(terrain[x][y], dir);
                    }
                }
            }
        }
    }
            
    public ArrayList<Location> getSquare(Point p) {
        ArrayList<Location> square = new ArrayList<Location>();

        int ox = p.x;
        int oy = p.y;

        for (int i = -1; i <= 1; i++) {
            int x = (int) ((ox - spaceSize / 2) / spaceSize);
            x = x + i;
            if (x >= 0 && x < dimension) {
                for (int j = -1; j <= 1; j++) {
                    int y = (int) ((oy - spaceSize / 2) / spaceSize);
                    y = y + j;
                    if (y >= 0 && y < dimension) {
                        if ((i == 0) && (j == 0)) {
                            continue;
                        }
                        square.add(terrain[x][y]);
                    }
                }
            }
        }

        return square;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(terrain[j][i].toString() + " ");
            }
            System.out.println();
        }
        return s;
    }

    public int getSpaceSize() {
        return spaceSize;
    }

    public Colony getColony() {
        return colony;
    }

    public void snapMovementOn(int delta) {
        snapMovement = true;
        //O(delta);
        //double evap = .2186*Math.pow((1.0/delta),-.759);
        //CommonScents.setEVAP((int) (evap+1.5));
        //o("D: " + delta + " E: " + evap);
        //3 * delta/55;
        //CommonScents.setEVAP(3 * delta/55);
    }

    public void snapMovementOff(int delta) {
        snapMovement = false;
        //double evap = ((double)ants.size()/((double)delta+1))+1;
        //CommonScents.setEVAP((int)evap);
        //o("D: " + delta + " E: " + evap);
        //CommonScents.setEVAP( 2 * delta/17);
    }

    public boolean getSnapMovement() {
        return snapMovement;
    }

    public void createNewLeaves(TerrainLocation block) {
        if (block.getResources() < 1 && block.getTerrain() instanceof Leaf) {
            block.setTerrain((Terrain) new Sand("sand"));
            getColony().lowerLeafCount();
            leavestoset++;
        }
        if (leavestoset + (8 - (int)(FOOD_RATE/25)) > 0) {
            int i = r.nextInt(500000);
            if (block.getTerrain() instanceof Sand && i == 1) {
                block.setTerrain((Terrain) new Leaf("leaf"));
                block.setResources(100);
                getColony().addLeafCount();
                leavestoset--;
            }
        }
    }

    int curr_delta = 0;

    public void onClockTick(int delta) {
        ArrayList<Ant> ants = colony.getAnts();
        if (getColony().getFoodCount() >= 2) {
            int i = 0;
            if (snapMovement) {
                i = r.nextInt(30);
            } else {
                i = r.nextInt(100);
            }
            if (i < SPAWN_RATE) {
                i = r.nextInt(100)+1;
                if (i <= antBalance) {
                    this.addAnt(AntType.GATHERER);
                    getColony().removeFood(2);
                } else{                       //Killed all the builders
                    this.addAnt(AntType.BUILDER);
                    getColony().removeFood(2);
                }
            }
        }
        if(ants.isEmpty()){
            if(spawnStarted){
                o("LOST");
            }
        }else{
            for (int j = 0; j < ants.size(); j++) {
                if (ants.get(j).getLifeSpan() <= 0) {

                    if(ants.get(j) instanceof GatheringAnt){
                        ants.remove(j);
                        if(ants.isEmpty()){
                            return;
                        }
                        getColony().lowerAntCount(AntType.GATHERER);
                    }else if(ants.get(j) instanceof BuilderAnt){
                        ants.remove(j);
                        if(ants.isEmpty()){
                            return;
                        }
                        getColony().lowerAntCount(AntType.BUILDER);
                    }


                }
                ants.get(j).onClockTick(delta, snapMovement);

                if (ants.get(j) instanceof GatheringAnt) {
                    if (ants.get(j).getOrigin().getTerrain() instanceof AntHill) {
                        ants.get(j).setFear(false);
                        if (ants.get(j).carryingFood) {
                            ants.get(j).stopCarrying();
                            ants.get(j).resetLevels();

                            colony.addFood(1);
                            colony.addScore(1);
                        } 
                    } else if (ants.get(j).getOrigin().getTerrain() instanceof Stream){
                        ants.get(j).lowerLifeSpan(5000);
                    } else if (ants.get(j).getOrigin().getTerrain() instanceof Leaf) {
                        ants.get(j).setCarrying();
                    }



                    if (ants.get(j).state == State.IDLE) {

                        if (ants.get(j).carryingFood) {
                            ants.get(j).getOrigin().getScent().alterScent("food", ants.get(j).getFP_LEVEL(), ants.get(j));
                            ants.get(j).decFP_LEVEL();
                            //if(j==0) o(ants.get(j).getFP_LEVEL());
                        } else {
                            ants.get(j).getOrigin().getScent().alterScent("return", ants.get(j).getRP_LEVEL(), ants.get(j));
                            ants.get(j).decRP_LEVEL();
                            //if(j==0) o(ants.get(j).getRP_LEVEL());

                        }
                         if (ants.get(j).getFear() == true){
                            ants.get(j).getOrigin().getScent().alterScent("danger", ants.get(j).getDP_LEVEL(), ants.get(j));
                            ants.get(j).decDP_LEVEL();
                            ants.get(j).stopCarrying();
                        }
                    }
                } else if (ants.get(j) instanceof BuilderAnt){
                if (ants.get(j).getOrigin().getTerrain() instanceof Stream){
                    ants.get(j).stopCarryingDirt();
                } else if (ants.get(j).getOrigin().getTerrain() instanceof AntHill){
                    ants.get(j).pickUpDirt();
                } else if (ants.get(j).getOrigin().getTerrain() instanceof Sand){
                    ants.get(j).pickUpDirt();
                }
            }
            }
        }
        //curr_delta += 1;
        
        for (TerrainLocation[] terrainrow : terrain) {
            for (TerrainLocation block : terrainrow) {
                block.onClockTick(delta);
                createNewLeaves(block);
                removeWater(block);
            }
        }

    }
    
    public void setSpawnRate(int rate){
        SPAWN_RATE = rate;
    }
    
    public void setFoodRate(int rate){
        FOOD_RATE = rate;
    }
    
    public void setLifeSpan(int rate){
        LIFE_SPAN = rate;
    }
    
    public void setAntBalance(int balance){
        this.antBalance = balance;
    }
    
    public int getDimension(){
        return dimension;
    }
    
    public void reset() {
        blowWind();
        for (TerrainLocation[] terrainrow : terrain) {
            for (TerrainLocation location : terrainrow) {
                if(location.getTerrain().getTexture().equals("stream")){
                    if (location.getX() < colony.getDimension() * 20 && location.getY() < colony.getDimension() * 20 ){
                        location.setTerrain((Terrain) new Sand("anthill"));
                    } else {
                        location.setTerrain((Terrain) new Sand("sand"));
                    }
                }
            }
        }
        colony.resetColony();
    }

    public void blowWind() {
        for (TerrainLocation[] locationrow : getLocations()) {
            for (TerrainLocation location : locationrow) {
                location.getScent().resetFoodIntensity();
                location.getScent().resetReturnIntensity();
            }
        }
    }
    
    public boolean isSpawnStarted() {
        return spawnStarted;
    }

    public void setSpawnStarted(boolean spawnStarted) {
        this.spawnStarted = spawnStarted;
    }
    
    public void removeWater(TerrainLocation block) {
        if (block.getResources() < 1 && block.getTerrain() instanceof Stream) {
            if (block.getX() < colony.getDimension() * 20 && block.getY() < colony.getDimension() * 20 ){
                block.setTerrain((Terrain) new Sand("anthill"));
            } else {
                block.setTerrain((Terrain) new Sand("sand"));
            }
        }
    }
    
    public TerrainLocation createStream(TerrainLocation startBlock, TerrainLocation endBlock){
        HashMap<TerrainLocation.Direction,TerrainLocation> list = startBlock.getNeighbors();
        HashMap<Integer, TerrainLocation.Direction> choices = new HashMap<Integer, TerrainLocation.Direction>();
        HashMap<Integer, Double> probs = new HashMap<Integer, Double>();
        Iterator it = list.entrySet().iterator();
        
        startBlock.setTerrain((Terrain) new Stream("stream"));
        startBlock.setResources(25);
        endBlock.setTerrain((Terrain) new Stream("stream"));
        endBlock.setResources(25);
        
        int count = 0;
        int bestmove = 100;
        
        int startX = startBlock.getX();
        int startY = startBlock.getY();
        //System.out.println("Start x: " + startX + ", Start y: " + startY);
        
        int endX = endBlock.getX();
        int endY = endBlock.getY();
        //System.out.println("End x: " + endX + ", End y: " + endY);
        
        while (it.hasNext() && bestmove == 100) {
            Map.Entry pairs = (Map.Entry) it.next();
            
            int neighborX = ((TerrainLocation) pairs.getValue()).getX();
            int neighborY = ((TerrainLocation) pairs.getValue()).getY();
            //System.out.println("Neighbor x: " + neighborX + ", Neighbor y: " + neighborY);            
           // if(((TerrainLocation)))
            if (neighborY == endY && neighborX == endX){
                choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                probs.put(count, 100.0);
                bestmove = count;
                count++;    
            } else if (neighborY <= startY){
                //opposite Y direction
                if (endY > startY){
                    if (neighborX > startX){
                        if(endX >= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 55.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        } else if (endX < startX){
                            //BAD MOVE
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 15.0);
                            //((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            count++;
                        }
                    } else if (neighborX < startX){
                        if(endX > startX){
                            //BAD MOVE
                        } else if (endX <= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 55.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        }            
                    }
                //matching Y direction    
                } else if (endY <= startY){
                    if (neighborX > startX){
                        if(endX >= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 85.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        } else if (endX < startX){
                            //BAD MOVE
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 45.0);
                            //((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            count++;
                        }
                    } else if (neighborX < startX){
                        if(endX > startX){
                            //BAD MOVE
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 45.0);
                            //((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            count++;
                        } else if (endX <= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 85.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        }            
                    }        
                }
            }else if (neighborY > startY){
                //matching Y direction
                if (endY >= startY){
                    if (neighborX > startX){
                        if(endX >= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 85.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        } else if (endX < startX){
                            //BAD MOVE
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 45.0);
                            //((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            count++;
                        }
                    } else if (neighborX < startX){
                        if(endX > startX){
                            //BAD MOVE
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 45.0);
                            //((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            count++;
                        } else if (endX <= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 85.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        }            
                    }
                //opposite Y direction    
                } else if (endY < startY){
                    if (neighborX > startX){
                        if(endX >= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 55.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        } else if (endX < startX){
                            //BAD MOVE
                        }
                    } else if (neighborX < startX){
                        if(endX > startX){
                            //BAD MOVE
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 15.0);
                            //((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            count++;
                        } else if (endX <= startX){
                            //matching X direction
                            choices.put(count, (TerrainLocation.Direction) pairs.getKey());
                            probs.put(count, 55.0);
                            ((TerrainLocation)pairs.getValue()).setTerrain((Terrain) new Stream("stream"));
                            ((TerrainLocation)pairs.getValue()).setResources(25);
                            count++;
                        }            
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
            //System.out.println("Choice: " + choice);
            Direction intendedBearing = choices.get(choice);
            TerrainLocation destination = list.get(intendedBearing);
            destination.setTerrain((Terrain) new Stream("stream"));
            destination.setResources(25);
            return destination;
        }
        TerrainLocation destination = new TerrainLocation();
        //System.out.println("Count: " + count);
        return destination;
    }
    
}
