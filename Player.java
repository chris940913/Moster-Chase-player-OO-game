

public class Player extends Moveable {
	public static final int DEF_ENERGY = 100;
	
    public boolean readyToStart = false;
    public int energy = DEF_ENERGY;
    public int placeTrapTimes = 0;
    public int escapeTimes = 0;
   
   public Player(Game p, int row, int col) throws Exception
   {
	   super(p);
	   currentCell = p.grid.getCell(row, col);   
	   currentDirection = ' ';
   }

   public void addEnergy(int v) {
	   energy += v;
   }

   public void decEnergy(int v) {
	   energy -= v;
	   if(energy < 0) energy = 0;
   }

   public void placeTrap() {
	   if(placeTrapTimes < 3) {
		   if(energy >= 50) {
			   placeTrapTimes=placeTrapTimes + 1;
			   Trap trap = new Trap(currentCell);
			   p.traps.add(trap);
		   }
	   }
   }
   
   public Cell move()
   {
	   if(energy <= 0) return currentCell;
	   
       currentCell = p.grid.getCell(currentCell,currentDirection);
       if(p.hasNougat(currentCell.row, currentCell.col)) {
    	   this.addEnergy(6);
    	   p.removeNougat(currentCell.row, currentCell.col);
       }       
       return currentCell;
   }
   
   public int maxCellsPerMove()
   {
	   return 1;
   }
   
   public  int pointsRemaining()
   {
	   return -1;  // not implemented
   }
   
   public void setReady(boolean val)
   {
	   readyToStart = val;
   }
   
   public boolean isReady()
   {   return readyToStart;
   }

}



