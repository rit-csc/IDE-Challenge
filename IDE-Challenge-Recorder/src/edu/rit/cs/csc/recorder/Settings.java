package edu.rit.cs.csc.recorder;


public final class Settings {
	
	//--------------------------------------------------------------------------
	// Options for debugging
	//--------------------------------------------------------------------------
	
	public static final boolean OnTop = true; //so it'll show up above eclipse
	
	//--------------------------------------------------------------------------
	// User viewable strings
	//--------------------------------------------------------------------------
	
	public static final String title = "CSC Recorder";
	
	public static final String RecordKeysTip = "Records the amount of key presses";
	public static final String RecordKeysText = "Keystrokes";
	public static final String RecordKeysTitle = "Keyboard Options";	
	public static final String RecordKeyDetailsTip = "Records the amount of times each individual character is pressed";
	public static final String RecordKeyDetailsText = "Characters";
	
	
	public static final String RecordMouseTitle = "Mouse Options";
	public static final String RecordMouseMovementTip = "Records how far the mouse travels";
	public static final String RecordMouseMovementText = "Distance";
	public static final String RecordMouseMovementDetailsTip = "Records where the mouse spends its time";
	public static final String RecordMouseMovementDetailsText = "Locations";
	
	public static final String RecordMouseClicksText = "Buttons";
	public static final String RecordMouseClicksTip = "Records how often each mouse button is clicked";	
	public static final String RecordMouseClicksDetailsText = "Click Locations";
	public static final String RecordMouseClicksDetailsTip = "Records where each button is clicked";
	
	public static final String StartText = "Record";
	public static final String StartTip = "Press to start recording the above selected options";
	public static final String StopText = "Stop Recording";
	public static final String StopTip = "";
}
