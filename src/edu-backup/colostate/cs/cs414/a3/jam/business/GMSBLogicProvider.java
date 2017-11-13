package edu.colostate.cs.cs414.a3.jam.business;

import java.util.Map;
import edu.colostate.cs.cs414.a3.jam.db.DAO;

public interface GMSBLogicProvider <F, T> {

	// Provider a concrete DB object derived from BaseDB and implementing DAO (i.e., TrainerDB).
	// The base GMSBLogic class and interface only have knowledge of these two base class levels.
	public DAO initDAO();
	
	// Do final validation before requesting to save to the DB.
	public Map <String, Object> doFinalValidate(F bo);
	
}  // end interface GMSBLogicProvider
