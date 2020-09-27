package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.solver;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query.Query;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.query.QueryManager;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.lang.event.TemporalNetworkNotification;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.lang.event.TemporalNetworkObserver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.lang.event.ex.NotificationPropagationFailureException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.FrameworkObject;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.tn.TemporalNetwork;

/**
 * 
 * @author anacleto
 *
 */
public abstract class TemporalSolver<T extends Query> extends FrameworkObject implements TemporalNetworkObserver, QueryManager<T>
{
	protected TemporalNetwork tn;
	
	/**
	 * 
	 */
	protected TemporalSolver(TemporalNetwork tn) {
		super();
		this.tn = tn;
		// subscribe to the network
		this.tn.subscribe(this);
	}
	
	/**
	 * 
	 */
	@Override
	public abstract void process(T query);
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isConsistent();
	
	/**
	 * 
	 */
	@Override
	public abstract void notify(TemporalNetworkNotification info)
			throws NotificationPropagationFailureException;
}
