package edu.colostate.cs.cs414.a3.jam.view.widgets;

import java.awt.Font;
import javax.swing.JLabel;

public class GMSLabel extends JLabel
{
	public GMSLabel(String text)
	{
		super(text);
		this.setFont (new Font("", Font.BOLD, 14));
	}

}  // end GMSLabel
