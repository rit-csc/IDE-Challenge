package edu.rit.cs.csc.recorder.features;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;


public class Feature {
	
	//TODO make sure the getters never return null?
	//TODO this class might just turn into Action
	
	//private final String name;
	//private final String tip;
	private final Action action;
	
	public Feature(String name, String tip, Action action) {
		
		if(action == null) {
			action = new AbstractAction(name) {
				private static final long serialVersionUID = 669063589516987139L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Stubbed action for " + ((AbstractButton)e.getSource()).getText());
				}
			};
		}
		action.putValue(Action.NAME, name);
		action.putValue(Action.SHORT_DESCRIPTION, tip);
		
		
		this.action = action;
	}
	
	//--------------------------------------------------------------------------
	// Getters
	//--------------------------------------------------------------------------
	
	public String getName() {
		return action.getValue(Action.NAME).toString();
	}
	
	public String getTip() {
		return action.getValue(Action.SHORT_DESCRIPTION).toString();
	}
	
	public Action getAction() {
		return action;
	}
	
}
