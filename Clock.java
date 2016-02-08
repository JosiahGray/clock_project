/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author
*  @version 0.2 &nbsp;
*  @since 2016-02-04
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
		for(int i=0; true; i++)
		{
			if(i%2 == 0)
			{
				myGUI.setDisplay(new int[] {1, 2, 0, 0, 10, 10}, true, "am");
			}
			else
			{
				myGUI.setDisplay(new int[] {1, 2, 0, 0, 0, 0}, false, "");
			}
			try{Thread.sleep(500);}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}