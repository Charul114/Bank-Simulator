class CompareTellers implements Comparator<Teller>{
	// overide compare() method
 	public int compare(Teller o1, Teller o2) {
		return o1.getEndBusyTime() - o2.getEndBusyTime(); 
	}
}

class ServiceArea {
  
  // Private data fields:
  
  // define one priority queue 
  private PriorityQueue <Teller> busyTellerQ;

  // define two FIFO queues
  private Queue<Customer> customerQ;
  private Queue<Teller> freeTellerQ;

  // define customer queue limit
  private int customerQLimit;


  // Constructor 
  public ServiceArea() 
  {
	// add statements
	  customerQLimit= 50;
	  busyTellerQ = new PriorityQueue<Teller>();
	  customerQ = new ArrayDeque<Customer>();
	  freeTellerQ = new ArrayDeque<Teller>();	  
  }

  // Constructor 
  public ServiceArea(int numTellers, int customerQlimit)
  {
	// add additional statements

	// use ArrayDeque to construct FIFO queue objects

	// construct PriorityQueue object
 	// overide compare() in Comparator to compare Teller objects
	  
	  customerQ = new ArrayDeque<Customer>();
	  freeTellerQ = new ArrayDeque<Teller>();	  
	  busyTellerQ= new PriorityQueue<Teller>( numTellers, 
						new CompareTellers()); 

	// initialize customerQlimit
	  customerQLimit =customerQlimit;

        // Construct Teller objects and insert into FreeTellerQ
        // assign teller ID from 1, 2,..., numTellers
	  
	  for (int i =1; i <= numTellers; i++)
	  {
		  Teller T = new Teller(i);
		  insertFreeTellerQ(T);
	  }
  }

  // -------------------------------------------------
  // freeTellerQ methods: remove, insert, empty, size
  // -------------------------------------------------

  public Teller removeFreeTellerQ()
  {
	// remove and return a free teller
	// Add statetments
	 return freeTellerQ.remove();
	  
  }

  public void insertFreeTellerQ(Teller teller)
  {
	  // insert a free teller
	  // Add statetments
	  freeTellerQ.add(teller);
  }

  public boolean emptyFreeTellerQ()
  {
	// is freeTellerQ empty?
	// Add statetments
	return freeTellerQ.isEmpty();
	
  }

  public int numFreeTellers()
  {
	// get number of free tellers
	// Add statetments
	return  freeTellerQ.size();
	
  }

  // -------------------------------------------------------
  // busyTellerQ methods: remove, insert, empty, size, peek
  // -------------------------------------------------------

  public Teller removeBusyTellerQ() 
  {
	// remove and return a busy teller
	// Add statetments
	  return busyTellerQ.remove();
	  
  }

  public void insertBusyTellerQ(Teller teller)
  {
	// insert a busy teller
	// Add statetments
	  busyTellerQ.add(teller);
  }

  public boolean emptyBusyTellerQ()
  {
	// is busyTellerQ empty?
	return busyTellerQ.isEmpty();
  }

  public int numBusyTellers()
  {
	// get number of busy tellers
	// Add statetments
	return busyTellerQ.size();
  }


  public Teller getFrontBusyTellerQ() 
  {
	// get front of busy tellers
	// "retrieve" but not "remove"
	// Add statetments
	  
	return busyTellerQ.peek();
  }


  // -------------------------------------------------------
  // customerQ methods: remove, insert, empty, size
  //                    and check isCustomerQTooLong()
  // -------------------------------------------------------

  public Customer removeCustomerQ()
  {
	// remove and return a customer 
	// Add statetments
	return customerQ.remove();
  }

  public void insertCustomerQ(Customer customer)
  {
	// insert a customer 
	// Add statetments
  customerQ.add(customer);
  }


  public boolean emptyCustomerQ()
  {
	// is customerQ empty?
	// Add statetments
	return customerQ.isEmpty();
  }

  public int numWaitingCustomers()
  {
	// get number of customers 
	// Add statetments
	  
	return customerQ.size();
  }

  public boolean isCustomerQTooLong()
  {
	// is customerQ too long?
	// Add statetments
	return customerQ.size() == customerQLimit;
	
  }

  public void printStatistics()
  {
  	System.out.println("\t# waiting customers : "+numWaitingCustomers());
  	System.out.println("\t# busy tellers      : "+numBusyTellers());
  	System.out.println("\t# free tellers      : "+numFreeTellers());
  }

  public static void main(String[] args) {

        // quick check

        // create a ServiceArea and 4 customers
        ServiceArea sc = new ServiceArea(4, 5);
        Customer c1 = new Customer(1,18,10);
        Customer c2 = new Customer(2,33,11);
        Customer c3 = new Customer(3,21,12);
        Customer c4 = new Customer(4,37,13);

        // insert customers into customerQ
  	sc.insertCustomerQ(c1);
  	sc.insertCustomerQ(c2);
  	sc.insertCustomerQ(c3);
  	sc.insertCustomerQ(c4);
	System.out.println(""+sc.customerQ);
        System.out.println("===============================================");
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
        System.out.println("===============================================");

        // remove tellers from freeTellerQ
	System.out.println("freeTellerQ:"+sc.freeTellerQ);
        System.out.println("===============================================");
	Teller p1=sc.removeFreeTellerQ();
	Teller p2=sc.removeFreeTellerQ();
	Teller p3=sc.removeFreeTellerQ();
	Teller p4=sc.removeFreeTellerQ();
	System.out.println("Remove free teller:"+p1);
	System.out.println("Remove free teller:"+p2);
	System.out.println("Remove free teller:"+p3);
	System.out.println("Remove free teller:"+p4);
        System.out.println("===============================================");
	System.out.println("freeTellerQ:"+sc.freeTellerQ);
	System.out.println("busyTellerQ:"+sc.busyTellerQ);
        System.out.println("===============================================");


        // insert customers to tellers
        p1.freeToBusy (c1, 13);
        p2.freeToBusy (c2, 13);
        p3.freeToBusy (c3, 13);
        p4.freeToBusy (c4, 13);
	System.out.println("Assign customers to free tellers");

        // insert tellers to busyTellerQ
        System.out.println("===============================================");
	System.out.println("Insert tellers to busyTellerQ");
	sc.insertBusyTellerQ(p1);
	sc.insertBusyTellerQ(p2);
	sc.insertBusyTellerQ(p3);
	sc.insertBusyTellerQ(p4);
	System.out.println("busyTellerQ:"+sc.busyTellerQ);
        System.out.println("===============================================");

        // remove tellers from busyTellerQ
	p1=sc.removeBusyTellerQ();
	p2=sc.removeBusyTellerQ();
	p3=sc.removeBusyTellerQ();
	p4=sc.removeBusyTellerQ();

        p1.busyToFree();
        p2.busyToFree();
        p3.busyToFree();
        p4.busyToFree();

	System.out.println("Remove busy teller:"+p1);
	System.out.println("Remove busy teller:"+p2);
	System.out.println("Remove busy teller:"+p3);
	System.out.println("Remove busy teller:"+p4);

   }


};

