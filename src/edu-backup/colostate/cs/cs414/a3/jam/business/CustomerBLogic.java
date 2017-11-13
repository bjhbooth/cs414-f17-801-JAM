package edu.colostate.cs.cs414.a3.jam.business;

import java.util.Map;
import java.util.Hashtable;
import java.util.regex.Pattern;

import edu.colostate.cs.cs414.a3.jam.db.DAO;
import edu.colostate.cs.cs414.a3.jam.db.CustomerDB;

public class CustomerBLogic extends GMSBaseBLogic  
{

	// ********************************************************
	//
	// Implement methods of GMSBLogicProvider Interface
	// 
	// ******************************************************** 

	// -------------------------------------------------
	// Provider a concrete DB object derived from BaseDB and implementing DAO (i.e., CustomerDB).
	// The base GMSBLogic class and interface only have knowledge of these two base class levels.
	// -------------------------------------------------
	public DAO initDAO()
	{
		return new CustomerDB();
	}

	// --------------------------------------------------
	// Do final validation before requesting to save.
	//
	// RETURN:  HASHTABLE (See GMSViewable for description).
	// --------------------------------------------------
	public Map<String, Object> doFinalValidate (GMSBO bo)
	{
		Boolean bValid = Boolean.TRUE;
	
		Hashtable<String, Object> results = new Hashtable<String, Object>();
		CustomerBO customer;
		try {
			customer = (CustomerBO)bo;
		} catch (Exception e) {
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			if (bo != null)
				results.put(GMSBO.Result.DATA.toString(), bo);
			results.put(GMSBO.Result.UNKNOWN.toString(), "CustomerBLogic.doFinalValidate:  Unknown or null Ojbect.  Expected CustomerBO");
	    
			return results;
		}

		// ------------------------------------------------------
		//  Do final validation of fields. 
		// 	Phone and Email are NOT required. 
		// ------------------------------------------------------  

		System.out.println("bValid is " + bValid.toString());
	
		if ( (customer.getCustomerID().length() < 1) || (customer.getCustomerID().length() > 15) )  
		{
			System.out.println("1");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.CustomerAttribute.ID.toString(), "Expected length 1-15 characters.");
		}
	
		if ( (customer.getFirstName().length() < 1) || (customer.getFirstName().length() > 25) )
		{
			System.out.println("2");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.FIRST_NAME.toString(), "Expected length 1-25 characters.");
		}
	
		if ( (customer.getLastName().length() < 1) || (customer.getLastName().length() > 25) )
		{
			System.out.println("3");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.LAST_NAME.toString(), "Expected length 1-25 characters.");
		}

		if ( (customer.getInsuranceProvider().length() < 1) || (customer.getInsuranceProvider().length() > 50) )
		{
			System.out.println("4");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.INSURANCE.toString(), "Expected length 1-50 characters.");
		}

		if ( (customer.getPrimaryAddress().getStreet().length() < 1) || (customer.getPrimaryAddress().getStreet().length() > 50 ) )
		{
			System.out.println("6");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_STREET.toString(), "Expected length 1-50 chars.");
		}

		if ( (customer.getPrimaryAddress().getCity().length() < 1) || (customer.getPrimaryAddress().getCity().length() > 25) )
		{
			System.out.println("7");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_CITY.toString(), "Expected length 1-25 characters.");
		}
	
		if ( (customer.getPrimaryAddress().getState().length() < 1) || (customer.getPrimaryAddress().getState().length() > 26) )
		{
			System.out.println("8");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_STATE.toString(), "Expected length 1-26 chars.");
		}
	
		if ( (customer.getPrimaryAddress().getZip().length() != 5) && (customer.getPrimaryAddress().getZip().length() != 9 ) )
		{
			System.out.println("9");
			bValid = Boolean.FALSE;
			results.put (CustomerBO.PersonAttribute.ADDR_ZIP.toString(), "Expected length 5 or 9.");
		}

		String phone = customer.getPhone();
		if ( (phone != null) && (phone.length() > 0) ) 
		{ 
			// 	User entered an optional phone number.  Validate it.
			boolean b = Pattern.matches("/d/d/d-/d/d/d-/d/d/d/d", phone);
			if ( (phone.length() > 10) || (b == false) )
			{
				System.out.println("10");
				bValid = Boolean.FALSE;
				results.put(CustomerBO.PersonAttribute.PHONE.toString(), "Expected format ddd-ddd-dddd.");
			}
		}
			
		String email = customer.getEmail();
		if ( (email != null) && (email.length() > 0) ) 
		{ 
			//  User entered an optional email.  Validate it.
			boolean b = Pattern.matches("[^@&&[^\\s]]@[^@[^@&&[^\\s]]", email);
			if ( (email.length() > 50) || (b == false) )
			{
				System.out.println("10");
				bValid = Boolean.FALSE;
				results.put(CustomerBO.PersonAttribute.EMAIL.toString(), "Enter valid email format.");
			}
		}
	
		System.out.println("bValid is " + bValid);
		
		results.put(GMSBO.Result.VALID.toString(), bValid);
		results.put(GMSBO.Result.DATA.toString(), bo);
		
		return results;
	
	}  // end doFinalValidate
	
}  // end class CustomerBLogic 
