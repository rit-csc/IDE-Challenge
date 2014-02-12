package edu.rit.cs.csc.recorder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.rit.cs.csc.recorder.features.Feature;
import edu.rit.cs.csc.recorder.gui.GUI;


public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//mouse options
		List<Feature> mouseOptions = new ArrayList<Feature>(4);
		mouseOptions.add(new Feature(Settings.RecordMouseClicksText, Settings.RecordMouseClicksTip, null));
		mouseOptions.add(new Feature(Settings.RecordMouseClicksDetailsText, Settings.RecordMouseClicksDetailsTip, null));
		mouseOptions.add(new Feature(Settings.RecordMouseMovementText, Settings.RecordMouseMovementTip, null));
		mouseOptions.add(new Feature(Settings.RecordMouseMovementDetailsText, Settings.RecordMouseMovementDetailsTip, null));
		
		//keyboard options
		List<Feature> keyboardOptions = new ArrayList<Feature>(2);
		keyboardOptions.add(new Feature(Settings.RecordKeysText, Settings.RecordKeysTip, null));
		keyboardOptions.add(new Feature(Settings.RecordKeyDetailsText, Settings.RecordKeyDetailsTip, null));
		
		HashMap<String, Collection<Feature>> options = new LinkedHashMap<String, Collection<Feature>>(2);
		options.put(Settings.RecordMouseTitle, mouseOptions);
		options.put(Settings.RecordKeysTitle, keyboardOptions);
		
		//TODO find standard Linux-type command line parser for Java
		//handle all configuable options by command line
		//cause them to change the feature's action's properites.  Ex: get(feature).enabled(value)
		
		
		//if GUI not disabled by commandline
		GUI gui = new GUI(options);
		//else check that everything was propertly configued and start recording or wait for the proper time and then record
		
		
		//TODO Mouse locations could be drawn as lines or as heatmaps
		//I thing everything would look interesting as heat maps, How to handle different screen resolutions for combining heatmaps?
		//	That might not be our problem if we export the data as CSV, then use MATLAB imaging processing scripts to combine and plot them as heatmaps - Know how Matlab data processing better than Java data processing libs
	}
	
}
