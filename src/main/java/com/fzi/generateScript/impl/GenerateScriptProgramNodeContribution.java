package com.fzi.generateScript.impl;


import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.ProgramAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class GenerateScriptProgramNodeContribution implements ProgramNodeContribution{
	private static final String HOST_IP = "ip";
	private static final String DEFAULT_VALUE= "default";
	private final ProgramAPI programAPI;
	private final DataModel model;

	
	public GenerateScriptProgramNodeContribution(ProgramAPIProvider apiProvider, GenerateScriptProgramNodeView view,
			DataModel model) {
		this.programAPI = apiProvider.getProgramAPI();
		this.model = model;
	}
	
	

	@Override
	public void openView() {
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		return "GenerateScript";
	}

	@Override
	public boolean isDefined() {
		return true;
	}
	
	public String getHostIP() {
		return model.get(HOST_IP, DEFAULT_VALUE);
	}
	
	@Override
	public void generateScript(ScriptWriter writer) {
		writer.appendRaw("popup(\"" + getInstallation().getHostIP() + "\" )");
	}

	
	private GenerateScriptInstallationNodeContribution getInstallation() {
		return programAPI.getInstallationNode(GenerateScriptInstallationNodeContribution.class);
	}
	
	/*
	 * private String generateIPInfo() {
		//model.set(IP, "setted_before");
		//System.out.println(model.get(IP, ""));
		return model.get(IP, "");
		//return model.isSet(IP) ? "The host IP is: " + getHostIP() + "." : "No IP set";
	}
	public KeyboardTextInput getKeyboardForTextField() {
		KeyboardTextInput keyboardInput = keyboardFactory.createStringKeyboardInput();
		keyboardInput.setInitialValue(getHostIP());
		return keyboardInput;
	}

	public KeyboardInputCallback<String> getCallbackForTextField() {
		return new KeyboardInputCallback<String>() {
			@Override
			public void onOk(String value) {
				setHostIP(value);
				//view.setPopupText(value);
			}
		};
	}
	
	public void setHostIP(final String value) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			@Override
			public void executeChanges() {
				if ("".equals(value)) {
					model.remove(IP);
				} else {
					model.set(IP, value);
				}
			}
		});

	}
	
	private String getHostIP() {
		return model.get(IP, "");
	}
	
	private String generateIPInfo() {
		//return "method testing";
		return model.isSet(IP) ? "The host IP is: " + getHostIP() + "." : "No IP set";
	}

	
	
	 * writer.appendLine("popup(\"" + generatePopupMessage() + "\", hello_world_swing_popup_title, False, False, blocking=True)");
	 * 
	 * 
	 * writer.appendRaw(
				"a = \"Im testing\" \n" + 
				"popup(a)"	);
	}
	 * 
	 * 
	 * writer.appendRaw("popup(\"TEST123\")");
	 * 
	 * 
	@Override
	public void generateScript(ScriptWriter writer) {
		writer.appendRaw( "	textmsg(\"value=\",3)(\n" + 
				"	MULT_jointstate = {{JOINT_STATE_REPLACE}}\n" + 
				"\n" + 
				"	SERVO_IDLE = 0\n" + 
				"	SERVO_RUNNING = 1\n" + 
				"	cmd_servo_state = SERVO_IDLE\n" + 
				"	cmd_servo_q = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0]\n" + 
				"	\n" + 
				"	def set_servo_setpoint(q):\n" + 
				"		enter_critical\n" + 
				"		cmd_servo_state = SERVO_RUNNING\n" + 
				"		cmd_servo_q = q\n" + 
				"		exit_critical\n" + 
				"	end\n" + 
				"	\n" + 
				"	thread servoThread():\n" + 
				"		state = SERVO_IDLE\n" + 
				"		while True:\n" + 
				"			enter_critical\n" + 
				"			q = cmd_servo_q\n" + 
				"			do_brake = False\n" + 
				"			if (state == SERVO_RUNNING) and (cmd_servo_state == SERVO_IDLE):\n" + 
				"				do_brake = True\n" + 
				"			end\n" + 
				"			state = cmd_servo_state\n" + 
				"			cmd_servo_state = SERVO_IDLE\n" + 
				"			exit_critical\n" + 
				"			if do_brake:\n" + 
				"				stopj(1.0)\n" + 
				"				sync()\n" + 
				"			elif state == SERVO_RUNNING:\n" + 
				"				servoj(q, {{SERVO_J_REPLACE}})\n" + 
				"			else:\n" + 
				"				sync()\n" + 
				"			end\n" + 
				"		end\n" + 
				"	end\n" + 
				"\n" + 
				"  socket_open(\"{{SERVER_IP_REPLACE}}\", {{SERVER_PORT_REPLACE}})\n" + 
				"\n" + 
				"  thread_servo = run servoThread()\n" + 
				"  keepalive = 1\n" + 
				"  while keepalive > 0:\n" + 
				"	  params_mult = socket_read_binary_integer(6+1)\n" + 
				"	  if params_mult[0] > 0:\n" + 
				"		  q = [params_mult[1] / MULT_jointstate, params_mult[2] / MULT_jointstate, params_mult[3] / MULT_jointstate, params_mult[4] / MULT_jointstate, params_mult[5] / MULT_jointstate, params_mult[6] / MULT_jointstate]\n" + 
				"		  keepalive = params_mult[7]\n" + 
				"		  set_servo_setpoint(q)\n" + 
				"	  end\n" + 
				"  end\n" + 
				"  sleep(.1)\n" + 
				"  socket_close()\n" + 
				"  kill thread_servo\n" + 
				"end");
	} */


}
