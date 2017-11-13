package edu.colostate.cs.cs414.a3.jam.controller;

import java.util.Map;

import edu.colostate.cs.cs414.a3.jam.controller.GMSController;
import edu.colostate.cs.cs414.a3.jam.business.GMSBaseBLogic;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.GMSThinBO;
import edu.colostate.cs.cs414.a3.jam.view.GMSViewable;
import edu.colostate.cs.cs414.a3.jam.view.GMSViewable.Mode;

// GMSBaseControl	
// Base controller class Keep the flow of control consistent for all 
// derived controllers.  For example:  gather, do simple validation,
// complete request if valid, switching views to update after create, 
// etc.  If a view truly needs different behavior, it can override 
// base methods.  
public abstract class GMSBaseControl implements GMSController <GMSBO, GMSThinBO>
{
	
	protected GMSViewable 	gmsView;
	protected GMSBaseBLogic	gmsBLogic;
		
	// --------------------------------------
	// Constructors
	// -------------------------------------
	protected GMSBaseControl (GMSViewable view)
	{
		super();
		this.gmsView = view;
		
	}  // end constructor
	
	// Dont's allow construction w/o a view 
	private GMSBaseControl ()
	{
		super();
		this.gmsView = null;
	}  // end default constructor 

	// -------------------------------------------------------
	// Public methods made available to the views. 
	// -------------------------------------------------------
	public boolean saveUserInput (Mode mode)
	{
		boolean success = false;
		
		// Gather input from the screen.  Call the derived class' implementation of retrieveUserInput. 
		GMSBO bo = retrieveUserInput();

		// Do preliminary validation (basic error checking that do not require detailed business logic 
		// or DB access (i.e., duplicates). For example, required fields are non-null, etc.
		// The return value is a Hashtable.  See its description at the top of 
		Map results = doSoftValidate(bo);
		if ( ((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue() == true) 
		{
			System.out.println("saveUserInput doSoftValidate returned TRUE - - calling saveNew or saveUpdate");
			
			// Data passed validation.  Attempt to save.
			if (mode == Mode.CREATE)
				results = gmsBLogic.saveNew(bo);
			else if (mode == Mode.UPDATE)
				results = gmsBLogic.saveUpdate(bo);
				
		   	if (((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue() == true)
			{
				// Save was successful.  Change mode to UPDATE.
				gmsView = this.gmsView.changeMode(Mode.UPDATE);
				success = true;
			}
		}

		// Regardless of failed save (CREATE OR UPDATE),
		// or successful save, populate the panel with data.  
		gmsView.populatePanel(results);
		return success;
		
	}  //  end saveUserInput 


	/*	public boolean delete(FBO bo)
	{
		// Try to cast
		try {
			GMSBO castbo = (GMSBO)bo;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
*/

}  // end GMSBaseControl
