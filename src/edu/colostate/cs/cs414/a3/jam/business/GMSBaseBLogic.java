package edu.colostate.cs.cs414.a3.jam.business;

import java.util.Map;

import edu.colostate.cs.cs414.a3.jam.db.DAO;

// GMSBaseBLogic
// Base class providing business logic for the GMS system.
// This layer exists to separate business logic from the methods in the database (BaseDB / DAO)
// layer that focus on code specific to accessing the DB.  This way, should the DB used
// change, the business logic remains here as the methods that access the DB modifies 
// to access the new DB. 
public abstract class GMSBaseBLogic implements GMSBLogicProvider <GMSBO, GMSThinBO> 
{	
	protected DAO	gmsDAO;
	
	// --------------------------------------
	// Constructors
	// -------------------------------------
	protected GMSBaseBLogic ()
	{
		super();
		gmsDAO = initDAO();
	}  // end constructor

	// -------------------------------------------------------
	// Public methods made available to the controller. 
	// -------------------------------------------------------				
	public Map <String, Object> saveNew(GMSBO bo)
	{
		// Do final validation before requesting to save.
		Map<String, Object> results = doFinalValidate(bo);

		// If validation failed, return the results.  			
		if ( ((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue() == false)
			return results; 
		
		// Validation was successful.  Attempt to save. 
		results = gmsDAO.add(bo);
			
		return results;
			
	}  //  end saveNew (GMSBO)

	// -------------------------------------------------------
	// Public methods made available to the controller. 
	// -------------------------------------------------------				
	public Map <String, Object> saveUpdate(GMSBO bo)
	{
		// Do final validation before requesting to save.
		Map<String, Object> results = doFinalValidate(bo);

		// If validation failed, return the results.  
		if ( ((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue() == false ) 
			return results; 
		
		// Validation was successful.  Attempt to save. 
		results = gmsDAO.update(bo);
			
		return results;
			
	}  //  end saveUpdate (GMSBO)


}  // end class GMSBaseBLogic
