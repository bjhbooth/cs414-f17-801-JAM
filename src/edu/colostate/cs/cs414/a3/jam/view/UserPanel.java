package edu.colostate.cs.cs414.a3.jam.view;

//  Abstract Class UserPanel 
//
//  Base class intended for use by the LoginUserPanel and CreateUserPanel classes.  
//  It providers the header portion of the view and some of hte button handling.  
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import edu.colostate.cs.cs414.a3.jam.controller.LoginControl;
import edu.colostate.cs.cs414.a3.jam.view.LoginUserPanel.ButtonListener;
import edu.colostate.cs.cs414.a3.jam.view.widgets.LinePanel;
import edu.colostate.cs.cs414.a3.jam.controller.LoginControl;
import edu.colostate.cs.cs414.a3.jam.view.CreateUserPanel;
import edu.colostate.cs.cs414.a3.jam.view.GMSViewable.Mode;

public abstract class UserPanel extends GMSPanel 
{
	protected LoginControl controller;
	
	protected JLabel lblErrorMessage; 
	
	protected JTextField tUserName;
	protected JPasswordField pfPassword;
	
	protected JButton btnSubmit;
	protected JButton btnCancel;

	//************************************************************
	// ABSTRACT METHODS
	//************************************************************

	// Return toe string to be displayed in the header of the panel.
	// IE:  LOG IN or CREATE USER 
	abstract protected String getHeaderLabel();
	
	// Return toe string to be displayed in the submit button label.
	// IE:  LOG IN or SAVE  
	abstract protected String getSubmitButtonLabel();

	// Return a JPanel containing the body of the Panel.  
	// This will be diffferent depending hte derived type.  
	// For instance CreateUserPanel and LoginUserPaenl.
	abstract protected JPanel getBodyPanel();

	// Method to handle "submit" button click in derived class.
	// Example:  
	// For login view this will login a user. 
	// For create user view this will create a new user.
	abstract protected void handleBtnSubmit();
	
	// Method to handle "cancel" button click in derived class.
	// Example:  
	// For login view this will exit the program. 
	// For create user view this close the view and return to the Login view. 
	abstract protected void handleBtnCancel();
	

	//************************************************************
	// CONSTRUCTOR
	//************************************************************
	protected UserPanel(Mode mode)
	{
		super(mode);
		
		this.setLayout(new GridBagLayout());

		// Contains the header and error message labels common to LoginUserPanel and CreateUserPanel
		// Call method implemented by this class. 
		JPanel headerPanel = getHeaderPanel(); 

		// Create widgets managed by the base class, but not on the header panel. 
		// Submit and cancel buttons and User Name and Password fields.  
		btnSubmit = new JButton (getSubmitButtonLabel());
		btnSubmit.setFont(new Font("", Font.BOLD, 16));
		btnCancel = new JButton ("CANCEL");
		btnCancel.setFont(new Font("", Font.BOLD, 16));
		tUserName = new JTextField();
		tUserName.setFont(new Font("", Font.BOLD, 16));
		pfPassword = new JPasswordField();
		pfPassword.setFont(new Font("", Font.BOLD, 16));
		
		// Contains the body of the view (user, password, etc,) which is different for Login and Create
		// Call Abstract Method implemented by derived class to get this custom panel. 
		JPanel bodyPanel = getBodyPanel();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		// Add the Header Panel and Body Panel  
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1; 		// fill the width of the view
//		c.gridwidth = 1;
//		c.insets = new Insets (10, 10, 0, 10);  // top, left, bottom, right
//		c.ipady = 40;  // default is 0
		
		this.add(headerPanel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(bodyPanel, c);
	
		// Add Listeners 
		((UserPanel)this).addListeners();
		
	}  // end UserPanel constructor

	private UserPanel()
	{
		// Don't invoke without a mode.
		super(Mode.CREATE);
	}

	
	//************************************************************
	// PUblic Access Methods
	//************************************************************
	public String getUserName()
	{
		return tUserName.getText();
	}
	
	public String getPassword() 
	{
		String pwd = new String (pfPassword.getPassword());
		return pwd;
	}

	//************************************************************
	// PUblic Working Methods
	//************************************************************
	
	
	public void addWindowListener (WindowAdapter listener)
	{
		UserPanel userPanel = null;
		
		try 
		{
			// Add the listener to listen on the current panel's frame. 
			// Example:  Likely a CreateUserPanel adding a listener belong to LoginUserPanel.
			// When the login view is notified the create closed, it gets focus. 
			userPanel = (UserPanel)this;
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(userPanel);
			topFrame.addWindowListener(listener);
		} catch (Exception e)
		{
			// Failed to get parent frame.  Don't do anything.  This is a nice-to-have 
			// to set focus back to parent on window closes. 
		}	

	}  // end addWindowListener

	//************************************************************
	// Protected Working Methods 
	//************************************************************
	
	// getHeaderPanel - Build the top portion of the view that contains 
	// the header and error meassage portion of the view
	protected JPanel getHeaderPanel ()
	{
		JPanel hdrPanel = new JPanel(new GridBagLayout());
				
		// ------------------------------
		// DECLARE THE COMPONENTS 
		// ------------------------------
		
		// Top of Login View - GMS Header
		JLabel lblGMSHeader = new JLabel ("GMS",  SwingConstants.CENTER);
		lblGMSHeader.setFont (new Font("", Font.BOLD, 48));
		lblGMSHeader.setForeground(Color.BLUE);
		
		
		// Left and Right panels with line to surround LOG IN label 
		LinePanel linePanelLeft = new LinePanel();   		// panel with a line drawn in it
		LinePanel linePanelRight = new LinePanel();			// panel with a line drawn in it
		JLabel lblHeader = new JLabel(getHeaderLabel(), SwingConstants.CENTER);
		lblHeader.setFont (new Font("", Font.BOLD, 26));
		
		//  Label to display error message as needed - MEssages will display in RED
		lblErrorMessage = new JLabel ();
		lblErrorMessage.setFont(new Font("", Font.BOLD, 18));
		lblErrorMessage.setForeground(Color.RED);

		// ------------------------------
		// ADD THE COMPONENTS TO THE PANEL 
		// ------------------------------

		GridBagConstraints c = new GridBagConstraints();
//		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		// GMS Label 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 3;
		c.insets = new Insets (10, 10, 0, 10);  // top, left, bottom, right
		c.ipady = 40;  // default is 0
		hdrPanel.add(lblGMSHeader, c);

		// Spacer Label 
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 3;
		c.insets = new Insets (0, 0, 0, 0);  // top, left, bottom, right
		c.ipady = 50;  // default is 0
		hdrPanel.add(new JLabel(), c);
		
		// Left and Right panels with line to surround header label (probably LOG IN or CREATE USER)  
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1; 		// Grow with the view
		c.gridwidth = 1;
		c.insets = new Insets (0, 10, 0, 5);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		hdrPanel.add(linePanelLeft, c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = .5; 		// Grow with the view
		c.gridwidth = 1;
		c.insets = new Insets (0, 0, 0, 0);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		hdrPanel.add(lblHeader, c);

		c.gridx = 2;
		c.gridy = 2;
		c.weightx = 1; 		// Grow with the view
		c.gridwidth = 1;
		c.insets = new Insets (0, 5, 0, 10);  // top, left, bottom, right
		c.ipady = 0;  // default is 0
		hdrPanel.add(linePanelRight, c);

		// Error Message Label)  
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1; 		// Grow with the view
		c.gridwidth = 0;
		c.insets = new Insets (10, 10, 10, 10);  // top, left, bottom, right
		c.ipady = 30;       // default is 0
		hdrPanel.add(lblErrorMessage, c);

		return hdrPanel;
		
	}  // end getHeaderPanel

	//************************************************************
	// Private Working Methods 
	//************************************************************

    // Add required listeners to the widgets.
	// Made private so base class calls it's own addLiseners and not 
	// the overridden method. 
	private void addListeners()
	{
		btnSubmit.addActionListener(new ButtonListener());
		btnCancel.addActionListener(new ButtonListener());
	}  // end addListeners

	//************************************************************
	// INNER CLASS:  ButtonListener
	//************************************************************
	class ButtonListener implements ActionListener 
	{
		public void actionPerformed (ActionEvent e)
		{
			if (e.getSource().equals(btnSubmit)) {
				// Login button pressed. 
				handleBtnSubmit();
			}  else if (e.getSource().equals(btnCancel)) {
				// Create User button pressed.
				System.out.println("In Cancel handler");
				handleBtnCancel();
			}
				  
		} //  end actionPerformed 
		
	}  // end Inner Class ButtonListener

	//************************************************************
	// INNER CLASS:  WindowListener
	//************************************************************
	
	// When the window being listened on closes, set forcus to the listening window.
	class UserWindowListener extends WindowAdapter 
	{
		public void windowClosed (WindowEvent e)
		{
			// Retrieve your parent frame.  Bring it to the top.
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(UserPanel.this);
			topFrame.toFront();
			topFrame.requestFocus();
			
			// Set focus within the frame to the user name field.
			UserPanel.this.tUserName.requestFocus();
		} //  end windowClosed 
		
	}  // end Inner Class ButtonListener

}  // end UserPanel class

