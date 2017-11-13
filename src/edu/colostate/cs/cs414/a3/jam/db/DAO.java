package edu.colostate.cs.cs414.a3.jam.db;

import java.util.Map;

//The default return type (unless otherwise defined by the derived classes) is    
//HASHTABLE (returned as a Map to easily handle change to HashMap, Tree, etc.)
//Contents:	STATUS     	   / VALID or INVALID
//			DATA           / Derived type of GMSBO or GMSTHinBO 
//          <ATTRIBUTE>    / Key defined in BO classes.  A key/value pair will be 
//							 added for each attribute with an error.  
//							 The associated value will be a short error message.
//							  Ex:  FIRST_NAME / "Required field." 

public interface DAO<F> 
{
	Map<String, Object> get (String key);  	// Retrieve one object with the specified key 
	Map<String, Object> getAll();    		// Retrieve all objects
	Map<String, Object> add(F f); 	 		// Add a new object to the database
	Map<String, Object> update(F f); 		// update the existing object
	boolean delete(F f); 					// delete the spefied object
	boolean delete(String kay); 			// delete the object with the specified key
}  // end DAO interface 
