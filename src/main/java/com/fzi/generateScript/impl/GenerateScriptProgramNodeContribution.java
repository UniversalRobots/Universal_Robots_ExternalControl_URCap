package com.fzi.generateScript.impl;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.ProgramAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputCallback;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputFactory;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

public class GenerateScriptProgramNodeContribution implements ProgramNodeContribution{
	private static final String ADVANCED_PARAM_KEY = "showadvancedparam";
	private static final boolean ADVANCED_PARAM_DEFAULT = false;
	private static final String MAX_LOST_PACKAGES= "maxlostpackages";
	private static final String MAX_LOST_PACKAGES_DEFAULT_VALUE= "1000";
	private static final String GAIN_SERVO_J= "";
	private static final String GAIN_SERVO_J_DEFAULT_VALUE= "0";
	private final ProgramAPI programAPI;
	private final DataModel model;
	private final GenerateScriptProgramNodeView view;
	private final KeyboardInputFactory keyboardFactory;
	

	
	public GenerateScriptProgramNodeContribution(ProgramAPIProvider apiProvider, GenerateScriptProgramNodeView view,
			DataModel model) {
		this.programAPI = apiProvider.getProgramAPI();
		this.model = model;
		this.view = view;
		this.keyboardFactory = apiProvider.getUserInterfaceAPI().getUserInteraction().getKeyboardInputFactory();
		updateAdvancedParam(getAdvancedParam());
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
	
	/*
	@Override
	public void generateScript(ScriptWriter writer) {
		writer.appendRaw("popup(\"" + getInstallation().getHostIP() + "\" )");
	}*/
	
	
	@Override
	public void generateScript(ScriptWriter writer) {
		writer.appendRaw("popup(\"IP: \"  \"" + getInstallation().getHostIP() + "\"   \"      MLP:  \"  \"" + getInstallation().getHostIP() + "\"  )");
	}
	
	
	private GenerateScriptInstallationNodeContribution getInstallation() {
		return programAPI.getInstallationNode(GenerateScriptInstallationNodeContribution.class);
	}
	
	
	public ItemListener getListenerForAdvancedParam() {
		return new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setAdvancedParam(true);
				} else {
					setAdvancedParam(false);
				}
			}
		}; 
	}
	
	
	public void setParam(String key, String value) {
		if ("".equals(value)) {
			resetToDefaultValue();
		} else {
			model.set(MAX_LOST_PACKAGES, value);  // TODO enum or so for key????
		}
	}
	
	public String getParam() {
		return model.get(MAX_LOST_PACKAGES, MAX_LOST_PACKAGES_DEFAULT_VALUE);
	}
	
	private void resetToDefaultValue() {
		model.set(MAX_LOST_PACKAGES, MAX_LOST_PACKAGES_DEFAULT_VALUE);
	}
	
	
	
	private boolean getAdvancedParam() {
		return model.get(ADVANCED_PARAM_KEY, ADVANCED_PARAM_DEFAULT);
	}
	
	public void setAdvancedParam(boolean show) {
		updateAdvancedParam(show);
		model.set(ADVANCED_PARAM_KEY, show);
	}
	
	
	private void updateAdvancedParam(boolean enable) {
		System.out.println("Advnced Param is set to: " + enable);
		if(enable) {
			//TODO show advanced param
		} else {
			//TODO hide advanced param
		}
	}
	
	public KeyboardTextInput getInputForTextField() {
		KeyboardTextInput keyboardInput = keyboardFactory.createStringKeyboardInput();
		keyboardInput.setInitialValue(getParam());
		return keyboardInput;
	}
	
	public KeyboardInputCallback<String> getCallbackForTextField() {
		return new KeyboardInputCallback<String>() {
			@Override
			public void onOk(String value) {
				System.out.println("VALUE: " + value);
				setParam(MAX_LOST_PACKAGES, value);
				view.UpdateMaxLostPackages_TF(value);;
			}
		};
	}
}
