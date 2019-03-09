import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/***
 * Circle class extends ShapeObj
 * @author tania
 * draw method draws the circle on the canvas
 */
public class Circle extends ShapeObj{
	
	public Circle(int id, String name, String shape, String color, String size, int x, int y) {
		super(id, name, shape, color, size, x, y);
	}
	
	//Draw methods
	public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle = new Ellipse2D.Double(this.getX() , this.getY(), this.getHeigh(), this.getWidth());
	    g2d.setColor(Color.blue);
	    //If isVisible (check box is checked) draw the circle
	    if(this.getIVisible())	{ 
	    	g2d.fill(circle);}
	    else g2d.fillOval(this.getX(), this.getY(), 0, 0);  
    }
}
