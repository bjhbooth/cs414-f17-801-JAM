package edu.colostate.cs.cs414.a3.jam.business;

import edu.colostate.cs.cs414.a3.jam.business.UserBO.AccessLevel;

public class CustomerBO extends PersonBO
{
	// Tags for identifying attributes as part of error reporting. 
	// Hashtable requires an Object as the Key and Value.
	// Enums by default use "int."  
	// For such a small app, accept the overhead of converting 
	// to help guarantee a consistent set of identifiers is used as keys. 
	public static enum CustomerAttribute {
		ID 			{ public String toString() { return "ID"; } },				// 15
		STATUS		{ public String toString() { return "STATUS"; } }			// ACTIVE or INACTIVE 
	};

	public static enum MemberStatus { 
		ACTIVE		{ public String toString() { return "ACTIVE"; } },
		INACTIVE 	{ public String toString() { return "INACTIVE"; } }
	};
	
	private String customerID; 
	MemberStatus memberStatus; 
	
	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public CustomerBO () 
	{
		super();
		this.customerID = "";
		this.memberStatus = MemberStatus.INACTIVE;
	}  // end default constructor

	public CustomerBO (String id, MemberStatus status, String firstName, String lastName, String phone, String email, String insuranceProvider, String street, String city, String state, String zip)
	{
		super(firstName, lastName, phone, email, insuranceProvider, street, city, state, zip);
		
		// Use the setters in case there is validation / etc. 
		setCustomerID(id);
		setMemberStatus(status);
	
	}  // end constructor
	
	// **********************************************************************
	// Getters and Setters  
	// **********************************************************************

	public String getCustomerID() 
	{ 
		return customerID; 
	}

	public void setCustomerID(String id) 
	{
		this.customerID = id;
	}

	public MemberStatus getMemberStatus() 
	{ 
		return memberStatus; 
	}

	public void setMemberStatus(MemberStatus status) 
	{
		this.memberStatus = status;
	}

	// **********************************************************************
	// Abstract Methods of GMSBO   
	// **********************************************************************

	public void  printObject()
	{
		System.out.println("Customer:");
		System.out.println("\tCustomer ID = " + this.getCustomerID());
		if (this.getMemberStatus() == MemberStatus.ACTIVE)
			System.out.println("Status = Active");
		else
			System.out.println("Status = Inactive");
		System.out.println("\tFirst = " + this.getFirstName());
		System.out.println("\tLast = " + this.getLastName());
		System.out.println("\tPhone = " + this.getPhone());
		System.out.println("\tEmail = " + this.getEmail());
		System.out.println("\tInsurance Provider = " + this.getInsuranceProvider());
		System.out.println("\tStreet = " + this.getPrimaryAddress().getStreet());
		System.out.println("\tCity = " + this.getPrimaryAddress().getCity());
		System.out.println("\tState = " + this.getPrimaryAddress().getState());
		System.out.println("\tZip = " + this.getPrimaryAddress().getZip());

	}  // end printObject

}  // end CustomerBO class
