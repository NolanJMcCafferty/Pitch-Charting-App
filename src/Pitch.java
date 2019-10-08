
public class Pitch {
	
	private int pitchNo, x, y, velocity, pitchType;
	private boolean isStrike, swing;
	
	public Pitch(int pitchNo, int x, int y, int velocity, int pitchType, boolean isStrike, boolean swing) {
		this.pitchNo = pitchNo;
		this.x = x;
		this.y = y;
		this.velocity = velocity;
		this.pitchType = pitchType;
		this.isStrike = isStrike;
		this.swing = swing;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getVelocity() {
		return velocity;
	}
	
	public String getPitchType() {
		return Integer.toString(pitchType);
	}
	
	public boolean isStrike() {
		return isStrike;
	}
	
	public boolean swing() {
		return swing;
	}
	
	/* method to output the pitch 
	 * as a row in a csv file
	 */
	public String csvRow() {
		return String.join(
			",", 
			Integer.toString(pitchNo),
			Integer.toString(pitchType),
			Integer.toString(x), 
			Integer.toString(y), 
			Integer.toString(velocity),
			Boolean.toString(isStrike),
			Boolean.toString(swing)
		);
	}
}
