package com.github.roxanne_rosjava.roxanne_rosjava_core.platform;

import java.lang.reflect.Constructor;

import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;
import org.ros.node.ConnectedNode;

/**
 * 
 * @author anacleto
 *
 */
public class PlatformProxyBuilder 
{
	/**
	 * 
	 * @param <T>
	 * @param pClass
	 * @param path
	 * @return
	 * @throws PlatformException
	 */
	public static <T extends PlatformProxy> T build(Class<T> pClass, String path) 
			throws  PlatformException
	{
		// create instance by reflection
		T proxy = null;
		try
		{
			// get constructor
			Constructor<T> c = pClass.getDeclaredConstructor();
			// set construct accessible
			c.setAccessible(true);
			// create instance
			proxy = c.newInstance();
			// configure instance 
			proxy.initialize(path);
		}
		catch (Exception ex) {
			throw new PlatformException("Error while creating platform proxy instance:\n\t- message= " + ex.getMessage() + "\n");
		}
		
		// get instance
		return proxy;
	}

	/**
	 *
	 * @param pClass
	 * @param node
	 * @param path
	 * @param <T>
	 * @return
	 * @throws PlatformException
	 */
	public static <T extends PlatformProxy> T build(Class<T> pClass, ConnectedNode node, String path)
			throws  PlatformException
	{
		// create instance by reflection
		T proxy = null;
		try
		{
			// get constructor
			Constructor<T> c = pClass.getDeclaredConstructor(ConnectedNode.class);
			// set construct accessible
			c.setAccessible(true);
			// create instance
			proxy = c.newInstance(node);
			// configure instance
			proxy.initialize(path);
		}
		catch (Exception ex) {
			throw new PlatformException("Error while creating platform proxy instance:\n\t- message= " + ex.getMessage() + "\n");
		}

		// get instance
		return proxy;
	}
}
