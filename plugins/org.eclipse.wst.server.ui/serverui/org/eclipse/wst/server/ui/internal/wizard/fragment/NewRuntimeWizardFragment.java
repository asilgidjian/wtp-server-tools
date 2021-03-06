/*******************************************************************************
 * Copyright (c) 2003, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.server.ui.internal.wizard.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.wst.server.core.IRuntimeWorkingCopy;
import org.eclipse.wst.server.core.IServerWorkingCopy;
import org.eclipse.wst.server.core.TaskModel;
import org.eclipse.wst.server.core.internal.ServerWorkingCopy;
import org.eclipse.wst.server.ui.internal.ServerUIPlugin;
import org.eclipse.wst.server.ui.internal.wizard.WizardTaskUtil;
import org.eclipse.wst.server.ui.internal.wizard.page.NewRuntimeComposite;
import org.eclipse.wst.server.ui.wizard.WizardFragment;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
/**
 * 
 */
public class NewRuntimeWizardFragment extends WizardFragment {
	// filter by type/version
	protected String type;
	protected String version;

	// filter by partial runtime type id
	protected String runtimeTypeId;

	protected Map<String, WizardFragment> fragmentMap = new HashMap<String, WizardFragment>();

	// Storing the NewRuntimeComposite is required to determine if the selected item
	// by the user is a valid server runtime
	protected NewRuntimeComposite comp;
	
	public NewRuntimeWizardFragment() {
		// do nothing
	}

	public NewRuntimeWizardFragment(String type, String version, String runtimeTypeId) {
		this.type = type;
		this.version = version;
		this.runtimeTypeId = runtimeTypeId;
	}

	public boolean hasComposite() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.ui.internal.task.WizardTask#getWizardPage()
	 */
	public Composite createComposite(Composite parent, IWizardHandle wizard) {
		comp = new NewRuntimeComposite(parent, wizard, getTaskModel(), type, version, runtimeTypeId);
		return comp;
	}

	public List getChildFragments() {
		List<WizardFragment> listImpl = new ArrayList<WizardFragment>();
		createChildFragments(listImpl);
		return listImpl;
	}

	protected void createChildFragments(List<WizardFragment> list) {
		if (getTaskModel() == null)
			return;
		
		IRuntimeWorkingCopy runtime = (IRuntimeWorkingCopy) getTaskModel().getObject(TaskModel.TASK_RUNTIME);
		if (runtime == null)
			return;
		
		WizardFragment sub = getWizardFragment(runtime.getRuntimeType().getId());
		if (sub != null)
			list.add(sub);
		
		IServerWorkingCopy server = (IServerWorkingCopy) getTaskModel().getObject(TaskModel.TASK_SERVER);
		if (server != null) {
			if (server.getServerType().hasServerConfiguration() && server instanceof ServerWorkingCopy) {
				ServerWorkingCopy swc = (ServerWorkingCopy) server;
				try {
					if (runtime.getLocation() != null && !runtime.getLocation().isEmpty())
						swc.importRuntimeConfiguration(runtime, null);
				} catch (CoreException ce) {
					// ignore
				}
			}
			
			list.add(new WizardFragment() {
				public void enter() {
					IRuntimeWorkingCopy runtime2 = (IRuntimeWorkingCopy) getTaskModel().getObject(TaskModel.TASK_RUNTIME);
					IServerWorkingCopy server2 = (IServerWorkingCopy) getTaskModel().getObject(TaskModel.TASK_SERVER);
					server2.setRuntime(runtime2);
				}
			});
			
			sub = getWizardFragment(server.getServerType().getId());
			if (sub != null)
				list.add(sub);
			
			list.add(WizardTaskUtil.SaveServerFragment);
		}
	}

	protected WizardFragment getWizardFragment(String typeId) {
		try {
			WizardFragment fragment = fragmentMap.get(typeId);
			if (fragment != null)
				return fragment;
		} catch (Exception e) {
			// ignore
		}
		
		WizardFragment fragment = ServerUIPlugin.getWizardFragment(typeId);
		if (fragment != null)
			fragmentMap.put(typeId, fragment);
		return fragment;
	}
	
	public boolean isComplete(){
		// If the selected runtime is invalid, the wizard 
		// should not allow the user to press Finish
		if (comp.hasValidSelectedRuntime()){
			return true;
		}
		return false;
	}	
}