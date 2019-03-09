import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.Checkbox;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;

public class Eltel_GUI {

	private JFrame frame;
	private List<ShapeObj> shapes = new ArrayList<>();
	private int shapesCounter; 
	private int y;
	private int amount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Eltel_GUI window = new Eltel_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Eltel_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initShapes();
		shapesCounter = 0;
		y = 10;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		DynamicShapes dynamicShapes = new DynamicShapes(this.shapes);
		dynamicShapes.setBounds(155, 9, 265, 231);
		frame.getContentPane().add(dynamicShapes);
		
		createCheckBox();
	
    	
    	ActionListener taskPerformer = this.createActionListener();
        Timer timer = new Timer(5000, taskPerformer);
		
        //Add Start button
        JButton btnStart = new JButton("Start");
        //Add an action listener to Start button, open a dialog to the user to get the n last locations to save
        //start to move the selected entities
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String shapeAmount = JOptionPane.showInputDialog(null,
		        "How many shapes?", "Random Shapes...", JOptionPane.PLAIN_MESSAGE);
				amount = Integer.parseInt(shapeAmount);
				timer.start();
			}
		});
		

		btnStart.setBounds(12, 198, 97, 25);
		frame.getContentPane().add(btnStart);
		
		//Create a Stop button
		JButton btnStop = new JButton("Stop");
		//Add an action listener, stop the movement and export the n last locations to svc file
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				exportLocations();
			}
		});
		btnStop.setBounds(12, 226, 97, 25);
		frame.getContentPane().add(btnStop);
	}
	
	//Init entities from Json file, max 10.
	private void initShapes() {
		JSONParser parser = new JSONParser();
        JSONArray jsonArray;
		try {
			//init entities from Json
			jsonArray = (JSONArray) parser.parse(new FileReader(
			        "entityData.json"));
	        for (Object o : jsonArray) {
	        	//Max number of supported entities is 10
	        	if(shapesCounter == 10) {
	        		break;
	        	}
	            ShapeObj shape = buildShape(o);
	            shapes.add(shape);
	            shapesCounter++;
	        }
	        
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Export n last locations of selected entities to svc file
	private void exportLocations() {
		try (PrintWriter writer = new PrintWriter(new File("locations.csv"))) {
			StringBuilder sb = new StringBuilder();
		      sb.append("ID,");
		      sb.append("Name,");
		      sb.append("Location,");
		      sb.append('\n');
		    //Get all selected entities
			for (ShapeObj shape : shapes) {
				if( shape != null && shape.getIVisible()) {
					//For all selected entities get n last locations
					List <String> pos = shape.getPositions(amount);
					for(String loc : pos) {
						if(loc != null) {
							sb.append("e" + shape.getId());
							sb.append(',');
							sb.append(shape.getName());
							sb.append(',');
							sb.append(loc);
							sb.append('\n');
						}
					}
					
				}
			}
		    writer.write(sb.toString());

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		
	}
	
	//Create a check box for all entities
	private void createCheckBox() {
		for (ShapeObj s : this.shapes) {
			String name = "eID" + s.getId() +"_" + s.getName();
			JCheckBox checkBox = new JCheckBox(name);
			checkBox.setName(name);
			
			//Add an action listener, when we check the check box box the entity will be visible
			checkBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index =shapes.indexOf(new ShapeObj(0, s.getName(), "", "", "", 0,0) {});
					if(index > -1) {
					  ShapeObj so =	shapes.get(index);
					  if(so != null) {
						  so.setIsVisible(!so.getIVisible());
						  frame.repaint();
						  
					  }
					}
				}
			});
			checkBox.setBounds(8, y, 113, 25);
			frame.getContentPane().add(checkBox);
			y += 30;
		}
	}
	
	//Create an action for Start button, move selected entities to a distance of 5 from current location at 5 sec intervals.
	//The entity cannot go back to previous location on next step
	private ActionListener createActionListener() {
		return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	for(ShapeObj item : shapes){
        			if(item.getIVisible()) {
        				Random rand = new Random(); 
        				int value = rand.nextInt(4);
        				switch (value) {
        				case 1:
        					//Check if previous move wasn't dawn
        					if(item.getPrevMove() != 2) {
        						item.setPrevMove(value);
        						item.moveUp();
        					}
        					else {
        						item.setPrevMove(3);
        						item.moveLeft();
        					}
        					break;
        				case 2:
        					//Check if previous move wasn't up
        					if(item.getPrevMove() != 1) {
        						item.setPrevMove(value);
        						item.moveDown();
        					}
        					else {
        						item.setPrevMove(3);
        						item.moveLeft();
        					}
        					break;
        				case 3:
        					//Check if previous move wasn't right
        					if(item.getPrevMove() != 4) {
        						item.setPrevMove(value);
        						item.moveLeft();
        					}
        					else {
        						item.setPrevMove(1);
        						item.moveDown();
        					}
        					break;
        				case 4:
        					//Check if previous move wasn't left
        					if(item.getPrevMove() != 3) {
        						item.setPrevMove(value);
        						item.moveRight();
        					}
        					else {
        						item.setPrevMove(1);
        						item.moveDown();
        					}
        					break;
        				default:
        					break;
        				}
        			}
        		}
            	frame.repaint();
            }
        };
	}
	
	//Build ShapeObj from Json
	private ShapeObj buildShape(Object o) {
		if (o != null) {
			JSONObject person = (JSONObject) o;

            String id = (String) person.get("entity_ID");
            int entity_ID = Integer.parseInt(id);
            
            String name = (String) person.get("name");

            String color = (String) person.get("color");

            String size = (String) person.get("size");
            
            String shape = (String) person.get("shape");
            
            String x = (String) person.get("X");
            int entity_x = Integer.parseInt(x);
            
            String y = (String) person.get("Y");
            int entity_y = Integer.parseInt(y);
            
            if(shape.equals(Shape.CIRCLE.toString())){
            	return new Circle(entity_ID, name, shape, color, size, entity_x, entity_y );
            } else if (shape.equals(Shape.TRIANGLE.toString())) {
            	return new Triangle(entity_ID, name, shape, color, size, entity_x, entity_y );
            }else {
            	return new Square(entity_ID, name, shape, color, size, entity_x, entity_y );
            }
		}
		return null;
		
	}
}
