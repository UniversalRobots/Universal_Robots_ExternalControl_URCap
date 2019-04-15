package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.InstallationAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;





public class GenerateScriptInstallationNodeContribution implements InstallationNodeContribution {

	private static final String HOST_IP = "";
	private static final String DEFAULT_VALUE = "000.000.000.000";
	
	private DataModel model;
	private final GenerateScriptInstallationNodeView view;
	private final InstallationAPI installationAPI;
	

	public GenerateScriptInstallationNodeContribution(InstallationAPIProvider apiProvider, GenerateScriptInstallationNodeView view, DataModel model) {
		this.installationAPI = apiProvider.getInstallationAPI();
		this.model = model;
		this.view = view;
	}



	@Override
	public void openView() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
	}

	
	public void setHostIP(String ip) {
		if ("".equals(ip)) {
			resetToDefaultValue();
		} else {
			model.set(HOST_IP, ip);
		}
	}
	
	public String getHostIP() {
		return model.get(HOST_IP, DEFAULT_VALUE);
	}
	
	private void resetToDefaultValue() {
		//view.setHostIP(DEFAULT_VALUE);
		model.set(HOST_IP, DEFAULT_VALUE);
	}
	
}
