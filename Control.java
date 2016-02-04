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

	/**
	*	Initializes the GUI and necessary variables with appropriate settings.
	*	
	*/
	public Control()
	{
		super("Hyperclock 2000");
		
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

		JPanel drawPanel = new JPanel();
		this.getContentPane().add(drawPanel);
	}

	/**
	*	Draws the clock display based off of the variable m_displayInfo.
	*	@param g the Graphics object to paint on
	*	@post the display will be drawn representing the current state of m_displayInfo
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
}