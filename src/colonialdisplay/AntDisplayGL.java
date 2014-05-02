/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialdisplay;

import colonialants.Ant;
import colonialants.AntHill;
import colonialants.BuilderAnt;
import colonialants.Environment;
import colonialants.Environment.AntType;
import colonialants.GatheringAnt;
import colonialants.Leaf;
import colonialants.Sand;
import colonialants.Stream;
import colonialants.Terrain;
import colonialants.TerrainLocation;
import colonialants.TerrainLocation.Direction;
import static colonialants.Utility.*;
import java.awt.geom.Rectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.MessageBox;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author George McDaid
 */
public class AntDisplayGL extends SkelLWJGL {

    Environment e;
    private TextureMapper tmap = null;
    private boolean loadTerrain = false;
    private ArrayList<Ant> antload;
    private HashMap<Integer, ArrayList> antlayf = new HashMap<Integer, ArrayList>();
    private HashMap<Integer, ArrayList> antlayr = new HashMap<Integer, ArrayList>();
    
    private void initTextures() {

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        tmap = new TextureMapper();

        tmap.initSheet(this.getClass().getResource("/colonialimages/spritesheethighres_5_2_2014.png"), "PNG");
        tmap.addSpriteLocation("sand", new Rectangle2D.Float(0f, .625f, .25f, .125f));
        tmap.addSpriteLocation("leaf", new Rectangle2D.Float(.25f, .625f, .25f, .125f));
        tmap.addSpriteLocation("stream", new Rectangle2D.Float(.5f, .5f, .25f, .125f));
        tmap.addSpriteLocation("brownleaf", new Rectangle2D.Float(0f, .75f, .25f, .125f));
        tmap.addSpriteLocation("redleaf", new Rectangle2D.Float(.25f, .75f, .25f, .125f));
        tmap.addSpriteLocation("anthill", new Rectangle2D.Float(.25f, .5f, .25f, .125f));

        tmap.addSpriteLocation("antNorth", new Rectangle2D.Float(.25f, 0, .25f, .125f));
        tmap.addSpriteLocation("antNorthEast", new Rectangle2D.Float(0, .125f, .25f, .125f));
        tmap.addSpriteLocation("antSouthEast", new Rectangle2D.Float(.25f, .125f, .25f, .125f));
        tmap.addSpriteLocation("antSouth", new Rectangle2D.Float(.5f, 0, .25f, .125f));
        tmap.addSpriteLocation("antSouthWest", new Rectangle2D.Float(.75f, 0, .25f, .125f));
        tmap.addSpriteLocation("antEast", new Rectangle2D.Float(0, 0, .25f, .125f));
        tmap.addSpriteLocation("antWest", new Rectangle2D.Float(.75f, .125f, .25f, .125f));
        tmap.addSpriteLocation("antNorthWest", new Rectangle2D.Float(.25f, .125f, .25f, .125f));

        tmap.addSpriteLocation("builderAntNorth", new Rectangle2D.Float(.5f, .625f, .25f, .125f));
        tmap.addSpriteLocation("builderAntNorthEast", new Rectangle2D.Float(.75f, .5f, .25f, .125f));
        tmap.addSpriteLocation("builderAntSouthEast", new Rectangle2D.Float(.25f, .875f, .25f, .125f));
        tmap.addSpriteLocation("builderAntSouth", new Rectangle2D.Float(.5f, .75f, .25f, .125f));
        tmap.addSpriteLocation("builderAntSouthWest", new Rectangle2D.Float(.5f, .875f, .25f, .125f));
        tmap.addSpriteLocation("builderAntEast", new Rectangle2D.Float(0, .875f, .25f, .125f));
        tmap.addSpriteLocation("builderAntWest", new Rectangle2D.Float(.75f, .75f, .25f, .125f));        
        tmap.addSpriteLocation("builderAntNorthWest", new Rectangle2D.Float(.75f, .625f, .25f, .125f));        
        
        tmap.addSpriteLocation("pheromoneReturn1", new Rectangle2D.Float(.25f, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneReturn2", new Rectangle2D.Float(.5f, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneReturn3", new Rectangle2D.Float(.75f, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneReturn4", new Rectangle2D.Float(.75f, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood1", new Rectangle2D.Float(0, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood2", new Rectangle2D.Float(0, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood3", new Rectangle2D.Float(.25f, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood4", new Rectangle2D.Float(.5f, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneNone", new Rectangle2D.Float(.75f, .875f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneDanger", new Rectangle2D.Float(0, .5f, .25f, .125f));

        /*		tmap.initSheet("src/colonialimages/spritesheet2.png", "PNG");
         tmap.addSpriteLocation("sand", new Rectangle2D.Float(.5f,.5f,.25f,.25f));
         tmap.addSpriteLocation("leaf", new Rectangle2D.Float(.25f,.5f,.25f,.25f));
         tmap.addSpriteLocation("anthill", new Rectangle2D.Float(0,.5f,.25f,.25f));
                
         tmap.addSpriteLocation("antNorth", new Rectangle2D.Float(.25f,0,.25f,.25f));
         tmap.addSpriteLocation("antNorthEast", new Rectangle2D.Float(.5f,0,.25f,.25f));
         tmap.addSpriteLocation("antSouthEast", new Rectangle2D.Float(.25f,.25f,.25f,.25f));
         tmap.addSpriteLocation("antSouth", new Rectangle2D.Float(0,.25f,.25f,.25f));
         tmap.addSpriteLocation("antSouthWest", new Rectangle2D.Float(.5f,.25f,.25f,.25f));
         tmap.addSpriteLocation("antEast", new Rectangle2D.Float(0,0,.25f,.25f));
         tmap.addSpriteLocation("antWest", new Rectangle2D.Float(.75f,.25f,.25f,.25f));
         tmap.addSpriteLocation("antNorthWest", new Rectangle2D.Float(.75f,0,.25f,.25f));
                
         tmap.addSpriteLocation("pheromoneReturn", new Rectangle2D.Float(.25f,.75f,.25f,.25f));
         tmap.addSpriteLocation("pheromoneFood", new Rectangle2D.Float(0,.75f,.25f,.25f));
         tmap.addSpriteLocation("pheromoneNone", new Rectangle2D.Float(.5f,.75f,.25f,.25f));
         */
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
                GL11.GL_REPEAT);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    @Override
    protected void initGL() {
        Rectangle bounds = canvas.getClientArea();
        GL11.glViewport(bounds.x, bounds.y, bounds.width, bounds.height);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GL11.glOrtho(0, bounds.width, bounds.height, 0, 1, -1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        initTextures();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        createModel();
    }

    protected void createModel() {
        e = new Environment();
        e.initEmptyField();
        e.initScents();
        //e.addTestAnts();
        //e.addTestScent();
    }

    @Override
    protected void renderGL() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glPushMatrix();
        {
            drawBackground();
            drawForeGround();
        }
        GL11.glPopMatrix();
    }

    private void drawBackground() {
        TerrainLocation[][] gridlocations = e.getLocations();
        for (TerrainLocation[] locations : gridlocations) {
            for (TerrainLocation location : locations) {
                String texture = location.getTerrain().getTexture();
                Rectangle bounds = location.getScreenLocation();
                GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                        tmap.getSheetID());
                drawTile(bounds, tmap.getSpriteLocation(texture));

                //if(location.getScent().FoodIntensity > 0 || location.getScent().ReturnIntensity > 0){
                String r = location.getScent().getTexture();
                Rectangle scentbounds = location.getScreenLocation();
                GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                        tmap.getSheetID());

                drawTile(scentbounds, tmap.getSpriteLocation(r));
                //}

            }
        }
    }

    private void drawForeGround() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        ArrayList<Ant> ants = e.getTestAnts();
        for (Ant ant : ants) {
            String r = ant.getTexture();
            Rectangle bounds = ant.getScreenPosition();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                    tmap.getSheetID());

            drawTile(bounds, tmap.getSpriteLocation(r));
        }
    }

    private void drawTile(Rectangle bounds, Rectangle2D.Float r) {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(r.x, r.y + r.height);
        GL11.glVertex2f(bounds.x, bounds.y + bounds.height);

        GL11.glTexCoord2f(r.x + r.width, r.y + r.height);
        GL11.glVertex2f(bounds.x + bounds.width, bounds.y + bounds.height);

        GL11.glTexCoord2f(r.x + r.width, r.y);
        GL11.glVertex2f(bounds.x + bounds.width, bounds.y);

        GL11.glTexCoord2f(r.x, r.y);
        GL11.glVertex2f(bounds.x, bounds.y);

        GL11.glEnd();
    }

    @Override
    protected void destroyGL() {
        closeLog();
    }

    @Override
    protected void onClockTick(int delta) {
        if (!animate) {
            return;
        }
        if (snapMovement) {
            e.snapMovementOn(delta);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            e.snapMovementOff(delta);
        }
        e.onClockTick(delta);
        updateFPS(delta); // update FPS Counter
        updateFoodCount();
        updateAntCount();
        updateLeafCount();
        updateScoreCount();
    }

    @Override
    protected void resetGL() {
        Rectangle bounds = canvas.getClientArea();
        GL11.glViewport(bounds.x, bounds.y, bounds.width, bounds.height);

    }

    protected void saveBackground() {
        prepareInsert(""
                + "INSERT INTO terrain "
                + "VALUES "
                + "(?,?,?,?,?,?,?,?,?)");
        for (TerrainLocation[] locationrow : e.getLocations()) {
            for (TerrainLocation location : locationrow) {
                try {
                    psInsert.setInt(TERRAIN_ID, location.getID());
                    psInsert.setInt(TERRAIN_GRIDX, location.getIdices().x);
                    psInsert.setInt(TERRAIN_GRIDY, location.getIdices().y);
                    psInsert.setString(TERRAIN_TYPE, location.getTerrain().getTexture());
                    psInsert.setInt(TERRAIN_RESOURCES, location.getResources());
                    if (location.getScent().getFlayer() == null) {
                        psInsert.setInt(TERRAIN_FLAYER, -1);
                    } else {
                        psInsert.setInt(TERRAIN_FLAYER, location.getScent().getFlayer().getID());
                    }
                    if (location.getScent().getRlayer() == null) {
                        psInsert.setInt(TERRAIN_RLAYER, -1);
                    } else {
                        psInsert.setInt(TERRAIN_RLAYER, location.getScent().getRlayer().getID());
                    }
                    psInsert.setDouble(TERRAIN_RINTENSITY, location.getScent().getReturnIntensity());
                    psInsert.setDouble(TERRAIN_FINTENSITY, location.getScent().getFoodIntensity());
                    psInsert.executeUpdate();
                } catch (SQLException ex) {
                    printSQLException(ex);
                    Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        commit();
    }

    protected void saveAnts() {
        try {
            prepareInsert(""
                    + "INSERT INTO ant "
                    + "VALUES "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            for (Ant ant : e.getColony().getAnts()) {
                psInsert.setInt(ANT_ID, ant.getID());
                psInsert.setString(ANT_TYPE, ant.toString());
                psInsert.setDouble(ANT_PIXELX, ant.getScreenPosition().x);
                psInsert.setDouble(ANT_PIXELY, ant.getScreenPosition().y);
                psInsert.setInt(ANT_INTENDEDBEARING, ant.getBearing());
                psInsert.setInt(ANT_LIFESPAN, ant.getLifeSpan());
                psInsert.setBoolean(ANT_FOOD, ant.getCarryingStatus());
                if (ant.getDestination() == null) {
                    psInsert.setInt(ANT_DESTINATIONX, -1);
                    psInsert.setInt(ANT_DESTINATIONY, -1);
                } else {
                    psInsert.setInt(ANT_DESTINATIONX, ant.getDestination().getIdices().x);
                    psInsert.setInt(ANT_DESTINATIONY, ant.getDestination().getIdices().y);
                }
                psInsert.setInt(ANT_ORIGINX, ant.getOrigin().getIdices().x);
                psInsert.setInt(ANT_ORIGINY, ant.getOrigin().getIdices().y);
                psInsert.setDouble(ANT_RP_LEVEL, ant.getRP_LEVEL());
                psInsert.setDouble(ANT_FP_LEVEL, ant.getFP_LEVEL());
                psInsert.setInt(ANT_STATE, ant.getState());
                psInsert.executeUpdate();
            }

        } catch (SQLException ex) {
            printSQLException(ex);
            //Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
        }

        commit();
    }
    
    private void loadBackground() {
        try {
            ResultSet rs = executeQuery(""
                    + "SELECT COUNT(id)"
                    + "FROM terrain");
            loadTerrain = false;
            if(rs!= null && rs.next()){
                if(rs.getInt(1) == e.getDimension()*e.getDimension()){
                    ResultSet terrainrs = executeQuery(""
                                            + "SELECT *"
                                            + "FROM terrain");
                    TerrainLocation[][] terrain = e.getLocations();
                    while(terrainrs.next()){
                        int i = terrainrs.getInt(TERRAIN_GRIDX);
                        int j = terrainrs.getInt(TERRAIN_GRIDY);
                        
                        String terraintype = terrainrs.getString(TERRAIN_TYPE);
                        
                        if(!terrain[i][j].toString().equals(terraintype)){
                            if(terraintype.equals("sand")){
                                terrain[i][j].setTerrain((Terrain) new Sand("sand"));
                            }else if(terraintype.equals("leaf")){
                                int res = terrainrs.getInt(TERRAIN_RESOURCES);
                                terrain[i][j].setTerrain((Terrain) new Leaf("leaf"));
                                terrain[i][j].setResources(res);
                            }else if(terraintype.equals("redleaf")){
                                 int res = terrainrs.getInt(TERRAIN_RESOURCES);
                                 terrain[i][j].setTerrain((Terrain) new Leaf("redleaf"));
                                 terrain[i][j].setResources(res);
                            }else if(terraintype.equals("stream")){
                                 int res = terrainrs.getInt(TERRAIN_RESOURCES);
                                 terrain[i][j].setTerrain((Terrain) new Stream("stream"));
                                 terrain[i][j].setResources(res);
                            }else if(terraintype.equals("anthill")){
                                 terrain[i][j].setTerrain((Terrain) new AntHill("anthill"));
                            }else{
                                 terrain[i][j].setTerrain((Terrain) new Sand("sand"));
                            }
                            
                        }
                        
                        int flayer = terrainrs.getInt(TERRAIN_FLAYER);
                        int rlayer = terrainrs.getInt(TERRAIN_RLAYER);
                        
                        double rintensity = terrainrs.getInt(TERRAIN_RINTENSITY);
                        double fintensity = terrainrs.getInt(TERRAIN_FINTENSITY);
                        
                        terrain[i][j].getScent().setFoodIntensity(fintensity);
                        terrain[i][j].getScent().setReturnIntensity(rintensity);
                        
                        Point p = new Point(i, j);
                        
                        if(flayer != -1){
                            ArrayList<Point> antlaylf = antlayf.get(flayer);
                            if(antlaylf != null){
                                antlaylf.add(p);
                            }else{
                                ArrayList<Point> points = new ArrayList<Point>();
                                points.add(p);
                                antlayf.put(flayer, points);
                            } 
                        }
                        
                        if(rlayer != -1){
                            ArrayList<Point> antlaylr = antlayr.get(rlayer);
                            if(antlaylr != null){
                                antlaylr.add(p);
                            }else{
                                ArrayList<Point> points = new ArrayList<Point>();
                                points.add(p);
                                antlayr.put(rlayer, points);
                            } 
                        }
                        
                    }
                    loadTerrain = true;
                }else{
                    // create dialog with ok and cancel button and info icon
                    MessageBox dialog = 
                      new MessageBox(shell, SWT.ICON_ERROR| SWT.OK);
                    loadTerrain = false;
                    dialog.setText("Load Error");
                    dialog.setMessage("There was an error loading the save data. Data has been cleared.");
                    dialog.open();
                    truncate("terrain");
                    truncate("ant");
                }
            }
        } catch (SQLException ex) {
            printSQLException(ex);
            Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void loadAnts() {
        antload = new ArrayList<Ant>(); 
        try {
            ResultSet rs = executeQuery(""
                    + "SELECT *"
                    + "FROM ant");
            while(rs.next()){
                int pixelx = rs.getInt(ANT_PIXELX);
                int pixely = rs.getInt(ANT_PIXELY);
                //o("P: "+pixelx+"|"+pixely);
                String type = rs.getString(ANT_TYPE);
                Ant a; 
                if(type.equalsIgnoreCase("ant")){
                    a = new Ant(new Point(0, 0), Environment.gatherertex);
                }else if(type.equalsIgnoreCase("gatherer")){
                    a = new GatheringAnt(new Point(0, 0),Environment.gatherertex);
                }else if(type.equalsIgnoreCase("builder")){
                    a = new BuilderAnt(new Point(0, 0),Environment.buildertex);
                }else{
                    a = new Ant(new Point(pixelx, pixely), Environment.gatherertex);
                }
                a.getScreenPosition().x = pixelx;
                a.getScreenPosition().y = pixely;
                
                a.setANT_LIFESPAN(rs.getInt(ANT_LIFESPAN));
                a.setCarryingFood(rs.getBoolean(ANT_FOOD));
                
                int dx = rs.getInt(ANT_DESTINATIONX);
                int dy=  rs.getInt(ANT_DESTINATIONY);
                //o(dx+"|"+dy);
                if(dx!=-1 && dy!=-1){
                  a.setDestination(e.getLocations()[dx][dy]);  
                }
                
                int ox = rs.getInt(ANT_ORIGINX);
                int oy=  rs.getInt(ANT_ORIGINY);
                a.setOrigin(e.getLocations()[ox][oy]);             
                
                a.setRP_LEVEL(rs.getInt(ANT_RP_LEVEL));
                a.setFP_LEVEL(rs.getInt(ANT_FP_LEVEL));
                
                int bearing = rs.getInt(ANT_INTENDEDBEARING);
                int state = rs.getInt(ANT_STATE);
                
                a.setIntendedBearing(Direction.fromValue(bearing));
                a.setState(Ant.State.fromValue(state));
                                
                int id = rs.getInt(ANT_ID);
                ArrayList<Point> layf = antlayf.get(id);
                ArrayList<Point> layr = antlayr.get(id);
                
                antload.add(a);
                
                
                TerrainLocation[][] terrain = e.getLocations();

                if(layf != null){
                    for (Point point : layf) {
                        terrain[point.x][point.y].getScent().setFlayer(a);
                    }
                }

                if(layr != null){
                    for (Point point : layr) {
                        terrain[point.x][point.y].getScent().setRlayer(a);
                    }
                }
                
                
            }
            //o(antload.size());
            e.getColony().setAnts(antload);
            
        } catch (SQLException ex) {
            Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        AntDisplayGL gl = new AntDisplayGL();
        initDB();
        gl.start();
        shutdownDB();
    }

    @Override
    protected void updateFoodCount() {
        labelFood.setText("Net Resources: " + e.getColony().getFoodCount());
    }

    @Override
    protected void updateGatherCount() {
        labelGather.setText("Gatherers Present: " + e.getColony().getAntCount(AntType.GATHERER));
    }

    @Override
    protected void updateBuilderCount() {
        labelBuilder.setText("Builders Present: " + e.getColony().getAntCount(AntType.BUILDER));
    }

    @Override
    protected void updateLeafCount() {
        labelLeaves.setText("Leaves Present: " + e.getColony().getLeafCount());
    }

    @Override
    protected void updateScoreCount() {
        labelScore.setText("Score (Resources Collected): " + e.getColony().getScore());
    }

    @Override
    protected void onLaySliderChange(int value) {
        ArrayList<Ant> ants = e.getColony().getAnts();
        for(int i = 0; i < ants.size(); i++){
        ants.get(i).setRP_LEVEL(value);
        ants.get(i).setFP_LEVEL(value);
        ants.get(i).setDP_LEVEL(value);
        }
    }

    @Override
    protected void onEvapSliderChange(int value) {
        TerrainLocation[][] terrain = e.getTerrain();
        for (TerrainLocation[] terrainrow : terrain) {
            for (TerrainLocation location : terrainrow) {
                location.getScent().setEVAP_RATE(value);
            }
        }

    }

    @Override
    protected void invokeWind() {
        e.blowWind();
    }

    private void updateAntCount() {
        updateGatherCount();
        updateBuilderCount();
    }

    @Override
    protected void onSliderLifespanChange(int value) {
        e.setLifeSpan((int)value);
    }

    @Override
    protected void onSliderBalanceChange(int value) {
        e.setAntBalance(value);
    }

    @Override
    protected void onSliderFoodChange(double value) {
        e.setFoodRate((int)value);
    }

    @Override
    protected void onSliderSpawnChange(double value) {
        e.setSpawnRate((int)value);
    }

    @Override
    protected void save() {
        if(animate){
            truncate("terrain");
            truncate("ant");
            animate = false;
            saveBackground();
            saveAnts();
            animate = true;
        }else{
            truncate("terrain");
            truncate("ant");
            saveBackground();
            saveAnts();
        }
    }

    @Override
    protected void load() {
        if(animate){
            animate = false;
            loadAnts();
            loadBackground();
        }else{
            loadAnts();
            loadBackground();
        }
    }   
    @Override
    protected void invokeReset() {
        e.reset();
    }
    
    @Override
    protected void invokeWater() {
        int x = r.nextInt(15) + 15;
        TerrainLocation start = e.getLocations()[x][0];
        
        x = r.nextInt(31);
        TerrainLocation end = e.getLocations()[x][31];
        
        while(start != end){
            start = e.createStream(start, end);
        }
    }
}
