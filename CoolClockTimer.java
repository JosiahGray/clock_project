/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author  Audrey Evans <audreyevans@ku.edu>
*  @author 	Hari Ramanan <hramanan@ku.edu>
*  @version 0.2 &nbsp;
*  @since 2016-02-08
*
*/


import java.util.Timer;
import java.util.TimerTask;

public class CoolClockTimer extends TimerTask
{
	int time;
	Control myGUI;
	//boolean hourFormat; //if true then military time, if false then 12 hr time
	boolean pause;
	boolean flash;
	String ampm;
	boolean military_time = true;
	boolean afternoon = false;
	
	/**
	* 	Constructor. sets variables
 	* 	@post hourFormat set to true, pause set to false, flash set to true, new Control instance myGUI instantiated
    */
	public CoolClockTimer()
	{
		myGUI = new Control(this);
		time = 0;
		//hourFormat = true;
		pause = false;
		flash = true;
		
	}
	
	/**
 	* Timing function, calls the convertseconds function and sends digits to GUI
 	* @post calls ConvertSeconds
 	* 
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
	*
	*/
	public void refresh()
	{
		int[] digits;
		digits = ConvertSeconds(time);
		ampm = TwelveHourPm();
		myGUI.setDisplay(digits, true, ampm); //new int[] {1,2,0,0,0,0}
	}
	
	public void togglePause()
	{
		pause = !pause;
	}
	
	public void toggleHourFormat()
	{
		military_time = !military_time;
		refresh();
	}
	
	/**
 	 * Converts seconds into the clock format
 	 * @param total seconds
 	 * @param hour format (am or pm)
 	 * @return integer array that represents each digit of the clock
 	 */
	public int[] ConvertSeconds(int total_seconds)
	{
		int[] time = {0,0,0,0,0,0};
		int seconds;
		int mins;
		int hours;
		
		if(military_time)
		{
			hours = total_seconds / 3600;
			time[0] = hours / 10;
			time[1] = hours % 10;
			total_seconds = total_seconds - (hours * 3600);
			mins = total_seconds / 60;
			time[2] = mins / 10;
			time[3] = mins % 10;
			total_seconds = total_seconds - (mins * 60);
			seconds = total_seconds;
			time[4] = seconds / 10;
			time[5] = seconds % 10;
			
		}
		else
		{
			hours = total_seconds / 3600;
			total_seconds = total_seconds - (hours * 3600);
			mins = total_seconds / 60;
			time[2] = mins / 10;
			time[3] = mins % 10;
			total_seconds = total_seconds - (mins * 60);
			seconds = total_seconds;
			time[4] = seconds / 10;
			time[5] = seconds % 10;
			if(hours > 12)
			{
				hours = hours - 12;
				afternoon = true;
			}
			time[0] = hours / 10;
			time[1] = hours % 10;
			if((time[0] == 0) && (time[1] == 0))
			{
				time[0] = 1;
				time[1] = 2;
			}
		}
		
		for(int i = 0; i < 6; i++)
		{
			System.out.print(time[i]);
			if((i == 1) || (i == 3))
			{
				System.out.print(":");
			}
		}
		return time;
		
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

	public void addTime(int amt)
	{
		if(amt>=0)
		{
			time = (time + amt) % 86400;
		}
		else 
		{
			time = (time + amt + 86400) % 86400;
		}
		refresh();
	}
	
}