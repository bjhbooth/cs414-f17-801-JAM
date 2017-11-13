package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

//import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.util.Map;
import java.awt.event.ActionEvent;

import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSLabel;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSTextField;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSErrorLabel;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.TrainerBO;
import edu.colostate.cs.cs414.a3.jam.controller.TrainerControl;

// Class TrainerPanel:
//
// Implements the view for the Hire New Trainer and Update Existing Trainer views
public class TrainerPanel extends GMSPanel 
{	
	// ------------------------------------
	// Class Attributes 
	// 
	// Keep references to more of the widgets (including lables)
	// so they can be modified (i.e., to short error). 
	// ------------------------------------

	//  Maintain reference to ALL attributes, including labels
	// to allow the color to be changed to identify errors.	
	private GMSLabel			lblEmployeeID;
	private JTextField			tEmployeeID;
	private JCheckBox 			cbActive;
	
	private GMSErrorLabel		lblErrEmployeeID;
	
	// Maintain references to the panels to allow access to their widgets
	private PersonalInfoPanel 	piPanel;
	private WorkInfoPanel 		wiPanel;
	
	// Buttons
	private JButton 			btnSave,
								btnDelete, 
				    			btnCancel;

	//************************************************************
	// STATIC METHODS  
	//************************************************************
	public static TrainerPanel displayTrainerView(Mode mode)
	{		
		JFrame frame = new JFrame("Gym Management System");
		TrainerPanel trainerPanel = new TrainerPanel(mode);
		frame.setSize(700, 930);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 			// Close the App if user does not login
		frame.getContentPane().add(trainerPanel, BorderLayout.CENTER);
//		loginFrame.pack();                          // size the frame 
		frame.setLocationRelativeTo(null);     // Center the frame on the screen
//		frame.setResizable(false);
		frame.setVisible(true);  				// DIsplay the frame
		
		return trainerPanel;
			
	}  // end static displayTrainerView (Mode)

	//************************************************************
	// CONSTRUCTORS - Protected - Use displayTrainerView
	//************************************************************
	protected TrainerPanel ()
	{
		this(Mode.CREATE);
	}  // end TrainerPanel() constructor
	
	protected TrainerPanel (Mode mode)
	{
		super(mode);
		gmsControl = new TrainerControl(this);
		
		this.setLayout(new GridBagLayout());

		// Contains the header (title, employee id, and active checkbox
		JPanel headerPanel = getHeaderPanel();  
		
		// Personal Information and Work Information panels 
		piPanel = new PersonalInfoPanel();
		wiPanel = new WorkInfoPanel();
		
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
		this.add(wiPanel, c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(buttonPanel, c);

		// Add Listeners 
		addListeners();
		
	}  // end TrainerPanel(Mode) constructor 

	// **********************************************
	// Implementation of Abstract Methods of GMSPanel 
	// **********************************************
	protected GMSPanel createPanel(Mode newMode)
	{
		TrainerPanel tp = new TrainerPanel(newMode); 
		return tp; 
	}

	//************************************************************
	// IMPLEMENTATION OF GMSViewable Interface
	//************************************************************

	public void populatePanel(Map<String, Object> results)
	{
		TrainerBO trainer;
		boolean bValid;
		
		// Clear the fields and any error labels.
		resetPanel();

		// Get the TrainerBO object and Valid status from the Hashtable.
		try {
			trainer = (TrainerBO)results.get(GMSBO.Result.DATA.toString());
			bValid = ((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue();
		} catch (Exception e) {
			return;
		}

		// Set the Employee ID
		tEmployeeID.setText(trainer.getEmployeeID());
		if (bValid == false)
		{
			// Set any error message.
			if (results.get(TrainerBO.EmployeeAttribute.ID.toString()) != null)
				lblErrEmployeeID.setText(results.get(TrainerBO.EmployeeAttribute.ID.toString()).toString());
		}
		
		// Set the PersonalInfo Panel fields. 
		piPanel.populatePanel(results);
		
		
		// Set the WorkInfo Panel fields.  This one does not have a business object, so just forward individual values.
		String hours = Integer.toString(trainer.getHours());
		String errHours = results.get(TrainerBO.TrainerAttribute.HOURS.toString()).toString();  // Null return is okay.
		
		String qualifications = trainer.getQualifications();
		String errQualifications = results.get(TrainerBO.TrainerAttribute.QUALIFICATIONS.toString()).toString();  // Null return is okay.
		
		wiPanel.populatePanel(hours, errHours, qualifications, errQualifications);
					
	}  // end populatePanel 
	
	// ------------------------------
	// Reset Panel 
	// ------------------------------
	public void resetPanel()
	{
		tEmployeeID.setText("");
		lblErrEmployeeID.setText("");
		
		piPanel.resetPanel();
		wiPanel.resetPanel();
		
	}  // end resetPanel 
	
	//************************************************************
	// PUblic Access Methods
	//************************************************************
	public String getEmployeeID()
	{
		return tEmployeeID.getText();
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

	public String getHours()
	{
		return wiPanel.getHours();
	}

	public String getQualifications()
	{
		return wiPanel.getQualifications();
	}
	
	//************************************************************
	// PROTECTED WORKING METHODS
	//************************************************************

	// getHeaderPanel - Build the top portion of the view that contains 
	// the header, employee id, and active checkbox
	protected JPanel getHeaderPanel()
	{
		JPanel hdrPanel = new JPanel(new GridBagLayout());

		// ------------------------------
		// DECLARE THE COMPONENTS 
		// ------------------------------

		// Top of Login View - Create / Update Trainer Header
		JLabel lblTitleHeader;
		if (this.viewMode == Mode.UPDATE)
			lblTitleHeader = new JLabel ("Update Existing Trainer",  SwingConstants.CENTER);
		else 
			lblTitleHeader = new JLabel ("Hire New Trainer",  SwingConstants.CENTER);
		lblTitleHeader.setFont (new Font("", Font.BOLD, 48));
		lblTitleHeader.setForeground(Color.BLUE);

		// Employee ID and active status
		lblEmployeeID = new GMSLabel("Employee ID");
		lblEmployeeID.setFont(new Font("", Font.BOLD, 18));
		tEmployeeID = new JTextField();
		tEmployeeID.setFont(new Font("", Font.BOLD, 18));
		if (this.viewMode == Mode.UPDATE)
		{
			// Make the textfield behave like a label.
			// Update mode cannot change the employee ID
			tEmployeeID.setBorder(null);
			tEmployeeID.setEditable(false);
			tEmployeeID.setBackground(null);  // or setOpaqaque (false);
		} 

		lblErrEmployeeID = new GMSErrorLabel("EID error");
		
		cbActive = new JCheckBox("Active");
		cbActive.setFont(new Font("", Font.BOLD, 18));

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
		// NESTED PANEL - employeePanel
		// Employee ID label and textfield, a spacer, and the Active CB
		// THese will be added to their own nested panel to allow them
		// two achieve uniform growth
		// ----------------------------------------------------------
		JPanel employeePanel = new JPanel (new GridBagLayout());

		// ---------------------------------
		// Add a border to the employee panel
		// ---------------------------------
		Border compound, raisedBevel, loweredBevel, redline;
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		redline = BorderFactory.createLineBorder(Color.red);

		// Now create a nice frame with the borders. 
		compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

		//Add a red outline to the frame.
		compound = BorderFactory.createCompoundBorder(redline, compound);
		
		employeePanel.setBorder(compound);

		// ---------------------------------
		// Add components to employeePanel
		// ---------------------------------
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
		c.anchor = GridBagConstraints.WEST;
		employeePanel.add(lblEmployeeID, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = .5; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 0, 0, 0);  // top, left, bottom, right
		c.anchor = GridBagConstraints.WEST;
		c.ipadx = 40;
		employeePanel.add(tEmployeeID, c);

		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 1; 		                   
		c.gridwidth = 1;
		c.insets = new Insets (5, 30, 0, 0);  // top, left, bottom, right
		c.ipadx = 30;
		employeePanel.add(new JLabel(""), c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets (5, 0, 0, 10);  // top, left, bottom, right
		employeePanel.add(cbActive, c);

		// Add Employee ID error label.    
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 2, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		employeePanel.add(lblErrEmployeeID, c);

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
		c.ipady = 15;
		hdrPanel.add(employeePanel, c);		

		return hdrPanel;
	
	}  // end getHeaderPanel 
	

	// getButtonPanel - Build the top portion of the view that contains 
	// the header, employee id, and active checkbox
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
		c.insets = new Insets (2, 0, 5, 5);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		c.ipadx = 0; 
		panel.add(btnSave, c);

		// Delete button  
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (2, 0, 5, 5);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		c.ipadx = 0;
		panel.add(btnDelete, c);

		// Cancel button  
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (2, 0, 5, 0);  // top, left, bottom, right
		c.ipady = 20;  // default is 0
		c.ipadx = 0;
		panel.add(btnCancel, c);

		if (this.viewMode != Mode.UPDATE)
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
		
	}  // end Inner Class ButtonListener

}  // end class TrainerPanel
