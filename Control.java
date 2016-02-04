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

	//Represents the digits to be displayed (any entry not 0-9 will not be displayed)
	int[] m_displayDigits;

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
		m_displayDigits = new int[] {10, 10, 10, 10};
		m_displayColon = false;
		m_displayMsg = "";
		
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
	*	This page in the Java documentation was referenced in creation of this method: https://docs.oracle.com/javase/tutorial/2d/geometry/arbitrary.html
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

		for(int digit = 0; digit<4; digit++)
		{
			boolean[][] sevenSeg = digitToArray(m_displayDigits[digit]);
			int digitSpacing = 150 + digit * 225;
			//Draw the vertical Segments
			for(int xIter = 0; xIter < 2; xIter++)
			{
				int xOffset = digitSpacing + xIter * 120;
				for(int yIter = 0; yIter < 2; yIter++)
				{
					if(sevenSeg[1][xIter*2+yIter])
					{
						int yOffset = 80 + yIter*120;
						GeneralPath segment = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xVertSeg.length);

						segment.moveTo (xVertSeg[0]+xOffset, yVertSeg[0]+yOffset);

						for (int i = 1; i < xVertSeg.length; i++) 
						{
						         segment.lineTo(xVertSeg[i]+xOffset, yVertSeg[i]+yOffset);
						}

						segment.closePath();

						g2.fill(segment);
					}
				}
			}

			//Draw the horizontal Segments
			int xOffset = digitSpacing+30;
			for(int yIter = 0; yIter < 3; yIter++)
			{
				int yOffset = 50 + yIter * 120;
				if(sevenSeg[0][yIter])
				{
					GeneralPath segment = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xHorzSeg.length);
					segment.moveTo (xHorzSeg[0]+xOffset, yHorzSeg[0]+yOffset);
					for (int i = 1; i < xHorzSeg.length; i++) 
					{
					         segment.lineTo(xHorzSeg[i]+xOffset, yHorzSeg[i]+yOffset);
					}
					segment.closePath();
					g2.fill(segment);
				}
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
	public void setDisplay(int[] digits, boolean colon, String msg)
	{
		m_displayDigits = digits.clone();
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
			case 0:	sevenSeg = new boolean[][] {
									{true, false, true},
									{true, true, true, true}
								};
				break;
			case 1:	sevenSeg = new boolean[][] {	
									{false, false, false},
									{false, false, true, true}
								};
				break;
			case 2:	sevenSeg = new boolean[][] {
									{true, true, true},
									{false, true, true, false}
								};
				break;
			case 3:	sevenSeg = new boolean[][] {
									{true, true, true},
									{false, false, true, true}
								};
				break;
			case 4:	sevenSeg = new boolean[][] {
									{false, true, false},
									{true, false, true, true}
								};
				break;
			case 5:	sevenSeg = new boolean[][] {
									{true, true, true},
									{true, false, false, true}
								};
				break;
			case 6:	sevenSeg = new boolean[][] {
									{true, true, true},
									{true, true, false, true}
								};
				break;
			case 7:	sevenSeg = new boolean[][] {
									{true, false, false},
									{true, false, true, true}
								};
				break;
			case 8:	sevenSeg = new boolean[][] {
									{true, true, true},
									{true, true, true, true}
								};
				break;
			case 9:	sevenSeg = new boolean[][] {
									{true, true, true},
									{true, false, true, true}
								};
				break;
			default: sevenSeg = new boolean[][] {
									{false, false, false},
									{false, false, false, false}
								};
				break;
		}
		return sevenSeg;
	}

}