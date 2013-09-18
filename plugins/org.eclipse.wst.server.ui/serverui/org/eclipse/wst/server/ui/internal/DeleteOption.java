package org.eclipse.wst.server.ui.internal;

import java.util.List;

import org.eclipse.wst.server.ui.IServerDeletionOperation;

/**
 * Delete option that will be added to DeleteServerDialog.
 *
 */
public class DeleteOption implements Comparable<DeleteOption>{
	
	private String id;
	private Integer order;
	private String label;
	private boolean selected;
	private IServerDeletionOperation operation;
	private String[] serverTypes;
	private String parent;
	private List<DeleteOption> children;
	
	public DeleteOption(String id, Integer order, String label, boolean selected, IServerDeletionOperation operation, 
			String[] serverTypes, String parent) {
		this.id = id;
		this.order = order;
		this.label = label;
		this.selected = selected;
		this.operation = operation;
		this.serverTypes = serverTypes;
		this.parent = parent;
	}
	
	/**
	 * Return the label of the option.
	 * 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns true if the option should be selected by default.
	 * 
	 * @return initial selection
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Returns the operation that should be performed if the option is selected.
	 * 
	 * @return operation that will be executed upon server deletion, <code>null</code> if no such
	 */
	public IServerDeletionOperation getOperation() {
		return operation;
	}

	/**
	 * Returns the server types to which the option is applicable.
	 * 
	 * @return server types array
	 */
	public String[] getServerTypes() {
		return serverTypes;
	}

	/**
	 * Returns the children of this option.
	 * 
	 * @return options children, <code>null</code> if no such
	 */
	public List<DeleteOption> getChildren() {
		return children;
	}

	/**
	 * Sets the children of the option
	 * 
	 * @param children options children
	 */
	public void setChildren(List<DeleteOption> children) {
		this.children = children;
	}

	/**
	 * Id of the extension point defining this option.
	 * 
	 * @return extension point id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the option ordering.
	 * 
	 * @return option ordering, <code>null</code> if no such
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * Returns the option parent option.
	 * 
	 * @return parent option, <code>null</code> if no such
	 */
	public String getParent() {
		return parent;
	}
	
	/**
	 * Compares the priorities of both options.
	 */
	public int compareTo(DeleteOption o) {
		if (getOrder() == null) {
			return 1;
		}
		if (o.getOrder() == null) {
			return -1;
		}
		return getOrder().compareTo(o.getOrder());
	}

}
