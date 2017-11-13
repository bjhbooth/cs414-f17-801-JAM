package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.Border;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import java.util.Map;

import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.EquipmentBO;
import edu.colostate.cs.cs414.a3.jam.controller.EquipmentControl;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSLabel;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSTextField;
import edu.colostate.cs.cs414.a3.jam.view.widgets.GMSErrorLabel;

// Class EquipmentPanel:
//
// Implements the view for the Inventory New Eqiupment and Update Equipment views
public class EquipmentPanel extends GMSPanel  
{
	// ------------------------------------
	// Class Attributes 
	// 
	// Keep references to more of the widgets (including lables)
	// so they can be modified (i.e., to short error). 
	// ------------------------------------

	// private EquipmentControl controller;  ??? 
	
	byte[] curImageBA = null;   // Current byte[] represetning the image. 
	
	//  Maintain reference to ALL attributes, including labels
	// to allow the color to be changed to identify errors.	
	private GMSLabel			lblEquipmentName;	// Name of the eqiupment (must be unique)
	private JTextField			tEquipmentName;		// Name of the eqiupment (must be unique)
	private JLabel 				lblImage;			// Label to display jpg
	private GMSLabel			lblQuantity;		
	private	GMSTextField		tQuantity;			// Specify the quantity available
	
	private GMSErrorLabel		lblErrEquipmentName,
								lblErrQuantity;
	
    // Buttons
	private JButton 			btnSave,
								btnDelete, 
				    			btnCancel, 
								btnFileChooser;

    //Create a file chooser
    final JFileChooser fc;

	//************************************************************
	// STATIC METHODS  
	//************************************************************
	public static EquipmentPanel displayEquipmentView(Mode mode)
	{		
		JFrame frame = new JFrame("Gym Management System");
		EquipmentPanel equipmentPanel = new EquipmentPanel(mode);
		frame.setSize(700, 800);  // w/h
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 			// Close the App if user does not login
		frame.getContentPane().add(equipmentPanel, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);     // Center the frame on the screen
		frame.setVisible(true);  				// DIsplay the frame
		
		return equipmentPanel;
			
	}  // end static displayEquipmentView (Mode)

	//************************************************************
	// CONSTRUCTORS - Protected - Use displayEquipmentView
	//************************************************************
	protected EquipmentPanel ()
	{
		this(Mode.CREATE);
	}  // end EquipmentPanel() constructor
	
	protected EquipmentPanel (Mode mode)
	{
		super(mode);
		//gmsControl = new EquipmentControl(this);
		System.out.println("CREATE EQUIPMENT CONTROL HERE");
		
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");  // only allow smaller files (jpg)
		fc.setFileFilter(filter);
		
		this.setLayout(new GridBagLayout());
		
		// ----------------------------------------
		// Retrieve the various panels that comprise the view.  
		// These subportions of the view are done on separate 
		// JPanels since they can have differnt columns that 
		// don't necessarily line up nicely.  Separate panels 
		// allows for better spacing / resizing. 
		// ----------------------------------------
		
		// Retrieve a panel containing the Equipment Inventory title and the name label and field. 
		JPanel hdrPanel = getHeaderPanel();
		
		// Retrieve a panel containing a lable with the image and the file search button
		JPanel imagePanel = getImagePanel();  

		// REtrieve a panel containing a Quantity label and field.
		JPanel qtyPanel = getQuantityPanel();
		
		// Retrieve a panel contaiining the buttons.
		JPanel buttonPanel = getButtonPanel();
		
		//----------------------------------------------------
		// Add the parts to the parent panel to build the view
		// ---------------------------------------------------
		GridBagConstraints c = new GridBagConstraints();

		// ---------------------------------
		// Add a border to the employee panel
		// ---------------------------------
		
		JPanel bodyPanel = new JPanel (new GridBagLayout());
		
		Border compound, raisedBevel, loweredBevel, redline;
		raisedBevel = BorderFactory.createRaisedBevelBorder();
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		redline = BorderFactory.createLineBorder(Color.red);

		// Now create a nice frame with the borders. 
		compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

		//Add a red outline to the frame.
//		compound = BorderFactory.createCompoundBorder(redline, compound);
		
		bodyPanel.setBorder(compound);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1; 		// fill the width of the view
		c.weighty = .5;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(hdrPanel, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = .5;
		c.fill = GridBagConstraints.BOTH;
		bodyPanel.add(imagePanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weighty = .1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		bodyPanel.add(qtyPanel, c);

		
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets (10, 25, 10, 25);
		this.add(bodyPanel, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(buttonPanel, c);

		// Add Listeners 
		addListeners();

	}  // end EquipomentPanel(Mode) constructor 

	// **********************************************
	// Implementation of Abstract Methods of GMSPanel 
	// **********************************************
	protected GMSPanel createPanel(Mode newMode)
	{
		EquipmentPanel ep = new EquipmentPanel(newMode); 
		return ep; 
	}

	//**********************************************************
	// IMPLEMENTATION OF GMSViewable Interface
	//************************************************************

	public void populatePanel(Map<String, Object> results)
	{
		EquipmentBO equipment;
		boolean bValid;
		
		// Clear the fields and any error labels.
		resetPanel();

		// Get the TrainerBO object and Valid status from the Hashtable.
		try {
			equipment = (EquipmentBO)results.get(GMSBO.Result.DATA.toString());
			bValid = ((Boolean)results.get(GMSBO.Result.VALID.toString())).booleanValue();
		} catch (Exception e) {
			return;
		}

		// Set the data fields
		tEquipmentName.setText(equipment.getName());
		displayImageArray(equipment.getImage());
		tQuantity.setText(Integer.toString(equipment.getQuantity()));
		
		// Flag any error messages that were passed back 
		if (bValid == false)
		{
			// Check for name error.
			if (results.get(EquipmentBO.EquipmentAttribute.NAME.toString()) != null)
				lblErrEquipmentName.setText(results.get(EquipmentBO.EquipmentAttribute.NAME.toString()).toString());
			
			// Check for quantity error.
			if (results.get(EquipmentBO.EquipmentAttribute.QUANTITY.toString()) != null)
				lblErrQuantity.setText(results.get(EquipmentBO.EquipmentAttribute.QUANTITY.toString()).toString());
		}
					
	}  // end populatePanel 

	// ------------------------------
	// Reset Panel 
	// ------------------------------
	public void resetPanel()
	{
		tEquipmentName.setText("");
		displayImageArray(null);
		tQuantity.setText("");
		lblErrEquipmentName.setText("");
		lblErrQuantity.setText("");
	}  // end resetPanel
		

	//************************************************************
	// PUblic Access Methods
	// 
	//************************************************************
	public String getEquipmentName()
	{
		return tEquipmentName.getText();
	}

	public byte[] getImageArray ()
	{
		return curImageBA;
	}
	
	// Returns the integer if valid; -1 otherwise so be flagged on save. 
	public int getQuantity()
	{
		String sQty = tQuantity.getText();
		if ((sQty == null) || (sQty.length() == 0))
			return -1;
			
		int iQty = Integer.valueOf(sQty).intValue();
		return iQty;
	}  // getQUantity

	public void setQuantity (int qty)
	{
		if (qty >= 0)
			tQuantity.setText(Integer.toString(qty));
		else 
			tQuantity.setText("");
	}  // end setQuantity

	//************************************************************
	// PUBLIC WORKING METHODS
	//************************************************************

	public void populateView (EquipmentBO eBO)
	{
		this.setName(eBO.getName());
		this.setQuantity(eBO.getQuantity());
		
	    // Display the image.
		this.displayImageArray(eBO.getImage());
	} // end populateView
	
	//************************************************************
	// PROTECTED WORKING METHODS
	//************************************************************

	// getHeaderPanel - Build the top portion of the view that contains 
	// the Equipment Inventory title and the name label and field
	protected JPanel getHeaderPanel()
	{
		JPanel hdrPanel = new JPanel(new GridBagLayout());

		// ------------------------------
		// DECLARE THE COMPONENTS 
		// ------------------------------

		// Top of View - Create / Update Inventory Header
		JLabel lblTitleHeader;
		if (this.viewMode == Mode.UPDATE)
			lblTitleHeader = new JLabel ("Update Equipment Inventory",  SwingConstants.CENTER);
		else 
			lblTitleHeader = new JLabel ("Inventory New Equipment",  SwingConstants.CENTER);
		lblTitleHeader.setFont (new Font("", Font.BOLD, 48));
		lblTitleHeader.setForeground(Color.BLUE);

		// Equipment name
		lblEquipmentName = new GMSLabel("Equipment Name");
		lblEquipmentName.setFont(new Font("", Font.BOLD, 22));
		lblEquipmentName.setHorizontalAlignment(JLabel.CENTER);
		tEquipmentName = new JTextField();
		tEquipmentName.setFont(new Font("", Font.BOLD, 22));
		if (this.viewMode == Mode.UPDATE)
		{
			// Make the textfield behave like a label.
			// Update mode cannot change the employee ID
			lblEquipmentName.setVisible(false);  // hide the name label, but keep it for spacing
			tEquipmentName.setFont(new Font("", Font.BOLD, 32));
			tEquipmentName.setHorizontalAlignment(JTextField.CENTER);
			tEquipmentName.setText("Equipment Name");
			tEquipmentName.setBorder(null);
			tEquipmentName.setEditable(false);
			tEquipmentName.setBackground(null);  // or setOpaqaque (false);
		} 

		lblErrEquipmentName = new GMSErrorLabel("Name error test");
		
		// ------------------------------
		// ADD COMPONENTS TO THE HEADER PANEL  
		// ------------------------------
		GridBagConstraints c = new GridBagConstraints();
				
		// Header Label 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 10, 25, 10);  // top, left, bottom, right
//		c.ipady = 20;  // default is 0
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		hdrPanel.add(lblTitleHeader, c);

		// Reset iPady
		c.ipady = 0;
		
		// ---------------------------------
		// Add components to header panel
		// ---------------------------------
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = .5; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 200, 5, 200);  // top, left, bottom, right
		c.ipadx = 200;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		hdrPanel.add(lblEquipmentName, c);

		c.gridx = 0;
		c.gridy = 3;
		c.weightx = .5; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 200, 1, 200);  // top, left, bottom, right
		c.ipadx = 200;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		hdrPanel.add(tEquipmentName, c);

		// Add Equpment Name error label.    
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 200, 10, 200);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		hdrPanel.add(lblErrEquipmentName, c);
		
		return hdrPanel;
	
	}  // end getHeaderPanel 


	// Retrieve a panel containing a label with the image and the file search button
	protected JPanel getImagePanel()
	{
		JPanel imgPanel = new JPanel(new GridBagLayout());

		// ------------------------------
		// DECLARE THE COMPONENTS 
		// ------------------------------

		// Top of Inventory View - Create / Update Equipment Header

		// Image Label 
		lblImage = new JLabel();
		lblImage.setHorizontalAlignment(JLabel.CENTER);
		lblImage.setVerticalAlignment(JLabel.CENTER);
		lblImage.setMinimumSize(new Dimension(400, 300));
		lblImage.setOpaque(true);
		lblImage.setBackground(Color.BLACK);
		// create a line border for the ImageLabel with the specified color and width
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        lblImage.setBorder(border);

		// Button to show file chooser 
		btnFileChooser = new JButton("Select Image");
		btnFileChooser.setFont(new Font("", Font.BOLD, 22));
		
		// ------------------------------
		// ADD COMPONENTS TO THE HEADER PANEL  
		// ------------------------------
		GridBagConstraints c = new GridBagConstraints();

		/*
		// Spacer Label 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
//		c.ipady = 20;  // default is 0
		c.ipadx = 30;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		imgPanel.add(new JLabel(), c);
*/
		// Image Label to display picture. 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = .5; 		// already fills the width of the view
		c.weighty = .5;
		c.gridwidth = 1;
		c.insets = new Insets (15, 25, 10, 25);  // top, left, bottom, right
//		c.ipady = 500;  // default is 0
//		c.ipadx = 500;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		imgPanel.add(lblImage, c);		
		
		/*
		// Spacer Label
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 10, 0, 10);  // top, left, bottom, right
//		c.ipady = 20;  // default is 0
		c.ipadx = 30;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		imgPanel.add(new JLabel(), c);
*/
		// Button to Invoke Choose File dialog 
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0; 		// already fills the width of the view
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets (0, 0, 10, 0);  // top, left, bottom, right
//		c.ipady = 300;  // default is 0
		c.ipadx = 75;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
//		c.ipady = 15;
		imgPanel.add(btnFileChooser, c);		
		
		return imgPanel;
	
	}  // end getImagePanel

	// REtrieve a panel containing a Quantity label and field.
	protected JPanel getQuantityPanel()
	{
		JPanel qtyPanel = new JPanel(new GridBagLayout());

		// ------------------------------
		// DECLARE THE COMPONENTS 
		// ------------------------------

		// Quantity Label 
		lblQuantity = new GMSLabel("Quantity");
		lblQuantity.setFont(new Font("", Font.BOLD, 22));
		lblQuantity.setHorizontalAlignment(JLabel.CENTER);
				
		// Quantity TextField 
		tQuantity = new GMSTextField();
		tQuantity.setFont(new Font("", Font.BOLD, 22));
		tQuantity.setHorizontalAlignment(JTextField.CENTER);
		tQuantity.setToolTipText("Integer 0 or greater.");
		
		lblErrQuantity = new GMSErrorLabel("Qty Error");

		// ------------------------------
		// ADD COMPONENTS TO THE HEADER PANEL  
		// ------------------------------
		GridBagConstraints c = new GridBagConstraints();
				
		// Quantity Label 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = .5; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (25, 200, 0, 200);  // top, left, bottom, right
//		c.ipady = 20;  // default is 0
		c.ipadx = 200;
		c.anchor = GridBagConstraints.SOUTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		qtyPanel.add(lblQuantity, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = .5; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (5, 200, 1, 200);  // top, left, bottom, right
//		c.ipady = 20;  // default is 0
		c.ipadx = 75;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		qtyPanel.add(tQuantity, c);

		// Add Quantity error label.    
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 200, 20, 200);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		qtyPanel.add(lblErrQuantity, c);

		return qtyPanel;

	}  // end getQuantityPanel 
	
	// getButtonPanel - Build the top portion of the view that contains 
	// the save, delete, and cancel buttons
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
		c.insets = new Insets (10, 10, 10, 5);  // top, left, bottom, right
		c.ipady = 15;  // default is 0
		c.ipadx = 0;
		panel.add(btnSave, c);

		// Delete button  
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (10, 0, 10, 5);  // top, left, bottom, right
		c.ipady = 15;  // default is 0
		c.ipadx = 0;
		panel.add(btnDelete, c);

		// Cancel button  
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 1; 		// already fills the width of the view
		c.gridwidth = 1;
		c.insets = new Insets (10, 0, 10, 10);  // top, left, bottom, right
		c.ipady = 15;  // default is 0
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

	// ----------------------------------------
	// Display the cur image held by the view. If none is selected, just return
	// ----------------------------------------
	protected void displayImageArray()
	{
		displayImageArray (curImageBA);
	}  
	
	// ----------------------------------------
	// Display the image represented by the byte array in the lblImage. 
	// Byte[] either came from JFileChooser or from an Equipment object
	// retrieved from the database. 
	// ----------------------------------------
	protected void displayImageArray(byte[] imageBA)
	{
		// https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
		
		
		
		// If there is not image, clear the label. 
		if (imageBA == null)
		{
			System.out.println("In displayImageArray with null to clear image");
			// Clear the label
			lblImage.setIcon(null);
			lblImage.setBackground(Color.BLACK);
			System.out.println("Called setIcon null");
		    // *IMPORTANT* - Revalidate() to cause JLabel to resize and be repainted.
		    lblImage.revalidate();
			System.out.println("Success");
			this.curImageBA = imageBA;
		    return;
		}
			
		// Display the image represented by the byte array.  
		BufferedImage img = null;
		try {
		     img = ImageIO.read(new ByteArrayInputStream(imageBA));
		     System.out.println("Image retrieved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("Label Width = " + lblImage.getWidth());
		System.out.println("Label height= " + lblImage.getHeight());
		Image dImg = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
		
		lblImage.setIcon(new ImageIcon(dImg));
		this.curImageBA = imageBA;
		
	}  // displayImageArray 
	
	//************************************************************
	// PROTECTED HANDLER METHODS INVOKED BY BUTTON LISTENER 
	//************************************************************

	protected void handleSaveRequest()
	{
		System.out.println("Save button pressed");
	}  // end handleSaveRequest

	protected void handleCancelRequest()
	{
		System.out.println("Cancel button pressed");
	}  // end handleCancelRequest

	protected void handleDeleteRequest()
	{
		System.out.println("Delete button pressed");
	}  // end handleDeleteRequest

	protected void handleChooseImageRequest()
	{
		//  In response to a button click:
		int returnVal = fc.showOpenDialog(EquipmentPanel.this);

		// -------------------------------------------------
		// Obtain a File object for the specified file. 
		// -------------------------------------------------
		File imgFile; 
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			imgFile = fc.getSelectedFile();
			System.out.println("File = " + imgFile.getName());
		} else {
			return;
		}
		
		// --------------------------------------------------
		// Open a connection to the file and read it. 
		// --------------------------------------------------
	//File imgFile = new File ("C:\\turnin\\test2.jpg");
		//FileInputStream fileInputStream = new FileInputStream("C:\\turnin\\test.jpg");
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(imgFile);
		} catch (Exception e) {
			System.out.println("Exception creating new File(filename");
			return;
		}

		System.out.println("Success.... now about to read in file");
		
		byte[] imgArray = new byte[(int)imgFile.length()];
		try {
			DataInputStream dataIS = new DataInputStream(fileInputStream);
			dataIS.readFully(imgArray);
		} catch (Exception e) {
			System.out.println("Error reading file");
			return;
		}
		
		System.out.println("Read file into byte[] ");

		// Keep a reference to it. 
		this.curImageBA = imgArray;
		
		// Display it in the lblImage field
		displayImageArray(imgArray);
		
	}  // end handleChooseImage
	
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
		btnFileChooser.addActionListener(new ButtonListener());
		
		lblImage.addComponentListener(new EquipmentComponentListener());
	}  // end addListeners

	private void removeListeners()
	{
		btnSave.removeActionListener(new ButtonListener());
		btnCancel.removeActionListener(new ButtonListener());
		if (this.viewMode == Mode.UPDATE)
			btnDelete.removeActionListener(new ButtonListener());
		btnSave.removeComponentListener(new EquipmentComponentListener());
		
	}  // end removeListeners


	//************************************************************
	// INNER CLASS:  ButtonListener
	// 
	// Save, Delete (update only), Cancel, and FileChhoser
	//************************************************************
	class ButtonListener implements ActionListener 
	{
		// Handle button events. 
		public void actionPerformed (ActionEvent e)
		{
			if (e.getSource().equals(btnSave)) 
			{
				EquipmentPanel.this.handleSaveRequest(); 
			}  
			else if (e.getSource().equals(btnCancel))
			{
				EquipmentPanel.this.handleCancelRequest();
			}
			else if (e.getSource().equals(btnDelete))
			{
				EquipmentPanel.this.handleDeleteRequest();
				System.out.println("Delete pressed!");
			}
			else if (e.getSource().equals(btnFileChooser)) 
			{
				// Call outer class handler. 
				EquipmentPanel.this.handleChooseImageRequest();
			}  // end Action performed 
		} //  end actionPerformed 
	
	}  // end Inner Class ButtonListener

	//************************************************************
	// INNER CLASS:  ComponentAdapter
	//************************************************************
	
	// When the window being listened on resizes, resize the image displayed in lblImage.
	class EquipmentComponentListener extends ComponentAdapter 
	{
		public void componentResized (ComponentEvent e)
		{
			EquipmentPanel.this.displayImageArray();
		} //  end windowClosed 
		
	}  // end Inner Class EquipmentComponentListener

}  // end EaquipmentPanel class 
