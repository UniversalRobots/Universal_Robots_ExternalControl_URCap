package com.fzi.generateScript.impl;

import java.awt.Font;
import java.awt.GridLayout;
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

	private JCheckBox advancedParam_CB; 
	private JPanel standardParamsPanel;
	private JPanel advancedParamsPanel;
	private JTextField maxLostPackages_TF = new JTextField(15);
	private JTextField gainServoj_TF= new JTextField(15);

	
	public GenerateScriptProgramNodeView(ViewAPIProvider apiProvider) {
	}

	@Override
	public void buildUI(JPanel panel, ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		panel.setLayout(new GridLayout(2, 1, 5, 5));
		standardParamsPanel = new JPanel();
		JLabel standardParamsLabel = new JLabel("Standard Parameters: ");
		standardParamsLabel.setFont(new Font("Serif", Font.BOLD, 20));
		standardParamsPanel.add(standardParamsLabel);
		
		advancedParamsPanel = new JPanel();
		JLabel advancedParamsLabel = new JLabel("Advanced Parameters: ");
		advancedParamsLabel.setFont(new Font("Serif", Font.BOLD, 20));
		advancedParamsPanel.add(advancedParamsLabel);
		
		panel.add(standardParamsPanel);
		panel.add(advancedParamsPanel);
		
		createMaxLostPackages(standardParamsPanel, provider, maxLostPackages_TF,"Max Nr. of lost pkg: ", "1000");
		panel.add(createAdvancedParamBox(provider));

		createGainServoJ(advancedParamsPanel, provider, gainServoj_TF, "Gain servoj: ", "0");	
		
		advancedParamsPanel.setVisible(false);
	}
	

	public void setAdvancedParam_CB(boolean checked) {
		advancedParam_CB.setSelected(checked);
	}
	

	private void createMaxLostPackages(JPanel panel, final ContributionProvider<GenerateScriptProgramNodeContribution> provider, 
			final JTextField textField, String labelText, String defaultInput) {
		JLabel label = new JLabel(labelText);
		panel.add(label);
		
		textField.setText(defaultInput);
		textField.setFocusable(false);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KeyboardTextInput keyboardInput = provider.get().getInputForMaxLostPackages();
				keyboardInput.show(textField, provider.get().getCallbackForMaxLostPackages());
			}
		});
		panel.add(textField);
	}
	
	private void createGainServoJ(JPanel panel, final ContributionProvider<GenerateScriptProgramNodeContribution> provider, 
			final JTextField textField, String labelText, String defaultInput) {
		JLabel label = new JLabel(labelText);
		panel.add(label);
		
		textField.setText(defaultInput);
		textField.setFocusable(false);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				KeyboardTextInput keyboardInput = provider.get().getInputForGainServoj();
				keyboardInput.show(textField, provider.get().getCallbackForGainServoj());
			}
		});
		panel.add(textField);
	}
	
	private Box createAdvancedParamBox(ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		Box advancedParamBox = Box.createHorizontalBox();
		advancedParam_CB = new JCheckBox("Show advanced parameters");
		advancedParam_CB.setHorizontalTextPosition(SwingConstants.RIGHT);
		advancedParam_CB.setBorder(BorderFactory.createEmptyBorder());
		advancedParam_CB.addItemListener(getListenerForAdvancedParam(provider));
		advancedParam_CB.setMaximumSize(advancedParam_CB .getPreferredSize());
		advancedParamBox.add(advancedParam_CB );
		advancedParamBox.add(Box.createHorizontalGlue());
		return advancedParamBox;
	}

	public ItemListener getListenerForAdvancedParam(final ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		return new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					provider.get().setAdvancedParam(true);
				} else {
					provider.get().setAdvancedParam(false);
				}
			}
		}; 
	}
	
	
	public void updateMaxLostPackages_TF(String value) {
		maxLostPackages_TF.setText(value);
	}
	
	public void updateGainServoj_TF(String value) {
		gainServoj_TF.setText(value);
	}
	
	public void showAdvancedParameters(boolean visible) {
		advancedParamsPanel.setVisible(visible);
	}
	
	
}
