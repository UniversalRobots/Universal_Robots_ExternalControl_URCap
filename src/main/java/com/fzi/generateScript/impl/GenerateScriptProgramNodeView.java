package com.fzi.generateScript.impl;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

public class GenerateScriptProgramNodeView implements SwingProgramNodeView<GenerateScriptProgramNodeContribution>{

	private final ViewAPIProvider apiProvider;
	
	public GenerateScriptProgramNodeView(ViewAPIProvider apiProvider) {
		this.apiProvider = apiProvider;
	}
	
	private JComboBox<Integer> ioComboBox = new JComboBox<Integer>();
	private JSlider durationSlider = new JSlider();
	
	@Override
	public void buildUI(JPanel panel, ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(createDescription("Select which output to Light Up:"));
		panel.add(createSpacer(5));
		panel.add(createIOComboBox(ioComboBox, provider));
		panel.add(createSpacer(20));
		panel.add(createDescription("Select the duration of the Light Up:"));
		panel.add(createSpacer(5));
		panel.add(createDurationSlider(durationSlider, 0, 10, provider));
	}
	
	public void setIOComboBoxItems(Integer[] items) {
		ioComboBox.removeAllItems();
		ioComboBox.setModel(new DefaultComboBoxModel<Integer>(items));
	}
	
	public void setIOComboBoxSelection(Integer item) {
		ioComboBox.setSelectedItem(item);
	}
	
	public void setDurationSlider(int value) {
		durationSlider.setValue(value);
	}
	
	private Box createDescription(String desc) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(desc);
		
		box.add(label);
		
		return box;
	}
	
	private Box createIOComboBox(final JComboBox<Integer> combo,
			final ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(" digital_out ");
		
		combo.setPreferredSize(new Dimension(104, 30));
		combo.setMaximumSize(combo.getPreferredSize());
		
		combo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					provider.get().onOutputSelection((Integer) e.getItem());
				}
			}
		});
		
		box.add(label);
		box.add(combo);
		
		return box;
	}
	
	private Box createDurationSlider(final JSlider slider, int min, int max,
			final ContributionProvider<GenerateScriptProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setOrientation(JSlider.HORIZONTAL);
		
		slider.setPreferredSize(new Dimension(275, 30));
		slider.setMaximumSize(slider.getPreferredSize());
		
		final JLabel value = new JLabel(Integer.toString(slider.getValue())+" s");
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				value.setText(Integer.toString(newValue)+" s");
				provider.get().onDurationSelection(newValue);
			}
		});
		
		box.add(slider);
		box.add(value);
		
		return box;
	}
	
	private Component createSpacer(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}

}