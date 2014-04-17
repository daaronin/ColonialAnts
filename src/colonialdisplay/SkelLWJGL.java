package colonialdisplay;


import colonialants.CommonScents;
import colonialants.Environment.AntType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GLContext;
import static colonialants.Debug.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public abstract class SkelLWJGL {
	/* animation indicator for menu selector */
	boolean animate = false;
        boolean snapMovement = false;

	// SVGA 800x600
	// XVGA 1024x768
	public static final int SCREEN_WIDTH = 872, 
			SCREEN_HEIGHT = 750;
	
	/* time at last frame */
	long lastFrame;
	
	/* frames per second */
	int fps;
	
	/* last fps time */
	long lastFPS;

	/*
	 * Window manager parameters
	 */
	Display display = new Display();
	Shell shell;
	Composite comp;
        Composite comp2;
        Composite comp3;
        Composite comp4;
	GLData data = new GLData();
	GLCanvas canvas;
        
        Scale spawnScale;
        Scale balanceScale;
        Scale scaleLay;
        Scale scaleEvap;
        Scale scaleLifespan;
        Scale scaleFood;
        
        Label lifespanLabel;
	Label balanceLabel;
        Label spawnLabel;
        Label labelFood;
        Label labelFoodRate;
        Label labelGather;
        Label labelBuilder;
        Label labelLeaves;
        Label labelScore;
        Label labelEvap;
        Label labelLay;
        
        Button btnWind;
        Button btnWater;
        Button btnFungi;
        Button btnAttack;
        /**
	 * Get the accurate time system
	 * 
     * @return 
	 */
	public long getTime(){		
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Calculate how many milliseconds has elapsed since last frame
     * @return 
	 */
	
	public int getDelta(){
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
	
	public void updateFPS(int delta){
		if (getTime() - lastFPS > 1000){
                        shell.setText("Ant Colony: D: " + delta + " | FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public void start() {
		
		// Initialize the Window Manager
		final Shell shell = initSWT();
		initGL();
		
		// Call all timing initializations
		getDelta();
		lastFPS = getTime();
		
		// Start a thread that loops and
		// handles frame generation
		final Runnable frameHandler = new Runnable(){

			@Override
			public void run() {
				
				// Software to handle frame creation
				// runs per frame
				if (!canvas.isDisposed()){
					canvas.setCurrent();
					
					int delta = getDelta();

					onClockTick(delta);
					updateFPS(delta);
					
					renderGL();
					canvas.swapBuffers();
					
					//display.timerExec(50, this);
					//Run this asynchronously
					display.asyncExec(this);
				}
			}
		}; // End runnable
		
		canvas.addListener(SWT.Paint, new Listener(){

			@Override
			public void handleEvent(Event event) {
				resetGL();
			}
		});
		
		canvas.addListener(SWT.Resize, new Listener(){
                        @Override
			public void handleEvent(Event event) {
				resetGL();
			}
		});
		
		display.asyncExec(frameHandler);
		
		while(!shell.isDisposed()){
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		destroyGL();
		display.dispose();
	}
	
	private Shell initSWT() {

		shell = new Shell(display,SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setLayout(new FormLayout());
		shell.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		shell.setText("Ant Colony: ");
                
                
                Image bg_image = null;
                try {
                    bg_image = new Image(display, this.getClass().getResource("/colonialimages/shellbackground.png").openStream());
                } catch (IOException ex) {
                    Logger.getLogger(SkelLWJGL.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                shell.setBackgroundImage(bg_image);

		// Create a composite
		comp = new Composite(shell, SWT.BORDER);
		comp.setLayout(new FillLayout());
                                
                comp2 = new Composite(shell, SWT.BORDER);
                FillLayout fillLayout = new FillLayout();
                fillLayout.type = SWT.VERTICAL;
                fillLayout.spacing = 1;
		comp2.setLayout(fillLayout);
                
                comp3 = new Composite(shell, SWT.BORDER);
                fillLayout = new FillLayout();
                fillLayout.type = SWT.VERTICAL;
                fillLayout.spacing = 1;
		comp3.setLayout(fillLayout);
                
                if(DEBUG){
                    comp4 = new Composite(shell, SWT.BORDER);
                    fillLayout = new FillLayout();
                    fillLayout.type = SWT.HORIZONTAL;
                    fillLayout.spacing = 1;
                    comp4.setLayout(fillLayout);
                }     
                
                FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 5);
		data1.top = new FormAttachment(0, 5);
		data1.right = new FormAttachment(75, 0);
                
               
                
                FormData data2 = new FormData();
		data2.left = new FormAttachment(comp, 5);
		data2.right = new FormAttachment(100, -5);
		data2.top = new FormAttachment(comp3, 5);
                
                
                if(DEBUG){
                    data1.bottom = new FormAttachment(90, 0);
                    data2.bottom = new FormAttachment(90,0);
                }else{
                    data1.bottom = new FormAttachment(100, 0);
                    data2.bottom = new FormAttachment(100,0);
                }
                
                comp.setLayoutData(data1);
                comp2.setLayoutData(data2);
                
                
                FormData data3 = new FormData();
		data3.left = new FormAttachment(comp, 5);
		data3.right = new FormAttachment(100, -5);
		data3.top = new FormAttachment(0, 5);
                data3.bottom = new FormAttachment(20,0);
                comp3.setLayoutData(data3);
                
		if(DEBUG){
                    FormData data4 = new FormData();
                    data4.left = new FormAttachment(0, 5);
                    data4.right = new FormAttachment(75, 0);
                    data4.top = new FormAttachment(comp, 5);
                    data4.bottom = new FormAttachment(100, -5);
                    comp4.setLayoutData(data4);
                }
                
		// Depth size 
		data.depthSize = 1;
		data.doubleBuffer = true;
                
                balanceScale = new Scale(comp2, SWT.NONE);
                balanceScale.setMinimum (0);
                balanceScale.setMaximum (10);
                balanceScale.setIncrement(1);
                balanceScale.setPageIncrement(1);
                
                balanceLabel = new Label(comp2, SWT.NONE);
                balanceLabel.setText("Gatherers:Builders | 10:0");
                
                balanceScale.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                  int perspectiveValue = balanceScale.getSelection();
                  int gath = 10-perspectiveValue;
                  int build = perspectiveValue;
                  balanceLabel.setText("Gatherers:Builders | "+gath+":"+build);
                  onSliderBalanceChange(gath*10);
                }
                });
                
                
                spawnScale = new Scale(comp2, SWT.NONE);
                spawnScale.setMaximum (10);
                spawnScale.setMinimum (0);
                spawnScale.setIncrement(1);
                spawnScale.setPageIncrement(1);
                spawnScale.setSelection(1);
                
                spawnLabel = new Label(comp2, SWT.NONE);
                spawnLabel.setText("Spawn Rate: 1");
		
                spawnScale.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                  int perspectiveValue = spawnScale.getSelection();
                  spawnLabel.setText("Spawn Rate: " + perspectiveValue);
                  onSliderSpawnChange(perspectiveValue);
                }
                });
                
                scaleEvap = new Scale(comp2, SWT.NONE);
                scaleEvap.setMaximum (100);
                scaleEvap.setMinimum (0);
                scaleEvap.setIncrement(1);
                scaleEvap.setPageIncrement(1);
                scaleEvap.setSelection(3);
                
                labelEvap = new Label(comp2, SWT.NONE);
                labelEvap.setText("Evap Rate: 3");
		
                scaleEvap.addListener(SWT.Selection, new Listener() {
                    @Override
                    public void handleEvent(Event event) {
                      int perspectiveValue = scaleEvap.getSelection();
                      labelEvap.setText("Evap Rate: " + perspectiveValue);
                      //O(perspectiveValue);
                      onEvapSliderChange(perspectiveValue);
                    }
                });
                
                scaleLay = new Scale(comp2, SWT.NONE);
                scaleLay.setMaximum (100);
                scaleLay.setMinimum (1);
                scaleLay.setIncrement(1);
                scaleLay.setPageIncrement(1);
                scaleLay.setSelection(3);
                
                labelLay = new Label(comp2, SWT.NONE);
                labelLay.setText("Lay Rate: 3");
		
                scaleLay.addListener(SWT.Selection, new Listener() {
                    @Override
                    public void handleEvent(Event event) {
                      int perspectiveValue = scaleLay.getSelection();
                      labelLay.setText("Lay Rate: " + perspectiveValue);
                      //O(perspectiveValue);
                      onLaySliderChange(perspectiveValue);
                    }

                });
                
                scaleLifespan = new Scale(comp2, SWT.NONE);
                scaleLifespan.setMaximum (4000);
                scaleLifespan.setMinimum (500);
                scaleLifespan.setIncrement(100);
                scaleLifespan.setPageIncrement(100);
                scaleLifespan.setSelection(2000);
                
                lifespanLabel = new Label(comp2, SWT.NONE);
                lifespanLabel.setText("Ant Lifespan: 2000");
		
                scaleLifespan.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                  int perspectiveValue = scaleLifespan.getSelection();
                  lifespanLabel.setText("Ant Lifespan: " + perspectiveValue);
                  onSliderLifespanChange(perspectiveValue);
                }
                });
                
                scaleFood = new Scale(comp2, SWT.NONE);
                scaleFood.setMaximum (400);
                scaleFood.setMinimum (50);
                scaleFood.setIncrement(50);
                scaleFood.setPageIncrement(50);
                scaleFood.setSelection(200);
                
                labelFoodRate = new Label(comp2, SWT.NONE);
                labelFoodRate.setText("Food Rate: 200");
		
                scaleFood.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                  int perspectiveValue = scaleFood.getSelection();
                  labelFoodRate.setText("Food Rate: " + perspectiveValue);
                  onSliderFoodChange(perspectiveValue);
                }
                });
                
                
                                
                labelFood = new Label(comp3, SWT.NONE);
                labelFood.setText("Net Resources: 0");
                
                labelGather = new Label(comp3, SWT.NONE);
                labelGather.setText("Gatherers Present: 0");
                
                labelBuilder = new Label(comp3, SWT.NONE);
                labelBuilder.setText("Builders Present: 0");
                
                
                labelLeaves = new Label(comp3, SWT.NONE);
                labelLeaves.setText("Leaves Present: 0");
                
                labelScore = new Label(comp3, SWT.NONE);
                labelScore.setText("Resources Collected: 0");
                
                if(DEBUG){
                    btnWind = new Button(comp4, SWT.NONE);
                    btnWind.setText("Wind");

                    btnWind.addListener(SWT.Selection, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                          invokeWind();
                        }

                    });

                    btnWater = new Button(comp4, SWT.NONE);
                    btnWater.setText("Water");

                    btnFungi = new Button(comp4, SWT.NONE);
                    btnFungi.setText("Plague");
                }
                
                // set up canvas
		canvas = new GLCanvas(comp, SWT.NONE, data);
		canvas.setCurrent();
		canvas.setMenu(initMenu());
		// Setup GL Context
		try {
			GLContext.useContext(canvas);
		} catch (LWJGLException e) {
			System.err.println("Context error!");
			System.exit(-1);
		}
		
		shell.setText("Ant Colony:");
		//shell.setSize(WIDTH, HEIGHT);
		shell.open();
		return shell;
	}
	
	public Rectangle getScreenArea(){
		return shell.getClientArea();
	}
	
	public Menu initMenu(){
		
		// Creates a pop-up menu
		Menu menu = new Menu(shell, SWT.POP_UP);
		// Add items to it
		final MenuItem item0 = new MenuItem( menu, SWT.PUSH);

		item0.setText(animate ? "Stop" : "Animate");
		
		item0.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				animate = !animate;
				item0.setText(animate ? "Stop" : "Animate");
			}
			
		});
                
               final MenuItem item1 = new MenuItem( menu, SWT.PUSH);

		item1.setText(snapMovement ? "Advanced" : "Snap");
		
		item1.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				snapMovement = !snapMovement;
                                
                                int evap;
                                
                                if(snapMovement){
                                    evap = 2;
                                }else{
                                    evap = 7;
                                }
                                
                                //CommonScents.setEVAP(evap);
                                scaleEvap.setSelection(evap);
                                labelEvap.setText("Evap Rate: " + evap);
                                
                                item1.setText(snapMovement ? "Advanced" : "Snap");
			}
			
		});
		
		MenuItem item2 = new MenuItem(menu, SWT.PUSH);
		item2.setText("Exit");
		item2.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				shell.dispose();
			}
		});	
		return menu;
	}
	
	protected abstract void initGL();
	protected abstract void destroyGL();
	protected abstract void onClockTick(int delta);
	protected abstract void renderGL();	
	protected abstract void resetGL();
        
        protected abstract void onLaySliderChange(int value);
        protected abstract void onEvapSliderChange(int value);
        protected abstract void onSliderLifespanChange(int value);
        protected abstract void onSliderBalanceChange(int value);
        protected abstract void onSliderFoodChange(double value);
        protected abstract void onSliderSpawnChange(double value);
        
        protected abstract void updateFoodCount();
        protected abstract void updateGatherCount();
        protected abstract void updateBuilderCount();
        protected abstract void updateLeafCount();
        protected abstract void updateScoreCount();
        
        protected abstract void invokeWind();
}
