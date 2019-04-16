package com.fzi.generateScript.impl;


import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

public class GenerateScriptProgramNodeView implements SwingProgramNodeView<GenerateScriptProgramNodeContribution>{

	private final ViewAPIProvider apiProvider;
	//private JLabel previewTitle;
	
	public GenerateScriptProgramNodeView(ViewAPIProvider apiProvider) {
		this.apiProvider = apiProvider;
	}

	@Override
	public void buildUI(JPanel panel, ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		panel.add(createInfo());
		
	}
	
	private Box createInfo() {
		Box infoBox = Box.createHorizontalBox();
		infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoBox.add(new JLabel("This program will log the remote host's IP."));
		return infoBox;
	}

}