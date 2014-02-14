package edu.rit.cs.csc.recorder.features;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;


@NonNullByDefault public class Feature {
	
	//TODO this class might just turn into Action
	
	private final String id;
	private final Action action;
	
	public Feature(@Nullable String id, String name, String tip, @Nullable Action action) throws IllegalArgumentException {
		if(name.isEmpty()) {
			throw new IllegalArgumentException("The given name cannot be empty.");
		}
		if(id == null) {
			id = name.substring(0, 1).toLowerCase();
		}
		this.id = id;
		
		if(action == null) {
			action = new AbstractAction(name) {
				private static final long serialVersionUID = 669063589516987139L;
				
				private boolean lastState = false; //true if we were selected at the end of the last actionPerformed() call, else false
				
				@Override
				public void actionPerformed(@Nullable ActionEvent e) {
					if(e == null) {
						return;
					} else if(e.getSource() == null) {
						return;
					}
					System.out.println(e.getActionCommand());
					
					//the accelerator key doesn't change the JCheckBox's checked/selected state so we need to manually update it
					boolean currentState = ((Boolean)this.getValue(Action.SELECTED_KEY)).booleanValue();
					if(lastState == currentState) {
						putValue(Action.SELECTED_KEY, Boolean.valueOf(!currentState));
					}
					lastState = ((Boolean)getValue(Action.SELECTED_KEY)).booleanValue();
				}
			};
		}
		action.putValue(Action.NAME, name);
		action.putValue(Action.SHORT_DESCRIPTION, tip);
		action.putValue(Action.SELECTED_KEY, Boolean.FALSE);
		
		//add a shortcut key for this action, matching it's single character command line option
		if(this.id.length() == 1) {
			int location = name.toLowerCase().indexOf(this.id);
			if(location >= 0) {
				action.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.valueOf(location));
				action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(this.id.toUpperCase()));
			}
		}
		
		this.action = action;
	}
	
	//TODO enable()/disable()?
	
	//--------------------------------------------------------------------------
	// Getters
	//--------------------------------------------------------------------------
	
	public String getId() {
		final String id = this.id;
		if(id == null ) {
			throw new IllegalStateException("Id cannot be null");
		}
		
		return id;
	}
	
	public String getName() {
		final String name = action.getValue(Action.NAME).toString();
		if(name == null ) {
			throw new IllegalStateException("Name cannot be null");
		}
		
		return name;
	}
	
	public String getTip() {
		final String tip = action.getValue(Action.SHORT_DESCRIPTION).toString();
		if(tip == null) {
			throw new IllegalStateException("Tip cannot be null");
		}
		
		return tip;
	}
	
	public boolean isActive() {
		Object o = getAction().getValue(Action.SELECTED_KEY);
		if(o == null) {
			throw new IllegalStateException("Selected state cannot be null");
		}
		
		return ((Boolean)o).booleanValue();
	}
	
	public Action getAction() {
		final Action a = action; //odd code to get through static-NonNull analysis
		if(a == null) {
			throw new IllegalStateException("Action cannot be null");
		}
		
		return a;
	}
	
}
