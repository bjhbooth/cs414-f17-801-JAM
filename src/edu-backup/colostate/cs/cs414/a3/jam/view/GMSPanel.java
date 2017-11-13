package edu.colostate.cs.cs414.a3.jam.view;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import edu.colostate.cs.cs414.a3.jam.controller.GMSBaseControl;

// GMSPanel 
// 1) Provide a base (generic) type by which all panels 
// in the system can be referred to.  
// 2) Provide a core set of common fucntions to 
// implement panel behavior. 
public abstract class GMSPanel extends JPanel implements GMSViewable
{
	// Mode in which the view is being instantiated.
	protected Mode viewMode; 
	protected GMSBaseControl gmsControl;

	// **********************************************
	// Constructor 
	// **********************************************
	protected GMSPanel(Mode mode)
	{
		this.viewMode = mode;	
	}
	
	private GMSPanel()
	{
		// Don't invoke without a mode.
	}
	
	// **********************************************
	// Abstract Methods of GMSPanel 
	// **********************************************
	protected abstract GMSPanel createPanel(Mode newMode);
	
	// **********************************************
	// Implementation of GMSViewable
	// **********************************************
	public GMSPanel changeMode(Mode newMode)
	{
		// Do nothing if view is already in the requested mode.
		if (this.viewMode == newMode)
			return this; 
		
		System.out.println("Swapping panel");
				
		// Retrieve the current parent Frame. 
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		GMSPanel newPanel = createPanel(newMode);
		topFrame.getContentPane().add(newPanel, BorderLayout.CENTER);
		topFrame.toFront();
		topFrame.requestFocus();
		
		// Cleanup
		topFrame.getContentPane().remove(this);
		
		System.out.println("Done swapping");
		
		return newPanel;
		
	}  // changeMode (Mode) 

}  //  end GMSPanel  
