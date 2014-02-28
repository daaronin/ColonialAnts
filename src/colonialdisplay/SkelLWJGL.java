package colonialdisplay;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GLContext;

public abstract class SkelLWJGL {
	/* animation indicator for menu selector */
	boolean animate = false;

	// SVGA 800x600
	// XVGA 1024x768
	public static final int SCREEN_WIDTH = 800, 
			SCREEN_HEIGHT = 800;
	
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
	GLData data = new GLData();
	GLCanvas canvas;
	
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
			shell.setText("Ant Colony: " + fps);
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
		shell.setLayout(new FillLayout());
		shell.setSize(SCREEN_WIDTH, SCREEN_WIDTH);
		shell.setText("Ant Colony: ");

		// Create a composite
		comp = new Composite(shell, SWT.BORDER);
		comp.setLayout(new FillLayout());
		
		// Depth size 
		data.depthSize = 1;
		data.doubleBuffer = true;
		
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

		item0.setText(animate ? "Stop": "Animate");
		
		item0.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				animate = !animate;
				item0.setText(animate ? "Stop": "Animate");
			}
			
		});
		
		MenuItem item1 = new MenuItem(menu, SWT.PUSH);
		item1.setText("Exit");
		item1.addListener(SWT.Selection, new Listener(){
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
}
