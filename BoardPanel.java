import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/* This panel represents the game board (grid) 
 * It also responds to game related events
 * The overridden paintcompnent() is called whenever the board
 * or the pieces needs to be updated 
 */
public class BoardPanel extends JPanel {

   private Graphics gr;
   private Game p;
   private final int CELLWIDTH = 40;
   private final int CELLHEIGHT = 40;
   private final int LMARGIN = 50;
   private final int TMARGIN = 8;
   private final Font font = new Font(Font.SANS_SERIF, Font.ITALIC | Font.BOLD, 12);
   
   public BoardPanel(Game p)
   {
	   this.p = p;
	   gr = this.getGraphics(); 
	   this.setFocusable(true);
	   this.requestFocus();
   }
   
   /* returns the x coordinate based on left margin and cell width */
   private int xCor(int col)
   {
	   return LMARGIN + col * CELLWIDTH;
   }
   /* returns the y coordinate based on top margin and cell height */
   private int yCor(int row)
   {
	   return TMARGIN + row * CELLHEIGHT;
   }
   
   /* Redraws the board and the pieces
    * Called initially and in response to repaint()
    */
   protected void paintComponent(Graphics gr)
   {
        super.paintComponent(gr);
        gr.setFont(font);
  		Cell cells[] = p.grid.getAllCells();
		Cell cell;
        for (int i=0; i<cells.length; i++)
        {
           cell = cells[i];
           if (cell.col%5 == 0 && cell.row%5 == 0)
        	   gr.setColor(Color.cyan);
           else 
        	   gr.setColor(Color.white);
           gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);         	
    	   gr.setColor(Color.black);
           gr.drawRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
        }
        
        for(int i = 0; i < p.nougats.size(); i++) {
        	Nougat nougat = p.nougats.get(i);
        	cell = nougat.cell;
     	    gr.setColor(Color.GREEN);
            gr.fillOval(xCor(cell.col)+CELLWIDTH/8, yCor(cell.row)+CELLWIDTH/8, CELLWIDTH*3/4, CELLHEIGHT*3/4);
    	    gr.setColor(Color.blue);
            gr.drawString("N",xCor(cell.col)+CELLWIDTH/3 + 2, yCor(cell.row)+2*CELLWIDTH/3);
        }
        
        for(int i = 0; i < p.traps.size(); i++) {
        	Trap trap = p.traps.get(i);
        	cell = trap.cell;
     	    gr.setColor(Color.PINK);
            gr.fillOval(xCor(cell.col)+CELLWIDTH/8, yCor(cell.row)+CELLWIDTH/8, CELLWIDTH*3/4, CELLHEIGHT*3/4);
    	    gr.setColor(Color.MAGENTA);
            gr.drawString("T",xCor(cell.col)+CELLWIDTH/3 + 2, yCor(cell.row)+2*CELLWIDTH/3);
        }        
        
        cell = p.player.getCell();
 	    gr.setColor(Color.red);
        gr.fillOval(xCor(cell.col)+CELLWIDTH/8, yCor(cell.row)+CELLWIDTH/8, CELLWIDTH*3/4, CELLHEIGHT*3/4);
 	    gr.setColor(Color.white);
        gr.drawString("P",xCor(cell.col)+CELLWIDTH/3 + 2, yCor(cell.row)+2*CELLWIDTH/3);
        
        if (p.monster.viewable())
        {
           cell = p.monster.getCell();
    	   gr.setColor(Color.black);
           gr.fillOval(xCor(cell.col)+CELLWIDTH/8, yCor(cell.row)+CELLWIDTH/8, CELLWIDTH*3/4, CELLHEIGHT*3/4);
    	   gr.setColor(Color.white);
           gr.drawString("M",xCor(cell.col)+CELLWIDTH/3 + 2, yCor(cell.row)+2*CELLWIDTH/3);
        }
    }	
}






