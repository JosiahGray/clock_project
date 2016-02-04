class ThreadDemo extends Thread {
   private Thread t;
   private String threadName;
   
   ThreadDemo( String name){
       threadName = name;
       System.out.println("Creating " +  threadName );
   }
   public void run() {
      System.out.println("Running " +  threadName );
      Control myGUI = new Control();

      try {
          for(int i=0; i<420; i++)
          { 
            if(i%2 == 0)
            {
              myGUI.setDisplay(new int[] {10, 4, 2, 0}, false, "");
            }
            else
            {
              myGUI.setDisplay(new int[] {10, 10, 10, 10}, false, "");
            }
            this.sleep(350);
            System.out.println("Cycling...");
          }
     } catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
     }
     System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start ()
   {
      System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}