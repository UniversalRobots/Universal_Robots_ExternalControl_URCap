package com.fzi.generateScript.impl;


import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.ProgramAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class GenerateScriptProgramNodeContribution implements ProgramNodeContribution{
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
	
	@Override
	public void generateScript(ScriptWriter writer) {
		writer.appendRaw("popup(\"" + getInstallation().getHostIP() + "\" )");
	}
	
	private GenerateScriptInstallationNodeContribution getInstallation() {
		return programAPI.getInstallationNode(GenerateScriptInstallationNodeContribution.class);
	}
	
}
