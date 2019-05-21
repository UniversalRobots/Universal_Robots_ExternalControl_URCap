package com.jbm.urcap.sample.scriptCommunicator.communicator;

public class ScriptCommand {

	private boolean sendAsPrimary = true;
	private final String primary_prefix = "def ";
	private final String secondary_prefix = "sec ";
	
	private final String programName;
	private final String postfix = "end\n";
	
	private String commandContent = "";
	
	/**
	 * Create a new ScriptCommand
	 */
	public ScriptCommand() {
		this.programName = "myCustomScript():\n";
	}
	
	/** 
	 * Create a new ScriptCommand with a custom name
	 * @param commandName the custom name (must be alphanumeric, start with letter)
	 */
	public ScriptCommand(String commandName) {
		this.programName = commandName+"():\n";
	}
	
	/**
	 * Append a URScript line to the ScriptCommand
	 * @param command the URScript line to append
	 */
	public void appendLine(String command) {
		commandContent += " "+command+"\n";
	}
	
	/**
	 * Send this ScriptCommand as a primary program
	 * This is the default behavior
	 */
	public void setAsPrimaryProgram() {
		this.sendAsPrimary = true;
	}
	
	/**
	 * Send this ScriptCommand as a secondary program
	 * Note: In this mode, no commands must take physical time
	 */
	public void setAsSecondaryProgram() {
		this.sendAsPrimary = false;
	}
	
	/**
	 * Returns if the ScriptCommand is configured as a primary program
	 * @return true if a primary program, false if a secondary program
	 */
	public boolean isPrimaryProgram() {
		return this.sendAsPrimary;
	}
	
	@Override
	public String toString() {
		String command = "";
		if(this.sendAsPrimary) {
			command += primary_prefix;
		} else {
			command += secondary_prefix;
		}
		command += this.programName;
		command += this.commandContent;
		command += this.postfix;
		return command;
	}
	
}
