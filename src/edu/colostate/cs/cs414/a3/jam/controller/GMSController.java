package edu.colostate.cs.cs414.a3.jam.controller;

import java.util.Map;

// GMSController
//
// Provide a standard interface to invoke functionality to be provided 
// by all controllers in the GMS system.  Gerneric types are used 
// in light of the specific controllers communicating with 
// different concrete buiness objects types.  
public interface GMSController <F, T> 
{	
	// Retrieve values entered by the user.  Call the derived class' implementation of this method.
	// Return a business object derived from GMSBO; null if the object could not be created.
	public F retrieveUserInput ();

	// Do preliminary validation (basic error checking that do not require detailed business logic 
	// or DB access (i.e., duplicates). For example, required fields are non-null and correct type, etc.
	public Map <String, Object> doSoftValidate (F bo);
	
}  // end interface GMSController 


