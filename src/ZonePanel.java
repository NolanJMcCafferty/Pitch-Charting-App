import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ZonePanel extends JPanel implements MouseListener{
	
	private static final int ZONE_HEIGHT = 300;
	private static final int ZONE_WIDTH = 250;
	private static final int ZONE_LEFT = 175;
	private static final int ZONE_TOP = 150;
	private static final int BALL_RADIUS = 40;
	private static final int BALL_OFFSET = BALL_RADIUS / 2;
	private static final int BORDER_WIDTH = 3;
	private static final int fontSize = 45;
	private static final int[] plateCornersX = {175, 175, 300, 425, 425, 175}; 
	private static final int[] plateCornersY = {550, 570, 600, 570, 550, 550}; 
	private ArrayList<Pitch> pitches;
	private String[] csvHeaders = {"pitch#", "type", "locationX", "locationY", "velocity", "strike", "swing"};
	boolean locationChosen = false;
	private int x, y;
	
	public ZonePanel() {
		addMouseListener(this);
		pitches = new ArrayList<Pitch>();
	}
	public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      Graphics2D canvas = (Graphics2D) g;  
	      canvas.setStroke(new BasicStroke(BORDER_WIDTH));
	      canvas.setColor(Color.black);
	      canvas.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
	      
	      canvas.drawRect(ZONE_LEFT, ZONE_TOP, ZONE_WIDTH, ZONE_HEIGHT);    
	      canvas.drawPolygon(plateCornersX, plateCornersY, plateCornersX.length); 
	      
	      if (locationChosen) {
    		  canvas.setColor(Color.lightGray);
 	    	  canvas.fillOval(x - BALL_OFFSET, y - BALL_OFFSET, BALL_RADIUS, BALL_RADIUS);
	      }
	      
	      for (Pitch pitch : pitches) {
	    	  Color pitchColor = pitch.isStrike() ? Color.green : Color.red;
	    	  canvas.setColor(pitchColor);
	    	  canvas.drawString(pitch.getPitchType(), pitch.getX() - fontSize / 4, pitch.getY() + fontSize / 3);
	    	  if (pitch.swing()) {
	    		  canvas.drawOval(pitch.getX() - BALL_OFFSET, pitch.getY() - BALL_OFFSET, BALL_RADIUS, BALL_RADIUS);
	    	  }
	      }
	     
	   } 
	
	
	
	public void mouseClicked(MouseEvent e) {
	    locationChosen = true;
	    x = e.getX();
	    y = e.getY();
	    repaint();
	}
	
	public void addPitch(int pitchNo, int v, int type, boolean isStrike, boolean swing) {
		pitches.add(new Pitch(pitchNo, x, y, v, type, isStrike, swing));
		locationChosen = false;
		repaint();
	}
	
	public void resetZone() {
		pitches = new ArrayList<Pitch>();
		locationChosen = false;
		repaint();
	}
	
	public void saveZone(String pitcherName) {
		// get today's date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");  
		String date = dtf.format(LocalDateTime.now());  
	 	String filename = String.join(" ", pitcherName, date).replaceAll(" ", "_");
	 	new File("../" + filename).mkdir();
		this.saveZoneImage(filename);
		this.savePitchesCSV(filename);
	 	
	}
	
	public void saveZoneImage(String filename) {
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		this.paint(g);
		 try {
		        ImageIO.write(image, "png", new File("../" + filename + "/" + filename));
		    } catch (IOException e) {
		        System.out.println("Error saving zone image");
		   }
	}
	
	public void savePitchesCSV(String filename) {
		try {
			PrintWriter outputFile = new PrintWriter(new FileWriter("../" + filename + "/" + filename + ".csv"));
			outputFile.println(String.join(",", csvHeaders));
			for (Pitch pitch : pitches) {
				outputFile.println(pitch.csvRow());
			}
			outputFile.close();
		} catch (IOException e) {
			System.out.println("CSV file save failed" + e);
		}
		
	}
	
	public boolean isLocationChosen() {
		return locationChosen;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
