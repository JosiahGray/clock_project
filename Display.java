/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @version 1.0 &nbsp;
*  @since 2016-02-14
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

/**
*	A custom JPanel that displays the clock time and settings in a format similar to that of a 7 segment display.
*/
public class Display extends JPanel
{

	/**
	*	Represents the digits to be displayed (any entry digit of a value other than 0-9 will not be displayed).
	*/
	int[] m_displayDigits;

	/**
	*	Represents whether a colon should be displayed or not.
	*/
	boolean m_displayColon;

	/**
	*	Represents the string to draw next to the clock display (am/pm).
	*/
	String m_displayMsg;

	String m_displayTime;

	String m_displayDate;

	int m_strSize;

  Font DSEG14MR;

	/**
	*	Constructor.  Sets the display variables.
	*	@post: 	m_displayDigits is intialized to an array of four 10s, m_display colon is initialized to false, and m_displayMsg is initialized to an empty String.
	*/
    public Display()
    {
       	//Set the display variables
			m_displayDigits = new int[] {10, 10, 10, 10, 10, 10};
			m_displayColon = false;
			m_displayMsg = "";
			m_displayTime = "";
			m_displayDate = "";
			m_strSize = 280;

			try {
				DSEG14MR = Font.createFont(Font.TRUETYPE_FONT, new File("DSEG14Modern-Regular.ttf"));
				DSEG14MR = DSEG14MR.deriveFont(Font.PLAIN, m_strSize);
			} catch (IOException|FontFormatException e) {
					 //Handle exception
			}

    }


    /**
    *	Returns the preferred size of the Display
    *	@return the preferred size of the display
    */
    public Dimension getPreferredSize()
    {
        return new Dimension(1500, 400);
    }


	/**
	*	Draws the clock display based off of the display information variables.
	*	This page in the Java documentation was referenced in creation of this method: https://docs.oracle.com/javase/tutorial/2d/geometry/arbitrary.html
	*	@param 	g the Graphics object to paint on
	*	@post 	the display will be drawn representing the current state of m_displayDigits, m_displayColon, and m_displayMsg
	*/
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		//Draw the background
		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle2D.Double(0, 0, 1500, 400));

		DSEG14MR = DSEG14MR.deriveFont(Font.PLAIN, m_strSize);
		g2.setColor(Color.RED);
		// g2.registerFont(DSEG7MR);
		g2.setFont(DSEG14MR);
		FontMetrics fm = g2.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(m_displayTime, g2);
		int x = (this.getWidth() - (int) r.getWidth()) / 2;
		int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();

		g2.drawString(m_displayTime, x, y);

		// //These are the coordinates for the vertical pieces of the 7 segment display
		// int xVertSeg[] = {20,  0,  0,  20, 40, 40};
		// int yVertSeg[] = { 0, 20, 80, 100, 80, 20};
		//
		// //These are the coordinates for the horizontal pieces of the 7 segment display
		// int xHorzSeg[] = {0,  20, 80, 100, 80, 20};
		// int yHorzSeg[] = {20,  0,  0,  20, 40, 40};
		//
		// if(m_displayColon)
		// {
		// 	g.fillOval(462, 100, 30, 30);
		// 	g.fillOval(462, 250, 30, 30);
		//
		// 	g.fillOval(912, 100, 30, 30);
		// 	g.fillOval(912, 250, 30, 30);
		// }
			DSEG14MR = DSEG14MR.deriveFont(Font.PLAIN, 2*m_strSize/10);
			g.setFont(DSEG14MR);
			g.drawString(m_displayMsg, x + 47*m_strSize/10, y + 5*m_strSize/21);
			g.drawString(m_displayDate, x + 4*m_strSize/3, y + 5*m_strSize/21);
/*
		for(int digit = 0; digit<6; digit++)
		{
			boolean[][] sevenSeg = digitToArray(m_displayDigits[digit]);
			int digitSpacing = 60 + digit * 225;
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
		} */
	}

	/**
	*	Sets m_displayDigits, m_displayColon, and m_displayMsg and refreshes the clock screen.
	*	@param 	digits a four digit number to be displayed on the clock face
	*	@param 	colon true if colon should be displayed, false if it should not
	*	@param 	msg the message to be displayed next to the digit display (am/pm)
	*/
	public void setDisplay(int[] digits, boolean colon, String msg, String timeStr, String dateStr)
	{
		m_displayDigits = digits.clone();
		m_displayColon = colon;
		m_displayMsg = msg;
		m_displayTime = timeStr;
		m_displayDate = dateStr;

		this.removeAll();
		this.revalidate();
		this.repaint();
	}

	/**
	*	Associates a given digit with it's 7 segment display representation as a 2d array of booleans.
	*	The array is organized as the first three horizontal segments descending and then the vertical segments moving down first then to the right.
	*	Anything other than a digit 0-9 will result in the function returning a blank representation (use this to display nothing).
	*	@param 	digit the digit to retrieve 7 segment representation of
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

	public void zoomIn()
	{
		if(m_strSize < 280)
			m_strSize += 20;
	}
	public void zoomOut()
	{
		if(m_strSize > 20)
		m_strSize -= 20;
	}
}
