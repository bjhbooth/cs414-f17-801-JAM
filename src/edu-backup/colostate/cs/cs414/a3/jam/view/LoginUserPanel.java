package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

//  Class LoginUserPanel 
//
// Implements the Login View, extending from the abstract UserPanel class.    

public class LoginUserPanel extends UserPanel
{
	protected JButton btnCreateUser;

	//************************************************************
	// Static Method to Display View in a Frame
	//************************************************************
	public static LoginUserPanel displayLoginUserView()
	{		
		JFrame loginFrame = new JFrame("Gym Management System");
		LoginUserPanel luPanel = new LoginUserPanel();
		loginFrame.setSize(400, 550);
		loginFrame.setUndecorated(true); 									// Remove title bar
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 			// Close the App if user does not login
		loginFrame.getContentPane().add(luPanel, BorderLayout.CENTER);
//		loginFrame.pack();                          // size the frame 
		loginFrame.setLocationRelativeTo(null);     // Center the frame on the screen
		loginFrame.setResizable(false);
		loginFrame.setVisible(true);  				// DIsplay the frame
		
		return luPanel;
				
	}  // displayLoginView

	//************************************************************
	// CONSTRUCTOR - Use Static displayLoginUserView
	//************************************************************

	protected LoginUserPanel()
	{
		super();
		
		addListeners();
		
	}  // end LoginUserPanel constructor

	//************************************************************
	// IMPLEMENTATION OF ABSTRACT METHODS
	//************************************************************

	// Return toe string to be displayed in the header of the panel.
	// IE:  LOG IN or CREATE USER 
	protected String getHeaderLabel()
	{
		return ("LOG IN");
	}

	// Return toe string to be displayed in the submit button label.
	// IE:  LOG IN or SAVE  
	protected String getSubmitButtonLabel()
	{
		return ("LOG IN");
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

		// Create the Create User button. 
		// with the look and feel of a hyperlink
		btnCreateUser = new JButton("Create New User");
		btnCreateUser.setBorderPainted(false);
		btnCreateUser.setFocusPainted(false);
		btnCreateUser.setContentAreaFilled(false);
		btnCreateUser.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 16));
		btnCreateUser.setForeground(Color.BLUE);

		// ------------------------------
		// ADD THE COMPONENTS TO THE PANEL 
		// ------------------------------

		GridBagConstraints c = new GridBagConstraints();
//		c.anchor = GridBagConstraints.LINE_START;
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

		// Add the Create User Button  
		c.gridx = 1;
		c.gridy = 9;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 4;
		c.insets = new Insets (15, 5, 10, 10);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		c.ipadx = 0;
		c.anchor = GridBagConstraints.CENTER;
		bdyPanel.add(btnCreateUser, c);

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
	// From the Login Panel this will exit the program.  
	protected void handleBtnCancel()
	{
		System.exit(0);
	}  // end handleBtnCancel
	

	//************************************************************
	// PROTECTED WORKING CLASSES 
	//************************************************************
	
	private void addListeners()
	{
		btnCreateUser.addActionListener(new ButtonListener());
	}  // end addListeners
	
	//************************************************************
	// INNER CLASS:  ButtonListener
	//************************************************************
	class ButtonListener implements ActionListener 
	{
		// Handle the Create User Button.  THe Submit and Cancel buttons
		// will be handled by a listener in the base class.  
		public void actionPerformed (ActionEvent e)
		{
			if (e.getSource().equals(btnCreateUser)) 
			{
				// Login button pressed. 
				CreateUserPanel cuPanel = CreateUserPanel.displayCreateUserView();
				cuPanel.addWindowListener(new UserWindowListener());
			}  
			  
		} //  end actionPerformed 
	
	}  // end Inner Class ButtonListener

}  // end LoginUserPanel
