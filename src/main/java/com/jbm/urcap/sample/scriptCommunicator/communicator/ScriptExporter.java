package com.jbm.urcap.sample.scriptCommunicator.communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ScriptExporter {

	private final String SEND_IP;
	
	private String RETURN_IP;
	private int RETURN_PORT = 5500;
	
	private final String RETURN_SOCKETNAME = "\"EXPORT_SOCKET\"";
	
	/**
	 * Default constructor
	 * Sets transmission and return IP to localhost (127.0.0.1)
	 * Use this if on the same platform as URControl
	 */
	public ScriptExporter() {
		this.SEND_IP = "127.0.0.1";
		this.RETURN_IP = this.SEND_IP;
	}
	
	/**
	 * Constructor allowing a different robot IP than localhost
	 * @param Robot_IP the IP address of the robot
	 */
	public ScriptExporter(String Robot_IP) {
		this.SEND_IP = Robot_IP;
		this.RETURN_IP = this.SEND_IP;
	}
	
	/**
	 * Change the port which the data is returned to. 
	 * Default is 5500. Change is this port is occupied. 
	 * @param port The new port used to return data
	 */
	public void setReturnPort(int port) {
		this.RETURN_PORT = port;
	}
	
	/**
	 * Change the IP address which the data is returned to. 
	 * By default, the return IP and the transmission IP is the same. 
	 * @param Return_IP The IP address of this application
	 */
	public void setReturnIP(String Return_IP) {
		this.RETURN_IP = Return_IP;
	}
	
	/**
	 * When called, the ScriptCommand will be sent to the robot as a Secondary Program. 
	 * The variable with variable_name will be sent back, and will be returned. 
	 * User should ensure, that the script 
	 *    a) does not take physical time 
	 *    b) the variable variable_name is defined
	 *    c) the variable can be casted to int
	 * @param command The script command to send
	 * @param variable_name The name of the variable to return from the ScriptCommand
	 * @return The resulting value of the variable, as an integer
	 */
	public int exportIntegerFromURScript(ScriptCommand command, String variable_name) {
		ScriptCommand newCommand = buildScriptCommandToExport(command, variable_name);
		String reply = readValueFromRobot(newCommand);
		
		int integerValue = Integer.parseInt(reply);
		
		return integerValue;
	}
	
	/**
	 * When called, the ScriptCommand will be sent to the robot as a Secondary Program. 
	 * The variable with variable_name will be sent back, and will be returned. 
	 * User should ensure, that the script 
	 *    a) does not take physical time 
	 *    b) the variable variable_name is defined
	 * @param command The script command to send
	 * @param variable_name The name of the variable to return from the ScriptCommand
	 * @return The resulting value of the variable, as String
	 */
	public String exportStringFromURScript(ScriptCommand command, String variable_name) {
		ScriptCommand newCommand = buildScriptCommandToExport(command, variable_name);
		String reply = readValueFromRobot(newCommand);
		
		return reply;
	}
	
	private ScriptCommand buildScriptCommandToExport(ScriptCommand command, String variable_name) {
		// Change to secondary program
		command.setAsSecondaryProgram();
		
		command.appendLine("socket_open(\""+RETURN_IP+"\","+RETURN_PORT+","+RETURN_SOCKETNAME+")");
		
		command.appendLine("socket_send_string("+variable_name+","+RETURN_SOCKETNAME+")");
		command.appendLine("socket_send_byte(13,"+RETURN_SOCKETNAME+")");	// CR
		command.appendLine("socket_send_byte(10,"+RETURN_SOCKETNAME+")");	// LF
		
		command.appendLine("socket_close("+RETURN_SOCKETNAME+")");
		
		return command;
	}
	
	private String readValueFromRobot(ScriptCommand commandWithReturn) {
		String input = "";
		try{
			// Create return socket
			ServerSocket server = new ServerSocket(RETURN_PORT);
			
			ScriptSender sender = new ScriptSender(SEND_IP);
			sender.sendScriptCommand(commandWithReturn);
			
			Socket returnSocket = server.accept();
			
			BufferedReader readerFromURScript = new BufferedReader(new InputStreamReader(returnSocket.getInputStream()));
			input = readerFromURScript.readLine();
			
			// Housekeeping
			readerFromURScript.close();
			returnSocket.close();
			server.close();
		} 
		catch (IOException e){
			System.out.println(e);
		}
		return input;
	}
	
}
