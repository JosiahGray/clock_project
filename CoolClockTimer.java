//put imports here (timertask and timer)

class CoolClockTimer extends TimerTask
{
	int time;
	Control myGUI;
	boolean hourFormat;
	boolean pause;
	public CoolClockTimer()
	{
		myGUI = new Control(this);
		time = 0;
		hourFormat = true;
		pause = true;
	}

	run()
	{
		if(!pause)
		{
			
			
			
			
			
			myGUI.setDisplay(digits, true,""); //new int[] {1,2,0,0,0,0}
			time++;
		}
	}

	changeTime(int change)
	{
		time = (time+change)%86400;
	}
}