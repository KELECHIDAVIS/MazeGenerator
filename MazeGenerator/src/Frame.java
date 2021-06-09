import javax.swing.JFrame;

import Framework.GameFrame;

public class Frame extends GameFrame
{
	
	
	
	Frame( String title)
	{
		super( title); 
		Panel panel = new Panel(); 
		add(panel); 
		
		
		setVisible(true); 
		
		pack(); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
	}

}
