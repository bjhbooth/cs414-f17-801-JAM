package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.Map;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSTextField;
import edu.colostate.cs.cs414.a3.jam.view.widgets.LinePanel;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.TrainerBO;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSErrorLabel;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSLabel;

//Class:  WorkInfoPanel
//
//Create the Work Information portion of the Hire New Trainer and Register CUstomer views.  
public class WorkInfoPanel extends JPanel 
{
    // --------------------------------------
    // Attributes 
    // Keep references to all widgets to 
    // modify for error reporting (color, etc.)
    // --------------------------------------
	private GMSLabel 	lblHours,
	               		lblQualifications;
	
	private GMSTextField tHours;
	private JTextArea    tQualifications;
	 	           
	// Error message labels. 
	private GMSErrorLabel lblErrHours,
				   		  lblErrQualifications;

	//************************************************************
	// CONSTRUCTOR
	//************************************************************
	public WorkInfoPanel()
	{
		super();
		this.setLayout(new GridBagLayout());
		
		// Created to get the proper inset when added to a main panel.
		JPanel insetPanel = new JPanel(new GridBagLayout());
		
		// ------------------------------------------------------
		// Instantiate / Initialize the Components
		// ------------------------------------------------------
		
		lblHours = new GMSLabel("Hours:");
		tHours = new GMSTextField();
		lblQualifications = new GMSLabel("Qualifications:");
	    tQualifications = new JTextArea(15, 2);
	    tQualifications.setLineWrap(true);
	    tQualifications.setWrapStyleWord(true);
	    JScrollPane scrollQualifications = new JScrollPane(tQualifications);
		scrollQualifications.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollQualifications.setPreferredSize(new Dimension(100, 75));

		lblErrHours = new GMSErrorLabel("Hours Error");
		lblErrQualifications = new GMSErrorLabel("Qualifications Error");

		// ---------------------------------
		// Add a border to the Work Info panel
		// ---------------------------------
		Border compound, raisedBevel, loweredBevel;
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		
		// Now create a nice frame with the borders. 
		compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
		insetPanel.setBorder(compound);

		// --------------------------------------------
		// Work Information header with line 
		// --------------------------------------------

		JPanel nestedHdrPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel wiLabel = new JLabel("Work Information", SwingConstants.LEFT);
		wiLabel.setFont (new Font("", Font.BOLD, 22));
		wiLabel.setForeground(Color.BLUE);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 0, 10);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedHdrPanel.add(wiLabel, c);

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
		// Add Hours  
		// --------------------------------------------
		JPanel nestedDetailsPanel = new JPanel (new GridBagLayout());

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedDetailsPanel.add(lblHours, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = .5;
		c.gridwidth = 1;
		c.weighty = 0;
		c.insets = new Insets(10, 5, 0, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		nestedDetailsPanel.add(tHours, c);


		// Add hours error label.    
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 5, 0, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedDetailsPanel.add(lblErrHours, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 1;
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedDetailsPanel.add(lblQualifications, c);

		c.gridx = 1;
		c.gridy = 2;
		c.weightx = .5;
		c.weighty = 1;
		c.insets = new Insets(10, 5, 0, 10);
		c.ipady = 50;
		nestedDetailsPanel.add(scrollQualifications, c);

		// Reset ipady
		c.ipady = 0;
		
		// Add qualifications error label.    
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 5, 10, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		nestedDetailsPanel.add(lblErrQualifications, c);

		// Reset ipady
		c.ipady = 0;
		
		// --------------------------------------------
		// 	Add the two nested panels to the main panel.   
		//	--------------------------------------------
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		insetPanel.add(nestedHdrPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		insetPanel.add(nestedDetailsPanel, c);

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (10, 10, 10, 10);  // top, left, bottom, right
		c.anchor = GridBagConstraints.CENTER;
		c.ipady = 0;		
		this.add(insetPanel, c);

	}  // end WorkInfoPanel constructor
	
	//************************************************************
	// PUblic Access Methods
	//************************************************************
	public String getHours()
	{
		return tHours.getText();
	}

	public String getQualifications()
	{
		return tQualifications.getText();
	}

	//************************************************************
	// PUblic Working Methods
	//************************************************************

	// Populate the panel with the given strings.  ErrHours and errQUalifiactions can be null or blank ("") if 
	// the fields are valid. 
	public void populatePanel(String hours, String errHours, String qualifications, String errQualifications)
	{
		// Clear the fields and any error labels.
		resetPanel();
		
		tHours.setText(hours);
		tQualifications.setText(qualifications);
		if (errHours != null)
			lblErrHours.setText(errHours);
		if (errQualifications != null)	
			lblErrQualifications.setText(errQualifications);
		
	}  // end populatePanel 
	
	// ------------------------------
	// Reset Panel 
	// ------------------------------
	public void resetPanel()
	{
		tHours.setText("");
		tQualifications.setText("");
		lblErrHours.setText("");
		lblErrQualifications.setText("");
		
	}  // end resetPanel 
	

}  // end WorkInfoPanel class
