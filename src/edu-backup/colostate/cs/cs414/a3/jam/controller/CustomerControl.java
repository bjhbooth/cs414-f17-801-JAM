package edu.colostate.cs.cs414.a3.jam.controller;

import java.util.Map;
import java.util.Hashtable;

import edu.colostate.cs.cs414.a3.jam.view.CustomerPanel;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.CustomerBO;
import edu.colostate.cs.cs414.a3.jam.business.CustomerBLogic;

public class CustomerControl extends GMSBaseControl 
{

	// *********************************************************************
	// 
	// Constructors - Hide the defult constructor to require a CustomerPanel.
	// 
	// *********************************************************************

	// Construct a CustomerControl with the specified view.  
	// The GMSBaseContrl manages the reference as the base type
	// GMSPanel.  The constructor will instantiate a GMSBLogic object.
	public CustomerControl(CustomerPanel view)
	{
		super(view);
		this.gmsBLogic = new CustomerBLogic();
	}
	
	private CustomerControl()
	{
		super(null);
		this.gmsBLogic = null;
	}

	// *********************************************************************
	// 
	// GMSController interface methods
	// 
	// *********************************************************************

	// --------------------------------------------------
	// retrieveUserInput:
	// 
	// Retrieve values entered by the user.  Call the derived
	// class' implementation of this method as it will have 
	// knowledge of the specific view. 
	//
	// Return CustomerBO (derived from GMSBO); null if the 
	// object could not be created.
	// --------------------------------------------------
	public CustomerBO retrieveUserInput()
	{
		// Controller is not a/w a view. 
		if (gmsView == null) 
			return null;
		
		CustomerPanel customerView; 
		
		try {
			customerView = (CustomerPanel)gmsView;		
		} catch (Exception e) {
		    return null;
		}
	
		// Gather values from the view. 
		String id = customerView.getCustomerID();
		CustomerBO.MemberStatus status = customerView.getStatus();
		
		String firstName = customerView.getFirstName();
		String lastName = customerView.getLastName();
		String street = customerView.getStreet();
		String city = customerView.getCity();
		String state = customerView.getState();
		String zip = customerView.getZip();
		String phone = customerView.getPhone();
		String email = customerView.getEmail();
		String insurance = customerView.getInsuranceProvider();

		CustomerBO customer = new CustomerBO (id, status, firstName, lastName, phone, email, insurance, street, city, state, zip);
				
		return customer;
		
	}  // retrieveUserInput
	

	// --------------------------------------------------
	// doSoftValidate 
	// 
	// Perform basic validation that does not require DB or Logic specific knowledge.
	// Checks include:  Required fields provided and are correct type (i.e., String converts to number).
	// Save for the business module those things requring buesiness knowledge or DB access (duplicates).
	// 
	// The default return type (unless otherwise defined by the derived classes) is    
	// HASHTABLE (returned as a Map to easily handle change to HashMap, Tree, etc.)
	// Contents:	STATUS         / VALID or INVALID
	//				DATA           / Derived type of GMSBO or GMSTHinBO 
	//	            <ATTRIBUTE>    / Key defined in BO class.  A key/value pair will be 
	//								 added for each attribute with an error.  
	//								 The associated value will be a short error message.
	//	 							 Ex:  FIRST_NAME / "Required field." 
	// --------------------------------------------------
	public Map<String, Object> doSoftValidate (GMSBO bo)
	{
		Boolean bValid = Boolean.TRUE;
		
		Hashtable<String, Object> results = new Hashtable<String, Object>();
		CustomerBO customer = null;
		
		try {
			customer = (CustomerBO)bo;
		} catch (Exception e) {
		    results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
		    if (bo != null)
		    	results.put(GMSBO.Result.DATA.toString(), bo);
		    results.put(GMSBO.Result.UNKNOWN.toString(), "CustomerControl.doSoftValidate:  Unknown or null Ojbect.  Expected CustomerBO");
		    
		    return results;
		}

		// ------------------------------------------------------
		// Check for empty / null values on required fields.
		//  	Phone and Email are NOT required. 
		// ------------------------------------------------------ 

		System.out.println("bValid is " + bValid.toString());
		
		if ( (customer.getCustomerID() == null) || customer.getCustomerID().isEmpty() )
		{
			System.out.println("1");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.CustomerAttribute.ID.toString(), "Required field.");
		}
		
		if ( (customer.getFirstName() == null) || customer.getFirstName().isEmpty() )
		{
			System.out.println("2");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.FIRST_NAME.toString(), "Required field.");
		}
		
		if ( (customer.getLastName() == null) || customer.getLastName().isEmpty() )
		{
			System.out.println("3");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.LAST_NAME.toString(), "Required field.");
		}

		if ( (customer.getInsuranceProvider() == null) || customer.getInsuranceProvider().isEmpty() )
		{
			System.out.println("4");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.INSURANCE.toString(), "Required field.");
		}

		if ( (customer.getPrimaryAddress().getStreet() == null) || customer.getPrimaryAddress().getStreet().isEmpty() )
		{
			System.out.println("6");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_STREET.toString(), "Required field.");
		}

		if ( (customer.getPrimaryAddress().getCity() == null) || customer.getPrimaryAddress().getCity().isEmpty() )
		{
			System.out.println("7");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_CITY.toString(), "Required field.");
		}
		
		if ( (customer.getPrimaryAddress().getState() == null) || customer.getPrimaryAddress().getState().isEmpty() )
		{
			System.out.println("8");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_STATE.toString(), "Required field.");
		}
		
		if ( (customer.getPrimaryAddress().getZip() == null) || customer.getPrimaryAddress().getZip().isEmpty() )
		{
			System.out.println("9");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_ZIP.toString(), "Required field.");
		}

		System.out.println("bValid is " + bValid);
		
		results.put(GMSBO.Result.VALID.toString(), bValid);
		results.put(GMSBO.Result.DATA.toString(), bo);
		
		return results;
		
	}  // end doSoftValidate

}  // end class CustomerControl
