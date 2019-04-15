package com.fzi.generateScript.impl;
import java.util.Locale;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class GenerateScriptProgramNodeService implements SwingProgramNodeService<GenerateScriptProgramNodeContribution, GenerateScriptProgramNodeView>{

	@Override
	public String getId() {
		return "GenerateScriptNode";
	}

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		configuration.setChildrenAllowed(false);
	}

	@Override
	public String getTitle(Locale locale) {
		return "Generate Script";
	}

	@Override
	public GenerateScriptProgramNodeView createView(ViewAPIProvider apiProvider) {
		return new GenerateScriptProgramNodeView(apiProvider);
	}

	@Override
	public GenerateScriptProgramNodeContribution createNode(ProgramAPIProvider apiProvider, GenerateScriptProgramNodeView view,
			DataModel model, CreationContext context) {
		return new GenerateScriptProgramNodeContribution(apiProvider, view, model);
	}

}