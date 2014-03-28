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
import org.eclipse.swt.graphics.Rectangle;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author George McDaid
 */
public class AntDisplayGL extends SkelLWJGL{

    Environment e;
    private TextureMapper tmap = null;
    
    private void initTextures() {

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		tmap = new TextureMapper();
                
                tmap.initSheet("src/colonialimages/spritesheethighres.png", "PNG");
		tmap.addSpriteLocation("sand", new Rectangle2D.Float(.25f,.5f,.25f,.125f));
                tmap.addSpriteLocation("leaf", new Rectangle2D.Float(.75f,.5f,.25f,.125f));
                tmap.addSpriteLocation("brownleaf", new Rectangle2D.Float(0,.625f,.25f,.125f));
                tmap.addSpriteLocation("redleaf", new Rectangle2D.Float(.25f,.625f,.25f,.125f));
                tmap.addSpriteLocation("anthill", new Rectangle2D.Float(0,.5f,.25f,.125f));
                
                tmap.addSpriteLocation("antNorth", new Rectangle2D.Float(.25f,0,.25f,.125f));
                tmap.addSpriteLocation("antNorthEast", new Rectangle2D.Float(.5f,0,.25f,.125f));
                tmap.addSpriteLocation("antSouthEast", new Rectangle2D.Float(.25f,.125f,.25f,.125f));
                tmap.addSpriteLocation("antSouth", new Rectangle2D.Float(0,.125f,.25f,.125f));
                tmap.addSpriteLocation("antSouthWest", new Rectangle2D.Float(.5f,.125f,.25f,.125f));
                tmap.addSpriteLocation("antEast", new Rectangle2D.Float(0,0,.25f,.125f));
                tmap.addSpriteLocation("antWest", new Rectangle2D.Float(.75f,.125f,.25f,.125f));
                tmap.addSpriteLocation("antNorthWest", new Rectangle2D.Float(.75f,0,.25f,.125f));
                
                tmap.addSpriteLocation("pheromoneReturn1", new Rectangle2D.Float(0,.375f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneReturn2", new Rectangle2D.Float(.25f,.375f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneReturn3", new Rectangle2D.Float(.5f,.375f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneReturn4", new Rectangle2D.Float(.75f,.375f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneFood1", new Rectangle2D.Float(0,.25f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneFood2", new Rectangle2D.Float(.25f,.25f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneFood3", new Rectangle2D.Float(.5f,.25f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneFood4", new Rectangle2D.Float(.75f,.25f,.25f,.125f));
                tmap.addSpriteLocation("pheromoneNone", new Rectangle2D.Float(.75f,.75f,.25f,.125f));

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
                    Rectangle bounds = location.getScreenLocaiton();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                    tmap.getSheetID());
                    drawTile(bounds, tmap.getSpriteLocation(texture));
                    
                    String r = location.getScent().getTexture();
                    Rectangle scentbounds = location.getScreenLocaiton();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                    tmap.getSheetID());

                    drawTile(scentbounds, tmap.getSpriteLocation(r));
                }
            }
         }
        
        private void drawForeGround() {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            ArrayList<Ant> ants = e.getTestAnts();
            for(Ant ant : ants){
                    String r = ant.getTexture();
                    Rectangle bounds = ant.getScreenPosition();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                    tmap.getSheetID());

                    drawTile(bounds, tmap.getSpriteLocation(r));
             }                                        
        }

	private void drawTile(Rectangle bounds, Rectangle2D.Float r) {
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(r.x, r.y+r.height);
                GL11.glVertex2f(bounds.x, bounds.y + bounds.height);
                
                
                GL11.glTexCoord2f(r.x+r.width, r.y + r.height);
		GL11.glVertex2f(bounds.x + bounds.width, bounds.y + bounds.height);
                
                GL11.glTexCoord2f(r.x+r.width, r.y);
                GL11.glVertex2f(bounds.x + bounds.width, bounds.y);
		
		
                GL11.glTexCoord2f(r.x, r.y);
                GL11.glVertex2f(bounds.x, bounds.y);
		
		GL11.glEnd();
	}
        
        @Override
	protected void destroyGL() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onClockTick(int delta) {
		if (!animate)
			return;
		e.onClockTick(delta);
		updateFPS(); // update FPS Counter
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
    protected void onSliderChange(AntType TYPE, int value) {
        
    }
    
}
