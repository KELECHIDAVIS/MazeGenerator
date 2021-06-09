import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import Framework.GamePanel;
import Framework.audio.Audio;

public class Panel extends GamePanel
{
	private final int width=800, height=800; 
	private final double UPS = 60.0 ; //increase the speed of the simulation caps at 60 
	protected int rows, cols; 
	protected  ArrayList<Cell> grid = new ArrayList<Cell>(); 
	protected Stack stack = new Stack();
	protected Audio completion = new Audio("/ding.wav"); 
	protected Cell current; 
	protected Thread gameThread ; 
	private boolean done  =false ; 
	protected final int cellWidth = 40; 
	protected float goalPercent = 0.94f; 
	protected int goalOffset = 2;
	Panel()
	{
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		this.cols = (int)(width/cellWidth); 
		this.rows = (int)(height/cellWidth);
		
		
		
		
		for(int i =0; i<rows; i++)
		{
			for(int y =0; y<cols; y++)
			{
				Cell cell = new Cell(i,y,this); 
				grid.add(cell); 
			}
		}
		
		this.current = grid.get(0); 
		this.current.visited = true; 
		
		gameThread = new Thread(this); 
		gameThread.start(); 
		
		
		
	}
	

	@Override
	public void paint(Graphics g)
	{
		
		super.paint(g);
		for(int i =0; i<grid.size();i++) 
		{
			grid.get(i).draw(g); 
			
		}
		
		g.setColor(new Color(0,255,0,80)); // starting color 
		g.fillRect(grid.get(0).y+goalOffset, grid.get(0).i+goalOffset, (int)(cellWidth*goalPercent),(int)(cellWidth*goalPercent));
		
		g.setColor(new Color(255,0,0,80));// ending color 
		g.fillRect(grid.get(grid.size()-1).y*cellWidth+goalOffset, grid.get(grid.size()-1).i*cellWidth+goalOffset, (int)(cellWidth*goalPercent), (int)(cellWidth*goalPercent));
		
	}
	
	
	public void update() throws NullPointerException 
	{
		
		//Step 1 for the algorithm
		Cell next = this.current.checkNeighbors();  // gets a random unvisited neighbor
		
		if(next!=null)
		{
			next.visited = true;
			
			
			//Step 2 
			stack.add(current); 
			
			//Step 3
			removeWalls(current, next); 
			
			
			//Step 4
			this.current = next; 
		}else if(!stack.isEmpty())
		{
			current = (Cell)stack.pop(); 
		}else if(stack.isEmpty()&&!done)
		{
			System.out.println("done");
			done  = true; 
			completion.play(); 
		}else {
			revert(); 
		}
		repaint(); 
	}
	
	
	public void revert()
	{
		for(int i =0; i<grid.size();i++) 
		{
			grid.get(i).color = new Color(0,0,0,90); 
			
		}
	}
	public void removeWalls(Cell a, Cell b)
	{
		int x= a.y- b.y; 
		int y = a.i - b.i; 
		if(x==1)
		{
			a.walls[3] = false; 
			b.walls[1]  =false; 
		}else if(x==-1)
		{
			a.walls[1] = false; 
			b.walls[3]  =false; 
		}
		
		if(y==1)
		{
			a.walls[0] = false; 
			b.walls[2]  =false; 
		}else if(y==-1)
		{
			a.walls[2] = false; 
			b.walls[0]  =false; 
		}
	}

	@Override
	public void run()
	{
		long lastTime = System.nanoTime() ; 
		long timer = System.currentTimeMillis(); 
		
		final double ns = 1000000000.0/ UPS;  // it updates five times per second so it is easier to see
		double delta = 0; 
		int frames=0;
		int updates = 0; 
		while(true)
		{
			
			long now = System.nanoTime(); 
			delta += (now-lastTime)/ns; 
			lastTime=now; 
			while(delta>=1)
			{
				update(); 
				updates++; 
				delta--; 
			}
			
			frames++; 
			
			if(System.currentTimeMillis()-timer>1000)
			{
				repaint(); 
				timer+=1000; 
				 // Updates per seconds
				frames=0; 
				updates=0; 
				
			}
		}
		
	}

}
