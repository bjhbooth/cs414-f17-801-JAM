package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.border.*;

import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSTextField;
import edu.colostate.cs.cs414.a3.jam.view.widgets.LinePanel;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSLabel;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.TrainerBO;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSErrorLabel;
import edu.colostate.cs.cs414.a3.jam.business.PersonBO;


// Class:  PersonalInfoPanel
// 
// Implements the "Personal Information" portion of the Hire New Trainer and Register Customer Panels
public class PersonalInfoPanel extends JPanel  
{
	// State Names for State JComboBox. 
    private String[] states = {
    	"  ", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
    	"KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
    	"NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
    	"UT", "VT", "VA", "WA", "WV", "WI", "WY"
    };
    
    // --------------------------------------
    // Attributes 
    // Keep references to all widgets to 
    // modify for error reporting (color, etc.)
    // --------------------------------------
	private GMSLabel lblFirstName,
		    	   lblLastName,
		           lblStreet,
		           lblCity,
		           lblState,
		           lblZip,
		           lblPhone,
		           lblEmail,
		           lblInsProvider;

	private GMSTextField tFirstName,
	 	           tLastName,
	 	           tStreet,
	 	           tCity,
	 	           tZip,
	 	           tPhone,
	 	           tEmail,
	 	           tInsProvider;
	 	           
	private JComboBox cbState;

	// Error message labels. 
	private GMSErrorLabel lblErrFirstName,
				   		  lblErrLastName,
						  lblErrStreet,
						  lblErrCity,
						  lblErrState,
						  lblErrZip,
						  lblErrPhone,
						  lblErrEmail,
						  lblErrInsProvider;

	//************************************************************
	// CONSTRUCTOR
	//************************************************************

	public PersonalInfoPanel ()
	{
		super();
		this.setLayout(new GridBagLayout());
		
		JPanel insetPanel = new JPanel(new GridBagLayout());
		
		// ------------------------------------------------------
		// Instantiate / Initialize the Components
		// ------------------------------------------------------

		lblFirstName = new GMSLabel("First:");
		lblLastName = new GMSLabel("Last:");
		lblStreet = new GMSLabel("Street:");
		lblCity = new GMSLabel("City:");
		lblState = new GMSLabel("State:");
		lblZip = new GMSLabel("Zip:");
        lblPhone = new GMSLabel("Phone:");
        lblEmail = new GMSLabel("Email:");
        lblInsProvider = new GMSLabel("Insurance Provider:");

        lblErrFirstName = new GMSErrorLabel("FN Error Message");
        lblErrLastName = new GMSErrorLabel("LN error message");
		lblErrStreet = new GMSErrorLabel("Street error message");
		lblErrCity = new GMSErrorLabel("City Err");
		lblErrState = new GMSErrorLabel("State Err");
		lblErrZip = new GMSErrorLabel("Zip Err");
        lblErrPhone = new GMSErrorLabel("PHone Err");
        lblErrEmail = new GMSErrorLabel("Email Err");
        lblErrInsProvider = new GMSErrorLabel("INs Prov Err");
        
		tFirstName = new GMSTextField();    // 25
		tLastName = new GMSTextField();     // 25
		tStreet = new GMSTextField();       // 50
		tCity = new GMSTextField();         // 25
		tZip = new GMSTextField();	      // 9
		tPhone = new GMSTextField();        // 10
		tEmail = new GMSTextField();		  // 50
		tInsProvider = new GMSTextField();  // 50
		
		cbState = new JComboBox (states);
		
		// Invoke methods to create sections of the Personal Info Panel. 
		JPanel hdrPanel = getHeaderPanel();			// PI header and First/Last name
		JPanel addrPanel = getAddressPanel(); 		// Address  
		JPanel contactPanel = getContactPanel();	// Phone, email
		JPanel insPanel = getInsurancePanel();		// Insurance provider 
		
		// ---------------------------------
		// Add a border to the Personal Info panel
		// ---------------------------------
		Border compound, raisedBevel, loweredBevel;
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		
		// Now create a nice frame with the borders. 
		compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
		insetPanel.setBorder(compound);

		// ------------------------------------------------------
		// Add the four panels to the main panel to comprise the view.  
		// ------------------------------------------------------
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		insetPanel.add(hdrPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		insetPanel.add(addrPanel, c);

		c.gridx = 0;
		c.gridy = 2;
		insetPanel.add(contactPanel, c);

		c.gridx = 0;
		c.gridy = 3;
		insetPanel.add(insPanel, c);

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (10, 10, 10, 10);  // top, left, bottom, right
		c.anchor = GridBagConstraints.CENTER;
		c.ipady = 0;		
		this.add(insetPanel, c);
		
	}  // end PersonalInfoPanel constructor

	//************************************************************
	// PUBLIC WORKER METHODS
	//************************************************************

	public void populatePanel(Map<String, Object> results)
	{
		PersonBO person;
		boolean bValid;
		
		// Clear the fields and any error labels.
		resetPanel();
		
		try {
			// Cast only to PersonBO level.  Don't care if derived Trainer or Customer. 
			person = (PersonBO)results.get(GMSBO.Result.DATA.toString());
			bValid = ((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue();
		} catch (Exception e) {
			return;
		}

		// Populate the fields with data from the business object stored in the 
		tFirstName.setText(person.getFirstName());
		tLastName.setText(person.getLastName());
		tStreet.setText(person.getPrimaryAddress().getStreet());
		tCity.setText(person.getPrimaryAddress().getCity());
		cbState.setSelectedItem(person.getPrimaryAddress().getState());
		tZip.setText(person.getPrimaryAddress().getZip());
		tPhone.setText(person.getPhone());
		tEmail.setText(person.getEmail());
		tInsProvider.setText(person.getInsuranceProvider());

		// In in an invalid state, there are one or more fields 
		// with an error.  Check for messages.   
		if (bValid == false)
		{
			Object temp = null;
			
			// One ore more attributes have errors.
			temp = results.get(PersonBO.PersonAttribute.FIRST_NAME.toString());
			if (temp != null)
				lblErrFirstName.setText(temp.toString());

			temp = results.get(PersonBO.PersonAttribute.LAST_NAME.toString());
			if (temp != null)
				lblErrLastName.setText(temp.toString());
			
			temp = results.get(PersonBO.PersonAttribute.ADDR_STREET.toString());
			if (temp != null)
				lblErrStreet.setText(temp.toString());

			temp = results.get(PersonBO.PersonAttribute.ADDR_CITY.toString());
			if (temp != null)
				lblErrCity.setText(temp.toString());

			temp = results.get(PersonBO.PersonAttribute.ADDR_STATE.toString());
			if (temp != null)
				lblErrState.setText(temp.toString());

			temp = results.get(PersonBO.PersonAttribute.ADDR_ZIP.toString());
			if (temp != null)
				lblErrZip.setText(temp.toString());

			temp = results.get(PersonBO.PersonAttribute.PHONE.toString());
			if (temp != null)
				lblErrPhone.setText(temp.toString());

			temp = results.get(PersonBO.PersonAttribute.EMAIL.toString());
			if (temp != null)
				lblErrEmail.setText(temp.toString());

			temp = results.get(PersonBO.PersonAttribute.INSURANCE.toString());
			if (temp != null)
				lblErrInsProvider.setText(temp.toString());
				
		}  // end if !bValid
			
		
	}  // end populatePanel 
	
	// Clear all fields on the panel. 
	public void resetPanel()
	{
		
		// Clear entry fields.  
		tFirstName.setText("");
		tLastName.setText("");
		tStreet.setText("");
		tCity.setText("");
		cbState.setSelectedIndex(-1);
		tZip.setText("");
		tPhone.setText("");
		tEmail.setText("");
		tInsProvider.setText("");

		// Clear error fields
		lblErrFirstName.setText("");
		lblErrLastName.setText("");
		lblErrStreet.setText("");
		lblErrCity.setText("");
		lblErrState.setText("");
		lblErrZip.setText("");
		lblErrPhone.setText("");
		lblErrEmail.setText("");
		lblErrInsProvider.setText("");
		
	}  // end resetPanel 

	//************************************************************
	// PROTECTED WORKER METHODS
	//************************************************************

	// Create a JPanel containing the PI header and First/Last name
	protected JPanel getHeaderPanel()
	{
		JPanel hdrPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// --------------------------------------------
		// Personal Information header with line 
		// --------------------------------------------
		JPanel nestedHdrPanel = new JPanel (new GridBagLayout());
		
		JLabel piLabel = new JLabel("Personal Information", SwingConstants.LEFT);
		piLabel.setFont (new Font("", Font.BOLD, 22));
		piLabel.setForeground(Color.BLUE);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 0, 10);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedHdrPanel.add(piLabel, c);
		
		LinePanel linePanel = new LinePanel(Color.BLUE, 7);   		// panel with a line drawn in it
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.gridwidth = 3;
		c.insets = new Insets(10, 0, 0, 10);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		nestedHdrPanel.add(linePanel, c);

		// --------------------------------------------
		// First and Last Name fields  
		// --------------------------------------------
		JPanel nestedNamePanel = new JPanel (new GridBagLayout());

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedNamePanel.add(lblFirstName, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = .5;
		c.gridwidth = 1;
		c.insets = new Insets(10, 5, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		nestedNamePanel.add(tFirstName, c);

		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 20, 0, 0);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;	
		nestedNamePanel.add(lblLastName, c);

		c.gridx = 3;
		c.gridy = 0;
		c.weightx = .5;
		c.gridwidth = 1;
		c.insets = new Insets(10, 5, 0, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		nestedNamePanel.add(tLastName, c);

		// --------------------------------------------
		// Add the error message labels.    
		// --------------------------------------------

		// First Name Error Label
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 5, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedNamePanel.add(lblErrFirstName, c);

		// Last Name Error Label
		c.gridx = 3;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 5, 0, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.ipadx = 10;
		nestedNamePanel.add(lblErrLastName, c);
		
		c.ipadx = 0;

		// --------------------------------------------
		// Add the two nested panels to the main header panel.   
		// --------------------------------------------
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		hdrPanel.add(nestedHdrPanel, c);

		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		hdrPanel.add(nestedNamePanel, c);
		
		return hdrPanel;
		
	}  // end getHeaderPanel 

	// Create a JPanel containing street, city, state, and zip widgets 
	protected JPanel getAddressPanel()
	{
		JPanel addrPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		// --------------------------------------------
		//   Address Header 
		// --------------------------------------------
		GMSLabel lblAddress = new GMSLabel("Address");
		lblAddress.setFont(new Font("", Font.ITALIC, 18));
				
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = .5;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets (10, 10, 0, 0);
		addrPanel.add(lblAddress, c);
		
		// --------------------------------------------
		//   Street  
		// --------------------------------------------
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (5, 10, 0, 0);
		addrPanel.add(lblStreet, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		c.ipadx = 100;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets (5, 5, 0, 10);
		addrPanel.add(tStreet, c);

		// Street Error Label 
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (0, 5, 0, 10);
		c.ipadx = 10;
		addrPanel.add(lblErrStreet, c);
		
		c.ipadx = 0;

		// --------------------------------------------
		//   City / State / Zip
		//   Place them on a nested panel so they 
		//   have their own solumns separate from the 
		//   street. 
		// --------------------------------------------
		
		JPanel nestedAddrPanel = new JPanel (new GridBagLayout());

		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (5, 10, 00, 0);
		addrPanel.add(lblCity, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = .5;
		c.insets = new Insets(5, 5, 0, 0);
		c.ipadx = 60;
		nestedAddrPanel.add(tCity, c);

		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0;
		c.insets = new Insets(5, 25, 0, 0);
		c.ipadx = 20;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedAddrPanel.add(lblState, c);

		c.gridx = 3;
		c.gridy = 0;
		c.weightx = .5;
		c.insets = new Insets(5, 2, 0, 10);
		c.ipadx = 30;
		nestedAddrPanel.add(cbState, c);

		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0;
		c.insets = new Insets(5, 25, 0, 0);
		c.ipadx = 20;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedAddrPanel.add(lblZip, c);

		c.gridx = 5;
		c.gridy = 0;
		c.weightx = .5;
		c.insets = new Insets(5, 2, 0, 10);
		c.ipadx = 30;
		nestedAddrPanel.add(tZip, c);

		// --------------------------------------------
		// Add the error message labels.    
		// --------------------------------------------

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 5, 0, 0);
		c.ipadx = 10;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedAddrPanel.add(lblErrCity, c);

		c.gridx = 3;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.insets = new Insets(0, 2, 0, 10);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.ipadx = 10;
		nestedAddrPanel.add(lblErrState, c);

		c.gridx = 5;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.insets = new Insets(0, 2, 0, 10);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.ipadx = 10;
		nestedAddrPanel.add(lblErrZip, c);

		c.ipadx = 0;

		// -----------------------------------------------------
		// Add the nested address panel to the main address panel
		// -----------------------------------------------------
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.ipadx = 100;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets (5, 0, 10, 0);
		addrPanel.add(nestedAddrPanel, c);
		
		return addrPanel;

	}  // end getAddressPanel 

	// Create a JPanel containing phone and email widgets  
	protected JPanel getContactPanel()
	{
		JPanel contactPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		// --------------------------------------------
		//   Contact Header 
		// --------------------------------------------
		GMSLabel lblContact = new GMSLabel("Contact");
		lblContact.setFont(new Font("", Font.ITALIC, 18));
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = .5;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets (10, 10, 0, 0);
		contactPanel.add(lblContact, c);
		
		// --------------------------------------------
		//   Phone and Email   
		// --------------------------------------------

		// Phone
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (5, 10, 0, 0);
		contactPanel.add(lblPhone, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = .5;
		c.weighty = 1;
		c.gridwidth = 1;
		c.ipadx = 75;
		c.insets = new Insets (5, 5, 0, 10);
		c.anchor = GridBagConstraints.WEST;
		contactPanel.add(tPhone, c);

		// Phone Error Label 
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (0, 5, 0, 10);
		c.ipadx = 10;
		contactPanel.add(lblErrPhone, c);

        c.ipadx = 0;

        // Email 
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (5, 10, 0, 0);
		contactPanel.add(lblEmail, c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = .5;
		c.weighty = 1;
		c.gridwidth = 1;
		c.ipadx = 75;
		c.insets = new Insets (5, 5, 0, 10);
		contactPanel.add(tEmail, c);

		// Email Error Label 
		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (0, 5, 0, 10);
		c.ipadx = 10;
		contactPanel.add(lblErrEmail, c);
		
		return contactPanel;

	}  // end getContactPanel 

	// Create a JPanel containing insurance widgets  
	protected JPanel getInsurancePanel()
	{
		JPanel insPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		// --------------------------------------------
		//   Add a little space
		// --------------------------------------------
						
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = .5;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.ipadx = 15;
		c.insets = new Insets (15, 10, 0, 0);
		insPanel.add(new JLabel(), c);
		
		// --------------------------------------------
		//   Add the insurance provider widgets.    
		// --------------------------------------------

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (25, 10, 0, 0);
		insPanel.add(lblInsProvider, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.insets = new Insets (15, 5, 0, 10);
		insPanel.add(tInsProvider, c);

		// Insurance Provider Error Label 
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets (0, 5, 10, 10);
		c.ipadx = 10;
		insPanel.add(lblErrInsProvider, c);

		return insPanel;
		
	}  // end getInsurancePanel 

	//************************************************************
	// PUblic Access Methods
	//************************************************************

	public String getFirstName()
	{
		return tFirstName.getText();
	}

	public String getLastName()
	{
		return tLastName.getText();
	}

	public String getStreet() 
	{
		return tStreet.getText();
	}

	public String getCity() 
	{
		return tCity.getText();
	}

	public String getState() 
	{
		System.out.println("Getting state from combo");
		String state = cbState.getSelectedItem().toString();
		System.out.println("Got it");
		return state;
	}

	public String getZip() 
	{
		return tZip.getText();
	}
	
	public String getPhone() 
	{
		return tPhone.getText();
	}

	public String getEmail() 
	{
		return tEmail.getText();
	}

	public String getInsuranceProvider() 
	{
		return tInsProvider.getText();
	}

}  // end class PersonalInfoPanel 
