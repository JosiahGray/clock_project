/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author  Audrey Evans <audreyevans@ku.edu>
*  @author 	Hari Ramanan <hramanan@ku.edu>
*  @version 1.0 &nbsp;
*  @since 2016-02-14
*
*/

import java.util.Timer;
import java.util.TimerTask;

/**
*	A custom TimerTask that keeps track of the time and settings, running every second.  This class also uses the GUI Control object to display the information and get input from the user.
*/
public class CoolClockTimer extends TimerTask
{
	/**
	*	The current time in seconds.  Always has a value between 0-86399 (86400 being the number of seconds in a standard 24 hour day).
	*/
	int time;
	
	/**
	*	The instance of the primary GUI class Control, which inherits from JFrame.
	*/
	Control myGUI;

	/**
	*	Represents whether time incrementation should be paused, true indicating a paused state.
	*/
	boolean pause;

	/**
	*	Represents whether time should be displayed in 24 hour (military time) format, or 12 hour am/pm format.  True indicates military format.
	*/
	boolean military_time;
	
	/**
	* 	Constructor which sets variables and creates a new instance of Control for display purposes.
 	* 	@post 	time is set to 0, military_time is set to true, pause is set to false, new Control instance myGUI instantiated
    */
	public CoolClockTimer()
	{
		myGUI = new Control(this);
		time = 0;
		military_time = true;
		pause = false;
	}
	
	/**
 	* 	This function is called every second by a Timer.  It calls refresh to update the display to the current state of the clock then adds 1 second to the time.
 	* 	@pre 	clock settings and variables have all been set
 	*	@post 	the display reflects the status of the clock at the time this function was called and the time has been incremented by 1 second.
 	*/
	public void run()
	{
		if(!pause)
		{
			refresh();
			addTime(1);
		}
	}

	/**
	*	Updates the display according to the current time and display settings
	*	@post 	display is refreshed to reflect the current time and settings
	*/
	public void refresh()
	{
		int[] digits;
		myGUI.setDisplay(ConvertSeconds(), true, TwelveHourPm()); //new int[] {1,2,0,0,0,0}
	}
	
	/**
	*	Switches the pause flag to it's opposite
	*	@post 	pause is set to the opposite value
	*/
	public void togglePause()
	{
		pause = !pause;
	}
	

	/**
	*	Switches the military time flag to it's opposite
	*	@post 	military_time is set to the opposite value
	*/
	public void toggleHourFormat()
	{
		military_time = !military_time;
		refresh();
	}
	
	/**
 	 * 	Converts seconds into the clock format
 	 * 	@return integer array that represents each digit of the clock
 	 */
	public int[] ConvertSeconds()
	{
		int[] digit_time = {0,0,0,0,0,0};
		int total_seconds = time;
		int seconds;
		int mins;
		int hours;
		
		if(military_time)
		{
			hours = total_seconds / 3600;
			digit_time[0] = hours / 10;
			digit_time[1] = hours % 10;
			total_seconds = total_seconds - (hours * 3600);
			mins = total_seconds / 60;
			digit_time[2] = mins / 10;
			digit_time[3] = mins % 10;
			total_seconds = total_seconds - (mins * 60);
			seconds = total_seconds;
			digit_time[4] = seconds / 10;
			digit_time[5] = seconds % 10;
			
		}
		else
		{
			hours = total_seconds / 3600;
			total_seconds = total_seconds - (hours * 3600);
			mins = total_seconds / 60;
			digit_time[2] = mins / 10;
			digit_time[3] = mins % 10;
			total_seconds = total_seconds - (mins * 60);
			seconds = total_seconds;
			digit_time[4] = seconds / 10;
			digit_time[5] = seconds % 10;
			if(hours > 12)
			{
				hours = hours - 12;
			}
			digit_time[0] = hours / 10;
			digit_time[1] = hours % 10;
			if((digit_time[0] == 0) && (digit_time[1] == 0))
			{
				digit_time[0] = 1;
				digit_time[1] = 2;
			}
		}
		
		/* For testing purposes, not needed for production level code.
		for(int i = 0; i < 6; i++)
		{
			System.out.print(time[i]);
			if((i == 1) || (i == 3))
			{
				System.out.print(":");
			}
		}
		*/

		return digit_time;
		
	}
	
	/**
	*	Returns the appropriate string to print on the display based on the current time and hour format (military_time)
	*	@return the appropriate string to print
	*/
	public String TwelveHourPm()
	{
		if(military_time)
			return ("");
		else if(time >= 43200)
			return("pm");
		else
			return("am");
	}


	/**
 	* 	Takes in a change in the time and converts that into seconds
 	* 	@param 	amt the change in time
 	*	@post 	the time is shifted by amt seconds and is still in the valid second range (0-86399)
 	*/
	public void addTime(int amt)
	{
		time = ((time + amt) % 86400 + 86400) % 86400;
		refresh();
	}
	
}