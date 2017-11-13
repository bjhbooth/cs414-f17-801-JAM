package edu.colostate.cs.cs414.a3.jam.view;

import java.util.Map;

// GMSViewable 
// Standard Interface provided by all GMS Panels.   
//
// The default return type (unless otherwise defined by the derived classes) is    
// HASHTABLE (returned as a Map to easily handle change to HashMap, Tree, etc.)
// Contents:	STATUS      / VALID or INVALID
//				DATA           / Derived type of GMSBO or GMSTHinBO 
//             <ATTRIBUTE>  / Key defined in BO class.  A key/value pair will be 
//							 added for each attribute with an error.  
//							 The associated value will be a short error message.
// 							  Ex:  FIRST_NAME / "Required field." 
public interface GMSViewable 
{
	// Determine the mode of the view.  Both are essentially identical.  
	static public enum Mode 
	{
		CREATE,		  	// View to create new data.
		UPDATE;			// View to update existing data.
	}  
	
	public GMSPanel changeMode(Mode newMode);
	public void populatePanel(Map<String, Object> results);
	public void resetPanel();   // clear all fields including error labels. 
		
}  // end interface GMSVIewable 


