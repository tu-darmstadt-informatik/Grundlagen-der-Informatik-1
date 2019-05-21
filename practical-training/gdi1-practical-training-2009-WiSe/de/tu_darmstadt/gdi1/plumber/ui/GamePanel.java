/*============================================================================*/

package de.tu_darmstadt.gdi1.plumber.ui;

/*============================================================================*/

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.tu_darmstadt.gdi1.plumber.exceptions.InternalFailureException;
import de.tu_darmstadt.gdi1.plumber.exceptions.InvalidOperationException;
import de.tu_darmstadt.gdi1.plumber.exceptions.ParameterOutOfRangeException;

/*============================================================================*/

/**
 * Class representing a basic Plumber game field display that you can extend for
 * your own solution by deriving from it.
 * 
 * @author Steven Arzt, Oren Avni
 * @version 1.2
 */
public abstract class GamePanel extends JPanel implements MouseListener {
	
	/* ======================================================================== */

	private static final long serialVersionUID = -1037100806444236904L;

	/* ======================================================================== */

	// the list of entities
	private Vector<JButton> entities = null;
	
	// the loaded images, accessed by name
	private HashMap<String, ImageIcon> images = null;
	
	// the parent window
	private GameWindow parentWindow = null;
	
	// The current layout width and height based on the level
	private int layoutWidth = 0, layoutHeight = 0;
	
	// switch for enabling or disabling automatic sizing
	private boolean autosize = false;

	/* ======================================================================== */

	/**
	 * Creates a new instance of the GamePanel class
	 * 
	 * @param theParentWindow
	 *            The parent window on which this panel is placed
	 */
	public GamePanel(GameWindow theParentWindow) {
		super();

		// Set the reference
		parentWindow = theParentWindow;

		// Create the internal objects
		entities = new Vector<JButton>();
		images = new HashMap<String, ImageIcon>();
	}

	/* ======================================================================== */
	
	
	/**
	 * Enables or disables automatic sizing
	 * @param Autosize True if automatic sizing shall be enabled, otherwise
	 * false
	 */
	public void setAutosize
		(boolean Autosize)
	{
		autosize = Autosize;
		resizePanel ();
	}
	
	/* ======================================================================== */
	
	/**
	 * Resizes the game panel to match the current contents
	 */
	private void resizePanel ()
	{
		int oldWidth = getWidth (), oldHeight = getHeight (); 
		int width = 0, height = 0;
		for (int i = 0; i < entities.size (); i++)
		{
			JButton btn = entities.get (i);
			int icoWidth = btn.getIcon ().getIconWidth () + 2;
			int icoHeight = btn.getIcon ().getIconHeight () + 2;
			
			width = Math.max (width, icoWidth);
			height = Math.max (height, icoHeight);
			
			// +2px border size (one pixel per side)
			if (autosize)
				btn.setPreferredSize (new Dimension (icoWidth, icoHeight));
			else
				btn.setSize (icoWidth, icoHeight);
		}
		
		width = layoutWidth * width;
		height = layoutHeight * height;
		setSize (width, height);
		
		if (oldWidth != width || oldHeight != height)
		{
			if (autosize)
				parentWindow.pack ();
			panelResized ();
		}
	}
	
	/* ======================================================================== */

	/**
	 * Draws the game field once again and updates its graphical representation
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	public void redraw() throws InternalFailureException {
		// The following code contains a few tricks concerning Swing and
		// threading. I do not require anyone to understand it - mainly
		// workarounds for the mess Sun has done...
		
		if (Thread.currentThread ().getName ().contains ("AWT-EventQueue"))
		{
			clearFrame();
			setGamePanelContents();
			resizePanel ();
		}
		else
			try
			{
				SwingUtilities.invokeAndWait (new Runnable ()
				{
					//@Override
					public void run ()
					{
						clearFrame();
						setGamePanelContents();
						resizePanel ();
					}	
				});
			}
			catch (Exception ex)
			{
				throw new InternalFailureException (ex);
			}
	}

	/* ======================================================================== */

	/**
	 * Method for setting the game panel's contents. Override this method to
	 * place your game entities like walls, crates etc. on the game field.
	 */
	protected abstract void setGamePanelContents();

	/* ======================================================================== */
	
	/**
	 * Clears the game frame by removing all entity buttons and recreating the
	 * corresponding internal data structures. This method can also be used for
	 * initialization purposes.
	 */
	private void clearFrame() {
		for (int i = 0; i < entities.size(); i++) {
			JButton btn = entities.get(i);
			btn.setVisible(false);
			remove(btn);
			synchronized (entities)
			{
				entities.remove(btn);
			}
			clearFrame();
			return;
		}			
	}

	/* ======================================================================== */

	/**
	 * Notifies the game panel that a new level has been loaded
	 * 
	 * @param width
	 *            The width of the level just loaded
	 * @param height
	 *            The height if the level just loaded
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	void notifyLevelLoaded(int width, int height)
			throws ParameterOutOfRangeException, InternalFailureException {
		// Check the parameters
		if (width <= 0)
			throw new ParameterOutOfRangeException("Game Panel width negative");
		if (height <= 0)
			throw new ParameterOutOfRangeException("Game Panel height negative");

		// Initialize the layout
		layoutWidth = width;
		layoutHeight = height;
		setLayout(new GridLayout(height, width));
		redraw();
	}

	/* ======================================================================== */

	/**
	 * Returns whether there are already entities on this game panel
	 * 
	 * @return True if there are already entities on this game panel, otherwise
	 *         false
	 */
	protected boolean hasEntities() {
		return entities.size() > 0;
	}

	/* ======================================================================== */

	/**
	 * Checks whether a specific image has already been registered with the game
	 * panel
	 * 
	 * @param identifier
	 *            The unique image identifier
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 */
	protected boolean isImageRegistered(String identifier)
			throws ParameterOutOfRangeException {
		// Check the parameter
		if (identifier == null || identifier.equals(""))
			throw new ParameterOutOfRangeException("Identifier invalid");
		return images.containsKey(identifier);
	}

	/* ======================================================================== */

	/**
	 * Registers a new image in this game panel. Please note that the identifier
	 * must be unique, so the operation will fail if an image with the specified
	 * identifier has already been registered.
	 * 
	 * @param identifier
	 *            The new image's unique identifier
	 * @param fileName
	 *            The file name from which to load the image file
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InvalidOperationException
	 *             Thrown if this operation is not permitted due to the object's
	 *             current state
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	protected void registerImage(String identifier, String fileName)
			throws ParameterOutOfRangeException,
				   InvalidOperationException,
				   InternalFailureException {
		try
		{
			// Check for file existence
			File f = new File (fileName);
			if (!f.exists ())
				throw new InvalidOperationException
					("File " + fileName + " not found");
			
			StringBuilder builder = new StringBuilder ();
			builder.append ("file:");
			builder.append (File.separator);
			builder.append (f.getCanonicalPath ());
			registerImage(identifier, new URL (builder.toString ()));
		}
		catch (MalformedURLException ex)
		{
			throw new ParameterOutOfRangeException ("fileName", ex);
		}
		catch (IOException ex)
		{
			throw new InternalFailureException (ex);
		}
	}
	
	/* ======================================================================== */

	/**
	 * Registers a new image in this game panel. Please note that the identifier
	 * must be unique, so the operation will fail if an image with the specified
	 * identifier has already been registered.
	 * 
	 * @param identifier
	 *            The new image's unique identifier
	 * @param fileName
	 *            The URL from which to load the image file
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InvalidOperationException
	 *             Thrown if this operation is not permitted due to the object's
	 *             current state
	 */
	protected void registerImage(String identifier, URL fileName)
			throws ParameterOutOfRangeException, InvalidOperationException {
		// Check the parameters
		if (identifier == null || identifier.equals(""))
			throw new ParameterOutOfRangeException("Identifier invalid");
		if (fileName == null || fileName.equals(""))
			throw new ParameterOutOfRangeException("FileName invalid");

		if (isImageRegistered(identifier))
			throw new InvalidOperationException(
					"An image with this identifier "
							+ "has already been registered");
		
		images.put(identifier, new ImageIcon(fileName));
	}

	/* ======================================================================== */
	
	/**
	 * Unregisters a previously registered image from this game panel. If the
	 * specified identifier does not exist, an exception is thrown.
	 * @param identifier
	 *            The image's unique identifier
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InvalidOperationException
	 *             Thrown if this operation is not permitted due to the object's
	 *             current state
	 */
	protected void unregisterImage(String identifier)
			throws ParameterOutOfRangeException, InvalidOperationException {
		// Check the parameters
		if (identifier == null || identifier.equals(""))
			throw new ParameterOutOfRangeException("Identifier invalid");

		if (!isImageRegistered(identifier))
			throw new InvalidOperationException(
					"An image with this identifier "
							+ "has not been registered");
		images.remove (identifier);
	}
	
	/* ======================================================================== */

	/**
	 * Places a graphical entity on the game panel.
	 * 
	 * @param imageIdentifier
	 *            The identifier of a previously registered image that will be
	 *            used for rendering the entity
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @return The placed entity (JButton).
	 */
	protected JButton placeEntity(String imageIdentifier)
			throws ParameterOutOfRangeException {

		// Check the parameters
		if (imageIdentifier == null || imageIdentifier.equals(""))
			throw new ParameterOutOfRangeException("ImageIdentifier invalid");

		// Get the image icon
		ImageIcon img = images.get(imageIdentifier);
		if (img == null)
			throw new RuntimeException("An image with the identifier "
					+ imageIdentifier + " could not be found");

		return(
				placeEntity(
						img
				)
		);
	}

	/**
	 * Places a graphical entity on the game panel.
	 * 
	 * @param image
	 *            An image which will be used for the entity.
	 * @return The placed entity (JButton).
	 */
	protected JButton placeEntity(Image image){
		return(
				placeEntity(
						new ImageIcon(
								image
						)
				)
		);
	}

	/**
	 * Places a graphical entity on the game panel.
	 * 
	 * @param icon
	 *            An icon which will be used for the entity.
	 * @return The placed entity (JButton).
	 */
	protected JButton placeEntity(Icon icon){
		// Create the visual entity
		JButton btn = new JButton();

		btn.setMargin(
				new Insets(
						0 , 0 , 0 , 0
				)
		);

		synchronized(entities){
			entities.add(btn);
		}

		btn.addKeyListener(parentWindow);
		btn.addMouseListener(this);
		btn.setIcon(icon);

		// add it
		add(btn);
		btn.requestFocus();

		return( btn );
	}


	/* ======================================================================== */

	/**
	 * This method is called whenever an entity on the game field is clicked
	 * 
	 * @param positionX
	 *            The x coordinate of the entity that was clicked
	 * @param positionY
	 *            The y coordinate of the entity that was clicked
	 */
	protected abstract void entityClicked(int positionX, int positionY);

	/* ======================================================================== */
	
	/**
	 * This method is called whenever the game panel is resized
	 */
	protected abstract void panelResized ();
	
	/* ======================================================================== */

	// @Override
	/**
	 * This method handles the "mouse clicked" mouse event by converting the
	 * event into a call to <em>entityClicked(int, int)</em>.
	 * 
	 * @param evt the mouse event caused by clicking "somewhere" on the screen
	 * @see #entityClicked(int, int)
	 */
	public void mouseClicked(MouseEvent evt) {
		if (!hasEntities())
			return;
		// retrieve first button
		JButton refBtn = entities.get(0);
		
		// iterate buttons until right one was found
		for (int i = 0; i < entities.size(); i++) {
			JButton btn = entities.get(i);
			if (evt.getSource() == btn) {
				// determine x and y position
				int posX = evt.getXOnScreen();
				posX = posX - (int) this.getLocationOnScreen().getX();

				int posY = evt.getYOnScreen();
				posY = posY - (int) this.getLocationOnScreen().getY();

				// pass message along
				entityClicked(posX / refBtn.getWidth(), posY
						/ refBtn.getHeight());
				
				// done!
				evt.consume();
				break;
			}
		}
	}

	/* ======================================================================== */

	public void mousePressed(MouseEvent arg0) {
		// nothing to be done here
	}

	/* ======================================================================== */

	public void mouseReleased(MouseEvent arg0) {
		// nothing to be done here
	}

	/* ======================================================================== */

	public void mouseEntered(MouseEvent arg0) {
		// nothing to be done here
	}

	/* ======================================================================== */

	public void mouseExited(MouseEvent arg0) {
		// nothing to be done here
	}

	/* ======================================================================== */

	/**
	 * Gets the parent window for this game field
	 * 
	 * @return This game field's parent window
	 */
	public GameWindow getParentWindow() {
		return parentWindow;
	}

	/* ======================================================================== */

}

/* ============================================================================ */
