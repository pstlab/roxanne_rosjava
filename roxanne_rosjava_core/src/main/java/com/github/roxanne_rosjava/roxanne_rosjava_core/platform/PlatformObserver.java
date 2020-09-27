package com.github.roxanne_rosjava.roxanne_rosjava_core.platform;

import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformFeedback;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformObservation;

/**
 * 
 * @author anacleto
 *
 */
public interface PlatformObserver 
{
	/**
	 * 
	 * @param feedback
	 */
	public void feedback(PlatformFeedback feedback);
	
	/**
	 * Asynchronous notification of some observed events from 
	 * the platform
	 * 
	 * @param obs
	 */
	public void observation(PlatformObservation<? extends Object> obs);
}
