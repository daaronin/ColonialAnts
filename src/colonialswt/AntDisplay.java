/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialswt;

import colonialants.Environment;
import org.eclipse.swt.graphics.Rectangle;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author George McDaid
 */
public class AntDisplay extends SkelGL{

    Environment e;
    
    //Inits models here
    @Override
    protected void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        float aspect = WIDTH / (float) HEIGHT;
        GLU.gluPerspective(fovy, aspect, zNear, zFar);
        GLU.gluLookAt(0, 0.5f, eyez, 0, 0, 0, 0, 1, 0);

        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glClearColor(1, 1, 1, 0);
        e = new Environment();
        e.initEmptyField();
    }

    @Override
    protected void update(int delta) {
    }

    @Override
    protected void resetGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        Rectangle bounds = canvas.getBounds();
        float aspectRatio = (float) bounds.width / bounds.height;

        GLU.gluPerspective(fovy, aspectRatio, zNear, zFar);
        GLU.gluLookAt(eyex, eyey, eyez, 0, 0, 0, 0, 1, 0);
        GL11.glViewport(0, 0, bounds.width, bounds.height);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    
    //Render models here
    @Override
    protected void renderGL() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        Rectangle bounds = canvas.getBounds();
        float aspectRatio = (float) bounds.width / bounds.height;

        GLU.gluPerspective(fovy, aspectRatio, zNear, zFar);
        GLU.gluLookAt(eyex, eyey, eyez, 0, 0, 0, 0, 1, 0);
        GL11.glViewport(0, 0, bounds.width, bounds.height);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        for(int i = 0;i<e.getLocations().length;i++){
            for(int j = 0;j<e.getLocations()[i].length;j++){
                if (e.getLocations()[i][j].toString().equals("O")) {
                    GL11.glColor3f(.647059f, .164706f, .164706f);
                } else if (e.getLocations()[i][j].toString().equals("L")) {
                    GL11.glColor3f(0, 1, 0);
                }
                //GL11.glColor3f(2, 171, 16);
                int cx = i+1;
                int cy = j+1;
                GL11.glBegin(GL11.GL_TRIANGLES);
                GL11.glVertex2f(cx-1, cy+1);
                GL11.glVertex2f(cx-1, cy-1);
                GL11.glVertex2f(cx+1, cy-1);
                
                GL11.glVertex2f(cx-1, cy+1);
                GL11.glVertex2f(cx+1, cy+1);
                GL11.glVertex2f(cx+1, cy-1);
                GL11.glEnd();
            }
        }
        
    }
    
    public void renderColony(){
        
    }

    @Override
    protected void initTextures() {
    }
    
    public static void main(String [] args){
        AntDisplay ad = new AntDisplay();
        ad.start();
    }

}
