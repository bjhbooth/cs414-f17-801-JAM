package edu.colostate.cs.cs414.a3.jam.business;

import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.Map;

import edu.colostate.cs.cs414.a3.jam.db.DAO;
import edu.colostate.cs.cs414.a3.jam.db.TrainerDB;

public class TrainerBLogic extends GMSBaseBLogic
{

	// ********************************************************
	//
	// Implement methods of GMSBLogicProvider Interface
	// 
	// ******************************************************** 

	// Provider a concrete DB object derived from BaseDB and implementing DAO (i.e., TrainerDB).
	// The base GMSBLogic class and interface only have knowledge of these two base class levels.
	public DAO initDAO()
	{
		return new TrainerDB();
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
		TrainerBO trainer;
		try {
			trainer = (TrainerBO)bo;
		} catch (Exception e) {
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			if (bo != null)
				results.put(GMSBO.Result.DATA.toString(), bo);
			results.put(GMSBO.Result.UNKNOWN.toString(), "TrainerBLogic.doFinalValidate:  Unknown or null Ojbect.  Expected TrainerBO");
	    
			return results;
		}

		// ------------------------------------------------------
		//  Do final validation of fields. 
		// 	Phone and Email are NOT required. 
		// ------------------------------------------------------  

		System.out.println("bValid is " + bValid.toString());
	
		if ( (trainer.getEmployeeID().length() < 1) || (trainer.getEmployeeID().length() > 15) )  
		{
			System.out.println("1");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.EmployeeAttribute.ID.toString(), "Expected length 1-15 characters.");
		}
	
		if ( (trainer.getFirstName().length() < 1) || (trainer.getFirstName().length() > 25) )
		{
			System.out.println("2");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.FIRST_NAME.toString(), "Expected length 1-25 characters.");
		}
	
		if ( (trainer.getLastName().length() < 1) || (trainer.getLastName().length() > 25) )
		{
			System.out.println("3");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.LAST_NAME.toString(), "Expected length 1-25 characters.");
		}

		if ( (trainer.getInsuranceProvider().length() < 1) || (trainer.getInsuranceProvider().length() > 50) )
		{
			System.out.println("4");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.INSURANCE.toString(), "Expected length 1-50 characters.");
		}

		if (trainer.getHours() <= 0) 
		{
			System.out.println("5");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.TrainerAttribute.HOURS.toString(), "Integer greater than 0 required");
		}


		if ( (trainer.getPrimaryAddress().getStreet().length() < 1) || (trainer.getPrimaryAddress().getStreet().length() > 50 ) )
		{
			System.out.println("6");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_STREET.toString(), "Expected length 1-50 chars.");
		}

		if ( (trainer.getPrimaryAddress().getCity().length() < 1) || (trainer.getPrimaryAddress().getCity().length() > 25) )
		{
			System.out.println("7");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_CITY.toString(), "Expected length 1-25 characters.");
		}
	
		if ( (trainer.getPrimaryAddress().getState().length() < 1) || (trainer.getPrimaryAddress().getState().length() > 26) )
		{
			System.out.println("8");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_STATE.toString(), "Expected length 1-26 chars.");
		}
	
		if ( (trainer.getPrimaryAddress().getZip().length() != 5) && (trainer.getPrimaryAddress().getZip().length() != 9 ) )
		{
			System.out.println("9");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_ZIP.toString(), "Expected length 5 or 9.");
		}

		String phone = trainer.getPhone();
		if ( (phone != null) && (phone.length() > 0) ) 
		{ 
			// 	User entered an optional phone number.  Validate it.
			boolean b = Pattern.matches("/d/d/d-/d/d/d-/d/d/d/d", phone);
			if ( (phone.length() > 10) || (b == false) )
			{
				System.out.println("10");
				bValid = Boolean.FALSE;
				results.put(TrainerBO.PersonAttribute.PHONE.toString(), "Expected format ddd-ddd-dddd.");
			}
		}
			
		String email = trainer.getEmail();
		if ( (email != null) && (email.length() > 0) ) 
		{ 
			//  User entered an optional email.  Validate it.
			boolean b = Pattern.matches("[^@&&[^\\s]]@[^@[^@&&[^\\s]]", email);
			if ( (email.length() > 50) || (b == false) )
			{
				System.out.println("10");
				bValid = Boolean.FALSE;
				results.put(TrainerBO.PersonAttribute.EMAIL.toString(), "Enter valid email format.");
			}
		}
	
		if ( (trainer.getQualifications().length() < 1) || trainer.getQualifications().length() > 100 )
		{
			System.out.println("10");
			System.out.println("Qual" + trainer.getQualifications());
			bValid = Boolean.FALSE;
			results.put (TrainerBO.TrainerAttribute.QUALIFICATIONS.toString(), "Expected length 1-100.");
		}

		System.out.println("bValid is " + bValid);
		
		results.put(GMSBO.Result.VALID.toString(), bValid);
		results.put(GMSBO.Result.DATA.toString(), bo);
		
		return results;
	
	}  // end doFinalValidate

}  // end class TrainerBLogic