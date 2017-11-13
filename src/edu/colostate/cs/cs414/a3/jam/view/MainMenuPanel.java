package edu.colostate.cs.cs414.a3.jam.view;

//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.BorderLayout;
//import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;

import javax.swing.border.*;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;


public class MainMenuPanel extends JPanel 
{
	
	private JPanel btnPanel;
	private JLabel lblHeader;
	
	private JButton	btnHireTrainer,
					btnRegisterCustomer,
					btnInventoryEquipment,
					btnModTrainer,
					btnModCustomer,
					btnModInventory;
	
	public MainMenuPanel()
	{
		super();
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 75, 25, 75));
		
		ImageIcon image = new ImageIcon("c:\\TempWorkingDir\\freeweights.jpg");
		JLabel imageLabel = new JLabel("", image, JLabel.CENTER);
		this.setBackground(Color.GRAY);
		this.add(imageLabel, BorderLayout.NORTH);

		JLabel lblHeader = new JLabel ("Welcome!");
		lblHeader.setFont(new Font("Courier", Font.BOLD, 38));
		lblHeader.setForeground(Color.BLACK);
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
				
		btnHireTrainer = new JButton("Hire New Trainer");
		btnHireTrainer.setFont(new Font("Courier", Font.BOLD, 26));
		btnRegisterCustomer = new JButton("Register New Customer");
		btnRegisterCustomer.setFont(new Font("Courier", Font.BOLD, 26));
		btnInventoryEquipment = new JButton ("Inventory New Equipment");
		btnInventoryEquipment.setFont(new Font("Courier", Font.BOLD, 26));
		btnModTrainer = new JButton("Modify Trainer");
		btnModTrainer.setFont(new Font("Courier", Font.BOLD, 26));
		btnModCustomer = new JButton("Modify Customer");
		btnModCustomer.setFont(new Font("Courier", Font.BOLD, 26));
		btnModInventory = new JButton("Modify Equipment Inventory");
		btnModInventory.setFont(new Font("Courier", Font.BOLD, 26));
		
		// Add buttons to a panel that can be added as the third row. 
		JPanel btnPanel = new JPanel(); 
		btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		btnPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
		btnPanel.setLayout(new GridLayout(7, 1, 10, 10));
		btnPanel.add(lblHeader);
		btnPanel.add(btnHireTrainer);
		btnPanel.add(btnRegisterCustomer);
		btnPanel.add(btnInventoryEquipment);
		btnPanel.add(btnModTrainer);
		btnPanel.add(btnModCustomer);
		btnPanel.add(btnModInventory);
		this.add(btnPanel, BorderLayout.CENTER);

		
	}  // end MainMenuPanel constructor

}  // end MainMenuPanel class
