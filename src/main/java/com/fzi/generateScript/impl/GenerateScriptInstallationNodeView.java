package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GenerateScriptInstallationNodeView implements SwingInstallationNodeView<GenerateScriptInstallationNodeContribution> {
	JTextField textField;

	public GenerateScriptInstallationNodeView() {
		this.textField = new JTextField("IP address", 15);
		
	}

	@Override
	public void buildUI(JPanel panel, GenerateScriptInstallationNodeContribution contribution) {   
		
		JLabel label = new JLabel("Please setup the remote host's IP: ");
		panel.add(label);

		
		JButton button = new JButton("Save");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("*************  VALUE **************");
				System.out.println(textField.getText());
				
			}
		});
		panel.add(textField);
		panel.add(button);
	}


	
	
	
	
	
	
	
	
	
	
	
	
}
