package edu.colostate.cs.cs414.a3.jam.business;

import edu.colostate.cs.cs414.a3.jam.business.UserBO.AccessLevel;

// Person Business Object.  This class is created because both the Trainer and Customer & Manager classes require 
// personal information to be stored.  This is a way of not duplicating that implementation.  
// Given the size of the system, this may be overkill at this point.  However, it opens the door to 
// goowth (storing other personal information - - gender, multiple addresses, etc. 

public abstract class PersonBO extends GMSBO
{
	// Tags for identifying attributes as part of error reporting.
	// Hashtable requires an Object as the Key and Value.
	// Enums by default use "int."  
	// For such a small app, accept the overhead of converting 
	// to help guarantee a consistent set of identifiers is used as keys. 
	public static enum PersonAttribute {
		FIRST_NAME  { public String toString() { return "FIRST_NAME"; } },		// 25
		LAST_NAME   { public String toString() { return "LAST_NAME"; } },		// 25
		ADDR_STREET	{ public String toString() { return "ADDR_STREET"; } }, 	// 50
		ADDR_CITY	{ public String toString() { return "ADDR_CITY"; } },		// 25
		ADDR_STATE	{ public String toString() { return "ADDR_STATE"; } },		// 26
		ADDR_ZIP	{ public String toString() { return "ADDR_ZIP"; } },		// 9
		PHONE		{ public String toString() { return "PHONE"; } },			// 10 - NOT REQUIRED 
		EMAIL		{ public String toString() { return "EMAIL"; } },			// 50 - NOT REQUIRED 
		INSURANCE 	{ public String toString() { return "INSURANCE"; } }		// 50 
	};
	
	private String 	firstName,
					lastName, 
					phone,
					email,
					insuranceProvider;
	
	private Address primaryAddress;
	
	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public PersonBO ()
	{
		super();
		
		this.firstName = "";
		this.lastName = "";
		this.phone = "";
		this.email = "";
		this.insuranceProvider = "";
		this.primaryAddress = null;
	}  // end default constructor

	public PersonBO (String firstName, String lastName, String phone, String email, String insuranceProvider, String street, String city, String state, String zip)
	{
		super();
		
		// Use the setters in csae there is validation / etc. 
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setEmail(email);
		setInsuranceProvider(insuranceProvider);
		setPrimaryAddress(street, city, state, zip);
	}  // end constructor

	public PersonBO (String firstName, String lastName, String phone, String email, String insuranceProvider, Address primaryAddress)
	{
		// Use the setters in csae there is validation / etc. 
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setEmail(email);
		setInsuranceProvider(insuranceProvider);
		setPrimaryAddress(primaryAddress);
	}  // end constructor

	// **********************************************************************
	// Getters and Setters  
	// **********************************************************************

	public String getFirstName() 
	{ 
		return firstName; 
	}

	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	public String getLastName() 
	{ 
		return lastName; 
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getFullName() 
	{
	    return firstName + " " + lastName;
	}

	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getInsuranceProvider() 
	{ 
		return insuranceProvider; 
	}

	public void setInsuranceProvider(String insuranceProvider) 
	{
		this.insuranceProvider = insuranceProvider;
	}

	public Address getPrimaryAddress()
	{
		return primaryAddress;
	}

	public void setPrimaryAddress(String street, String city, String state, String zip)
	{
		Address address = new Address (street, city, state, zip);
		setPrimaryAddress(address);
	}
	
	public void setPrimaryAddress(Address primaryAddress)
	{
		this.primaryAddress = primaryAddress;
	}
	
}  // end PersonBO class
