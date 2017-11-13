package edu.colostate.cs.cs414.a3.jam.view.widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.JPanel;

public class LinePanel extends JPanel 
{
	Color color;
	int thickness;
	
    public LinePanel()
    {
    	super();
    	this.color = Color.BLACK;
    	this.thickness = 1;
    	
    }  // LinePanel
    
    public LinePanel(Color color, int thickness)
   	{
    	super();
    
    	this.color = color;
    	this.thickness = thickness;
    	
    }  // LinePanel (Color thickness)
    
	public void paintComponent (Graphics g)
	{
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(thickness));
	    g.setColor(color);

	    int width = getWidth();
	    int height = getHeight();

	    //g.drawLine(0, height/2, width, height/2);
    	g2.drawLine(0, height/2, width, height/2);  
    }  // end paintCompnent
	
}  //  end class LinePanel
