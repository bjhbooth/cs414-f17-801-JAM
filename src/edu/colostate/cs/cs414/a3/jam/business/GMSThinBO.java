package edu.colostate.cs.cs414.a3.jam.business;

public abstract class GMSThinBO
{
	public static enum Result {
		VALID 			{ public String toString() { return "VALID"; } },
		THINBO			{ public String toString() { return "BO"; } },
		UNKNOWN			{ public String toString() { return "UNKNOWN"; } }
	}
	
	// Protected so derived classes instantiate it.
	protected GMSThinBO()
	{
		super();
	}  // constructor
	
	abstract public void  printObject();
	
}  // end class GMSThinBO
