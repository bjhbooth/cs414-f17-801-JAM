package edu.colostate.cs.cs414.a3.jam.business;

import edu.colostate.cs.cs414.a3.jam.business.UserBO.AccessLevel;

// EquipmentBO 
public class EquipmentBO extends GMSBO
{
	// Tags for identifying attributes as part of error reporting. 
	// Hashtable requires an Object as the Key and Value.
	// Enums by default use "int."  
	// For such a small app, accept the overhead of converting 
	// to help guarantee a consistent set of identifiers is used as keys. 
	public static enum EquipmentAttribute {
		NAME		{ public String toString() { return "NAME"; } },
		QUANTITY	{ public String toString() { return "QUANTITY"; } },
		IMAGE		{ public String toString() { return "IMAGE"; } }
	}

	// **********************************************************************
	// Attributes 
	// **********************************************************************
	String 		name;
	int			quantity;
	byte[]		image;		// The view will be responsible for converting the information
							// to the appropriate type to display.  Examople:  
							// a Java GUI will use ImageBuffer.  
	
	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public EquipmentBO ()
	{
		super();
		
		this.name = "";
		this.image = null;
		this.quantity = 0;
	}  // end default constructor

	public EquipmentBO (String name, byte[] image, int quantity)
	{
		super();
		
		// Use the setters in case there is validation / etc. 
		setName(name);
		setImage(image);
		setQuantity(quantity);
	}  // end constructor

	// **********************************************************************
	// Getters and Setters  
	// **********************************************************************

	public String getName() 
	{ 
		return name; 
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public byte[] getImage() 
	{ 
		return image; 
	}

	public void setImage(byte[] image) 
	{
		this.image = image;
	}
	
	public int getQuantity() 
	{ 
		return quantity; 
	}

	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
	
	// **********************************************************************
	// Abstract Methods of GMSBO   
	// **********************************************************************

	public void  printObject()
	{
		System.out.println("Equipment:");
		System.out.println("\tEquipment name = " + this.getName());
		System.out.println("Quantity = " + this.getQuantity());
		if (image != null)
			System.out.println("Has image");
		else
			System.out.println("Image not assigned");
			
	}  // end printObject
	
}  // end class EquipmentBO
