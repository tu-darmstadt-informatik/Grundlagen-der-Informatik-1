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

public class StockTicker_Solution extends GraphicsProgram {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1866080068453559421L;

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

    // we keep the timers in this collection to start them after init()
    private Vector<Timer> timers;

    // the appr. time when the timers were startes
    private long startedAt;

    // the mapping of ticker symbols to colors
    protected Map<String, Color> colors;
    
    // 
    protected Map<String, GPoint> oldPos;

    /**
     * Create a new StockTicker.
     *
     * This will instantiate all timers with their calls to updateTicker and put
     * them into the timers collection.
     */
    public StockTicker_Solution() {
	timers = new Vector<Timer>(NR_TIMERS);

	final Map<String, Double> oldWorth = new HashMap<String, Double>();
	for (String symbol : SYMBOLS) {
	    // start somewhere near MAX_VALUE/2
	    oldWorth.put(symbol, MAX_VALUE * Math.random() / 2 + (MAX_VALUE / 4));
	}
	for (int i = 0; i < NR_TIMERS; i++) {
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
    
	oldPos = new HashMap<String, GPoint>();
	
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

    @Override
    public void init() {
	// add your call to the methods from 6.2 here
    	GLine line_y = new GLine (0 , 0 , 0 , getHeight() );
    	line_y.setColor(Color.black);
    	add(line_y);
    	
    	for (int i = 0; i < (getHeight() / 50) ; i++) 
    	{
    		GLabel label = new GLabel (Integer.toString(MAX_VALUE - 100*(10-i)), 5 , getHeight() - (50*i + 5) ) ;
    		add(label);
    		for (int j = 0; j < (getWidth() / 8) ; j++)
    		{
    		GLine line_x = new GLine ( j * 8, getHeight() - (50 * i), j * 8 + 5, getHeight() - (50 * i));
    		line_x.setColor(Color.gray);
    		add(line_x);
    		}
    	}
    	
    	GRect names = new GRect(getWidth()- 70,0,70,80);
    	names.setFillColor(Color.white);
    	names.setColor(Color.white);
    	names.setFilled(true);
    	add(names);
    	
    	
    	for (int i = 0 ; i < SYMBOLS.length ; i++)
    	{
    	GLabel symbols = new GLabel (SYMBOLS[i], getWidth() - 50 , 5 + ((i+1) * 10));
    	symbols.setColor(colors.get(SYMBOLS[i]));
    	add(symbols);
    	}
    	
	// uncomment this line when you have implemented 6.3
	startTicking();
    }

	/**
     * Update the graph of a single ticker symbol.
     *
     * @param symbol A textual representation of the ticker symbol.
     * @param worth The new worth of the company.
     */
    public void updateTicker(String symbol, double worth) {
    	
    	
    	
    	
    	if (oldPos.containsKey(symbol)) {
   		     		    		
    	GLine stock = new GLine (
    			oldPos.get(symbol).getX(), //old x
    			oldPos.get(symbol).getY(), //old y
    			(System.currentTimeMillis() - startedAt) * getWidth() / MAX_DURATION, //new x
    			getHeight () - (worth / 2));//new y
    	stock.setColor(colors.get(symbol));
    	add(stock);
    	
    	oldPos.put(symbol, new GPoint (
    			(System.currentTimeMillis() - startedAt) * getWidth()/ MAX_DURATION ,
    			getHeight() - (worth / 2)));
    	} else
    		
    	{
    	GLine stock = new GLine(0,
    			getHeight() - (worth / 2),
    			(System.currentTimeMillis() - startedAt) * getWidth() / MAX_DURATION,
    			getHeight() - (worth / 2));
    	stock.setColor(colors.get(symbol));
    	add(stock);
    	
    	oldPos.put(symbol, new GPoint (
    			(System.currentTimeMillis() - startedAt) * getWidth() / MAX_DURATION,
    			getHeight()- (worth / 2)));
    		}
    	
    	
    	}
    	
    

    /**
     * Instantiate a new StockTicker and start it.
     * @param args 
     */
    public static void main(String[] args) {
	new StockTicker_Solution().start(args);
    }

}
