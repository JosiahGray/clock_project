/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author
*  @version 0.1 &nbsp;
*  @since 2016-02-01
*
*
*
*/

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
		Control myGUI = new Control();
		for(int i=0; i<420; i++)
		{
			if(i%2 == 0)
			{
				myGUI.setDisplay(new int[] {10, 1, 2, 3}, false, "");
			}
			else
			{
				myGUI.setDisplay(new int[] {10, 10, 10, 10}, false, "");
			}
			try{Thread.sleep(30000);}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}