package edu.colostate.cs.cs414.a3.jam.business;

// Implements the Trainer Business Object.

public class TrainerBO extends EmployeeBO 
{
	// Tags for identifying attributes as part of error reporting.
	// Hashtable requires an Object as the Key and Value.
	// Enums by default use "int."  
	// For such a small app, accept the overhead of converting 
	// to help guarantee a consistent set of identifiers is used as keys. 
	public static enum TrainerAttribute {
		HOURS		   { public String toString() { return "HOURS"; } },			// INT
		QUALIFICATIONS { public String toString() { return "QUALIFICATIONS"; } }    // 100 
	};
	
	private int hours;
	private String qualifications;

	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public TrainerBO ()
	{
		super();
		this.hours = 0;
		this.qualifications = "";
	}  // end default constructor

	public TrainerBO (int hours, String qualifications, String id, String firstName, String lastName, String phone, String email, String insuranceProvider, String street, String city, String state, String zip)
	{
		super(id, firstName, lastName, phone, email, insuranceProvider, street, city, state, zip);
		
		// Use the setters in csae there is validation / etc. 
		setHours(hours);
		setQualifications(qualifications);
	}  // end constructor

	// **********************************************************************
	// Getters and Setters  
	// **********************************************************************

	public int getHours() 
	{ 
		return hours; 
	}

	public void setHours (int hours) 
	{
		this.hours = hours;
	}

	public String getQualifications() 
	{ 
		return qualifications; 
	}

	public void setQualifications (String qualifications) 
	{
		this.qualifications = qualifications;
	}

	// **********************************************************************
	// Abstract Methods of GMSBO   
	// **********************************************************************

	public void  printObject()
	{
		System.out.println("Trainer:");
		System.out.println("\tEmployee ID = " + this.getEmployeeID());
		System.out.println("\tFirst = " + this.getFirstName());
		System.out.println("\tLast = " + this.getLastName());
		System.out.println("\tPhone = " + this.getPhone());
		System.out.println("\tEmail = " + this.getEmail());
		System.out.println("\tInsurance Provider = " + this.getInsuranceProvider());
		System.out.println("\tHours = " + this.getHours());
		System.out.println("\tQualifications = " + this.getQualifications());
		System.out.println("\tStreet = " + this.getPrimaryAddress().getStreet());
		System.out.println("\tCity = " + this.getPrimaryAddress().getCity());
		System.out.println("\tState = " + this.getPrimaryAddress().getState());
		System.out.println("\tZip = " + this.getPrimaryAddress().getZip());

	}  // end printObject
	
}  // end TrainerBO
