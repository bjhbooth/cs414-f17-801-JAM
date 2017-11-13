package edu.colostate.cs.cs414.a3.jam.controller;

import java.lang.Boolean;
import java.util.Map;
import java.util.Hashtable;
import edu.colostate.cs.cs414.a3.jam.business.TrainerBO;
import edu.colostate.cs.cs414.a3.jam.business.TrainerBLogic;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.GMSThinBO;
import edu.colostate.cs.cs414.a3.jam.view.TrainerPanel;

public class TrainerControl extends GMSBaseControl 
{
	// Construct a TrainerControl with the specified view.  
	// The GMSBaseContrl manages the reference as the base type
	// GMSPanel.  The constructor will instantiate a GMSLogic object. 
	public TrainerControl(TrainerPanel view)
	{
		super(view);
		this.gmsBLogic = new TrainerBLogic();
	}
	
	private TrainerControl()
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
	// Retrieve values entered by the user.  
	// Return a business object derived from GMSBO (here TrainerBO); 
	// null if the object could not be created.
	// --------------------------------------------------
	public TrainerBO retrieveUserInput()
	{
		// Controller is not a/w a view. 
		if (gmsView == null) 
			return null;
		
		TrainerPanel trainerView; 
		
		try {
			trainerView = (TrainerPanel)gmsView;		
		} catch (Exception e) {
		    return null;
		}
	
		// Gather values from the view. 
		String id = trainerView.getEmployeeID();
		String firstName = trainerView.getFirstName();
		String lastName = trainerView.getLastName();
		String street = trainerView.getStreet();
		String city = trainerView.getCity();
		String state = trainerView.getState();
		String zip = trainerView.getZip();
		String phone = trainerView.getPhone();
		String email = trainerView.getEmail();
		String insurance = trainerView.getInsuranceProvider();
		String qualifications = trainerView.getQualifications(); 
		int hours;
		try {
			hours = Integer.parseInt(trainerView.getHours());
		} catch (Exception e) {
			// View to have textbox that restricts input.  
			// Till then, will be invalid on validation.
			hours = -1;    
		}
				
		TrainerBO trainer = new TrainerBO (hours, qualifications, id, firstName, lastName, phone, email, insurance, street, city, state, zip);
				
		return trainer;
		
	}  // retrieveUserInput
	
	// --------------------------------------------------
	// Perform basic validation that does not require DB or Logic specific knowledge.
	// Checks include:  Required fields provided and are correct type (i.e., String converts to number).
	// Save for the business module those things requring buesiness knowledge or DB access (duplicates).
	// 
	// RETURN:  HASHTABLE (See GMSViewable for description).
	// --------------------------------------------------
	public Map<String, Object> doSoftValidate (GMSBO bo)
	{
		Boolean bValid = Boolean.TRUE;
		
		Hashtable<String, Object> results = new Hashtable<String, Object>();
		TrainerBO trainer = null;
		
		try {
			trainer = (TrainerBO)bo;
		} catch (Exception e) {
		    results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
		    if (bo != null)
		    	results.put(GMSBO.Result.DATA.toString(), bo);
		    results.put(GMSBO.Result.UNKNOWN.toString(), "TrainerControl.doSoftValidate:  Unknown or null Ojbect.  Expected TrainerBO");
		    
		    return results;
		}

		// ------------------------------------------------------
		// Check for empty / null values on required fields.
		//  	Phone and Email are NOT required. 
		// ------------------------------------------------------ 

		System.out.println("bValid is " + bValid.toString());
		
		if ( (trainer.getEmployeeID() == null) || trainer.getEmployeeID().isEmpty() )
		{
			System.out.println("1");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.EmployeeAttribute.ID.toString(), "Required field.");
		}
		
		if ( (trainer.getFirstName() == null) || trainer.getFirstName().isEmpty() )
		{
			System.out.println("2");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.FIRST_NAME.toString(), "Required field.");
		}
		
		if ( (trainer.getLastName() == null) || trainer.getLastName().isEmpty() )
		{
			System.out.println("3");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.LAST_NAME.toString(), "Required field.");
		}

		if ( (trainer.getInsuranceProvider() == null) || trainer.getInsuranceProvider().isEmpty() )
		{
			System.out.println("4");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.INSURANCE.toString(), "Required field.");
		}

		if (trainer.getHours() <= 0) 
		{
			System.out.println("5");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.TrainerAttribute.HOURS.toString(), "Integer greater than 0 required");
		}

		if ( (trainer.getPrimaryAddress().getStreet() == null) || trainer.getPrimaryAddress().getStreet().isEmpty() )
		{
			System.out.println("6");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_STREET.toString(), "Required field.");
		}

		if ( (trainer.getPrimaryAddress().getCity() == null) || trainer.getPrimaryAddress().getCity().isEmpty() )
		{
			System.out.println("7");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_CITY.toString(), "Required field.");
		}
		
		if ( (trainer.getPrimaryAddress().getState() == null) || trainer.getPrimaryAddress().getState().isEmpty() )
		{
			System.out.println("8");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_STATE.toString(), "Required field.");
		}
		
		if ( (trainer.getPrimaryAddress().getZip() == null) || trainer.getPrimaryAddress().getZip().isEmpty() )
		{
			System.out.println("9");
			bValid = Boolean.FALSE;
			results.put (TrainerBO.PersonAttribute.ADDR_ZIP.toString(), "Required field.");
		}

		if ( (trainer.getQualifications() == null) || trainer.getQualifications().isEmpty() )
		{
			System.out.println("10");
			System.out.println("Qual" + trainer.getQualifications());
			bValid = Boolean.FALSE;
			results.put (TrainerBO.TrainerAttribute.QUALIFICATIONS.toString(), "Required field.");
		}

		System.out.println("bValid is " + bValid);
		
		results.put(GMSBO.Result.VALID.toString(), bValid);
		results.put(GMSBO.Result.DATA.toString(), bo);
		
		return results;
		
	}  // end doSoftValidate
	
}  // end TrainerControl

