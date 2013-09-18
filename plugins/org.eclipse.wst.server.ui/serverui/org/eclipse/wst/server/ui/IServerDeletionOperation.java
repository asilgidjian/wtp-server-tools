package org.eclipse.wst.server.ui;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.wst.server.core.IServer;

/**
 * Operation that will be executed upon server(s) deletion. 
 *
 */
public interface IServerDeletionOperation extends IUndoableOperation {
	
	/**
	 * Sets the servers for which the operation will be performed.
	 * 
	 * @param servers
	 */
	public void setServers(IServer[] servers);

}
