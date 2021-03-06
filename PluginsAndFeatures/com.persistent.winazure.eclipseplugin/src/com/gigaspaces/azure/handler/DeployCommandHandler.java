/**
 * Copyright (c) Microsoft Corporation
 * 
 * All rights reserved. 
 * 
 * MIT License
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files 
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, 
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH 
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */package com.gigaspaces.azure.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.internal.Workbench;

import waeclipseplugin.Activator;

import com.gigaspaces.azure.wizards.DeployWizard;
import com.gigaspaces.azure.wizards.DeployWizardDialog;
import com.microsoftopentechnologies.wacommon.utils.PluginUtil;
import com.persistent.contextmenu.SingleClickPublishUtils;
import com.persistent.ui.propertypage.WAProjectNature;

@SuppressWarnings("restriction")
public class DeployCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IProject selectedProject = PluginUtil.getSelectedProject();
		try {
			if (selectedProject.hasNature(WAProjectNature.NATURE_ID)) {
				DeployWizard wizard = new DeployWizard();
				if (wizard.getSelectedProject() == null) {
					return null;
				}
				wizard.setNeedsProgressMonitor(true);
				DeployWizardDialog dialog = new DeployWizardDialog(
						Workbench.getInstance().getActiveWorkbenchWindow().getShell(),
						wizard, Messages.publish);
				dialog.create();
				dialog.open();
			} else {
				SingleClickPublishUtils.exceute();
			}
		} catch (CoreException e) {
			Activator.getDefault().log("Error", e);
		}
		return null;
	}
}