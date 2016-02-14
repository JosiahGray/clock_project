/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author 	Audrey Evans <audreyevans@ku.edu>
*  @author Hari Ramanan <hramanan@ku.edu>
*  @author	Alan Wang <ultimate801@gmail.com>
*  @version 1.0 &nbsp;
*  @since 2016-02-14
*/

package cool_clock;

import java.util.Timer;
import java.util.TimerTask;

/**
*	The main class of the cool_clock, starts the Timer and TimerTask that carry out the rest of execution.
*/
public class Clock
{
	/**
	*  The main method of the Clock program
	*  <p>
	*  Creates a Timer and schedules it to execute a custom TimerTask (CoolClockTimer) every second.
	*  @param 	args command-line arguments
	*/
	public static void main(String[] args)
	{

		Timer myTimer = new Timer();
		myTimer.schedule(new CoolClockTimer(), 0, 1000);		
	}
}
