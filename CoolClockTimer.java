/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author  Audrey Evans <audreyevans@ku.edu>
*  @author 	Hari Ramanan <hramanan@ku.edu>
*  @version 1.0 &nbsp;
*  @since 2016-02-14
*
*/

//for playing mp3
//http://stackoverflow.com/questions/19603450/how-can-i-play-an-mp3-file


import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
*	A custom TimerTask that keeps track of the time and settings, running every second.  This class also uses the GUI Control object to display the information and get input from the user.
*/
public class CoolClockTimer extends TimerTask
{
	/**
	*	The current time in seconds.  Always has a value between 0-86399 (86400 being the number of seconds in a standard 24 hour day).
	*/
	int time;
	String time_msg;
	String date_msg;

	/**
	 * variables for the day and month. month has a value between 1 and 12. The value of day depends on the month (should reflect the real calendar for 2016).
	 */
	int day;
	int month;
	int timeExecuted;

	boolean timerSet;
	boolean alarmDone;
	boolean stopAlarm;
	

	/**
	 * Current time of the stopwatch
	 */
	int stopWatchTime;
	/**
	 * Current time of the timer
	 */
	int timerTime;
	
	
	//separate
	int alarmTime;
	/**
	*	The instance of the primary GUI class Control, which inherits from JFrame.
	*/
	Control myGUI;

	/**
	*	Represents whether time incrementation should be paused, true indicating a paused state.
	*/
	boolean pause;
	boolean pauseStopWatch;
	boolean pauseTimer;

	/**
	*	Represents whether time should be displayed in 24 hour (military time) format, or 12 hour am/pm format.  True indicates military format.
	*/
	boolean military_time;

	//clock expansion
	// booleans for what mode it's on
	/**
	 * boolean determining if clock is currently being displayed
	 */
	boolean displayClock; //clock
	/**
	 * boolean determining if stopwatch is currently being displayed
	 */
	boolean displaySW; //stopwatch
	/**
	 * boolean determining if timer is currently being displayed
	 */
	boolean displayTimer; //timer

	int alarmDuration;
	boolean firstPass;

	/**
	* 	Constructor which sets variables and creates a new instance of Control for display purposes.
 	* 	@post 	time is set to 0, military_time is set to true, pause is set to false, new Control instance myGUI instantiated
    */
	public CoolClockTimer()
	{
		myGUI = new Control(this);
		time = 0;	//initialize to 12:00 midnight
		month = 1;	//initialize to january 1st
		day = 1;
		stopWatchTime = 0; //stop watch time
		timerTime = 0; //timer time
		military_time = true;
		pause = false; //main clock pausing

		pauseStopWatch = true; //pauses stopwatch
		pauseTimer = true; //pauses timer

		timeExecuted = 0;

		//display
		//start with clock
		displayClock = true;
		displaySW = false;
		displayTimer = false;
		alarmDone = false;

		//
		timerSet = false;
		alarmDuration = 300;
		alarmTime = 0;
		stopAlarm = false;

	}
	/**
	 * This function is called by control to change booleans so that all operations take place on clock
	 * @pre clock settings and variables have all been set
	 * @post all further operations will take place on the clock
	 */
	public void showClock(){
		displayClock = true;
		displaySW = false;
		displayTimer = false;
	}
	/**
	 * This function is called by control to change booleans so that all operations take place on stopwatch
	 * @pre stopwatch settings and variables have all been set
	 * @post all further operations will take place on the stopwatch
	 */
	public void showSW(){
		displayClock = false;
		displaySW = true;
		displayTimer = false;
	}
	/**
	 * This function is called by control to change booleans so that all operations take place on timer
	 * @pre timerlock settings and variables have all been set
	 * @post all further operations will take place on the timer
	 */
	public void showTimer(){
		displayClock = false;
		displaySW = false;
		displayTimer = true;
	}
	/**
	 * This function is called by control to change booleans so that all operations take place on clock
	 * @pre timer has been set and time has expired
	 * @post plays an alarm indicating timer time has expired
	 */
	public void playAlarm(){
		try
		{
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.getClass().getResource("GoalHorn.wav"));
				Clip fc = AudioSystem.getClip();
				fc.open(audioStream);
				fc.start();
	 	}
		catch(Exception e)
		{
			e.printStackTrace();
	
        }
	}
	public void disableDisplay(){
		displayClock = false;
		displaySW = false;
		displayTimer = false;
	}
	
	
	/**
 	* 	This function is called every second by a Timer.  It calls refresh to update the display to the current state of the clock then adds 1 second to the time.
 	* 	@pre 	clock settings and variables have all been set
 	*	@post 	the display reflects the status of the clock at the time this function was called and the time has been incremented by 1 second.
 	*/
	public void run()
	{
		
		timeExecuted ++;
		refresh();
		//if clock is not paused
		if(!pause)
		{
			refresh();
			//this controls time for clock
			//modding a counter variable by 100 since we update 100 times a second
			if(timeExecuted % 100 == 0){
				addTime(1);
			}
		}
		//if stop watch hasn't been paused
		if(!pauseStopWatch){
			refresh();
			addSWTime(1);
		}
		//if timer hasn't been paused
		if(!pauseTimer){
			if(timerTime > 0){
				refresh();
				if(timeExecuted % 100 == 0){
					timerTime--;
				}
			} else{
				//make timer sound
				//if boolean is still false for turning off the alarm
					if(!stopAlarm && alarmDuration == 300){
						
						playAlarm();
						alarmDuration = 0;
					} else if(stopAlarm){ 
						alarmDuration = 299;
						stopAlarm = false;
						pauseTimer = true;
					}
				alarmDuration ++;

			}
			//control timer
		}
	}





	/**
	*	Updates the display according to the current time and display settings
	*	@post 	display is refreshed to reflect the current time and settings
	*/
	public void refresh()
	{
		int[] digits;
		//if timer should be displayed
		if(displayTimer){
			myGUI.setDisplay(TimerConvertSeconds(), true, "", time_msg, "");
			//if stopwatch should be displayed
		} else if(displaySW){
			myGUI.setDisplay(SWConvertSeconds(), true, "", time_msg, "");
			//if clock should be displayed
		} else if(displayClock){
			myGUI.setDisplay(ConvertSeconds(), true, TwelveHourPm(), time_msg, date_msg);
			//if display is disabled 
		} else{
			myGUI.setDisplay(ConvertSeconds(), true, "", "", "");
		}
		//new int[] {1,2,0,0,0,0}
	}

	/**
	*	Sets stopwatch time to zero and pauses stop watch until started again
	*	@post 	stopWatchTime set to zero. pauseStopWatch set to true
	*/
	public void resetSW(){

		stopWatchTime = 0;
		pauseStopWatch = true;

	}
	/**
	*	Sets timer time to zero and pauses timer until started again or if time has expired on timer, turns off alarm
	*	@post 	timerTime set to zero. pauseTimer set to true. timerSet set to false. Or if time expired, stopAlarm set to true
	*/
	public void resetTimer(){
		//if there is still time on the timer
		if(timerTime != 0){
			timerTime = 0;
			timerSet = false;
			pauseTimer = true;
			
		} else {
			//set boolean to turn off alarm
			stopAlarm = true;
		}
	}
	/**
	*	Switches the pause flag to it's opposite
	*	@post 	pause is set to the opposite value
	*/
	public void togglePause()
	{
		pause = !pause;
	}
	//clock expansion
	/**
	*	Switches the pauseStopWatch flag to it's opposite
	*	@post 	pauseStopWatch is set to the opposite value
	*/
	public void toggleSWPause(){

		pauseStopWatch = !pauseStopWatch;

	}
	/**
	*	Switches the pauseTimer flag to it's opposite
	*	@post 	pauseTimer is set to the opposite value
	*/
	public void toggleTimerPause(){

		pauseTimer = !pauseTimer;

	}
	/**
 	* 	Takes in a change in the time and converts that into centiseconds. Stopwatch can go up to 59 minutes, 59 seconds, 99 centiseconds 
 	* 	@param 	amt the change in time
 	*	@post 	the stop watch time is shifted by amt seconds and is still in the valid centisecond range (0-359999)
 	*/
	public void addSWTime(int amt){

		stopWatchTime = ((stopWatchTime + amt) % 360000 + 360000) % 360000;
	}
	/**
 	* 	Takes in a change in the time and converts that into seconds. Stopwatch can go up to 99 hours, 59 minutes, 59 seconds 
 	* 	@param 	amt the change in time
 	*	@post 	the stop watch time is shifted by amt seconds and is still in the valid second range (0-359999)
 	*/
	public void addTimerTime(int amt){

		//99 hours 59 minutes 59 seconds
		timerTime = ((timerTime + amt) % 360000 + 360000) % 360000;
	}
	//converts stop watch seconds into clock format
	/**
 	 * 	Converts centiseconds into the stopwatch
 	 * 	@return integer array that represents each digit of the stop watch
 	 */
	public int[] SWConvertSeconds(){
		int[] digSWTime = {0,0,0,0,0,0};
		int totalSWSeconds = stopWatchTime;
		int centiseconds;
		int seconds;
		int minutes;
		minutes = totalSWSeconds / 6000;
		digSWTime[0] = minutes / 10;
		digSWTime[1] = minutes % 10;
		totalSWSeconds = totalSWSeconds - (minutes * 6000);
		seconds = totalSWSeconds / 100;
		digSWTime[2] = seconds / 10;
		digSWTime[3] = seconds % 10;
		totalSWSeconds = totalSWSeconds - (seconds * 100);
		centiseconds = totalSWSeconds;
		digSWTime[4] = centiseconds / 10;
		digSWTime[5] = centiseconds % 10;
		time_msg = "" + digSWTime[0] + digSWTime[1] + ":" + digSWTime[2] + digSWTime[3] + ":" + digSWTime[4] + digSWTime[5];

		return digSWTime;
	}
	/**
 	 * 	Converts seconds into the timer format
 	 * 	@return integer array that represents each digit of the timer
 	 */
	public int[] TimerConvertSeconds(){
		int[] digTimerTime = {0,0,0,0,0,0};
		int totalTimerSeconds = timerTime;
		int seconds;
		int mins;
		int hours;
		hours = totalTimerSeconds / 3600;
		digTimerTime[0] = hours / 10;
		digTimerTime[1] = hours % 10;
		totalTimerSeconds = totalTimerSeconds - (hours * 3600);
		mins = totalTimerSeconds / 60;
		digTimerTime[2] = mins / 10;
		digTimerTime[3] = mins % 10;
		totalTimerSeconds = totalTimerSeconds - (mins * 60);
		seconds = totalTimerSeconds;
		digTimerTime[4] = seconds / 10;
		digTimerTime[5] = seconds % 10;
		time_msg = "" + digTimerTime[0] + digTimerTime[1] + ":" + digTimerTime[2] + digTimerTime[3] + ":" + digTimerTime[4] + digTimerTime[5];

		return digTimerTime;

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
		time_msg = "" + digit_time[0] + digit_time[1] + ":" + digit_time[2] + digit_time[3] + ":" + digit_time[4] + digit_time[5];
		date_msg = "    " + getDayOfWeek() + ", "+ day + "/" + month + "/16";
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
		//time = ((time + amt) % 86400 + 86400) % 86400;

		//adjust time while keeping it within the bounds of 0-86400 and adjusting for day changeovers.
		time += amt;
		if(time > 86399)
		{
			day++;
			time -= 86400;
		}
		if(time < 0)
		{
			day--;
			time += 86400;
		}

		//keep days and months within their respective bounds.
		switch(month)
		{
		//january
		case 1:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month = 12;
				day = 31;
			}
			break;
		//february
		case 2:
			if(day > 29)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//march
		case 3:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 29;
			}
			break;
		//april
		case 4:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//may
		case 5:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//june
		case 6:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//july
		case 7:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//august
		case 8:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//september
		case 9:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//october
		case 10:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//november
		case 11:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//december
		case 12:
			if(day > 31)
			{
				month = 1;
				day = 31;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//default to january 1st in case of error
		default:
			month = 1;
			day = 1;
		}

		refresh();
	}

	/**
	 * 	Takes in a change in the month and adjusts the month variable accordingly
	 * 	@param 	amt the change in month
	 *	@post 	the month is shifted by amt months and is still in the valid month range (1-12)
	 */
	public void addMonth(int amt)
	{
		//month = ((((month + amt - 1) % 12) + 12) % 12) + 1;

		// brute force method
		month += amt;
		if(month > 12)
		{
			month = 1;
		}
		if(month < 1)
		{
			month = 12;
		}

		refresh();
	}

	/**
	 * 	Takes in a change in the day and adjusts the day variable accordingly
	 * 	@param 	amt the change in day
	 *	@post 	the day is shifted by amt days and is still in the valid day range (dependent on the month)
	 */
	public void addDay(int amt)
	{
		day += amt;

		switch(month)
		{
		//january
		case 1:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month = 12;
				day = 31;
			}
			break;
		//february
		case 2:
			if(day > 29)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//march
		case 3:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 29;
			}
			break;
		//april
		case 4:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//may
		case 5:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//june
		case 6:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//july
		case 7:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//august
		case 8:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//september
		case 9:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//october
		case 10:
			if(day > 31)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//november
		case 11:
			if(day > 30)
			{
				month++;
				day = 1;
			}
			if(day < 1)
			{
				month--;
				day = 31;
			}
			break;
		//december
		case 12:
			if(day > 31)
			{
				month = 1;
				day = 31;
			}
			if(day < 1)
			{
				month--;
				day = 30;
			}
			break;
		//default to january 1st in case of error
		default:
			month = 1;
			day = 1;
		}

		refresh();
	}

	/**
	 * 	Determines the day of the week (String) based on the current numberical month and day
	 *	@return the current day of the week as a String
	 */
	public String getDayOfWeek()
	{
		String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		int[] shifter = {4,0,1,4,6,2,4,0,3,5,1,3};

		//shorter method
		return (weekDays[((day + shifter[month-1]) % 7)]);

		/*brute force method
		int shift = 0;
		String dayOfWeek = "Sunday";

		switch(month)
		{
		case 1:
			shift = 4;
			break;
		case 2:
			shift = 0;
			break;
		case 3:
			shift = 1;
			break;
		case 4:
			shift = 4;
			break;
		case 5:
			shift = 6;
			break;
		case 6:
			shift = 2;
			break;
		case 7:
			shift = 4;
			break;
		case 8:
			shift = 0;
			break;
		case 9:
			shift = 3;
			break;
		case 10:
			shift = 5;
			break;
		case 11:
			shift = 1;
			break;
		case 12:
			shift = 3;
			break;
		}

		dayOfWeek = weekDays[((day+shift)%7)];
		return(dayOfWeek);
		*/
	}
}
