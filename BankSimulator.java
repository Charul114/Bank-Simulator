package PJ3;

import java.util.*;
import java.io.*;


class BankSimulator {

  // input parameters
  private int numTellers, customerQLimit;
  private int simulationTime, dataSource;
  private int chancesOfArrival, maxTransactionTime;

  // statistical data
  private int numGoaway, numServed, totalWaitingTime;

  // internal data
  private int customerIDCounter;   // customer ID counter
  private ServiceArea servicearea; // service area object
  private Scanner dataFile;	   // get customer data from file
  private Random dataRandom;	   // get customer data using random function
  private String tempFile;
  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;  
  private int transactionTime;

  // initialize data fields
  private BankSimulator()
  {
	// add statements
	
  }

  private void setupParameters()
  {
	// read input parameters
	// setup dataFile or dataRandom
	// add statements
	  
	  Scanner input = new Scanner(System.in);
	  
	 System.out.println("Enter simulation time(positive integer):" );
	 simulationTime = input.nextInt();
	 
	 System.out.println("Enter the number of tellers:" );
	 numTellers = input.nextInt();
	 
	 System.out.println("Enter chances (0% < & <= 100%) of new customer" );
	 chancesOfArrival = input.nextInt();
	 
	 System.out.println("Enter maximum transaction time:");
	 maxTransactionTime = input.nextInt();
	 
	 System.out.println("Enter customer queue limit" );
	 customerQLimit = input.nextInt();
	 
	 System.out.println("Enter 0/1 to get data from Random/file: ");
	 dataSource = input.nextInt();
	 
	 
	 if(dataSource ==1)
	 {
		 System.out.println("Enter file name" );
	 	tempFile = input.next();
	 	
	  try {
		dataFile = new Scanner (new File (tempFile));
	  		}
	  catch (FileNotFoundException e) 
	 {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }
	 }
	 else 
	 {
		 System.out.println("Random File Generated");
		 dataRandom = new Random();
	 }
	  
  }

  // Refer to step 1 in doSimulation()
  private void getCustomerData()
  {
	// get next customer data : from file or random number generator
	// set anyNewArrival and transactionTime
        // see Readme file for more info
     if (dataSource == 1)
     {
			  
			  	int data1 = Integer.parseInt(dataFile.next());
			  	int data2= Integer.parseInt(dataFile.next());
			  	anyNewArrival = (((data1%100)+1)<= chancesOfArrival);
		         transactionTime = (data2%maxTransactionTime)+1;
		
	  }
	  
	  else 
	  {
		  dataRandom = new Random();
		  anyNewArrival = ((dataRandom.nextInt(100)+1) <= chancesOfArrival);
	        transactionTime = dataRandom.nextInt(maxTransactionTime)+1;
	  }
  }

  private void doSimulation()
  {
	// add statements
	  System.out.println("   ***   Start Simulation    ***");

	// Initialize ServiceArea
	  servicearea = new ServiceArea(numTellers, customerQLimit);

	// Time driver simulation loop
  	for (int currentTime = 0; currentTime < simulationTime; currentTime++) {

    		// Step 1: any new customer enters the bank?
    		getCustomerData();
    		System.out.println("------------------------------------------");
    		System.out.println("Time: " + currentTime);

    		if (anyNewArrival) {

      		    // Step 1.1: setup customer data
    			customerIDCounter++;
    			Customer B = new Customer(customerIDCounter, transactionTime, currentTime);
    			System.out.println("Customer #" + customerIDCounter + " arrives with transaction time " + transactionTime);
      		    // Step 1.2: check customer waiting queue too long?
                    //           if it is too long, update numGoaway
                    //           else enter customer queue
    			if(servicearea.isCustomerQTooLong())
    					{
    					   System.out.println("Customer #" + customerIDCounter + " has left");
    				       numGoaway++;
    					}
    			else 
    				{
    					servicearea.insertCustomerQ(B);
    					System.out.println("Customer #" + customerIDCounter +" gets in the queue");
    				}
    			
    		} 
    		else 
    		{
      		    System.out.println("No new customer!");
    		}

                // Step 2: free busy tellers that are done at currentTime, add to free cashierQ
    		   // Step 3: get free tellers to serve waiting customers at currentTime
    		while(!servicearea.emptyBusyTellerQ() && servicearea.getFrontBusyTellerQ().getEndBusyTime()==currentTime)
    		{
    		
    		 	//if(!servicearea.emptyBusyTellerQ() && servicearea.getFrontBusyTellerQ().getEndBusyTime()==currentTime)
    		 	
    		 		Teller T = servicearea.removeBusyTellerQ();	
    		 		Customer B = T.busyToFree();
    		 		servicearea.insertFreeTellerQ(T);
    		 		System.out.println("Customer #" + B.getCustomerID() + " is done");
    		 		System.out.println("Teller #" + T.getTellerID() + " is free");
    		 		B = null;
    		 	
    		}
    		while(!servicearea.emptyFreeTellerQ() && !servicearea.emptyCustomerQ())
    		 	{
    		 		Teller T = servicearea.removeFreeTellerQ();
    		 		Customer B = servicearea.removeCustomerQ();
    		 		totalWaitingTime+=(currentTime - B.getArrivalTime());
    		 		T.freeToBusy(B, currentTime);
    		 		servicearea.insertBusyTellerQ(T);
    		 		numServed++;
    		 		System.out.println("Customer #" + B.getCustomerID() + " gets teller");
    		 		System.out.println("Teller #" + T.getTellerID() + " starts serving customer #" + customerIDCounter + " for "+ transactionTime + " units" );
    		 	
               
    		 	}
  	       
    			
  	} // end simulation loop

  	// clean-up - close scanner
  	if (dataSource ==1)
  		dataFile.close();
  }

  private void printStatistics()
  {
	// add statements into this method!
	// print out simulation results
	// see the given example in project statement
        // you need to display all free and busy gas pumps


        // need to free up all customers in queue to get extra waiting time.
        // need to free up all tellers in free/busy queues to get extra free & busy time.
	  System.out.println(" ");
	  System.out.println("***      End of Simulation Report     ***");
	  System.out.println(" ");
	  System.out.println("# total arrival customers:"+ customerIDCounter);
      System.out.println("# customers gone-away:"+ numGoaway);
      System.out.println("# customers served:"+ numServed);
      System.out.println(" ");
      System.out.println("Current Teller info");
      System.out.println("# waiting customers:"+servicearea.numWaitingCustomers());
      System.out.println("# busy tellers:"+servicearea.numBusyTellers());
      System.out.println("# free tellers:"+servicearea.numFreeTellers());
      
      if(!servicearea.emptyCustomerQ())
      {
    	  Customer B = servicearea.removeCustomerQ();
    	  totalWaitingTime += (simulationTime - B.getArrivalTime());
    	  B = null;
      }
      System.out.println(" ");
      System.out.println("Total waiting time is:" + totalWaitingTime);
	  System.out.println("Average waiting time is:" + ((double)totalWaitingTime/(double)numServed));
      
      if(!servicearea.emptyBusyTellerQ())
    	  System.out.println(" ");
    	  System.out.println("Busy Teller info");
    
     
         while(!servicearea.emptyBusyTellerQ())
      {
          Teller T= servicearea.removeBusyTellerQ();
          T.setEndBusyTime(simulationTime);
          T.updateTotalBusyTime();
          T.printStatistics();
          T=null;
      }
         if(!servicearea.emptyFreeTellerQ())
       	  System.out.println("Free Teller info");
       
         
        while(!servicearea.emptyFreeTellerQ())
         {
             Teller T= servicearea.removeFreeTellerQ();
             T.setEndFreeTime(simulationTime);
             T.updateTotalFreeTime();
             T.printStatistics();
             T=null;
         }
     
     
  }

  private String (int i) {
	// TODO Auto-generated method stub
	return null;
}

// *** main method to run simulation ****

  public static void main(String[] args) {
   	BankSimulator runBankSimulator=new BankSimulator();
   	runBankSimulator.setupParameters();
   	runBankSimulator.doSimulation();
   	runBankSimulator.printStatistics();
  }

}
