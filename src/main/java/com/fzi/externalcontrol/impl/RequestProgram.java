package com.fzi.externalcontrol.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.jbm.urcap.sample.scriptCommunicator.communicator.ScriptCommand;

public class RequestProgram {

	// custom IP
	private final String hostIp;
	// custom port
	private final int portNr;
	
	/*
	 * Default constructor
	 */
	public RequestProgram(String hostIp, String portNr) {
		this.hostIp = hostIp;
		this.portNr = Integer.parseInt(portNr);
	}
	

	
	
	public void sendCommand(ScriptCommand scriptCommand) {
		String command = commandToString(scriptCommand);
		
		try {
			// socket creation
			Socket socket = new Socket(hostIp, portNr);
			if (socket.isConnected()) {
				// output stream creation
				DataOutputStream out;
				out = new DataOutputStream(socket.getOutputStream());
				
				// send command
				out.write(command.getBytes("US-ASCII"));
				out.flush();
				out.close();
			}
			socket.close();
		} catch (IOException e) {
			System.err.println(e);
		}
		
	}
	
	
	public String commandToString(ScriptCommand scriptCommand) {
		return scriptCommand.toString();
	}
}
