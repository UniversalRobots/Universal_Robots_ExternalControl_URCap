package com.fzi.generateScript.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

public class GenerateScriptProgramNodeView implements SwingProgramNodeView<GenerateScriptProgramNodeContribution>{

	//private final ViewAPIProvider apiProvider;
	private JCheckBox advancedParam_CB; 
	// general parameters
	private JTextField maxLostPackages_TF = new JTextField(15);
	
	// advanced parameters
	private JTextField gainServoj_TF= new JTextField(15);

	
	public GenerateScriptProgramNodeView(ViewAPIProvider apiProvider) {
		//this.apiProvider = apiProvider;
	}

	@Override
	public void buildUI(JPanel panel, ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		
		createParam(panel, provider, maxLostPackages_TF,"Max Nr. of lost pkg: ", "1000");
		//panel.add(createAdvancedParamBox(provider));
		//panel.add(createHorizontalSpacing(10));
		//panel.add(createVerticalSpacing(10));
		//createParam(panel, provider, gainServoj_TF, "Gain servoj: ", "0");	
	}
	

	public void setAdvancedParam_CB(boolean checked) {
		advancedParam_CB.setSelected(checked);
	}
	

	private void createParam(JPanel panel, final ContributionProvider<GenerateScriptProgramNodeContribution> provider, 
			final JTextField textField, String labelText, String defaultInput) {
		JLabel label = new JLabel(labelText);
		panel.add(label);
		
		textField.setText(defaultInput);
		textField.setFocusable(false);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KeyboardTextInput keyboardInput = provider.get().getInputForTextField();
				keyboardInput.show(textField, provider.get().getCallbackForTextField());
			}
		});
		panel.add(textField);
	}
	/*
	private Box createAdvancedParamBox(ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		Box advancedParamBox = Box.createHorizontalBox();
		advancedParam_CB = new JCheckBox("Show advanced parameters");
		advancedParam_CB.setHorizontalTextPosition(SwingConstants.RIGHT);
		advancedParam_CB.setBorder(BorderFactory.createEmptyBorder());
		advancedParam_CB.addItemListener(provider.get().getListenerForAdvancedParam());
		advancedParam_CB.setMaximumSize(advancedParam_CB .getPreferredSize());
		advancedParamBox.add(advancedParam_CB );
		advancedParamBox.add(Box.createHorizontalGlue());
		return advancedParamBox;
	}
*/
	

	private Component createVerticalSpacing(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
	
	private Component createHorizontalSpacing(int width) {
		return Box.createRigidArea(new Dimension(width, 0));
	}
	
	public void UpdateMaxLostPackages_TF(String value) {
		maxLostPackages_TF.setText(value);
	}
	/*
	public void UpdateGainServoj_TF(String value) {
		gainServoj_TF.setText(value);
	}*/
}
