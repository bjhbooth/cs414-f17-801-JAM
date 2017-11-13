package edu.colostate.cs.cs414.a3.jam.business;


public abstract class GMSBO 
{
	public static enum Result {
		VALID 			{ public String toString() { return "VALID"; } },
		DATA			{ public String toString() { return "DATA"; } },      // GMSBO, LIST<GMSBO>, etc. 
		UNKNOWN			{ public String toString() { return "UNKNOWN"; } }
}

	// Protected so derived classes instantiate
	protected GMSBO()
	{
		super();
	}  // constructor
	
	abstract public void  printObject();
	
}  // end class GMSBO
