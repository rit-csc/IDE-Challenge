package edu.rit.cs.csc.recorder.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import edu.rit.cs.csc.recorder.Settings;
import edu.rit.cs.csc.recorder.features.Feature;

//TODO change things to use Action and put all gui operations on the event dispatching thread

public class GUI {
	
	private final Set<Action> actions = new HashSet<Action>();
	
	//TODO document - Feature collection cannot be null but the Map can be null
	public GUI(Map<String, Collection<Feature>> options) {
		if(options == null) {
			options = Collections.emptyMap();
		}
		
		JPanel middle = new JPanel();
		//middle.setLayout(new BoxLayout(middle, BoxLayout.LINE_AXIS));
		middle.setLayout(new GridLayout(1,1));
		
		for(String title: options.keySet()) {
			JPanel group = new JPanel();
			group.setBorder(BorderFactory.createTitledBorder(title));
			group.setLayout(new BoxLayout(group, BoxLayout.PAGE_AXIS));
			
			for(Feature f: options.get(title)) {
				JCheckBox box = new JCheckBox();
				box.setAction(f.getAction());
				actions.add(box.getAction());
				group.add(box);
			}
			
			middle.add(group);
		}
		
		
		/*
		//----------------------------------------------------------------------
		// Keyboard parts
		//----------------------------------------------------------------------
		
		JCheckBox keyBox = new JCheckBox(Settings.RecordKeysText);
		keyBox.setToolTipText(Settings.RecordKeysTip);
		
		JCheckBox keyDetailsBox = new JCheckBox(Settings.RecordKeyDetailsText);
		keyDetailsBox.setToolTipText(Settings.RecordKeyDetailsTip);
		
		
		JPanel keyboardPanel = new JPanel();
		keyboardPanel.setBorder(BorderFactory.createTitledBorder(Settings.RecordKeysTitle));
		keyboardPanel.setLayout(new BoxLayout(keyboardPanel, BoxLayout.PAGE_AXIS));
		keyboardPanel.add(keyBox);
		keyboardPanel.add(keyDetailsBox);
		
		options.add(keyBox);
		options.add(keyDetailsBox);
		
		//----------------------------------------------------------------------
		// Mouse parts
		//----------------------------------------------------------------------
		
		JCheckBox movementBox = new JCheckBox(Settings.RecordMouseMovementText);
		movementBox.setToolTipText(Settings.RecordMouseMovementTip);
		
		JCheckBox movementDetailsBox = new JCheckBox(Settings.RecordMouseMovementDetailsText);
		movementDetailsBox.setToolTipText(Settings.RecordMouseMovementDetailsTip);
		
		JCheckBox clickBox = new JCheckBox(Settings.RecordMouseClicksText);
		clickBox.setToolTipText(Settings.RecordMouseClicksTip);
		
		JCheckBox clickDetailsBox = new JCheckBox(Settings.RecordMouseClicksDetailsText);
		clickDetailsBox.setToolTipText(Settings.RecordMouseClicksDetailsTip);
		
		JPanel mousePanel = new JPanel();
		mousePanel.setBorder(BorderFactory.createTitledBorder(Settings.RecordMouseTitle));
		mousePanel.setLayout(new BoxLayout(mousePanel, BoxLayout.PAGE_AXIS));
		mousePanel.add(movementBox);
		mousePanel.add(movementDetailsBox);
		mousePanel.add(clickBox);
		mousePanel.add(clickDetailsBox);
		
		options.add(movementBox);
		options.add(movementDetailsBox);
		options.add(clickBox);
		options.add(clickDetailsBox);
		 */
		
		//----------------------------------------------------------------------
		// Bottom parts
		//----------------------------------------------------------------------
		
		//TOP - location of where to save stats + browse button
		
		//Left justified - start time selection?
		//Left justified - end time selection or length of recording?
		
		//Center justified? - start button
		//Right justified - countdown until start
		
		JButton start = new JButton(Settings.StartText);
		start.setToolTipText(Settings.StartTip);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				String text = b.getText();
				
				if(Settings.StartText.equals(text)) {
					//TODO start recording or the timer to start recording
					
					//lock button states so the user knows what's being recorded
					for(Action ab: actions) {
						ab.setEnabled(false);
					}
					b.setText(Settings.StopText);
					b.setToolTipText(Settings.StopTip);
				} else if(Settings.StopText.equals(text)) {
					//TODO stop recording or stop the timer that starts recording
					
					//unlock button states - FIXME: if a button was dissabled for some other reason, this will still renable it.  This is a potential bug.
					for(Action ab: actions) {
						ab.setEnabled(true);
					}
					b.setText(Settings.StartText);
					b.setToolTipText(Settings.StartTip);
				} else {
					System.err.println("Unmatched button text: " + text);
				}
			}
		});
		
		JPanel bottomPanel = new JPanel();
		SpringLayout spring = new SpringLayout();
		bottomPanel.setLayout(spring);
		bottomPanel.add(start);
		spring.putConstraint(SpringLayout.HORIZONTAL_CENTER, start, 0, SpringLayout.HORIZONTAL_CENTER, bottomPanel);
		spring.putConstraint(SpringLayout.VERTICAL_CENTER, start, 0, SpringLayout.VERTICAL_CENTER, bottomPanel);
		bottomPanel.setPreferredSize(new Dimension(30, 50));
		spring.layoutContainer(bottomPanel);
		
		//----------------------------------------------------------------------
		// Main window
		//----------------------------------------------------------------------
		
		final JFrame f = new JFrame(Settings.title);
		Container p = f.getContentPane();
		p.setLayout(new BorderLayout());
		//p.add(keyboardPanel, BorderLayout.WEST);
		//p.add(mousePanel, BorderLayout.EAST);
		p.add(middle, BorderLayout.CENTER);
		p.add(bottomPanel, BorderLayout.SOUTH);
		
		f.pack();
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop(Settings.OnTop);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//press ESC to close the program
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {
			@Override
			public boolean postProcessKeyEvent(KeyEvent e) {
				System.out.println("KeyPressed: " + e.getKeyChar() + ", " + e.getKeyCode());
				if( e.getKeyCode() != KeyEvent.VK_ESCAPE) {
					return false;
				}
				
				WindowEvent windowClosing = new WindowEvent(f, WindowEvent.WINDOW_CLOSING);
				f.dispatchEvent(windowClosing);
				return true;
			}
		});
		
		//TODO allow tray icon
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				f.setVisible(true);
			}
		});
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI gui = new GUI(null);
	}
	
}
