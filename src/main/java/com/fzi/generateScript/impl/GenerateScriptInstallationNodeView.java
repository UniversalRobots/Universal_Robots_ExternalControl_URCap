package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GenerateScriptInstallationNodeView implements SwingInstallationNodeView<GenerateScriptInstallationNodeContribution> {
	private JTextField textField;

	public GenerateScriptInstallationNodeView() {
	}

	@Override
	public void buildUI(JPanel panel, final GenerateScriptInstallationNodeContribution contribution) {   
		
		JLabel label = new JLabel("Please setup the remote host's IP: ");
		panel.add(label);
		textField = new JTextField(15);
		textField.setFocusable(false);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KeyboardTextInput keyboardInput = contribution.getInputForTextField();
				keyboardInput.show(textField, contribution.getCallbackForTextField());
			}
		});
		
		panel.add(textField);
	
	}


	
	/*
	 * @Override
	public void buildUI(JPanel panel, GenerateScriptInstallationNodeContribution contribution) {   
		
		JLabel label = new JLabel("Please setup the remote host's IP: ");
		panel.add(label);
		
		
		JButton button = new JButton("Save");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("*************  VALUE **************");
				//System.out.println(textField.getText());
				//System.out.println(contribution.getHostIP());
				
			}
		});
		panel.add(textField);
		panel.add(button);
	}
	 */
	
	
	
	
	
	
	
	
	
	
	
	
}
