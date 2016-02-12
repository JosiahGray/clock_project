/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author  Audrey Evans <audreyevans@ku.edu>
*  @author 	Hari Ramanan <hramanan@ku.edu>
*  @version 0.2 &nbsp;
*  @since 2016-02-08
*
*/

//This is just a joke branch, but here's the resources I used for researching how to play sound/source of the sound files
//http://stackoverflow.com/questions/19603450/how-can-i-play-an-mp3-file
//http://soundbible.com/1461-Big-Bomb.html



import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	boolean missionImpossible;
	
	public CoolClockTimer()
	{
		myGUI = new Control(this);
		time = 0;
		//hourFormat = true;
		pause = false;
		flash = true;
		missionImpossible = false;
	}
	
	public void run()
	{
		if(!pause && !missionImpossible)
		{
			refresh();
			addTime(1);
		}
		else if(missionImpossible)
		{
			military_time = true;
			refresh();
			if(time>0) time--;
			else
			{
				try
				{
				    AudioInputStream audioStream =
				    AudioSystem.getAudioInputStream(
				    this.getClass().getResource("boom.wav"));
				    Clip boom = AudioSystem.getClip();
				    boom.open(audioStream);
				    boom.start();
				    Thread.sleep(3000);
				    System.exit(0);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void startMissionImpossible()
	{
		if(!missionImpossible)
		{
			try
			{
			    AudioInputStream audioStream =
			    AudioSystem.getAudioInputStream(
			    this.getClass().getResource("Mission_Impossible.wav"));
			    Clip miTheme = AudioSystem.getClip();
			    miTheme.open(audioStream);
			    miTheme.start();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			missionImpossible = true;
			time = 50;
			refresh();
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
		if(!missionImpossible)
		{
			military_time = !military_time;
			refresh();
		}
	}
	


	
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
		if(!missionImpossible)
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
	
}