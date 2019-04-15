package com.fzi.generateScript.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;


public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Generate Script registering!");
		
		bundleContext.registerService(SwingProgramNodeService.class, new GenerateScriptProgramNodeService(), null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		
	}
}