import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class StockTicker extends GraphicsProgram {

    /**
	 * Solves a warning; not realy needed
	 */
	private static final long serialVersionUID = 1L;

	// maximum delay for timers to call updateTicker
    private static final int MAX_DURATION = 5000;

    // the maximum value to expect
    private static final int MAX_VALUE = 1000;

    // the number of calls to updateTicker
    private static final int NR_TIMERS = 3000;

    // the maximum difference of old and new value
    private static final int MAX_FLUCTUATION = 15;

    // the ticker symbols traded
    private static final String[] SYMBOLS = new String[] { "SNC", "EKDD",
							   "ATD", "AGN", "HEDM" };

    private Map<String, acm.graphics.GPoint> oldPos;
    
    // we keep the timers in this collection to start them after init()
    private Vector<Timer> timers;

    // the appr. time when the timers were startes
    private long startedAt;

    // the mapping of ticker symbols to colors
    protected Map<String, Color> colors;

    /**
     * Create a new StockTicker.
     *
     * This will instantiate all timers with their calls to updateTicker and put
     * them into the timers collection.
     */
    public StockTicker()
    {
    	timers = new Vector<Timer>(NR_TIMERS);

    	final Map<String, Double> oldWorth = new HashMap<String, Double>();
    	for (String symbol : SYMBOLS)
    	{
    		// start somewhere near MAX_VALUE/2
    		oldWorth.put(symbol, MAX_VALUE * Math.random() / 2 + (MAX_VALUE / 4));
    	}
    	for (int i = 0; i < NR_TIMERS; i++)
    	{
    		// instantiate a timer with a random delay within 0..MAX_DURATION
    		Timer timer = new Timer((int) (Math.random() * MAX_DURATION),
				    	  new ActionListener() {
						@Override
					    public void actionPerformed(ActionEvent e) {
					    // get a random ticker symbol
					    String symbol = SYMBOLS[(int) (Math.random() * SYMBOLS.length)];

					    // calculate the new worth
					    double newWorth = oldWorth.get(symbol)
						+ MAX_FLUCTUATION * 2
						* (Math.random() - 0.5);

					    // do not increase beyond MAX_VALUE
					    if (newWorth > MAX_VALUE) {
						newWorth -= MAX_FLUCTUATION;
					    }
					    // save the new value for next time
					    oldWorth.put(symbol, newWorth);

					    updateTicker(symbol, newWorth);
					}
				    });
    		// only fire once
    		timer.setRepeats(false);
    		timers.add(timer);
    	}

    	// pre-allocate the color mappings
    	colors = new HashMap<String, Color>();
    	colors.put("SNC", Color.BLUE);
    	colors.put("EKDD", Color.ORANGE);
    	colors.put("ATD", Color.MAGENTA);
    	colors.put("AGN", Color.GRAY);
    	colors.put("HEDM", Color.RED);
	
    	//initialize oldPos HashMap  	
    	oldPos = new HashMap<String, acm.graphics.GPoint>();
    }

    /**
     * Start the timers and set the startTime.
     */
    public void startTicking() {
	startedAt = System.currentTimeMillis();
	for (Timer timer : timers) {
	    timer.start();
	}
    }

    /**
     * Correct Height
     * Delete this when acm is fixed
     */
    @Override
    public int getHeight()
    {
    	return super.getHeight() - 30;
    }
    
    /**
     * Draw Interface:	- Y-Axis
     * 					- X-Lines every 50 pixel
     * 					- Value of every line
     * 					- White Rect
     * 					- Names of TickerSymbols in right Color
     */
    @Override
    public void init()
    {
    	{	//draw horizontal line
    		GLine hline = new GLine(0,0,0,getHeight());
    		add(hline); //draw it
    	}
    	
    	final int distance = 50; //50px distance between vlines
    	
    	{	//draw vertical lines
    		for(int i=0; i <= (getHeight()/distance);i++) //lines
    		{
    			for(int j=0; j < (getWidth()/10);j++) //small lines every 10px with leght 5
    			{
    				//many small lines per "line"
    				GLine vline = new GLine(	j*10+1, //+1 to not override the x-achsis
    											getHeight()-i*distance,
    											j*10+5+1,
    											getHeight()-i*distance);
    				vline.setColor(Color.LIGHT_GRAY); //light-gray!!!
    				add(vline); //draw it
    			}
    		
    			//once per line
    			GLabel label = new GLabel(	String.valueOf((int)(((double)MAX_VALUE/(double)getHeight())*i*distance)),
    										3,
    										getHeight()-i*distance-2);
    			add(label); //draw it
    		}
    	}
    	
    	{	//Draw white Rect
    		GRect rect = new GRect(getWidth()-120,0,120,120);
    		rect.setColor(Color.WHITE);		//color
    		rect.setFilled(true);			//do fill
    		add(rect);						//draw it
    	}
    	
    	{	//Draw Labels in Rect
    		for(int i=0;i < SYMBOLS.length; i++)
    		{
    			//new label with symbol
    			GLabel label = new GLabel(SYMBOLS[i],getWidth()-100,i*20+20);
    			//set the right color
    			label.setColor(colors.get(SYMBOLS[i])); //load color here
    			//draw it
    			add(label);
    		}
    	}
    		
	// uncomment this line when you have implemented 6.3 - i have
	startTicking();
    }
    
    /**
     * Converts a X-Value relative to startedAt to an Pixel-Value
     * relative to MAX_DURATION and Window-Width
     * 
     * @param x X-Value relative to startedAT 
     * @return Pixel-Value which represents given X-Value
     */
    public double XVal2XPix(double x)
    {
    	//pixel_per_timepassed
    	return ((double)getWidth()/(double)MAX_DURATION)*x; 
    }
    
    /**
     * Converts a Y-Value(worth) to an Pixel-Value
     * relative to MAX_VALUE and Window-Height
     * 
     * @param y Y-Value(worth) 
     * @return Pixel-Value which represents given Y-Value
     */
    public double YVal2YPix(double y)
    {
    	//pixel_per_valuepoint
    	return ((double)getHeight()/(double)MAX_VALUE)*y;
    }

    /**
     * Update the graph of a single ticker symbol.
     *
     * @param symbol A textual representation of the ticker symbol.
     * @param worth The new worth of the company.
     */
    public void updateTicker(String symbol, double worth)
    {
    	//Calculate new Coordinates with time passed since start(x) and worth(y)
    	double x_val = XVal2XPix(System.currentTimeMillis() - startedAt);
    	double y_val = YVal2YPix(worth);
    	
    	//Get last Value of given ticker-symbol
    	GPoint oldgp = oldPos.get(symbol);
    	
    	//No old value stored at first call for every ticker-symbol
    	if(oldgp == null)
    	{
    		//create new Point with actual values instead
    		oldgp = new GPoint(x_val/*1*/,y_val);//x_val,y_val);
    	}
    	
    	//line from oldpos to newpos
    	GLine line = new GLine(	oldgp.getX(), //oldpos
    							oldgp.getY(),
    							x_val,        //newpos
    							y_val);
    	
    	//set the right color for every ticker-symbol
    	line.setColor(colors.get(symbol));
    	
    	//paint it
    	add(line);
    	
    	//store new pos as oldpos for next call.
    	oldPos.put(symbol, new GPoint(x_val,y_val));
    }

    /**
     * Instantiate a new StockTicker and start it.
     */
    public static void main(String[] args) {
	new StockTicker().start(args);
    }

}
