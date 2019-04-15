package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


public class GenerateScriptInstallationNodeView implements SwingInstallationNodeView<GenerateScriptInstallationNodeContribution> {
	
	private JTextField jTextField;
	
	public GenerateScriptInstallationNodeView() {
		
	}

	@Override
	public void buildUI(JPanel panel, GenerateScriptInstallationNodeContribution contribution) {   
		panel.add(createInfo());
		//panel.add(createVerticalSpacing());
		//panel.add(createInput(contribution));
	}

	
	
	

	
	private Box createInfo() {
		Box infoBox = Box.createVerticalBox();
		infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		JTextPane pane = new JTextPane();
		pane.setBorder(BorderFactory.createEmptyBorder());
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(attributeSet, 0.5f);
		StyleConstants.setLeftIndent(attributeSet, 0f);
		pane.setParagraphAttributes(attributeSet, false);
		pane.setText("The popup title below is shared between all Hello World program nodes.\nThe title cannot be empty.");
		pane.setEditable(false);
		pane.setMaximumSize(pane.getPreferredSize());
		pane.setBackground(infoBox.getBackground());
		infoBox.add(pane);
		return infoBox;
	}

	private Box createInput(final GenerateScriptInstallationNodeContribution contribution) {
		Box inputBox = Box.createHorizontalBox();
		inputBox.setAlignmentX(Component.LEFT_ALIGNMENT);

		inputBox.add(new JLabel("Popup title:"));
		//inputBox.add(createHorizontalSpacing());

		jTextField = new JTextField();
		jTextField.setFocusable(false);
		//jTextField.setPreferredSize(style.getInputfieldSize());
		jTextField.setMaximumSize(jTextField.getPreferredSize());
		jTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//KeyboardTextInput keyboardInput = contribution.getInputForTextField();
				//keyboardInput.show(jTextField, contribution.getCallbackForTextField());
			}
		});
		inputBox.add(jTextField);

		return inputBox;
	}

	private Component createHorizontalSpacing() {
		//return Box.createRigidArea(new Dimension(style.getHorizontalSpacing(), 0));
		return null;
	}

	private Component createVerticalSpacing() {
		//return Box.createRigidArea(new Dimension(0, style.getVerticalSpacing()));
		return null;
	}

	public void setPopupText(String t) {
		jTextField.setText(t);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
