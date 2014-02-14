package edu.rit.cs.csc.recorder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.Action;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import edu.rit.cs.csc.recorder.features.Feature;
import edu.rit.cs.csc.recorder.gui.GUI;


public class Main {
	
	
	public static Option buildOption(Feature f) {
		if( f == null) {
			throw new IllegalArgumentException("The given feature cannot be null");
		}
		
		return new Option(f.getId(), f.getName(), false, f.getTip());
	}
	
	//TODO subclass our LinkedHashMap to provide an iterator so we don't need double loops every time we want to iterator over every Feature
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//----------------------------------------------------------------------
		// Register supported features/options
		//----------------------------------------------------------------------
		//TODO make the command line ids audo-generated or put into Settings.  Also programmically ensure they're all unique as they override each other if they aren't unique.
		
		//mouse options
		List<Feature> mouseOptions = new ArrayList<Feature>(4);
		mouseOptions.add(new Feature("b", Settings.RecordMouseClicksText, Settings.RecordMouseClicksTip, null));
		mouseOptions.add(new Feature("p", Settings.RecordMouseClicksDetailsText, Settings.RecordMouseClicksDetailsTip, null));
		mouseOptions.add(new Feature("d", Settings.RecordMouseMovementText, Settings.RecordMouseMovementTip, null));
		mouseOptions.add(new Feature("m", Settings.RecordMouseMovementDetailsText, Settings.RecordMouseMovementDetailsTip, null));
		
		//keyboard options
		List<Feature> keyboardOptions = new ArrayList<Feature>(2);
		keyboardOptions.add(new Feature("k", Settings.RecordKeysText, Settings.RecordKeysTip, null));
		keyboardOptions.add(new Feature("c", Settings.RecordKeyDetailsText, Settings.RecordKeyDetailsTip, null));
		
		HashMap<String, Collection<Feature>> options = new LinkedHashMap<String, Collection<Feature>>(2);
		options.put(Settings.RecordMouseTitle, mouseOptions);
		options.put(Settings.RecordKeysTitle, keyboardOptions);
		
		//non-GUI options
		Option helpOption = new Option("h", "Help", false, "Displays this help text and exits");
		Option versionOption = new Option("v", "Version", false, "Displays version information and exits");
		
		//----------------------------------------------------------------------
		// Configure the features/options
		//----------------------------------------------------------------------
		
		Options clOptions = new Options();
		clOptions.addOption(helpOption);
		clOptions.addOption(versionOption);
		for(String group: options.keySet()) {
			for(Feature f: options.get(group)) {
				clOptions.addOption(buildOption(f));
			}
		}
		
		//read the parameters
		HelpFormatter help = new HelpFormatter();
		CommandLineParser parser = new PosixParser(); //PosixParser handles combining options: "ls -a -l" as "ls -al"
		CommandLine cmd = null;
		try {
			cmd = parser.parse(clOptions, args);
		} catch(ParseException e) {
			System.err.println("Error parsing command line options: " + e.getLocalizedMessage());
			help.printHelp(Settings.RunStatement, clOptions, true);
			System.exit(1);
		}
		
		//handle the exit cases
		if(cmd == null) {
			throw new IllegalStateException("Command line parse is null");
		}
		if(cmd.hasOption(helpOption.getOpt()) || cmd.hasOption(versionOption.getOpt())) {
			if(cmd.hasOption(versionOption.getOpt())) {
				System.out.println(Settings.Title);
			}
			if(cmd.hasOption(helpOption.getOpt())) {
				help.printHelp(Settings.RunStatement, clOptions, true);
			}
			
			System.exit(0);
		}
		
		//configure the features
		for(String group: options.keySet()) {
			for(Feature f: options.get(group)) {
				f.getAction().putValue(Action.SELECTED_KEY, cmd.hasOption(f.getId()));
			}
		}
		
		//----------------------------------------------------------------------
		// Start the main program
		//----------------------------------------------------------------------
		
		//if GUI not disabled by commandline
		GUI gui = new GUI(options);
		//else check that everything was propertly configued and start recording or wait for the proper time and then record
		
		
		//TODO Mouse locations could be drawn as lines or as heatmaps
		//I thing everything would look interesting as heat maps, How to handle different screen resolutions for combining heatmaps?
		//	That might not be our problem if we export the data as CSV, then use MATLAB imaging processing scripts to combine and plot them as heatmaps - Know how Matlab data processing better than Java data processing libs
	}
	
	//To run this program from command line from Eclipse's bin folder:
	//java -cp .:../libs/commons-cli-1.2/commons-cli-1.2.jar:../libs/JNativeHook/jar/ edu.rit.cs.csc.recorder.Main
	
}
