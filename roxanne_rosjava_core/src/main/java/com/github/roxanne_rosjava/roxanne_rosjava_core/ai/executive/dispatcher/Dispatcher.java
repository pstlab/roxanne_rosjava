package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.dispatcher;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ex.ExecutionException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.FrameworkObject;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.executive.ExecutivePlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.Executive;

/**
 * 
 * @author anacleto
 *
 */
public abstract class Dispatcher<T extends Executive> extends FrameworkObject
{
	@ExecutivePlaceholder
	protected T executive;
	
	/**
	 * 
	 */
	public void clear() {
		// nothing to do
	}
	
	/**
	 * The method handle the current tick of the executor's clock
	 * 
	 * @param tick
	 * @throws ExecutionException
	 * @throws PlatformException
	 */
	public abstract void handleTick(long tick) 
			throws ExecutionException, PlatformException; 
}