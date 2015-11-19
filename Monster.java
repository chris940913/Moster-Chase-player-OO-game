public class Monster extends Moveable {
	public boolean canView = true;  // allows
	public int removeTrapTime = 0;
	public Trap meetTrap = null;
	
	public Monster(Game p, int row, int col) throws Exception
	{
	   super(p);
	   setCell(p.grid.getCell(row,col));
	}
	public Cell move()
	{
		currentDirection = p.grid.getBestDirection(currentCell, p.player.getCell());
        currentCell = (p.grid.getCell(getCell(),getDirection()));
        
        meetTrap = p.hasTrap(currentCell.row, currentCell.col);
        if(meetTrap != null) {
        	meetTrap.meet = true;
        	removeTrapTime = 4;
        }        
        
        return currentCell;
	}
	public boolean viewable()  // can be used for hiding
	{
		return canView;
	}
}