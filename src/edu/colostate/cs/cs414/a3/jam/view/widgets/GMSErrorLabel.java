package edu.colostate.cs.cs414.a3.jam.view.widgets;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class GMSErrorLabel extends JLabel 
{
	public GMSErrorLabel ()
	{
		this("");		
	}
	
	public GMSErrorLabel(String errMsg)
	{ 
        this.setVerticalAlignment(JLabel.TOP);
		this.setFont(new Font("", Font.ITALIC, 10));
        this.setForeground(Color.RED);
        this.setText(errMsg);
	}
	
}  // end class GMSErrorLabel


