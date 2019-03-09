import java.awt.Graphics;
import java.awt.Graphics2D;

/***
 * Square class extends ShapeObj
 * @author tania
 * draw method draws the square on the canvas
 */
public class Square extends ShapeObj{
	
	public Square(int id, String name, String shape, String color, String size, int x, int y) {
		super(id, name, shape, color, size, x, y);	
	}
	
	//Draw methods
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.getColorObg());
      //If isVisible (check box is checked) draw the square
        if(this.getIVisible())
        	g2d.fillRect(this.getX(), this.getY(), this.getHeigh(), this.getWidth());  
        else g2d.fillRect(this.getX(), this.getY(), 0, 0);  
	}
	
	public void hide(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.getColorObg());
        g2d.fillRect(this.getX(), this.getY(), 0, 0);  
	}
}
