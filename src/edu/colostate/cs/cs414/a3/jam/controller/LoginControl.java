package edu.colostate.cs.cs414.a3.jam.controller;

import edu.colostate.cs.cs414.a3.jam.view.LoginUserPanel;

public class LoginControl 
{
	private LoginUserPanel view;
	
	public LoginControl(LoginUserPanel panel)
	{
		view = panel;
	}

	public boolean loginUser()
	{
		view.getUserName();
		view.getPassword();
		
		return true;
		
	}  // end loginUser

	public boolean createNewUser()
	{
		return true;
		
	}
	
}  // end class loginCOntroll

	
