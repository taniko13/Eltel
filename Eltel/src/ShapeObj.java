import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/***
 * Abstract class for shapes
 * @author tania
 *Fields: id, name, shape, color, size, coordinates(x, y), isVisible, heigh, width, prevMove, locations
 */
public abstract class ShapeObj {
	private int entity_ID;
	private String name;
	private String shape;
	private String color;
	private String size;
	private int x;
	private int y;
	private boolean isVisible;
	//Calculate via size
	private int height;
	private int width;
	//Save the previous move
	private int prevMove;
	//save all locations for current shape
	private List<String> locations;
	
	//Constructors
	public ShapeObj() {}
	
	public ShapeObj(int id, String name, String shape, String color, String size, int x, int y) {
		this.entity_ID = id;
		this.name = name;
		this.shape = shape;
		this.color = color;
		this.size = size;
		this.x = x;
		this.y = y;
		this.isVisible = false;
		this.prevMove = -1;
		this.locations = new ArrayList<String>();
		//Calculate the height and width 
		if(size.equals("small")) {
			this.height  = 10;
			this.width = 10;
		}
		else if(size.equals("medium")) {
			this.height = 20;
			this.width = 20;
		}
		else {
			this.height = 30;
			this.width = 30;
		}
	}
	
	//Getters
	public int getId() {
		return this.entity_ID;
	}
	public String getName() {
		return this.name;
	}
	public String getShape() {
		return this.shape;
	}
	public String getColor() {
		return this.color;
	}
	public String getSize() {
		return this.size;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public boolean getIVisible() {
		return this.isVisible;
	}
	
	public int getHeigh() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getPrevMove() {
		return this.prevMove;
	}
	
	//Get n last locations
	public List<String> getPositions(int n) {
		int size =locations.size();
		if(n < size) {
			return this.locations.subList(size-n, size);
		}
		return locations;
	}
	
	//Setter
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
		
	}
	
	public void setPosition() {
		this.locations.add(this.getLocation());
	}
	
	public void setPrevMove(int prev) {
		this.prevMove = prev;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	//Move methods
	public void moveUp() {
		if(this.y > 5) {
			this.y -= 5;
			this.setPosition();
		}
	}
	
	public void moveDown() {
		if(this.y < 95) {
			this.y += 5;
			this.setPosition();
		}
	}
	
	public void moveLeft() {
		if(this.x >5) {
			this.x -= 5;
			this.setPosition();
		}
	}
	
	public void moveRight() {
		if(this.x < 95) {
			this.x += 5;
			this.setPosition();
		}
	}
	
	//Get location(coordinates x,y) as String
	public String getLocation() {
		return this.x + "," + this.y;
	}
	
	
	@Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of ShapeObj or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof ShapeObj)) { 
            return false; 
        } 
          
        // typecast o to ShapeObj so that we can compare data members  
        ShapeObj c = (ShapeObj) o; 
          
        // Compare the data members and return accordingly  
        return this.name == c.getName(); 
    } 
	
	//Convert the color from string to Color object
	protected Color getColorObg() {
		switch (this.color) {
		case "red":
			return Color.RED;
		case "blue":
			return Color.BLUE;
		default:
			return Color.GREEN;
		}
	}
}
