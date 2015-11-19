

public abstract class Moveable {
	public char currentDirection;  
	public Cell currentCell;
	public Game p;
   
   public Moveable(Game p)
   {
	   this.p = p;
   }
   public void setDirection(char d)
   {
	   currentDirection = d;
   }
   public char getDirection()
   {
      return currentDirection;	    
   }
   public void setCell(Cell c)
   {
	   currentCell = c;
   }
   public Cell getCell()
   {
      return currentCell;	    
   }
   public abstract Cell move();
}

