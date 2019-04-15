package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.InstallationAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;




public class GenerateScriptInstallationNodeContribution implements InstallationNodeContribution {

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
	
	

}
