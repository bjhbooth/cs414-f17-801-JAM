package edu.colostate.cs.cs414.a3.jam;

import java.awt.*;

import javax.swing.*;
import edu.colostate.cs.cs414.a3.jam.view.PersonalInfoPanel;
import edu.colostate.cs.cs414.a3.jam.view.WorkInfoPanel;
import edu.colostate.cs.cs414.a3.jam.view.CreateUserPanel;
import edu.colostate.cs.cs414.a3.jam.view.TrainerPanel;
import edu.colostate.cs.cs414.a3.jam.view.CustomerPanel;
import edu.colostate.cs.cs414.a3.jam.view.LoginUserPanel;
import edu.colostate.cs.cs414.a3.jam.view.EquipmentPanel;
import edu.colostate.cs.cs414.a3.jam.db.TrainerDB;
import edu.colostate.cs.cs414.a3.jam.business.TrainerBO;
import java.sql.*;
import java.awt.image.*;
import edu.colostate.cs.cs414.a3.jam.view.MainMenuPanel;

// included for IMAGE test
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import edu.colostate.cs.cs414.a3.jam.business.EquipmentBO;
import edu.colostate.cs.cs414.a3.jam.db.EquipmentDB;
import edu.colostate.cs.cs414.a3.jam.controller.TrainerControl;

// Main class of the Gym Management System Application.  Will invoke the Login screen. 


public class GymManagementSystemApp {

	public static void main(String[] args) 
	{
		
		TrainerControl tc = new TrainerControl (null);
		tc.save();
		
		
		EquipmentPanel ep = EquipmentPanel.displayEquipmentView(EquipmentPanel.Mode.CREATE_EQUIPMENT);
		EquipmentPanel.displayEquipmentView(EquipmentPanel.Mode.UPDATE_EQUIPMENT);
      
		LoginUserPanel myPanel = LoginUserPanel.displayLoginUserView();
		//CreateUserPanel myPanel = CreateUserPanel.displayCreateUserView();
		TrainerPanel myPanel2 = TrainerPanel.displayTrainerView(TrainerPanel.Mode.CREATE_TRAINER);
		CustomerPanel myPanel3 = CustomerPanel.displayCustomerView(CustomerPanel.Mode.CREATE_CUSTOMER);

	
		/* COPIED ALL THIS TO HANDLE FILE CHOOSE  
		// Open a connection to the .jpg.
		File imgFile = new File ("C:\\turnin\\test2.jpg");
		//FileInputStream fileInputStream = new FileInputStream("C:\\turnin\\test.jpg");
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(imgFile);
		} catch (Exception e) {
			System.out.println("Exception creating new File(filename");
		}

//		prep.setBinaryStream(3, fis, fileLenght);

		System.out.println("Success.... now about to read in file");
		
		byte[] imgArray = new byte[(int)imgFile.length()];
		try {
			DataInputStream dataIs = new DataInputStream(new FileInputStream(imgFile));
			dataIs.readFully(imgArray);
		} catch (Exception e) {
			System.out.println("Error reading file");
		}
		
		System.out.println("Read file in ");
*/		
		
	/*	
		// DISPLAY THE IMAGE REPRESENTED BY HTE BYTE ARRAY 
		BufferedImage img = null;
		try {
		     img = ImageIO.read(new ByteArrayInputStream(imgArray));
		     System.out.println("Image retrieved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		JLabel myLabel = new JLabel();
		
		panel.add(myLabel);
		myLabel.setIcon(new ImageIcon(img));
		frame.setSize(new Dimension(550,700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Do System.exit(0) on close
		frame.setLocationRelativeTo(null);  // Center the frame on hte screen
		frame.setVisible(true);  // DIsplay the frame
		System.out.println("Width = " + myLabel.getWidth());
		System.out.println("Height = " + myLabel.getHeight());
		
		EquipmentBO e = new EquipmentBO("TestEquipment4", imgArray, 5);
		EquipmentDB db = new EquipmentDB();
		
		if (db.add(e))
			System.out.println("Save successful");
		else
			System.out.println("Save UNsuccessful");
*/
// ------------------------------------------------------
//		TRY TO GET IT BACK 
//		
		/*
		e = db.get("TestEquipment2");

		BufferedImage img2 = null;
		try {
			 if (e.getImage() == null)
				 System.out.println("Image is null");
		     img2 = ImageIO.read(new ByteArrayInputStream(e.getImage()));
		     System.out.println("Image retrieved successfully.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		JFrame frame2 = new JFrame();
		JPanel panel2 = new JPanel();
		frame2.getContentPane().add(panel2, BorderLayout.CENTER);
		JLabel myLabel2 = new JLabel();
		
		panel2.add(myLabel2);
		myLabel2.setIcon(new ImageIcon(img2));
		frame2.setSize(new Dimension(550,700));
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Do System.exit(0) on close
		frame2.setLocationRelativeTo(null);  // Center the frame on hte screen
		frame2.setVisible(true);  // DIsplay the frame

*/		
		

		/*
//		public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
		    BufferedImage bi = null;
		    try {
		        ImageIcon ii = new ImageIcon("c:\\TempWorkingDir\\mat.jpg");//path to image
		        bi = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		        Graphics2D g2d = (Graphics2D) bi.createGraphics();
		        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
		        g2d.drawImage(ii.getImage(), 0, 0, 50, 50, null);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
     
		 Image background = Toolkit.getDefaultToolkit().createImage("c:\\TempWorkingDir\\mat.jpg");
		  JPanel panel = new JPanel (new BorderLayout());
		  JLabel imageLabel = new JLabel ();
		  ImageIcon i = new ImageIcon(bi);
		  imageLabel.setIcon(i);
		  panel.add(imageLabel, BorderLayout.NORTH);
		  panel.add(imageLabel, BorderLayout.SOUTH);
		  panel.add(imageLabel, BorderLayout.EAST);
		  panel.add(imageLabel, BorderLayout.WEST);
		  
		 //		 panel.drawImage(background, 0, 0, null);

*/
		
/*		try {
			
		

		TrainerDB trainerDB = new TrainerDB();
		TrainerBO t = trainerDB.get("E2");
		t.printTrainer();

		} catch (Exception e) {
		   System.out.println("Main caught error connecting");
		   System.out.println(e);
		}

		JFrame mainFrame = new JFrame("Gym Management System");

		mainFrame.setSize(new Dimension(550,700));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Do System.exit(0) on close
		mainFrame.getContentPane().add(new MainMenuPanel(), BorderLayout.CENTER);
		mainFrame.setLocationRelativeTo(null);  // Center the frame on hte screen
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);  // DIsplay the frame
*/

//		frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Do System.exit(0) on close
//		frame0.getContentPane().add(new HireNewTrainerPanel(), BorderLayout.CENTER);
//		frame0.pack();  // size the frame 
//		frame0.setLocationRelativeTo(null);  // Center the frame on hte screen 
//		frame0.setVisible(true);  // DIsplay the frame
		
	}  // end main

}  // end GymManagementSystemApp class
