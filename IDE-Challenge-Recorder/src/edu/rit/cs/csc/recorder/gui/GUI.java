package edu.rit.cs.csc.recorder.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import edu.rit.cs.csc.recorder.Settings;
import edu.rit.cs.csc.recorder.features.Feature;

//TODO change things to use Action and put all gui operations on the event dispatching thread

@NonNullByDefault public class GUI {
	
	private final Set<Action> actions = new HashSet<Action>();
	
	//TODO document - Feature collection cannot be null but the Map can be null
	public GUI(Map<String, Collection<Feature>> options) {
		
		//----------------------------------------------------------------------
		// Option groups
		//----------------------------------------------------------------------
		
		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(1,1));
		
		for(String title: options.keySet()) {
			JPanel group = new JPanel();
			group.setBorder(BorderFactory.createTitledBorder(title));
			group.setLayout(new BoxLayout(group, BoxLayout.PAGE_AXIS));
			
			for(Feature f: options.get(title)) {
				Action action = f.getAction();
				JCheckBox box = new JCheckBox();
				box.setAction(action);
				box.getActionMap().put("shortcut", action);
				box.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke)action.getValue(Action.ACCELERATOR_KEY), "shortcut");
				actions.add(box.getAction());
				group.add(box);
			}
			
			middle.add(group);
		}
		
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
			public void actionPerformed(@Nullable ActionEvent e) {
				if(e == null) {
					return;
				}
				
				JButton b = (JButton)e.getSource();
				String text = b.getText();
				
				if(Settings.StartText.equals(text)) {
					//TODO start recording or the timer to start recording
					
					//lock button states so the user knows what's being recorded  //TODO do we want them to be able to stop recording something if it's taking up too much CPU/memory?
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
		
		final JFrame f = new JFrame(Settings.Title);
		Container p = f.getContentPane();
		p.setLayout(new BorderLayout());
		p.add(middle, BorderLayout.CENTER);
		p.add(bottomPanel, BorderLayout.SOUTH);
		
		f.setAlwaysOnTop(Settings.OnTop);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//center the window
		f.pack();
		if(GraphicsEnvironment.isHeadless()) {
			f.setLocationRelativeTo(null);
		} else {
			Point placement = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			int newX = placement.x - (f.getWidth() / 2);
			int newY = placement.y - (f.getHeight() / 2);
			if(newX >= 0 && newY >= 0) {
				f.setLocation(newX, newY);
			}
		}
		
		//press ESC to close and exit the program
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {
			@Override
			public boolean postProcessKeyEvent(@Nullable KeyEvent e) {
				if( e != null && e.getKeyCode() != KeyEvent.VK_ESCAPE) {
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
	
}
