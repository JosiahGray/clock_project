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
	Display displayPanel;

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

		displayPanel = new Display();
		this.getContentPane().add(displayPanel);
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
	*	Calls setDisplay in displayPanel
	*	@param digits a four digit number to be displayed on the clock face
	*	@param colon true if colon should be displayed, false if it should not
	*	@param msg the message to be displayed next to the digit display (am/pm)
	*/
	public void setDisplay(int[] digits, boolean colon, String msg)
	{
		displayPanel.setDisplay(digits, colon, msg);
	}
}