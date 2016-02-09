/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author  Audrey Evans <audreyevans@ku.edu>
*  @author 	Hari Ramanan <hramanan@ku.edu>
*  @version 0.2 &nbsp;
*  @since 2016-02-08
*
*/


//put imports here (timertask and timer)
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

class CoolClockTimer extends TimerTask
{
	int time;
	Control myGUI;
	boolean hourFormat; //if true then military time, if false then 12 hr time
	boolean amorpm;
	boolean pause;
	boolean flash;
	String ampm;
	
	public CoolClockTimer()
	{
		myGUI = new Control(this);
		time = 0;
		hourFormat = true;
		pause = false;
		flash = true;
		
	}
	
	public void run()
	{
		if(!pause)
		{
			
			int[] digits;
			
			digits = ConvertSeconds(time,hourFormat);
			ampm = TwelveHourPm(amorpm);
			
			
			
			
			myGUI.setDisplay(digits, true,ampm); //new int[] {1,2,0,0,0,0}
			addTime(1);
		}
	}
	
	

	static boolean military_time = true;
	static boolean afternoon = false;
	
	//public static void main(String[] args)
	//{
	//	Scanner reader = new Scanner(System.in);  // Reading from System.in
	//	System.out.println("Enter a number: ");
	//	int n = reader.nextInt();
	//	System.out.println("Enter 0 for 12 hour clock or 1 for military time");
	//	int miltime = reader.nextInt();
	//	ConvertSeconds(n, miltime);
	//	System.out.println(TwelveHourPm(afternoon));
//	}
	
	public static int[] ConvertSeconds(int total_seconds, boolean hourFormat )
	{
		int[] time = {0,0,0,0,0,0};
		int seconds;
		int mins;
		int hours;
		
		if(hourFormat)
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
	
	public static String TwelveHourPm(boolean aft)
	{
		if(aft)
			return("pm");
		else
			return("am");
	}

	public void addTime(int amt)
	{
		time = (time+amt)%86400;
	}
	
}