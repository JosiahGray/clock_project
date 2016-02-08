/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author
*  @version 0.2 &nbsp;
*  @since 2016-02-04
*
*
*
*/

import java.util.Timer;
import java.util.TimerTask;

public class Clock
{
	/**
	*  The main method of the Clock program
	*  <p>
	*  Creates and starts a Control object, which handles handles all necessary processes and subprocesses.  This is done in order to keep the GUI thread safe.
	*  @param args command-line arguments
	*
	*
	*/
	public static void main(String[] args)
	{

		Timer myTimer = new Timer();
		myTimer.schedule(new CoolClockTimer(), 0, 1000);		
	}
}
