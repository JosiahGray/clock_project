/**
*  @author	John McCain <johnm.freestate@gmail.com>
*  @author	Alan Wang <ultimate801@gmail.com>
*  @version 1.0 &nbsp;
*  @since 2016-02-14
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
*	A custom JFrame that contains JPanels which display the time information passed into this class and gives input from the user to CoolClockTimer.
*/
public class Control extends JFrame implements ActionListener
{
	/**
	*	JFrame used for pop up messages.
	*/
	JFrame popUpFrame;

	/**
	*	The clock digit display JPanel
	*/
	Display displayPanel;

	/**
	*	Instance of the CoolClockTimer for passing UI interaction information
	*/
	CoolClockTimer coolClock;

	/**
	*	Constructor.  Initializes the GUI, GUI components, and necessary variables with appropriate settings.
	*	@param myClock an instance of the CoolClockTimer that created this Control object, used for passing UI information (button presses) to the clock controller
	*/
	public Control(CoolClockTimer myClock)
	{
		super("Hyperclock 2000");

		//Set coolClock
		coolClock = myClock;

		//Set layout (how panels are organized within frame)
		//Alan, feel free to change this if need be
		setLayout( new FlowLayout() );

		//Sets the size, width px X height px
		//Note: this includes the ~40px top bar
		setSize(1500, 600);

		//Make the background black
		this.getContentPane().setBackground(Color.BLACK);

		//Puts the window in the middle of the screen
		setLocationRelativeTo(null);

		//Make the window not resizable
		setResizable(true);

		//Exit the application when the "X" button is pressed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		displayPanel = new Display();
		this.getContentPane().add(displayPanel);

		//Button Interface Code

		//buttonPanel will hold all of the buttons
		//the GridLayout will arange the buttons in a 2 row grid, 0 means it can have any number of columns
		JPanel buttonPanel = new JPanel( new GridLayout(2, 0) );
		buttonPanel.setPreferredSize( new Dimension(1500, 150) );

		//Create the buttons
	    //Top Row
	    JButton addHour = new JButton("Hour +"); //1
	    JButton addMinute = new JButton("Minutes +"); //2
	    JButton addSecond = new JButton("Seconds +"); //3
	    JButton addMonth = new JButton("Month +"); //4
	    JButton addDay = new JButton("Day +"); //5
	    JButton zoomIn = new JButton("Zoom In"); //6

	    JButton toggleFormat = new JButton("24 hour/12 hour"); //7

	    JButton timer = new JButton("Timer"); //8
	    JButton stopWatch = new JButton("Stopwatch"); //9
	    JButton showClock = new JButton("Clock");



	    //Bottom Row
	    JButton subHour = new JButton("Hour -"); //1
	    JButton subMinute = new JButton("Minutes -"); //2
	    JButton subSecond = new JButton("Seconds -"); //3
	    JButton subMonth = new JButton("Month -"); //4
	    JButton subDay = new JButton("Day -"); //5
	    JButton zoomOut = new JButton("Zoom Out"); //6

	    JButton toggleDisplay = new JButton("Show/Hide display"); //7 //also switch from stopwatch/timer
	    JButton togglePause = new JButton("Start/Pause"); //8
	    JButton resetSW = new JButton("Reset Stopwatch"); //9
	    



	    //Add actionListeners for the buttons
	    addHour.addActionListener(this);
	    addMinute.addActionListener(this);
		addSecond.addActionListener(this);
	    toggleFormat.addActionListener(this);
	    showClock.addActionListener(this);
	    subHour.addActionListener(this);
	    subMinute.addActionListener(this);
	    subSecond.addActionListener(this);
	    togglePause.addActionListener(this);
	    
	    //clock expansion
	    timer.addActionListener(this);
	    stopWatch.addActionListener(this);
	    zoomIn.addActionListener(this);
	    zoomOut.addActionListener(this);
	    toggleDisplay.addActionListener(this);
	    resetSW.addActionListener(this);
	    addMonth.addActionListener(this);
	    addDay.addActionListener(this);
	    subMonth.addActionListener(this);
	    subDay.addActionListener(this);
	    

	    //Add the buttons to our buttonPanel
	    buttonPanel.add(addHour); //1
	    buttonPanel.add(addMinute); //2
	    buttonPanel.add(addSecond); //3
	    buttonPanel.add(addMonth); //4
	    buttonPanel.add(addDay); //5
	    buttonPanel.add(zoomIn); //6
	    buttonPanel.add(toggleFormat); //7
	    buttonPanel.add(timer); //8
	    buttonPanel.add(stopWatch); //9
	    buttonPanel.add(showClock);

	    buttonPanel.add(subHour);//1
	    buttonPanel.add(subMinute); //2
	   	buttonPanel.add(subSecond);//3
	   	buttonPanel.add(subMonth);//4
	    buttonPanel.add(subDay);//5
	    buttonPanel.add(zoomOut); //6
	    buttonPanel.add(toggleDisplay); //7
	    buttonPanel.add(togglePause); //8
	    buttonPanel.add(resetSW);//9

	    //Add the buttonPanel to our JFrame
	    this.getContentPane().add(buttonPanel);

	    //Reveal ourselves to the world
		setVisible(true);

		//For pop up messages
		popUpFrame = new JFrame("Dialogue");
	}
	
			//add booleans to determine which method the button implements for each function
		boolean displayClock = true;
		boolean displayTimer = false;
		boolean displaySW = false;

	/**
	*	Handles GUI event responses such as button presses.
	*	@param	event the event that has occurred
	*	@post	the appropriate response to the event will be executed
	*/
	public void actionPerformed(ActionEvent event)
	{
	
		
		
		switch(event.getActionCommand())
		{
			case "Hour +": //1
				if(displayClock){
					
					coolClock.addTime(3600);
					
				} else if(displayTimer){

					coolClock.addTimerTime(3600);
					
            	} else{
            		
            		displayMessage("Error, logic off");
            	}
				break;
			case "Minutes +":
               if(displayClock){
                					
                	coolClock.addTime(60);
                					
                } else if(displayTimer){

					coolClock.addTimerTime(60);
                					
                } else{
                            		
            	   displayMessage("Error, logic off");
               }
				break;
            case "Seconds +": //3
               if(displayClock && !(displayTimer) && !(displaySW)){
                        		
                    coolClock.addTime(1);
                                
                } else if(!(displayClock)&& displayTimer && !(displaySW)){
                        		
                    coolClock.addTimerTime(1);
                        		
                } else{
                        		
                    displayMessage("Error, logic off");
                }
                break;
             case "Start/Pause": //4
                 if(displayClock && !(displayTimer) && !(displaySW)){
                        		
                    coolClock.togglePause();
                                
                 } else if(!(displayClock)&& displayTimer && !(displaySW)){
                        		
                    coolClock.toggleTimerPause();
                        		
                 } else if (!(displayClock)&& !(displayTimer) && displaySW){
                        		
                    coolClock.toggleSWPause();
                        		
                 } else{
                        		
                     displayMessage("Error, logic off");
                 } 
                 break;
              case "24 hour/12 hour": //5
                  if(displayClock && !(displayTimer) && !(displaySW)){
                       
                	  coolClock.toggleHourFormat();
                  } 
                  break;
              case "Hour -": //6
                  if(displayClock && !(displayTimer) && !(displaySW)){
                        		
                     coolClock.addTime(-3600);
                               
                   } else if(!(displayClock)&& displayTimer && !(displaySW)){
                        		
                      coolClock.addTimerTime(-3600);
                        		
                   } else{
                        		
                      displayMessage("Error, logic off");
                   }
                                break;
               case "Minutes -": //7
                   if(displayClock && !(displayTimer) && !(displaySW)){
                        		
                       coolClock.addTime(-60);
                                
                   } else if(!(displayClock)&& displayTimer && !(displaySW)){
                       		
                        coolClock.addTimerTime(-60);
                        		
                   } else{
                        displayMessage("Error, logic off");
                   }
                   break;
               case "Seconds -": //8
                   if(displayClock && !(displayTimer) && !(displaySW)){
                	   
                          coolClock.addTime(-1);
                                
                    } else if(!(displayClock)&& displayTimer && !(displaySW)){
                    	
                           coolClock.addTimerTime(-1);
                    	
                    } else{
                    	
                        displayMessage("Error, logic off");
                        		
                    }
                    break;  
                case "Stopwatch": //9
                     //toggle to stopwatch string
                     displaySW = true;
                     displayClock = false;
                     displayTimer = false;
                     //update coolClock
                     coolClock.showSW();
                     break;
                case "Month +": //10
                     if(displayClock){
                    	 
                       coolClock.addMonth(1);
                        		
                     }
                     break;
                case "Month -": //11
                     if(displayClock){
                    	 
                       coolClock.addMonth(-1);
                        		
                     }
                     break;
                case "Day +": //12
                    if(displayClock){
                    	
                       coolClock.addDay(1);
                       
                    }
                    break;
                 case "Day -": //13
                    if(displayClock){
                    	
                        coolClock.addDay(-1);
                     }
                     break;
                 case "Zoom in": //14
                     if(displayClock && !(displayTimer) && !(displaySW)){
                    	 
                        		//do clock stuff
                    	 
                     } else if(!(displayClock)&& displayTimer && !(displaySW)){
                    	 
                        		//do timer stuff
                    	 
                     } else if (!(displayClock)&& !(displayTimer) && displaySW){
                    	 
                        		//do stopwatch stuff
                    	 
                     } else{
                        		displayMessage("Error, logic off");
                     }
                        	//do zoom in stuff
                     break;
                 case "Zoom out": //15
                      if(displayClock && !(displayTimer) && !(displaySW)){
                    	  
                        		//do clock stuff
                    	  
                       } else if(!(displayClock)&& displayTimer && !(displaySW)){
                    	   
                        		//do timer stuff
                    	   
                       } else if (!(displayClock)&& !(displayTimer) && displaySW){
                    	   
                        		//do stopwatch stuff
                    	   
                       } else{
                    	   
                        		displayMessage("Error, logic off");
                        		
                       }
                        	//do zoom out stuff
                       break;
                   case "Show/Hide display": //16
                	   
                       if(displayClock && !(displayTimer) && !(displaySW)){
                    	   
                        		//do clock stuff
                    	   
                       } else if(!(displayClock)&& displayTimer && !(displaySW)){
                    	   
                        		//do timer stuff
                    	   
                       } else if (!(displayClock)&& !(displayTimer) && displaySW){
                    	   
                        		//do stopwatch stuff
                    	   
                       } else{
                        		displayMessage("Error, logic off");
                       }
                       break;
                    case "Timer"://17
                        	//do timer stuff
                        	displayTimer = true;
                        	displayClock = false;
                        	displaySW = false;
                        	//update CoolClockEnd
                        	coolClock.showTimer();
                        	break;
                    case "Reset Stopwatch"://18
                        if(displaySW){
                        	
                        	coolClock.resetSW();
                        	
                        }
                       break;
                    case "Clock":
                    	displayClock = true;
                    	displayTimer = false;
                    	displaySW = false;
                    	coolClock.showTimer();
                    	break;
                    	
			default:
				displayMessage("ERROR: Unrecognized event");
				break;
		}
	}

	/**
	*	Calls setDisplay in displayPanel
	*	@param 	digits a four digit number to be displayed on the clock face
	*	@param 	colon true if colon should be displayed, false if it should not
	*	@param 	msg the message to be displayed next to the digit display (am/pm)
	*/
	public void setDisplay(int[] digits, boolean colon, String msg)
	{
		displayPanel.setDisplay(digits, colon, msg);
	}

	/**
	*	Displays a message popup for errors or other messages.
	*	@param 	msg The string to be displayed in the dialogue window
	*/
	public void displayMessage(String msg)
	{
		JOptionPane.showMessageDialog(popUpFrame, msg);
	}
}
