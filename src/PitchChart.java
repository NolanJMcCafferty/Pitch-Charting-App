import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

public class PitchChart implements ActionListener{
	
	private static final int FRAME_HEIGHT = 750;
	private static final int FRAME_WIDTH = 900;
	private static final int PANEL_WIDTH = 250;
	private static final int PITCH_LIST_HEIGHT = 350;
	private static final int PITCH_LIST_WIDTH = PANEL_WIDTH - 20;
	private static final int PITCH_LIST_PANEL_HEIGHT = PITCH_LIST_HEIGHT + 35;
	private static final int SUBMIT_PANEL_HEIGHT = 250;
	private static final int FONT_SIZE = 12;
	private static final String INFO_DIV = "  |  ";
	private static final String[] PITCH_LIST = {"FA", "CU", "SL", "CH", "FS", "FC", "FT", "KN"};
	private static final Map<String, Integer> PITCH_MAP = Map.of(
			"FA", 1,
			"CU", 2,
			"SL", 3,
			"CH", 4,
			"FS", 5,
			"FC", 1,
			"FT", 1,
			"KN", 5
	);
	private ZonePanel zone;
	private int pitchCount = 0;
	private Vector<String> pitches = new Vector<String>();
	private JList<String> pitchList = new JList<String>(pitches);
	private JTextField pitcherName, startInning, endInning, velocityField;
	private JComboBox<String> pitchTypeField;
	private JRadioButton strike, swing;
	private JLabel pitchesLabel, submitLabel;
	
	public PitchChart() {
        JFrame frame = new JFrame("Pitch Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // center panel for strike zone display
        zone = new ZonePanel();

        // right panel for pitch chart info
        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(PANEL_WIDTH, FRAME_HEIGHT));
        infoPanel.setBackground(Color.lightGray);

        // top panel for pitcher name
		JPanel namePanel = new JPanel();
		namePanel.setBackground(Color.lightGray);

        JLabel nameLabel = new JLabel("Name:");
        pitcherName = new JTextField(12);
        namePanel.add(nameLabel);
        namePanel.add(pitcherName);
        namePanel.setBorder(new EmptyBorder(20, 10, 0, 10));

        // panel for innings pitched
		JPanel inningsPanel = new JPanel(new GridLayout(2,1));
		inningsPanel.setBackground(Color.lightGray);
		JLabel inningsLabel = new JLabel("Innings");
		inningsLabel.setHorizontalAlignment(JLabel.CENTER);
		inningsPanel.add(inningsLabel);

		JPanel inningsIntervalPanel = new JPanel();
		inningsIntervalPanel.setBackground(Color.lightGray);
		JLabel startLabel = new JLabel("start:");
		JLabel endLabel = new JLabel("  end:");
		startInning = new JTextField(3);
		endInning = new JTextField(3);

		inningsIntervalPanel.add(startLabel);
		inningsIntervalPanel.add(startInning);
		inningsIntervalPanel.add(endLabel);
		inningsIntervalPanel.add(endInning);
		inningsPanel.add(inningsIntervalPanel);

        // center panel for pitch list
        JPanel pitchListPanel = new JPanel();
        pitchListPanel.setBackground(Color.lightGray);
        pitchListPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        pitchListPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PITCH_LIST_PANEL_HEIGHT));
        
        pitchesLabel = new JLabel("Pitches: " + pitchCount);
        pitchListPanel.add(pitchesLabel, BorderLayout.NORTH);
        
        JScrollPane pitchesPane = new JScrollPane(pitchList);
        pitchesPane.setPreferredSize(new Dimension(PITCH_LIST_WIDTH, PITCH_LIST_HEIGHT));
        pitchListPanel.add(pitchesPane, BorderLayout.SOUTH);
        pitchList.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
        
        
        // panel to add new pitch
        JPanel addPitchPanel = new JPanel();
        addPitchPanel.setBackground(Color.lightGray);
        addPitchPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_WIDTH / 2));

        
        // pitch type field
        JLabel pitchTypeLabel = new JLabel("Type:");
        pitchTypeField = new JComboBox<String>(PITCH_LIST);
        pitchTypeField.addActionListener(this);
        addPitchPanel.add(pitchTypeLabel);
        addPitchPanel.add(pitchTypeField);
        
        // velocity field
        JLabel velocityLabel = new JLabel("Velocity:");
        velocityField = new JTextField(2);
        velocityField.addActionListener(this);
        addPitchPanel.add(velocityLabel);
        addPitchPanel.add(velocityField);
        
        JPanel pitchInfoButtons = new JPanel(new GridLayout(2,2));
        pitchInfoButtons.setBackground(Color.lightGray);   
        pitchInfoButtons.setBorder(new EmptyBorder(0, 5, 5, 5));
        
        // strike or ball radio buttons
        strike = new JRadioButton("Strike", true);
        JRadioButton ball = new JRadioButton("Ball");
        ButtonGroup strikeOrBall = new ButtonGroup();
        strikeOrBall.add(strike);
        strikeOrBall.add(ball);
        pitchInfoButtons.add(strike, BorderLayout.SOUTH);
        pitchInfoButtons.add(ball, BorderLayout.SOUTH);
        addPitchPanel.add(pitchInfoButtons);
        
        // take or swing radio buttons
        JRadioButton take = new JRadioButton("Take", true);
        swing = new JRadioButton("Swing");
        ButtonGroup takeOrSwing = new ButtonGroup();
        takeOrSwing.add(take);
        takeOrSwing.add(swing);
        pitchInfoButtons.add(take);
        pitchInfoButtons.add(swing);
        
        // add pitch button
        JButton addPitch = new JButton("Add Pitch");
        addPitch.addActionListener(this);
        addPitchPanel.add(addPitch);
        
        // panel for submit and reset buttons
        JPanel submitPanel = new JPanel();
        submitPanel.setPreferredSize(new Dimension(PANEL_WIDTH, SUBMIT_PANEL_HEIGHT));
        submitPanel.setBackground(Color.lightGray);
        
        JButton submit = new JButton("Submit");
        JButton reset = new JButton("Reset");
        reset.addActionListener(this);
        submit.addActionListener(this);
        submitPanel.add(submit);
        submitPanel.add(reset);
        
        submitLabel = new JLabel();
        submitPanel.add(submitLabel, BorderLayout.SOUTH);
        
        // add components to pitch info panel
        infoPanel.add(namePanel);
        infoPanel.add(inningsPanel);
        infoPanel.add(pitchListPanel);
        infoPanel.add(addPitchPanel);
        infoPanel.add(submitPanel);

        // add components to the frame.
        frame.getContentPane().add(BorderLayout.CENTER, zone);
        frame.getContentPane().add(BorderLayout.EAST, infoPanel);
        frame.setVisible(true);
	}
	
	/* create string of pitch characteristics
	 * and add it to the pitch list
	 */
	public void addPitchToList(int velocity, boolean isStrike, int pitchNum) {
		String pitchCall = isStrike ? "strike" : "ball";
		String pitchSpacing = pitchCount > 9 ? ". " : ".  ";
		String pitchType =  (String) pitchTypeField.getSelectedItem();
		String pitchInfo = pitchCount + pitchSpacing + pitchType + INFO_DIV + velocity + INFO_DIV + pitchCall;
		pitches.add(0, pitchInfo);
		pitchList.setListData(pitches);
	}

	/* show pop-up window to confirm that the user
 	 * wants to reset the pitch chart
	 */
	public boolean confirmReset() {
		Integer result = JOptionPane.showConfirmDialog(
				null,
				"Are you sure you want to reset this chart?"
		);
		return result == 0;
	}

	/* reset the pitch chart by removing the pitch
	 * list and the pitches on the zone
	 */
	public void resetChart() {
		pitchCount = 0;
		pitches = new Vector<String>();
		pitchList.setListData(pitches);	
		pitcherName.setText("");
		startInning.setText("");
		endInning.setText("");
		zone.resetZone();
	}
	
	/* method to check if a pitch can be added
	 * checks that there is a velocity, pitch type, and a
	 * location is chosen
	 */
	public boolean pitchCanBeAdded() {
		return velocityField.getText().matches("\\d+") &&
				zone.isLocationChosen();
	}

	/* method to check if the start inning and
	 * end inning fields are empty
	 */
	public boolean missingInnings() {
		return startInning.getText().isEmpty() || endInning.getText().isEmpty();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Reset") && this.confirmReset()) {
			this.resetChart();
			submitLabel.setText("");
		}
		else if (e.getActionCommand().equals("Submit") && !this.missingInnings()) {
			String[] split_name = pitcherName.getText().split(" ");
			String last_name = split_name[split_name.length - 1];

			String innings_pitched = startInning.getText() + "-" + endInning.getText();
			zone.saveZone(last_name + "_" + innings_pitched);
			submitLabel.setText("chart submitted");
		}
		else if (e.getActionCommand().equals("Add Pitch") && this.pitchCanBeAdded()) {
			//increase pitch count
			pitchCount++;
			
			int velocity = Integer.parseInt(velocityField.getText());
			boolean isStrike = strike.isSelected();
			boolean isSwing = swing.isSelected();
			int pitchType = PITCH_MAP.get((String) pitchTypeField.getSelectedItem());
			
			// add pitch to zone
			zone.addPitch(pitchCount, velocity, pitchType, isStrike, isSwing);	
			
			// add pitch to pitch list
			this.addPitchToList(velocity, isStrike, pitchType);
		}	
		
		pitchesLabel.setText("Pitches: " + pitchCount);
	}
	
	public static void main(String[] args) {
		PitchChart chart = new PitchChart();
	}
}
