/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import colonialants.Colony.ColLoc;
import colonialants.TerrainLocation.Direction;
import java.util.ArrayList;
import java.util.Random;
import org.eclipse.swt.graphics.Point;

/**
 *
 * @author George McDaid
 */
public class Environment {
    
    //Needs to be a multiple of 2 for now
    private final int spaceSize = 20;
    
    private int dimension = 29;
    
    private TerrainLocation[][] terrain;
    
    //Normally this will be a swarm holding all ants, sadly we must wait for Krish Fish, unless I get impatient
    //Envionment has a Swarm (I think this has become a colony. Eventually ants can go in there)
    private ArrayList<Ant> ants;
    private CommonScents scent;
    private Colony colony;
    private int population;
    Random r = new Random();
    
    public Environment(){
        terrain = new TerrainLocation[dimension][dimension];
        colony = new Colony();
        population = 50;
    }
    
    public Environment(int dimension){
        this.dimension = dimension;
        terrain = new TerrainLocation[dimension][dimension];
        colony = new Colony();
        population = 0;
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
                if(r.nextInt(100)<5){
                    terrain[i][j].setTerrain((Terrain) new Leaf("leaf"));
                }else{
                    terrain[i][j].setTerrain((Terrain) new Sand("sand"));
                }
                
                //System.out.print("("+locations[i][j].getX()+","+locations[i][j].getY()+") ");
            }
            //System.out.println();
        }
        
        initAntHill();
        
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
        String[] tex = { 
                            "antNorth", 
                            "antNorthEast", 
                            "antEast", 
                            "antSouthEast",
                            "antSouth", 
                            "antSouthWest", 
                            "antWest", 
                            "antNorthWest" 
                          };
        
        ants = new ArrayList<Ant>();
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
    
    public ArrayList<Ant> getTestAnts(){
        return ants;
    }
    
    public void addTestScent(){
        //scent = new CommonScents(new GridLocation(terrain[5][5].getX(),terrain[5][5].getY()));
    }
    
    public CommonScents getTestScent(){
        return scent;
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

    public void onClockTick(int delta) {
        for (Ant ant : ants) {
            
            ant.onClockTick(delta);
        }
    }

}
