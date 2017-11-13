package edu.colostate.cs.cs414.a3.jam.business;

public class UserBO extends GMSBO
{
	// Tags for identifying attributes as part of error reporting. 
	// Hashtable requires an Object as the Key and Value.
	// Enums by default use "int."  
	// For such a small app, accept the overhead of converting 
	// to help guarantee a consistent set of identifiers is used as keys. 
	public static enum Attribute {
		USERNAME	{ public String toString() { return "USER_NAME"; } },
		PASSWORD	{ public String toString() { return "PASSWORD"; } },
		ACCESS		{ public String toString() { return "ACCESS"; } }
	}

	public static enum AccessLevel {MANAGER, TRAINER, NONE};
	
	String 	userName,
			password;

	AccessLevel  accessLevel;
	
	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public UserBO ()
	{
		super();
		
		this.userName = "";
		this.password = "";
		this.accessLevel = AccessLevel.NONE;
	}  // end default constructor

	public UserBO (String userName, String password, AccessLevel accessLevel)
	{
		super();
		
		// Use the setters in case there is validation / etc. 
		setUserName(userName);
		setPassword(password);
		setAccessLevel(accessLevel);
	}  // end constructor

	// **********************************************************************
	// Getters and Setters  
	// **********************************************************************

	public String getUserName() 
	{ 
		return userName; 
	}

	public void setUserName(String uswerName) 
	{
		this.userName = userName;
	}

	public String getPassword() 
	{ 
		return password; 
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public AccessLevel getAccessLevel() 
	{ 
		return accessLevel; 
	}

	public void setAccessLevel(AccessLevel accessLevel) 
	{
		this.accessLevel = accessLevel;
	}

	// **********************************************************************
	// Abstract Methods of GMSBO   
	// **********************************************************************

	public void  printObject()
	{
		System.out.println("User:");
		System.out.println("\tUsername = " + this.getUserName());
		System.out.println("\tPassword = " + this.getPassword());
		AccessLevel access = this.getAccessLevel();
		if (access == AccessLevel.MANAGER)
			System.out.println("\tAccess = " + "Manager");
		else if (access == AccessLevel.TRAINER)
			System.out.println("\tAccess = " + "Trainer");
		else 
			System.out.println("\tAccess = " + "Unspecified");
		
	}  // end printObject

}  // end UserBO class
