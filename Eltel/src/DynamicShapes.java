import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DynamicShapes extends JPanel {
	
	private List<ShapeObj> shapes = new ArrayList<>();
	
	//Create the canvas 
	public DynamicShapes(List<ShapeObj> shapes) {
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(100, 100));
        
        for(ShapeObj item : shapes){
        	String shape = item.getShape().toLowerCase();
        	if(shape.equals(Shape.CIRCLE.toString())) {
        		addCircle((Circle) item);
        	} else if (shape.equals(Shape.TRIANGLE.toString())) {
        		addTriangle((Triangle)item);
        	}else {addSquare((Square)item);}
    	}
     }
	
	/***
	 * paintComponent draws every shape on canvas
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Object s : shapes) {
            if (s instanceof Circle) {
                ((Circle) s).draw(g);
            } else if (s instanceof Triangle) {
                ((Triangle) s).draw(g);
            } else {
            	((Square) s).draw(g);            
            }
        }
    }
	
	public void addCircle(Circle c) {
		shapes.add(c);
        repaint();
    }

    public void addSquare(Square s) {
    	shapes.add(s);
        repaint();
    }
    
    public void addTriangle(Triangle t) {
    	shapes.add(t);
        repaint();
    }
}
