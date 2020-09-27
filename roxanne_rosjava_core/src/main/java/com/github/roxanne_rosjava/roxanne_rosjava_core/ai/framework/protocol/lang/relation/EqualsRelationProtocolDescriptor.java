package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.protocol.lang.relation;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.protocol.lang.TokenProtocolDescriptor;

/**
 * 
 * @author alessandroumbrico
 *
 */
public class EqualsRelationProtocolDescriptor extends RelationProtocolDescriptor 
{
	/**
	 * 
	 * @param from
	 * @param to
	 */
	public EqualsRelationProtocolDescriptor(TokenProtocolDescriptor from, TokenProtocolDescriptor to) {
		super("equals", from, to);
	}
	
	/**
	 * 
	 */
	@Override
	public String export() {
		return this.from.getTimeline().getName() + " " + this.from.getId() + " "
				+ this.type + " "
				+ this.to.getTimeline().getName() + " " + this.to.getId();
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return this.export();
	}
}
