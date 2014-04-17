/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialdisplay;

import colonialants.Ant;
import colonialants.CommonScents;
import colonialants.Environment;
import colonialants.Environment.AntType;
import colonialants.TerrainLocation;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.swt.graphics.Rectangle;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import static colonialants.Debug.*;

/**
 *
 * @author George McDaid
 */
public class AntDisplayGL extends SkelLWJGL {

    Environment e;
    private TextureMapper tmap = null;

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

    public static void main(String[] args) {
        AntDisplayGL gl = new AntDisplayGL();
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


}
