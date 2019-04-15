package com.fzi.generateScript.impl;


import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;

public class GenerateScriptProgramNodeContribution implements ProgramNodeContribution{

	private final ProgramAPIProvider apiProvider;
	private final GenerateScriptProgramNodeView view;
	private final DataModel model;
	private final UndoRedoManager undoRedoManager;
	
	private static final String OUTPUT_KEY = "output";
	private static final String DURATION_KEY = "duration";
	
	private static final Integer DEFAULT_OUTPUT = 0;
	private static final int DEFAULT_DURATION = 1;
	
	public GenerateScriptProgramNodeContribution(ProgramAPIProvider apiProvider, GenerateScriptProgramNodeView view,
			DataModel model) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model = model;
		this.undoRedoManager = this.apiProvider.getProgramAPI().getUndoRedoManager();
	}
	
	public void onOutputSelection(final Integer output) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(OUTPUT_KEY, output);
			}
		});
	}
	
	public void onDurationSelection(final int duration) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(DURATION_KEY, duration);
			}
		});
	}
	
	private Integer getOutput() {
		return model.get(OUTPUT_KEY, DEFAULT_OUTPUT);
	}
	
	private int getDuration() {
		return model.get(DURATION_KEY, DEFAULT_DURATION);
	}
	
	private Integer[] getOutputItems() {
		Integer[] items = new Integer[8];
		for(int i = 0; i<8; i++) {
			items[i] = i;
		}
		return items;
	}
	
	@Override
	public void openView() {
		view.setIOComboBoxItems(getOutputItems());
		
		view.setIOComboBoxSelection(getOutput());
		view.setDurationSlider(getDuration());
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		return "GenerateScript: DO"+getOutput()+" t="+getDuration();
	}

	@Override
	public boolean isDefined() {
		return true;
	}
	
	@Override
	public void generateScript(ScriptWriter writer) {
		writer.appendRaw("popup(\"TEST123\")");
	}

	/*
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
