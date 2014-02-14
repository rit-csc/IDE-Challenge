package edu.rit.cs.csc.recorder.features;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;


public class Feature {
	
	//TODO make sure the getters never return null?
	//TODO this class might just turn into Action
	
	private final String id;
	private final Action action;
	
	public Feature(String id, String name, String tip, Action action) {
		if(id == null) {
			id = name;
		} else if(name == null) {
			name = id;
		}
		if(id == null) {
			throw new IllegalArgumentException("Both id and name cannot be null");
		}
		
		if(action == null) {
			action = new AbstractAction(name) {
				private static final long serialVersionUID = 669063589516987139L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Stubbed action for " + ((AbstractButton)e.getSource()).getText());
					System.out.println(this.getValue(SELECTED_KEY));
				}
			};
		}
		action.putValue(Action.NAME, name);
		action.putValue(Action.SHORT_DESCRIPTION, tip);
		action.putValue(Action.SELECTED_KEY, true);
		//TODO add shurtcut key
		
		this.id = id;
		this.action = action;
	}
	
	//TODO enable()/disable()?
	
	//--------------------------------------------------------------------------
	// Getters
	//--------------------------------------------------------------------------
	
	public String getId() {
		return id;
	}
	
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
