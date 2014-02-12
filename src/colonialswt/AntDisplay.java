/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialswt;

import colonialants.Environment;
import colonialants.Leaf;
import colonialants.Location;
import colonialants.Sand;
import colonialants.Stream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author George McDaid
 */
public class AntDisplay {

    Display display;
    Shell shell;
    Composite composite;
    Group group;
    Canvas canvas;    
    
    Environment e;

    protected void initView(){
        display = new Display();
        shell = new Shell(display);
        
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
        
        composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new FillLayout(SWT.HORIZONTAL));
        
        group = new Group(composite, SWT.SHADOW_ETCHED_IN);
        group.setText("The Colonial Ants");
        group.setLayout(new FillLayout(SWT.HORIZONTAL));
        
        canvas = new Canvas(group, SWT.NONE);
        
        //final Image image = new Image(display, "src/swtDemo/angryBird.jpg");
        
        canvas.addListener(SWT.Paint, new Listener() {

            @Override

            public void handleEvent(Event event) {

                renderModel(event);
                //event.gc.drawImage(image, 200, 200);

            }

        });
        
        
    }
        
    protected void showView(){
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
    
    protected void initModel(){
        e = new Environment();
        e.initEmptyField();
    }
    
    protected void renderModel(Event event){
        for (Location[] location : e.getLocations()) {
            for (Location space : location) {
                int s = e.getSpaceSize();
                double cx = space.getX();
                double cy = space.getY();
                
                Color c3;
                
                if(space.getTerrain() instanceof Sand){
                    c3 = new Color(event.display, 217, 209, 128);
                }else if(space.getTerrain() instanceof Leaf){
                    c3 = new Color(event.display, 15, 186, 9);
                }else if(space.getTerrain() instanceof Stream){
                    c3 = new Color(event.display, 85, 60, 245);
                }else{
                    c3 = new Color(event.display, 33, 200, 100);
                }
                
                event.gc.setBackground(c3);
                event.gc.fillRectangle((int)(cx-s), (int)(cy-s), s, s);
            }
        }
    }
    
    protected void start(){
        initModel();
        initView();
        showView();
    }
    
    public static void main(String[] args) {
        AntDisplay ad = new AntDisplay();
        ad.start();
    }

}
