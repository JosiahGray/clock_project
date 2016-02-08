//put imports here (timertask and timer)
import java.util.Timer;
import java.util.TimerTask;


class CoolClockTimer extends TimerTask
{
	int time;
	Control myGUI;
	boolean hourFormat;
	boolean pause;
	boolean flash;
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
			
			digits = new int[] {1,2,0,0,0,time};
			
			flash = !flash;
			
			
			myGUI.setDisplay(digits, true,""); //new int[] {1,2,0,0,0,0}
			time++;
		}
	}
	
	void changeTime(int change) //Hari's stuff
	{
		time = (time+change)%86400;
	}
}