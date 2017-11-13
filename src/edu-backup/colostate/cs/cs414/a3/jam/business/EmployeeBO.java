package edu.colostate.cs.cs414.a3.jam.business;

// Provide an Employee Business Object for hte Gym Management System.  This class does not have 
// a lot of functionality other than to provide a base class that the GymManagementSystem can 
// refer to as the logged in user (wiehter manager or trainer).  
public abstract class EmployeeBO extends PersonBO
{
	// Tags for identifying attributes as part of error reporting.
	// Hashtable requires an Object as the Key and Value.
	// Enums by default use "int."  
	// For such a small app, accept the overhead of converting 
	// to help guarantee a consistent set of identifiers is used as keys. 
	public static enum EmployeeAttribute {
		ID 		{ public String toString() { return "ID"; } }				// 15 
	};
	
	private String employeeID;   

	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public EmployeeBO () 
	{
		super();
		this.employeeID = "";
	}  // end default constructor

	public EmployeeBO (String id, String firstName, String lastName, String phone, String email, String insuranceProvider, String street, String city, String state, String zip)
	{
		super(firstName, lastName, phone, email, insuranceProvider, street, city, state, zip);
		
		// Use the setters in case there is validation / etc. 
		setEmployeeID(id);
	}  // end constructor

	// **********************************************************************
	// Getters and Setters  
	// **********************************************************************

	public String getEmployeeID() 
	{ 
		return employeeID; 
	}

	public void setEmployeeID(String id) 
	{
		this.employeeID = id;
	}


}  // end EmployeeBO class
