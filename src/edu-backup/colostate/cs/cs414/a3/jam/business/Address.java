package edu.colostate.cs.cs414.a3.jam.business;

// Address class to store parts of an address as a unit.  Since there are only Customer and Trainer objects that use this, 
// it may be more than is needed.  However, were the application to grow, the ability to have more than one address
// would be a possibility in which case it would be difficult to manage parts of an address as a single unit
// without the Address object to keep them together. 
public class Address 
{
	private String 	street,
			  		city,
			  		state,
			  		zip;
	
	// **********************************************************************
	// Constructors 
	// **********************************************************************
	public Address ()
		{
			this.street = "";
			this.city = "";
			this.state = "";
			this.zip = "";
		}  // end default constructor

	public Address (String street, String city, String state, String zip)
		{
			// Use the setters in case there is validation / etc. 
			setStreet(street);
			setCity(city);
			setState(state);
			setZip(zip);
			}  // end constructor

		// **********************************************************************
		// Getters and Setters  
		// **********************************************************************

		public String getStreet() 
		{ 
			return street; 
		}

		public void setStreet(String street)
		{
			this.street = street;
		}

		public String getCity() 
		{ 
			return city; 
		}

		public void setCity(String city)
		{
			this.city = city;
		}

		public String getState() 
		{ 
			return state; 
		}

		public void setState(String state)
		{
			this.state = state;
		}

		public String getZip() 
		{ 
			return zip; 
		}

		public void setZip(String zip)
		{
			this.zip = zip;
		}
	
}  // end Address class
