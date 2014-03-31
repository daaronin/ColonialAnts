package colonialdisplay;


import colonialants.CommonScents;
import colonialants.Environment.AntType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
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

public abstract class SkelLWJGL {
	/* animation indicator for menu selector */
	boolean animate = false;
        boolean snapMovement = false;

	// SVGA 800x600
	// XVGA 1024x768
	public static final int SCREEN_WIDTH = 865, 
			SCREEN_HEIGHT = 744;
	
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
	GLData data = new GLData();
	GLCanvas canvas;
        
        Scale scale;
        Scale scaleLay;
        Scale scaleEvap;
                        
	Label gathererLabel;
        Label builderLabel;
        Label labelFood;
        Label labelAnts;
        Label labelLeaves;
        Label labelScore;
        Label labelEvap;
        Label labelLay;
        
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
	
	public void updateFPS(){
		if (getTime() - lastFPS > 1000){
			shell.setText("Ant Colony: D: " + getDelta() + " | FPS: " + fps);
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
					updateFPS();
					
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

		// Create a composite
		comp = new Composite(shell, SWT.BORDER);
		comp.setLayout(new FillLayout());
                
                comp2 = new Composite(shell, SWT.BORDER);
                FillLayout fillLayout = new FillLayout();
                fillLayout.type = SWT.VERTICAL;
                fillLayout.spacing = 1;
		comp2.setLayout(fillLayout);
                
                FormData data1 = new FormData();
		data1.left = new FormAttachment(0,0);
		data1.top = new FormAttachment(0,0);
		data1.right = new FormAttachment(75,0);
                data1.bottom = new FormAttachment(90,0);
                comp.setLayoutData(data1);
                
                FormData data2 = new FormData();
		data2.left = new FormAttachment(comp, 5);
		data2.right = new FormAttachment(100, -5);
		data2.top = new FormAttachment(0, 5);
                data1.bottom = new FormAttachment(90,0);
                comp2.setLayoutData(data2);
		
		// Depth size 
		data.depthSize = 1;
		data.doubleBuffer = true;
                
                final Scale gathererScale = new Scale(comp2, SWT.NONE);
                gathererScale.setMaximum (10);
                gathererScale.setIncrement(1);
                gathererScale.setPageIncrement(1);
                
                gathererLabel = new Label(comp2, SWT.NONE);
                gathererLabel.setText("Gathering Ants: 0");
                
                gathererScale.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                  int perspectiveValue = gathererScale.getSelection();
                  gathererLabel.setText("Gathering Ants: " + perspectiveValue);
                  onSliderChange(AntType.GATHERER, perspectiveValue);
                }
                });
                
                
                final Scale builderScale = new Scale(comp2, SWT.NONE);
                builderScale.setMaximum (10);
                builderScale.setPageIncrement(1);
                
                builderLabel = new Label(comp2, SWT.NONE);
                builderLabel.setText("Builder Ants: 0");
		
                builderScale.addListener(SWT.Selection, new Listener() {
                @Override
                public void handleEvent(Event event) {
                  int perspectiveValue = builderScale.getSelection();
                  builderLabel.setText("Builder Ants: " + perspectiveValue);
                  onSliderChange(AntType.BUILDER, perspectiveValue);
                }
                });
                
                scaleEvap = new Scale(comp2, SWT.NONE);
                scaleEvap.setMaximum (20);
                scaleEvap.setMinimum (1);
                scaleEvap.setIncrement(1);
                scaleEvap.setPageIncrement(1);
                scaleEvap.setSelection(7);
                
                labelEvap = new Label(comp2, SWT.NONE);
                labelEvap.setText("Evap Rate: 7");
		
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
                scaleLay.setMaximum (600);
                scaleLay.setMinimum (1);
                scaleLay.setIncrement(50);
                scaleLay.setPageIncrement(50);
                scaleLay.setSelection(400);
                
                labelLay = new Label(comp2, SWT.NONE);
                labelLay.setText("Lay Rate: 400");
		
                scaleLay.addListener(SWT.Selection, new Listener() {
                    @Override
                    public void handleEvent(Event event) {
                      int perspectiveValue = scaleLay.getSelection();
                      labelLay.setText("Lay Rate: " + perspectiveValue);
                      //O(perspectiveValue);
                      onLaySliderChange(perspectiveValue);
                    }

                });
                
                labelFood = new Label(comp2, SWT.NONE);
                labelFood.setText("Resources Gathered: ");
                
                labelAnts = new Label(comp2, SWT.NONE);
                labelAnts.setText("Ants Present: ");
                
                labelLeaves = new Label(comp2, SWT.NONE);
                labelLeaves.setText("Leaves Present: ");
                
                labelScore = new Label(comp2, SWT.NONE);
                labelScore.setText("Score(Resources Collected): ");
                
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
                                
                                CommonScents.setEVAP(evap);
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
        
        protected abstract void onSliderChange(AntType TYPE, int value);
        protected abstract void onLaySliderChange(int value);
        protected abstract void onEvapSliderChange(int value);
        
        protected abstract void updateFoodCount();
        protected abstract void updateAntCount();
        protected abstract void updateLeafCount();
        protected abstract void updateScoreCount();
}
