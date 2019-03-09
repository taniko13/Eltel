import java.awt.Graphics;
import java.awt.Graphics2D;

/***
 * Triangle class extends the ShapeObj
 * @author tania
 *draw method draws triangle on the canvas
 */
public class Triangle extends ShapeObj {
	
	public Triangle(int id, String name, String shape, String color, String size, int x, int y) {
		super(id, name, shape, color, size, x, y);
	}
	
	//Draw methods
		public void draw(Graphics g) {
			int x = this.getX();
			int y = this.getY();
			Graphics2D g2d = (Graphics2D) g;
		    g2d.setColor(this.getColorObg());
		    int x2 = this.getWidth();
		    int x3 = this.getWidth() *2;
		    int y2 = -this.getHeigh();
		  //If isVisible (check box is checked) draw the triangle
		    if(this.getIVisible()) {
		    	g2d.fillPolygon(new int[]{x, x + x2, x + x3}, new int[]{y, y + y2, y }, 3);}
		    else g2d.fillRect(this.getX(), this.getY(), 0, 0);  
		}
}
