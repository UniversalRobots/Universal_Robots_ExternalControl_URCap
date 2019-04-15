package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GenerateScriptInstallationNodeView implements SwingInstallationNodeView<GenerateScriptInstallationNodeContribution> {
	
	public GenerateScriptInstallationNodeView() {
		
	}

	@Override
	public void buildUI(JPanel panel, GenerateScriptInstallationNodeContribution contribution) {   
		JLabel label = new JLabel("Please setup the remote host's IP: ");
		panel.add(label);

		JTextField textField = new JTextField("IP address", 15);
		panel.add(textField);
	}


	
	
	
	
	
	
	
	
	
	
	
	
}
