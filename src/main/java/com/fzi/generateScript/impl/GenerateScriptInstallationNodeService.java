package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.data.DataModel;

import java.util.Locale;

public class GenerateScriptInstallationNodeService implements
		SwingInstallationNodeService<GenerateScriptInstallationNodeContribution, GenerateScriptInstallationNodeView> {

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle(Locale locale) {
		return "Generate Script";
	}

	@Override
	public GenerateScriptInstallationNodeView createView(ViewAPIProvider apiProvider) {
		return new GenerateScriptInstallationNodeView();
	}
	
	
	
	

	@Override
	public GenerateScriptInstallationNodeContribution createInstallationNode(InstallationAPIProvider apiProvider,
			GenerateScriptInstallationNodeView view, DataModel model, CreationContext context) {
		return new GenerateScriptInstallationNodeContribution(apiProvider, view, model);
	}


	
	
	
}


