/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.server.ui.internal.view.servers;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.SelectionProviderAction;
/**
 * 
 */
public class ServerAction extends SelectionProviderAction {
	protected Shell shell;
	protected byte action;

	public ServerAction(Shell shell, ISelectionProvider provider, String label, byte action) {
		super(provider, label);
		this.shell = shell;
		this.action = action;
		setEnabled(ServerActionHelper.isActionEnabled(getSelection(), action));
	}
	
	/**
	 * Notifies this action that the given (non-structured) selection has changed
	 * in the selection provider.
	 * <p>
	 * The <code>SelectionProviderAction</code> implementation of this method
	 * does nothing. Subclasses may reimplement to react to this selection change.
	 * </p>
	 *
	 * @param selection the new selection
	 */
	public void selectionChanged(ISelection selection) {
		setEnabled(ServerActionHelper.isActionEnabled(selection, action));
	}
	
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(ServerActionHelper.isActionEnabled(selection, action));
	}
	
	/**
	 * The default implementation of this <code>IAction</code> method
	 * does nothing.  Subclasses should override this method
	 * if they do not need information from the triggering event,
	 * or override <code>run(Event)</code> if they do.
	 */
	public void run() {
		ServerActionHelper.performAction(shell, getSelection(), action);
	}
}