package com.github.roxanne_rosjava.roxanne_rosjava_core.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;

/**
 * 
 * @author alessandroumbrico
 *
 */
public abstract class RunnablePlatformProxy extends PlatformProxy 
{
	/**
	 * 
	 */
	protected RunnablePlatformProxy() {
		super();
	}
	
	/**
	 * 
	 * @throws PlatformException
	 */
	public abstract void start() 
			throws PlatformException;
	
	/**
	 * 
	 * @throws PlatformException
	 */
	public abstract void stop() 
			throws PlatformException;
}
