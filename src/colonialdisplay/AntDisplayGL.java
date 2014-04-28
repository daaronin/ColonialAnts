/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialdisplay;

import colonialants.Ant;
import static colonialants.Utility.*;
import colonialants.Environment;
import colonialants.Environment.AntType;
import colonialants.TerrainLocation;
import java.awt.geom.Rectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.swt.graphics.Rectangle;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author George McDaid
 */
public class AntDisplayGL extends SkelLWJGL {

    Environment e;
    private TextureMapper tmap = null;
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    private static Connection conn = null;
    private static ArrayList statements = new ArrayList(); // list of Statements, PreparedStatements
    private static PreparedStatement psInsert = null;
    private static PreparedStatement psUpdate = null;
    private static Statement s = null;
    private static ResultSet rs = null;
    private static String framework = "embedded";
    
    private void initTextures() {

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        tmap = new TextureMapper();

        tmap.initSheet(this.getClass().getResource("/colonialimages/spritesheethighres.png"), "PNG");
        tmap.addSpriteLocation("sand", new Rectangle2D.Float(.25f, .5f, .25f, .125f));
        tmap.addSpriteLocation("leaf", new Rectangle2D.Float(.75f, .5f, .25f, .125f));
        tmap.addSpriteLocation("brownleaf", new Rectangle2D.Float(0, .625f, .25f, .125f));
        tmap.addSpriteLocation("redleaf", new Rectangle2D.Float(.25f, .625f, .25f, .125f));
        tmap.addSpriteLocation("anthill", new Rectangle2D.Float(0, .5f, .25f, .125f));

        tmap.addSpriteLocation("antNorth", new Rectangle2D.Float(.25f, 0, .25f, .125f));
        tmap.addSpriteLocation("antNorthEast", new Rectangle2D.Float(.5f, 0, .25f, .125f));
        tmap.addSpriteLocation("antSouthEast", new Rectangle2D.Float(.25f, .125f, .25f, .125f));
        tmap.addSpriteLocation("antSouth", new Rectangle2D.Float(0, .125f, .25f, .125f));
        tmap.addSpriteLocation("antSouthWest", new Rectangle2D.Float(.5f, .125f, .25f, .125f));
        tmap.addSpriteLocation("antEast", new Rectangle2D.Float(0, 0, .25f, .125f));
        tmap.addSpriteLocation("antWest", new Rectangle2D.Float(.75f, .125f, .25f, .125f));
        tmap.addSpriteLocation("antNorthWest", new Rectangle2D.Float(.75f, 0, .25f, .125f));

        tmap.addSpriteLocation("pheromoneReturn1", new Rectangle2D.Float(0, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneReturn2", new Rectangle2D.Float(.25f, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneReturn3", new Rectangle2D.Float(.5f, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneReturn4", new Rectangle2D.Float(.75f, .375f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood1", new Rectangle2D.Float(0, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood2", new Rectangle2D.Float(.25f, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood3", new Rectangle2D.Float(.5f, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneFood4", new Rectangle2D.Float(.75f, .25f, .25f, .125f));
        tmap.addSpriteLocation("pheromoneNone", new Rectangle2D.Float(.75f, .625f, .25f, .125f));

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
    
    protected void saveAnts(){
    }

    public static void main(String[] args) {
        AntDisplayGL gl = new AntDisplayGL();
        try {
            
            Class.forName(driver).newInstance();
            
            conn = DriverManager.getConnection(protocol + "src/colonialdisplay/derbyDB;create=true");
            

            s = conn.createStatement();
            statements.add(s);

            // We create a table...
            try{
                s.execute("create table location1(num int PRIMARY KEY, addr varchar(40))");
                System.out.println("Created table location");
            }catch(SQLException e){
                
            }

            // and add a few rows...

            /* It is recommended to use PreparedStatements when you are
             * repeating execution of an SQL statement. PreparedStatements also
             * allows you to parameterize variables. By using PreparedStatements
             * you may increase performance (because the Derby engine does not
             * have to recompile the SQL statement each time it is executed) and
             * improve security (because of Java type checking).
             */
            // parameter 1 is num (int), parameter 2 is addr (varchar)
            psInsert = conn.prepareStatement(
                    "insert into location1 values (?, ?)");
            statements.add(psInsert);

            psInsert.setInt(1, 1958);
            psInsert.setString(2, "Webster St.");
            psInsert.executeUpdate();
            System.out.println("Inserted 1957 Webster");

            psInsert.setInt(1, 1911);
            psInsert.setString(2, "Union St.");
            psInsert.executeUpdate();
            System.out.println("Inserted 1910 Union");

            // Let's update some rows as well...
            // parameter 1 and 3 are num (int), parameter 2 is addr (varchar)
            psUpdate = conn.prepareStatement(
                    "update location1 set num=?, addr=? where num=?");
            statements.add(psUpdate);

            psUpdate.setInt(1, 180);
            psUpdate.setString(2, "Grand Ave.");
            psUpdate.setInt(3, 1956);
            psUpdate.executeUpdate();
            System.out.println("Updated 1956 Webster to 180 Grand");

            psUpdate.setInt(1, 300);
            psUpdate.setString(2, "Lakeshore Ave.");
            psUpdate.setInt(3, 180);
            psUpdate.executeUpdate();
            System.out.println("Updated 180 Grand to 300 Lakeshore");


            /*
             We select the rows and verify the results.
             */
            rs = s.executeQuery(
                    "SELECT num, addr FROM location1 ORDER BY num");

            /* we expect the first returned column to be an integer (num),
             * and second to be a String (addr). Rows are sorted by street
             * number (num).
             *
             * Normally, it is best to use a pattern of
             *  while(rs.next()) {
             *    // do something with the result set
             *  }
             * to process all returned rows, but we are only expecting two rows
             * this time, and want the verification code to be easy to
             * comprehend, so we use a different pattern.
             */
            int number; // street number retrieved from the database
            boolean failure = false;
            if (!rs.next()) {
                failure = true;
                //reportFailure("No rows in ResultSet");
            }

            if ((number = rs.getInt(1)) != 300) {
                failure = true;
                //reportFailure(
                //"Wrong row returned, expected num=300, got " + number);
            }

            if (!rs.next()) {
                failure = true;
                //reportFailure("Too few rows");
            }

            if ((number = rs.getInt(1)) != 1910) {
                failure = true;
//                reportFailure(
//                        "Wrong row returned, expected num=1910, got " + number);
            }

            if (rs.next()) {
                failure = true;
                //reportFailure("Too many rows");
            }
            
            rs = s.executeQuery("SELECT COUNT(*) FROM location1");
            rs.next();
            int ir = rs.getInt(1);
            o("ROWS: " + ir);

            if (!failure) {
                System.out.println("Verified the rows");
                
            }

            // delete the table
//            s.execute("drop table location");
//            System.out.println("Dropped table location");

            /*
             We commit the transaction. Any changes will be persisted to
             the database now.
             */
            conn.commit();
            System.out.println("Committed the transaction");

            /*
             * In embedded mode, an application should shut down the database.
             * If the application fails to shut down the database,
             * Derby will not perform a checkpoint when the JVM shuts down.
             * This means that it will take longer to boot (connect to) the
             * database the next time, because Derby needs to perform a recovery
             * operation.
             *
             * It is also possible to shut down the Derby system/engine, which
             * automatically shuts down all booted databases.
             *
             * Explicitly shutting down the database or the Derby engine with
             * the connection URL is preferred. This style of shutdown will
             * always throw an SQLException.
             *
             * Not shutting down when in a client environment, see method
             * Javadoc.
             */
            if (framework.equals("embedded")) {

                // the shutdown=true attribute shuts down Derby
                DriverManager.getConnection("jdbc:derby:;shutdown=true");

                    // To shut down a specific database only, but keep the
                // engine running (for example for connecting to other
                // databases), specify a database in the connection URL:
                //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
            }


            // release all open resources to avoid unnecessary memory usage
            // ResultSet
            if (rs != null) {
                rs.close();
                rs = null;
            }

            // Statements and PreparedStatements
            int i = 0;
            while (!statements.isEmpty()) {
                // PreparedStatement extend Statement
                Statement st = (Statement) statements.remove(i);
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException sqle) {
                    //printSQLException(sqle);
                }
            }

            //Connection
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }

    
    catch (ClassNotFoundException ex

    
        ) {
            Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex

    
        ) {
            Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex

    
        ) {
            Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (SQLException ex

    
        ) {
            Logger.getLogger(AntDisplayGL.class.getName()).log(Level.SEVERE, null, ex);
    }
        gl.start();
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
        //CommonScents.setLAY(value);
    }

    @Override
    protected void onEvapSliderChange(int value) {
        //CommonScents.setEVAP(value);

    }

    @Override
    protected void invokeWind() {
        for (TerrainLocation[] locationrow : e.getLocations()) {
            for (TerrainLocation location : locationrow) {
                location.getScent().resetFoodIntensity();
                location.getScent().resetReturnIntensity();
            }
        }
    }

    private void updateAntCount() {
        updateGatherCount();
        updateBuilderCount();
    }

    @Override
    protected void onSliderLifespanChange(int value) {
        
    }

    @Override
    protected void onSliderBalanceChange(int value) {
        e.setAntBalance(value);
    }

    @Override
    protected void onSliderFoodChange(double value) {
        
    }

    @Override
    protected void onSliderSpawnChange(double value) {
        
    }

    @Override
    protected void save() {
        saveAnts();
    }


    


}
