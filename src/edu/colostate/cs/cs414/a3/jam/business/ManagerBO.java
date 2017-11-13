package edu.colostate.cs.cs414.a3.jam.business;

//Implements the Manager  Business Object.  The manageer does not have much functionality in the applicatn.  
// It's primary function is to provider ta type "manager" as a role that can use the system. 

public class ManagerBO extends EmployeeBO 
{
	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public ManagerBO ()
	{
		super();
	}  // end default constructor

	public ManagerBO (long hours, String qualifications, String id, String firstName, String lastName, String phone, String email, String insuranceProvider, String street, String city, String state, String zip)
	{
		super(id, firstName, lastName, phone, email, insuranceProvider, street, city, state, zip); 
	}  // end constructor

	// **********************************************************************
	// Abstract printObject method from GMSBO 
	// **********************************************************************
	public void  printObject()
	{
		// This object is not heavily used.  Don't want to print. 
	}
	
}  // end ManagerBO
