package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.*;

import javax.swing.*;

import edu.colostate.cs.cs414.a3.jam.view.GMSViewable.Mode;

//  Class CreateUserPanel 
//
// Implements the Create User View, extending from the abstract UserPanel class.    

public class CreateUserPanel extends UserPanel
{
	// Additional widgets not included in the base class. 
	protected JTextField tEmployeeID;
	protected JPasswordField pfPasswordVerify;
	
	//************************************************************
	// Static Method to Display View in a Frame
	//************************************************************
	public static CreateUserPanel displayCreateUserView()
	{		
		JFrame cuFrame = new JFrame("Gym Management System");
		CreateUserPanel cuPanel = new CreateUserPanel();
		cuFrame.setSize(400, 700);
		cuFrame.setUndecorated(false); 									// Remove title bar
		cuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 			// Close the App if user does not login
		cuFrame.getContentPane().add(cuPanel, BorderLayout.CENTER);
//		loginFrame.pack();                          // size the frame 
		cuFrame.setLocationRelativeTo(null);     // Center the frame on the screen
		cuFrame.setResizable(false);
		cuFrame.setVisible(true);  				// DIsplay the frame
		
		return cuPanel;
				
	}  // displayLoginView

	//************************************************************
	// IMPLEMENTATION OF ABSTRACT METHODS OF USERPANEL
	//************************************************************

	// Return toe string to be displayed in the header of the panel.
	// IE:  LOG IN or CREATE USER 
	protected String getHeaderLabel()
	{
		return ("CREATE USER");
	}

	// Return toe string to be displayed in the submit button label.
	// IE:  LOG IN or SAVE  
	protected String getSubmitButtonLabel()
	{
		return ("SAVE");
	}

	// getBodyPanel - Build the bottom portion of the view that contains 
	// the text fields and buttons 
	protected JPanel getBodyPanel()
	{
		JPanel bdyPanel = new JPanel(new GridBagLayout());
				
		// ------------------------------
		// DECLARE THE COMPONENTS 
		// ------------------------------
		
		// User Name Label  
		JLabel lblUserName = new JLabel ("User Name");
		lblUserName.setFont (new Font("", Font.BOLD, 14));
		

		// Password Label  
		JLabel lblPassword = new JLabel ("Password");
		lblPassword.setFont (new Font("", Font.BOLD, 14));
		
		// Password text field is managed by Abstract UserPanel class
		// User Name text field is managed by Abstract UserPanel class

		// Verify Password Label  
		JLabel lblPasswordVerify = new JLabel ("Verify Password");
		lblPasswordVerify.setFont (new Font("", Font.BOLD, 14));

		// Verify Password Label  
		pfPasswordVerify = new JPasswordField();

		// Verify Password Label  
		JLabel lblEmployeeID = new JLabel ("Employee ID");
		lblPasswordVerify.setFont (new Font("", Font.BOLD, 14));

		// Employee ID text field l  
		tEmployeeID = new JTextField();
		
		// ------------------------------
		// ADD THE COMPONENTS TO THE PANEL 
		// ------------------------------

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		// User Name Label 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 2;
		c.insets = new Insets (10, 10, 0, 0);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		bdyPanel.add(lblUserName, c);

		// User Name Text Field 
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 4;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		bdyPanel.add(tUserName, c);

		// Password Label 
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 2;
		c.insets = new Insets (5, 10, 0, 0);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		bdyPanel.add(lblPassword, c);

		// Password Text Field 
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 4;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		bdyPanel.add(pfPassword, c);

		// Verify Password Label 
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 2;
		c.insets = new Insets (5, 10, 0, 0);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		bdyPanel.add(lblPasswordVerify, c);

		// Verify Password Text Field 
		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 4;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		bdyPanel.add(pfPasswordVerify, c);

		// Verify Employee ID Label 
		c.gridx = 0;
		c.gridy = 6;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 2;
		c.insets = new Insets (5, 10, 0, 0);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		bdyPanel.add(lblEmployeeID, c);

		// Employee ID Text Field 
		c.gridx = 0;
		c.gridy = 7;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 4;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		bdyPanel.add(tEmployeeID, c);

		// Add the Submit and Cancel buttons to the panel.  
		// These will be added to a nested JPanel to avoid 
		// problems with the columns growing at different rates.
		
		JPanel btnPanel = new JPanel(new GridBagLayout());

		// Submit button  
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (0, 0, 0, 5);  // top, left, bottom, right
		c.ipady = 25;  // default is 0
		c.ipadx = 0; 
		btnPanel.add(btnSubmit, c);

		// Cancel button  
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (0, 0, 0, 0);  // top, left, bottom, right
		c.ipady = 25;  // default is 0
		c.ipadx = 0;
		btnPanel.add(btnCancel, c);

		// Add the button panel containing the buttons  
		c.gridx = 0;
		c.gridy = 8;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 4;
		c.insets = new Insets (40, 10, 0, 10);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		c.ipadx = 0; 
		bdyPanel.add(btnPanel, c);

		return bdyPanel;
		
	}  // end geBodyPanel


	// Method to handle "submit" button click in derived class.
	// Example:  
	// For login view this will login a user. 
	// For create user view this will create a new user.
	protected void handleBtnSubmit()
	{
		System.out.println("handleBtnSubmit");
	}  // end handleButtonSubmit

	
	// Method to handle "cancel" button click in derived class.
	// From the Creae User View this will casue the view to be disposed. 
	protected void handleBtnCancel()
	{
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.dispose();
	}  // end handleBtnCancel
	

	// **********************************************
	// Implementation of Abstract Methods of GMSPanel 
	// **********************************************
	protected GMSPanel createPanel(Mode newMode)
	{
		CreateUserPanel cup = new CreateUserPanelnewModee); 
		return cup; 
	}

	//************************************************************
	// CONSTRUCTOR - Use static displayCreateUserView
	//************************************************************

	protected CreateUserPanel()
	{
		super(GMSPanel.Mode.CREATE);	
	}  // end CreateUserPanel constructor

	protected CreateUserPanel(Mode mode)
	{
		super(mode);	
	}  // end CreateUserPanel constructor

	//************************************************************
	// PUblic Access Methods
	//************************************************************
	public String getEmployeeID()
	{
		return tEmployeeID.getText();
	}
	
	public String getPasswordVerify() 
	{
		String pwd = new String (pfPasswordVerify.getPassword());
		return pwd;
	}


}  // end CreateUserPanel
