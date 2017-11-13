package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSLabel;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSErrorLabel;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.CustomerBO;

//Class CustomerPanel:
//
//Implements the view for the Register Customer and Update Existing Customer views

public class CustomerPanel extends GMSPanel
{
	// ------------------------------------
	// Class Attributes 
	// 
	// Keep references to more of the widgets (including labels)
	// so they can be modified (i.e., to short error). 
	// ------------------------------------

	//  Maintain reference to ALL attributes, including labels
	// to allow the color to be changed to identify errors.	
	private GMSLabel			lblCustomerID;
	private JTextField			tCustomerID;
	private JCheckBox 			cbStatus;
	
	private GMSErrorLabel		lblErrCustomerID;
	
	// Maintain references to the personal information panel to allow access to its widgets
	private PersonalInfoPanel 	piPanel;
		
	// Buttons
	private JButton 			btnSave,
								btnDelete, 
				    			btnCancel;

	//************************************************************
	// STATIC METHODS  
	//************************************************************
	public static CustomerPanel displayCustomerView(Mode mode)
	{		
		JFrame frame = new JFrame("Gym Management System");
		CustomerPanel customerPanel = new CustomerPanel(mode);
		frame.setSize(600, 710);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 			// Close the App if user does not login
		frame.getContentPane().add(customerPanel, BorderLayout.CENTER);
//		loginFrame.pack();                          // size the frame 
		frame.setLocationRelativeTo(null);     // Center the frame on the screen
//		frame.setResizable(false);
		frame.setVisible(true);  				// DIsplay the frame
		
		return customerPanel;
			
	}  // end static displayCustomerView (Mode)
	
	//************************************************************
	// CONSTRUCTORS - Protected - Use displayCustomerView
	//************************************************************
	protected CustomerPanel ()
	{
		this(Mode.CREATE);
	}  // end CustomerPanel() constructor
	
	protected CustomerPanel (Mode mode)
	{
		super(mode);
		gmsControl = new CustomerControl(this);
		
		this.setLayout(new GridBagLayout());
		
		// Contains the header (title, customer id, and status checkbox
		JPanel headerPanel = getHeaderPanel();  
		
		// Personal Information and Work Information panels 
		piPanel = new PersonalInfoPanel();
				
		JPanel buttonPanel = getButtonPanel();
		
		//----------------------------------------------------
		// Add the parts to the parent panel to build the view
		// ---------------------------------------------------
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1; 		// fill the width of the view
		c.weighty = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
//		c.insets = new Insets (10, 10, 0, 10);  // top, left, bottom, right
//		c.ipady = 40;  // default is 0
		this.add(headerPanel, c);
		
		// Reset fill
//		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(piPanel, c);

		c.gridx = 0;
		c.gridy = 2;
		this.add(buttonPanel, c);

		// Add Listeners 
		addListeners();
		
	}  // end CustomerPanel(Mode) constructor 
	
	// **********************************************
	// Implementation of Abstract Methods of GMSPanel 
	// **********************************************
	protected GMSPanel createPanel(Mode newMode)
	{
		CustomerPanel cp = new CustomerPanel(newMode); 
		return cp; 
	}

	//************************************************************
	// IMPLEMENTATION OF GMSViewable Interface
	//************************************************************

	public void populatePanel(Map<String, Object> results)
	{
		CustomerBO customer;
		boolean bValid;
		
		// Clear the fields and any error labels.
		resetPanel();

		// Get the CustomerBO object and Valid status from the Hashtable.
		try {
			customer = (CustomerBO)results.get(GMSBO.Result.DATA.toString());
			bValid = ((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue();
		} catch (Exception e) {
			return;
		}

		// Set the Customer ID
		tCustomerID.setText(customer.getCustomerID());
		if (bValid == false)
		{
			// Set any error message.
			if (results.get(CustomerBO.CustomerAttribute.ID.toString()) != null)
				lblErrCustomerID.setText(results.get(CustomerBO.CustomerAttribute.ID.toString()).toString());
		}
		
		cbStatus.setSelected(customer.getMemberStatus() == CustomerBO.MemberStatus.ACTIVE);
		
		// Set the PersonalInfo Panel fields. 
		piPanel.populatePanel(results);
					
	}  // end populatePanel 
	
	// ------------------------------
	// Reset Panel 
	// ------------------------------
	public void resetPanel()
	{
		tCustomerID.setText("");
		lblErrCustomerID.setText("");
		cbStatus.setSelected(false);
		
		piPanel.resetPanel();
		
	}  // end resetPanel 
	
	//************************************************************
	// PUblic Access Methods
	//************************************************************
	public String getCustomerID()
	{
		return tCustomerID.getText();
	}

	public CustomerBO.MemberStatus getStatus()
	{
		if (cbStatus.isSelected())	
			return CustomerBO.MemberStatus.ACTIVE;
		
		return CustomerBO.MemberStatus.INACTIVE;
	}
	
	public boolean isActiveCustomer() 
	{
		return cbStatus.isSelected();
	}

	public String getFirstName()
	{
		return piPanel.getFirstName();
	}

	public String getLastName()
	{
		return piPanel.getLastName();
	}

	public String getStreet() 
	{
		return piPanel.getStreet();
	}

	public String getCity() 
	{
		return piPanel.getCity();
	}

	public String getState() 
	{
		return piPanel.getState();
	}

	public String getZip() 
	{
		return piPanel.getZip();
	}
	
	public String getPhone() 
	{
		return piPanel.getPhone();
	}

	public String getEmail() 
	{
		return piPanel.getEmail();
	}

	public String getInsuranceProvider() 
	{
		return piPanel.getInsuranceProvider();
	}

	//************************************************************
	// PROTECTED WORKING METHODS
	//************************************************************

	// getHeaderPanel - Build the top portion of the view that contains 
	// the header, customer id, and status checkbox
	protected JPanel getHeaderPanel()
	{
		JPanel hdrPanel = new JPanel(new GridBagLayout());

		// ------------------------------
		// DECLARE THE COMPONENTS 
		// ------------------------------

		// Top of Login View - Create / Update Customer Header
		JLabel lblTitleHeader;
		if (this.viewMode == Mode.UPDATE)
			lblTitleHeader = new JLabel ("Update Existing Customer",  SwingConstants.CENTER);
		else 
			lblTitleHeader = new JLabel ("Register New Customer",  SwingConstants.CENTER);
		lblTitleHeader.setFont (new Font("", Font.BOLD, 48));
		lblTitleHeader.setForeground(Color.BLUE);

		// Employee ID and active status
		lblCustomerID = new GMSLabel("Customer ID");
		lblCustomerID.setFont(new Font("", Font.BOLD, 18));
		tCustomerID = new JTextField();
		tCustomerID.setFont(new Font("", Font.BOLD, 18));
		if (this.viewMode == Mode.UPDATE)
		{
			// Make the textfield behave like a label.
			// Update mode cannot change the customer ID
			tCustomerID.setBorder(null);
			tCustomerID.setEditable(false);
			tCustomerID.setBackground(null);  // or setOpaqaque (false);
		} 

		lblErrCustomerID = new GMSErrorLabel("CID error");
		
		cbStatus = new JCheckBox("Active");
		cbStatus.setFont(new Font("", Font.BOLD, 18));

		// ------------------------------
		// ADD COMPONENTS TO THE HEADER PANEL  
		// ------------------------------
		GridBagConstraints c = new GridBagConstraints();
				
		// Header Label 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
//		c.ipady = 20;  // default is 0
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		hdrPanel.add(lblTitleHeader, c);

		// Spacer Label between header and employee ID
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (0, 0, 0, 0);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		c.fill = GridBagConstraints.HORIZONTAL;
		hdrPanel.add(new JLabel(), c);		

		// Reset iPady
		c.ipady = 0;
		
		// -----------------------------------------------------------
		// NESTED PANEL - customerPanel
		// Customer ID label and textfield, a spacer, and the Active CB
		// THese will be added to their own nested panel to allow them
		// two achieve uniform growth
		// ----------------------------------------------------------
		JPanel customerPanel = new JPanel (new GridBagLayout());

		// ---------------------------------
		// Add a border to the customer panel
		// ---------------------------------
		Border compound, raisedBevel, loweredBevel, redline;
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		redline = BorderFactory.createLineBorder(Color.red);

		// Now create a nice frame with the borders. 
		compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

		//Add a red outline to the frame.
		compound = BorderFactory.createCompoundBorder(redline, compound);
		
		customerPanel.setBorder(compound);

		// ---------------------------------
		// Add components to employeePanel
		// ---------------------------------
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
		c.anchor = GridBagConstraints.WEST;
		customerPanel.add(lblCustomerID, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = .5; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 0, 0, 0);  // top, left, bottom, right
		c.anchor = GridBagConstraints.WEST;
		c.ipadx = 40;
		customerPanel.add(tCustomerID, c);

		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 1; 		                   
		c.gridwidth = 1;
		c.insets = new Insets (5, 30, 0, 0);  // top, left, bottom, right
		c.ipadx = 30;
		customerPanel.add(new JLabel(""), c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets (5, 0, 0, 10);  // top, left, bottom, right
		customerPanel.add(cbStatus, c);

		// Add Customer ID error label.    
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 2, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		customerPanel.add(lblErrCustomerID, c);

		// ---------------------------------
		// Add the employeePanel to the hdrPanel
		// ---------------------------------
  
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (0, 10, 0, 10);  // top, left, bottom, right
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 5;
		hdrPanel.add(customerPanel, c);		

		return hdrPanel;
	
	}  // end getHeaderPanel 

	// -----------------------------------------------------
	// getButtonPanel 
	// 
	// Build a panel containing the buttons 
	// for the view.  Save, Delete (modify only), and Cancel.
	// ------------------------------------------------------
	protected JPanel getButtonPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints ();
		
		// Create Instances of the Buttons 
		btnSave = new JButton (" SAVE ");
		btnSave.setFont(new Font("", Font.BOLD, 16));
		btnDelete = new JButton ("DELETE");
		btnDelete.setFont(new Font("", Font.BOLD, 16));
		btnCancel = new JButton ("CANCEL ");
		btnCancel.setFont(new Font("", Font.BOLD, 16));
		
		// Add the buttons to the Panel  
		// These are added to a nested JPanel to avoid 
		// problems with the columns growing at different rates.
		
		// Save button  
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 0, 5, 5);  // top, left, bottom, right
		c.ipady = 25;  // default is 0
		c.ipadx = 0; 
		panel.add(btnSave, c);

		// Delete button  
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 0, 5, 5);  // top, left, bottom, right
		c.ipady = 25;  // default is 0
		c.ipadx = 0;
		panel.add(btnDelete, c);

		// Cancel button  
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 0, 5, 0);  // top, left, bottom, right
		c.ipady = 25;  // default is 0
		c.ipadx = 0;
		panel.add(btnCancel, c);

		if (this.viewMode == Mode.CREATE)
		{
			// Hide the delete button.
			// Create mode cannot create an employee ID
			btnDelete.setVisible(false);
			btnDelete.setEnabled(false);
		} 

		return panel;
		
	}  // end getButtonPanel

	//************************************************************
	// Protected Handler Methods Invoked by Listeners  
	//************************************************************

	protected void handleSaveRequest()
	{
		System.out.println("In handleSaveReuest");
	}  // end handleSaveRequest
	
	protected void handleDeleteRequest()
	{
		System.out.println("In handleDeleteReuest");
	}  // end handleDeleteRequest

	protected void handleCancelRequest()
	{
		System.out.println("In handleCancelReuest");
	}  // end handleCancelRequest
	
	//************************************************************
	// Private Working Methods 
	//************************************************************

    // Add required listeners to the widgets.
	private void addListeners()
	{
		btnSave.addActionListener(new ButtonListener());
		btnCancel.addActionListener(new ButtonListener());
		if (this.viewMode == Mode.UPDATE)
			btnDelete.addActionListener(new ButtonListener());
		
	}  // end addListeners

	private void removeListeners()
	{
		btnSave.removeActionListener(new ButtonListener());
		btnCancel.removeActionListener(new ButtonListener());
		if (this.viewMode == Mode.UPDATE)
			btnDelete.removeActionListener(new ButtonListener());
	}  // end removeListeners

	//************************************************************
	// INNER CLASS:  ButtonListener
	//************************************************************
	class ButtonListener implements ActionListener 
	{
		public void actionPerformed (ActionEvent e)
		{
			if (e.getSource().equals(btnSave)) {
				// Save button pressed. 
				handleSaveRequest();
			}  else if (e.getSource().equals(btnCancel)) {
				// Create button pressed.
				handleCancelRequest();
			}  else if (e.getSource().equals(btnDelete)) {
				// Delete button pressed.
				handleDeleteRequest();
			}
			
				  
		} //  end actionPerformed 
	}  // end ButtonListener class
	
}  // end CustomerPanel class

