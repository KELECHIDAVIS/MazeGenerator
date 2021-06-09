import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Cell extends Rectangle
{
	
	
	private static int roadblock =0; 
	public int i,y; // 
	protected Color color = new Color(150,0,255, 100);  // for black its Color(0,0,0, 100);   // purple is 150,0,255, 100
	private int finderOffset=0; 
	private float finderFillPercent = 1f; 
	private Panel panel;  
	protected final int width; // the width of each cell.
	protected boolean visited; 
	protected boolean[] walls = {true, true,true,true}; //top , right, bottom, left       is the order that the walls are drawn in and the order that this array is in
	Cell(int i , int y , Panel panel ) // i is the row number while y is the column number REMEMBER THIS IF YOU HAVE ANY PROBLEMS
	{
		this.panel = panel; 
		this.i = i; 
		this.y = y; 
		this.width = panel.cellWidth; 
		this.visited = false; 
		
		
		
	}
	

	
	public Cell checkNeighbors()
	{
		
		ArrayList<Cell> neighbors = new ArrayList<Cell>(); 
		Cell top=null , right=null, bottom=null, left=null; 
		try
		{top = panel.grid.get(index(this.y,this.i-1));;}
		catch(IndexOutOfBoundsException e){}// inits top neighbor
		try
		{right = panel.grid.get(index(this.y+1,this.i)); }
		catch(IndexOutOfBoundsException e){}// inits right neighbor
		try
		{bottom = panel.grid.get(index(this.y,this.i+1)); }
		catch(IndexOutOfBoundsException e){}// inits bottom neighbor
		try
		{left = panel.grid.get(index(this.y-1,this.i)); }
		catch(IndexOutOfBoundsException e){}// inits left neighbor
		 //below code sees if the neighbor does exist; if so the it checks if it hasn't been visited; if thats the case then it is added to the arraylist
		
		
		
		if(top!=null)
		{
			if(!top.visited){ neighbors.add(top); }
		}
		if(right!=null)
		{
			if(!right.visited){ neighbors.add(right); }
		}
		if(bottom!=null)
		{
			
			if(!bottom.visited){ neighbors.add(bottom);}//
			
		}
		if(left!=null)
		{
			if(!left.visited){ neighbors.add(left); }
		}
		
		if(neighbors.size()>0)// returns a random neighbor otherwise returns null
		{
			Random r = new Random(); 
			int randint = r.nextInt(neighbors.size()); 
			return neighbors.get(randint); 
			
		}else
		{//where it should backtrack to find a different direction 
			if(!panel.stack.isEmpty())
			{
				roadblock++; 
				System.out.println("Roadblocks: "+roadblock);
			}
			
			
			return null; 
		}
	
	}
	
	public int index(int x,int y) // where x is y and  y is i
	{
		
		if(x<0||y<0||x>panel.cols-1||y>panel.rows-1)
		{
			return -1; 
		}
		
		
		//first one this.i = 0 , this.y = 0
		return x+ (y)*panel.cols; // this is the first divergence from the video; if you have a problem its probably based here  his (7:16 / 2nd)
	}
	public void draw(Graphics g )
	{
		
		g.setColor(Color.white); 
		
		int x = this.y*width; 
		int y = this.i*width; 
		
		if(this.walls[0])
		{
			g.drawLine(x,   y,  x+width,  y);
		}
		if(this.walls[1])
		{
			g.drawLine(x+width ,  y,  x+width,  y+width);
		}
		if(this.walls[2])
		{
			g.drawLine(x+width,  y+width,  x,  y+width);
		}
		if(this.walls[3])
		{
			g.drawLine(x,  y+width,  x,  y);
		}
		
		
		if(this.visited)
		{
			g.setColor(this.color); // purple for visual effect to follow the thing   new Color(150,0,255, 100)
			
			g.fillRect(x+finderOffset, y+finderOffset, (int)(width*finderFillPercent), (int)(width*finderFillPercent));
			//g.fillRect(x, y, width, width);// change this for the size of the purple when searching 
		}
		
		
	
	}
}
