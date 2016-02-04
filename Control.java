/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author
*  @version 0.1 &nbsp;
*  @since 2016-02-01
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Control extends JFrame implements ActionListener
{
	//Used for pop up messages
	JFrame popUpFrame;

	//The clock display panel
	JPanel displayPanel;

	//Represents the digits in the 7 segment display as a 2 digit array (4 arrays of 7, one for each digit of 7 segments)
	int m_displayDigits;

	//Represents whether a colon should be displayed or not
	boolean m_displayColon;

	//Represents the string to draw next to the clock display (am/pm)
	String m_displayMsg;

	/**
	*	Initializes the GUI and necessary variables with appropriate settings.
	*	
	*/
	public Control()
	{
		super("Hyperclock 2000");

		//Set the display variables
		//m_displayDigits 
		
		//Set layout (how panels are organized within frame)
		setLayout( new BorderLayout() );

		//Sets the size, width px X height px
		//Note: this includes the ~40px top bar
		setSize(1200, 600);

		//Puts the window in the middle of the screen
		setLocationRelativeTo(null);
		
		//Make the window not resizable
		setResizable(false);
		
		//Exit the application when the "X" button is pressed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Reveal ourselves to the world
		setVisible(true);

		//For pop up messages
		popUpFrame = new JFrame("Dialogue");

		displayPanel = new JPanel();
		this.getContentPane().add(displayPanel);
	}

	/**
	*	Draws the clock display based off of the display information variables.
	*	@param g the Graphics object to paint on
	*	@post the display will be drawn representing the current state of m_displayDigits, m_displayColon, and m_displayMsg
	*/
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		//Draw the background
		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle2D.Double(0, 0, 1200, 800));

		g2.setColor(Color.RED);

		//These are the coordinates for the vertical pieces of the 7 segment display
		int xVertSeg[] = {20,  0,  0,  20, 40, 40};
		int yVertSeg[] = { 0, 20, 80, 100, 80, 20};

		//These are the coordinates for the horizontal pieces of the 7 segment display
		int xHorzSeg[] = {0,  20, 80, 100, 80, 20};
		int yHorzSeg[] = {20,  0,  0,  20, 40, 40};

		//Draw the vertical Segments
		for(int xOffset = 50; xOffset <= 170; xOffset+=120)
		{
			for(int yOffset = 80; yOffset<= 200; yOffset+=120)
			{
				GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xVertSeg.length);

				polyline.moveTo (xVertSeg[0]+xOffset, yVertSeg[0]+yOffset);

				for (int i = 1; i < xVertSeg.length; i++) 
				{
				         polyline.lineTo(xVertSeg[i]+xOffset, yVertSeg[i]+yOffset);
				}

				polyline.closePath();

				g2.fill(polyline);
			}
		}

		//Draw the horizontal Segments
		for(int xOffset = 80; xOffset <= 80; xOffset+=135)
		{
			for(int yOffset = 50; yOffset <= 320; yOffset+=120)
			{
				GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xHorzSeg.length);

				polyline.moveTo (xHorzSeg[0]+xOffset, yHorzSeg[0]+yOffset);

				for (int i = 1; i < xHorzSeg.length; i++) 
				{
				         polyline.lineTo(xHorzSeg[i]+xOffset, yHorzSeg[i]+yOffset);
				}

				polyline.closePath();

				g2.fill(polyline);
			}
		}
	}

	/**
	*	Handles GUI event responses such as button presses.
	*	@param	event the event that has occurred
	*	@post	the appropriate response to the event will be executed
	*/
	public void actionPerformed(ActionEvent event)
	{
		switch(event.getActionCommand())
		{
			default:
				JOptionPane.showMessageDialog(popUpFrame, "ERROR: Unrecognized event");
				break;
		}
	}

	/**
	*	Sets m_displayDigits, m_displayColon, and m_displayMsg and refreshes the clock screen.
	*	@param digits a four digit number to be displayed on the clock face
	*	@param colon true if colon should be displayed, false if it should not
	*	@param msg the message to be displayed next to the digit display (am/pm)
	*/
	public void setDisplay(int digits, boolean colon, String msg)
	{
		m_displayDigits = digits%10000;
		m_displayColon = colon;
		m_displayMsg = msg;

		displayPanel.removeAll();
		displayPanel.revalidate();
  		displayPanel.repaint();
	}

	/**
	*	Associates a given digit with it's 7 segment display representation as a 2d array of booleans.
	*	The array is organized as the first three horizontal segments descending and then the vertical segments moving down first then to the right. 
	*	Anything other than a digit 0-9 will result in the function returning a blank representation (use this to display nothing).
	*	@param digit the digit to retrieve 7 segment representation of	
	*	@return a 2dimensional array of booleans representing the 7 segment representation of the given digit
	*
	*/
	private boolean[][] digitToArray(int digit)
	{
		boolean[][] sevenSeg = new boolean[2][];
		switch(digit)
		{
			case 0:	boolean[][] zero = 	{
									{true, false, true},
									{true, true, true, true}
								};
				break;
			case 1:	boolean[][] one = 	{	
									{false, false, false},
									{false, false, true, true}
								};
				break;
			case 2:	boolean[][] two = 	{
									{true, true, true},
									{false, true, true, false}
								};
				break;
			case 3:	boolean[][] three = 	{
									{true, true, true},
									{false, false, true, true}
								};
				break;
			case 4:	boolean[][] four = 	{
									{false, true, false},
									{true, false, true, true}
								};
				break;
			case 5:	boolean[][] five = 	{
									{true, true, true},
									{true, false, false, true}
								};
				break;
			case 6:	boolean[][] six = 	{
									{true, true, true},
									{true, true, false, true}
								};
				break;
			case 7:	boolean[][] seven = 	{
									{true, false, false},
									{true, false, true, true}
								};
				break;
			case 8:	boolean[][] eight = 	{
									{true, true, true},
									{true, true, true, true}
								};
				break;
			case 9:	boolean[][] nine = 	{
									{true, true, true},
									{true, false, true, true}
								};
				break;
			default: boolean[][] other = {
									{false, false, false},
									{false, false, false, false}
								};
				break;
		}
		return sevenSeg;
	}

}