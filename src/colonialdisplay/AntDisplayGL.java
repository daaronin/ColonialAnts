/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialdisplay;

import colonialants.Environment;
import colonialants.TerrainLocation;
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

		tmap.createTexture("src/colonialimages/Sand.png", "PNG", "sand");
		tmap.createTexture("src/colonialimages/AntBit.png", "PNG", "ant");
		tmap.createTexture("src/colonialimages/Leaf.png", "PNG", "leaf");
//		tmap.createTexture("src/images/Ant_E.png", "PNG", "antEast");
//		tmap.createTexture("src/images/Ant_SE.png", "PNG", "antSouthEast");
//		tmap.createTexture("src/images/Ant_S.png", "PNG", "antSouth");
//		tmap.createTexture("src/images/Ant_SW.png", "PNG", "antSouthWest");
//		tmap.createTexture("src/images/Ant_W.png", "PNG", "antWest");
//		tmap.createTexture("src/images/Ant_NW.png", "PNG", "antNorthWest");

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
        //e.addTestAnts();
        //e.addTestScent();
    }
    
    @Override
    protected void renderGL() {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GL11.glPushMatrix();
            {
                    drawBackground();
                    //drawForeGround();
            }
            GL11.glPopMatrix();
    }
    
        private void drawBackground() {
            TerrainLocation[][] gridlocations = e.getLocations();
            for (TerrainLocation[] locations : gridlocations) {
                for (TerrainLocation location : locations) {
                    String texture = "sand";
                    Rectangle bounds = location.getBoundry();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                    tmap.getTextureID(texture));
                    drawTile(bounds);
                }
            }
         }
        
        
        private void drawTile(Rectangle bounds) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(bounds.x, bounds.y);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(bounds.x, bounds.y + bounds.height);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(bounds.x + bounds.width, bounds.y + bounds.height);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(bounds.x + bounds.width, bounds.y);
		GL11.glTexCoord2f(0, 0);
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
		//e.onClockTick(delta);
		updateFPS(); // update FPS Counter
	}

	@Override
	protected void resetGL() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		AntDisplayGL gl = new AntDisplayGL();
                gl.start();
	}
    
}
