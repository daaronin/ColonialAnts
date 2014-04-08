/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import colonialants.Ant.State;
import colonialants.Colony.ColLoc;
import static colonialants.Debug.*;
import colonialants.TerrainLocation.Direction;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    //Normally this will be a swarm holding all ants, sadly we must wait for Krish Fish, unless I get impatient
    //Envionment has a Swarm (I think this has become a colony. Eventually ants can go in there)
    private ArrayList<Ant> ants;
    private Colony colony;
    private int population;
    Random r = new Random();
    double PRODUCTION_RATE = .1;
    private int leavestoset = 0;
    private boolean snapMovement = false;
    
    private String[] tex = { 
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
                            "pheromoneNone"
                          };
    
    public enum AntType{
        GATHERER, BUILDER
    }
    
    public Environment(){
        terrain = new TerrainLocation[dimension][dimension];
        colony = new Colony();
        population = 150;
        ants = new ArrayList<Ant>();
        
        Date d = new Date();
        initLog("src\\" + String.valueOf(d.getTime()) + ".csv");
    }
    
    private void initAntHill(){
        int is = 0;
        int ib = 0;
        
        int js = 0;
        int jb = 0;
        
        if(colony.getLocation() == ColLoc.TR){
            is = dimension-colony.getDimension();
            ib = dimension;
            
            js = 0;
            jb = 0+colony.getDimension();
        }else if(colony.getLocation() == ColLoc.TL){
            is = 0;
            ib = 0+colony.getDimension();
            
            js = 0;
            jb = 0+colony.getDimension();
        }else if(colony.getLocation() == ColLoc.BR){
            is = dimension-colony.getDimension();
            ib = dimension;
            
            js = dimension-colony.getDimension();
            jb = dimension;
        }else if(colony.getLocation() == ColLoc.BL){
            is = 0;
            ib = 0+colony.getDimension();
            
            js = dimension-colony.getDimension();
            jb = dimension;
        }
        
        for(int i = is;i<ib;i++){
            for(int j = js;j<jb;j++){
                terrain[i][j].setTerrain((Terrain) new AntHill("anthill"));
            }
        }
    }
    
    public void initEmptyField(){
        for(int i = 0;i < dimension;i++){
            for(int j = 0;j < dimension;j++){
                
                terrain[i][j] = new TerrainLocation(new Point(i,j), spaceSize);
                Random r = new Random();
                if(r.nextInt(100)<3){
                    terrain[i][j].setTerrain((Terrain) new Leaf("leaf"));
                    terrain[i][j].setResources(200);
                    getColony().addLeafCount();
                }else if(r.nextInt(8191)==1337){
                    terrain[i][j].setTerrain((Terrain) new Leaf("redleaf"));
                    terrain[i][j].setResources(1000);
                    getColony().addLeafCount();
                }else{
                    terrain[i][j].setTerrain((Terrain) new Sand("sand"));
                }
                
                //System.out.print("("+locations[i][j].getX()+","+locations[i][j].getY()+") ");
            }
            //System.out.println();
        }
        
        initAntHill();
        getColony().addFood(100);
        for(int i = 0;i < dimension;i++){
            for(int j = 0;j < dimension;j++){
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
					if ((i == 0) && (j == 0))
						continue;

					int y = loc.y + j;
					if (y >= 0 && y < dimension)
						squares.add(terrain[x][y]);
				}
			}
		}
		TerrainLocation[] blk = new TerrainLocation[squares.size()];
		for (int i = 0; i < squares.size(); i++) {
			blk[i] = (TerrainLocation) squares.get(i);
		}
		return blk;
	}
    
    public void addTestAnts(){
        
        for(int i = 0;i<(population);i++){
            int j = r.nextInt(100);
            if(j<10){
                ants.add(new GatheringAnt(new Point(5,5), terrain[5][5], tex));
            }else{
                ants.add(new Ant(new Point(5,5), terrain[5][5], tex));
            }
            
        }
        //ants[dimension-1] = new Ant(new Point(dimension-1, dimension-1), terrain[(dimension-1)][(dimension-1)]);
    }
    
    public void addAnt(AntType TYPE){
        //if(colony.getAntCount() < population){
            if(TYPE == AntType.GATHERER){
            ants.add(new GatheringAnt(new Point(5,5), terrain[5][5], tex));
            }else if(TYPE == AntType.BUILDER){
                ants.add(new BuilderAnt(new Point(5,5), terrain[5][5], tex));
            }else {
                ants.add(new Ant(new Point(5,5), terrain[5][5], tex));
            }
        //}
        
    }
    
    public ArrayList<Ant> getTestAnts(){
        return ants;
    }
    
    public void initScents(){
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                terrain[i][j].setScent(new CommonScents(tex));
                //scents.add(new CommonScents(terrain[i][j],tex));
            }
        }
    }
    
    public TerrainLocation[][] getLocations(){
        return terrain;
    }
    
    public void setNeighbors(TerrainLocation block) {

		Point coords = block.getIdices();

		for (int xoff = -1; xoff <= 1; xoff++) {

			int x = coords.x + xoff;

			if (x >= 0 && x < dimension) {

				for (int yoff = -1; yoff <= 1; yoff++) {

					if ((xoff == 0) && (yoff == 0))
						// Don't count current block
						continue;

					int y = coords.y + yoff;
					if (y >= 0 && y < dimension) {

						Direction dir = null;
						switch (xoff) {
						case -1:
							if (yoff == -1)
								dir = Direction.NORTWEST;
							if (yoff == 0)
								dir = Direction.NORTH;
							if (yoff == 1)
								dir = Direction.NORTHEAST;
							break;
						case 1:
							if (yoff == -1)
								dir = Direction.SOUTHWEST;
							if (yoff == 0)
								dir = Direction.SOUTH;
							if (yoff == 1)
								dir = Direction.SOUTHEAST;
							break;
						default:
							if (yoff == -1)
								dir = Direction.WEST;
							if (yoff == 1)
								dir = Direction.EAST;
						}
						block.addNeighbor(terrain[x][y], dir);
					}
				}
			}
		}
	}
    
    public ArrayList<Location> getSquare(Point p){
        ArrayList<Location> square  = new ArrayList<Location>();
        
        int ox = p.x;
        int oy = p.y;

        for(int i = -1;i<=1;i++){
            int x = (int)((ox-spaceSize/2)/spaceSize);
            x = x+i;
            if(x>=0&&x<dimension){
                for(int j = -1;j<=1;j++){
                    int y = (int)((oy-spaceSize/2)/spaceSize);
                    y = y+j;
                    if(y>=0&&y<dimension){
                        if((i==0)&&(j==0)){
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
    public String toString(){
        String s = "";
        for(int i = 0;i < dimension;i++){
            for(int j = 0;j < dimension;j++){
                System.out.print(terrain[j][i].toString() + " ");
            }
            System.out.println();
        }
        return s;
    }
    
    public int getSpaceSize(){
        return spaceSize;
    }
    
    public Colony getColony(){
        return colony;
    }
    
    public void snapMovementOn(int delta){
        snapMovement = true;
        //O(delta);
        //double evap = .2186*Math.pow((1.0/delta),-.759);
        //CommonScents.setEVAP((int) (evap+1.5));
        //o("D: " + delta + " E: " + evap);
        //3 * delta/55;
        //CommonScents.setEVAP(3 * delta/55);
    }
    
    public void snapMovementOff(int delta){
        snapMovement = false;
        //double evap = ((double)ants.size()/((double)delta+1))+1;
        //CommonScents.setEVAP((int)evap);
        //o("D: " + delta + " E: " + evap);
        //CommonScents.setEVAP( 2 * delta/17);
    }
    
    public boolean getSnapMovement(){
        return snapMovement;
    }
    
    public void createNewLeaves(TerrainLocation block){
        if (block.getResources() < 1 && block.getTerrain() instanceof Leaf){
            block.setTerrain((Terrain) new Sand("sand"));
            getColony().lowerLeafCount();
            leavestoset++;
        }
        if(leavestoset > 0){
            int i = r.nextInt(500000);
            if (block.getTerrain() instanceof Sand && i == 1){
                block.setTerrain((Terrain) new Leaf("leaf"));
                block.setResources(10);
                getColony().addLeafCount();
                leavestoset--;
            }
        }
    }

    int curr_delta = 0;
    
    public void onClockTick(int delta) {
        if(getColony().getFoodCount() >= 2){
            int i = 0;
            if (snapMovement){
                i = r.nextInt(30);
            }else{
                i = r.nextInt(100);
            }
            if(i<2){
                i = r.nextInt(3);
                if(i<3){
                    this.addAnt(AntType.GATHERER);
                    colony.addAntCount();
                    getColony().removeFood(2);
                }else if(i==133){                       //Killed all the builders
                    this.addAnt(AntType.BUILDER);
                }
            }
        }

        for (int j = 0; j<ants.size(); j++) {
            if(ants.get(j).getLifeSpan() <= 0){
                //ants.remove(j);
                //getColony().lowerAntCount();
            }
            ants.get(j).onClockTick(delta, snapMovement);
            
            if(ants.get(j).getOrigin().getTerrain() instanceof Leaf){
                ants.get(j).setCarrying();
            }
            
            if(ants.get(j) instanceof GatheringAnt){
                if(ants.get(j).getOrigin().getTerrain() instanceof AntHill){
                    if(ants.get(j).carryingFood){
                        ants.get(j).stopCarrying();
                        ants.get(j).resetLevels();
                        
                        colony.addFood(1);
                        colony.addScore(1);
                    }
                }
            
                if(ants.get(j).state == State.IDLE){
                    if (ants.get(j).carryingFood){
                        ants.get(j).getOrigin().getScent().alterScent("food", ants.get(j).getFP_LEVEL(), ants.get(j));
                        ants.get(j).decFP_LEVEL();
                        //if(j==0) o(ants.get(j).getFP_LEVEL());
                    } else {
                        ants.get(j).getOrigin().getScent().alterScent("return", ants.get(j).getRP_LEVEL(), ants.get(j));
                        ants.get(j).decRP_LEVEL();
                        //if(j==0) o(ants.get(j).getRP_LEVEL());

                    }
                }
            }
        }
        
        
        
        curr_delta += 1;
        //o("Log Delta: " + delta);
        //o("Curr Delta: " + curr_delta);
        //o();
        if(curr_delta >= delta*2){
            
            curr_delta = 0;
            logCycle(delta, getColony().getFoodCount(), getColony().getAntCount(), getColony().getLeafCount(), getColony().getScore(), 2, 0);
        }
        
        for (TerrainLocation[] terrainrow : terrain) {
            for (TerrainLocation block : terrainrow) {
                block.onClockTick(delta);
                createNewLeaves(block);
            }
        }
        
    }
}
