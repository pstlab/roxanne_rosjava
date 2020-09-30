package com.github.roxanne_rosjava.roxanne_rosjava_core.platform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNode;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformCommand;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformFeedback;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformObservation;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;

/**
 * 
 * @author anacleto
 *
 */
public abstract class PlatformProxy 
{
	protected final List<PlatformObserver> observers;							// list of platform observers
	
	/**
	 * 
	 */
	protected PlatformProxy() {
		// setup data structures
		this.observers = new ArrayList<>();
	}
	
	/**
	 * 
	 * @param cfgFile
	 * @throws PlatformException
	 */
	public abstract void initialize(String cfgFile) 
			throws PlatformException;
	
	
	/**
	 * This method registers an observer to a particular (physical) platform.
	 * 
	 *  The interactions between the executive and the physical platform are 
	 *  managed through a dedicated PROXY which must implement this interface
	 * 
	 * @param obesrver
	 */
	public void register(PlatformObserver observer) {
		// register a new observer
		synchronized (this.observers) {
			// add the observer
			this.observers.add(observer);
			// send signal
			this.observers.notifyAll();
		}
	}
	
	/**
	 * Unregister a (previously) registered observer to a particular (physical) platform.
	 * 
	 * 
	 * @param observer
	 */
	public void unregister(PlatformObserver observer) {
		// unregister observers
		synchronized (this.observers) {
			// remove observer
			this.observers.remove(observer);
			// send signal
			this.observers.notifyAll();
		}
	}
	
	/**
	 * 
	 * This method sends a request to the real/simulated platform to execute a command.
	 * 
	 * This method is usually meant for partially controllable commands. The platform/environment is 
	 * in charge of the execution of the related command and therefore it will then notify the 
	 * acting agent about the successful execution or failures.
	 * 
	 * Feedback about the execution of the command will be then received through the PlatformObserver
	 * interface
	 * 
	 * @param node
	 * @return
	 * @throws PlatformException
	 */
	public abstract PlatformCommand executeNode(ExecutionNode node)
			throws PlatformException;
	
	/**
	 * 
	 * This method sends a request to the real/simulated platform to start the execution of a command.
	 * 
	 * This method is usually meant for controllable commands. The acting agent is in charge of 
	 * deciding when to start the execution of a command.
	 * 
	 * @param node
	 * @return
	 * @throws PlatformException
	 */
	public abstract PlatformCommand startNode(ExecutionNode node) 
			throws PlatformException;
	

	/**
	 * 
	 * This method sends a request to the real/simulated platform to stop the execution of a command
	 * 
	 * This method is usually meant for controllable commands. The acting agent is in charge of 
	 * deciding when to stop the execution of a previously started command.  
	 * 
	 * @param cmd
	 * @throws PlatformException
	 */
	public abstract void stopNode(ExecutionNode node) 
			throws PlatformException;
	
	/**
	 * This method check whether an execution node represents a platform command or not. 
	 * 
	 *  Namely, the method checks if a particular token of a plan actually represents a 
	 *  command to be executed on some platform. 
	 *  
	 *  Clearly, the implementation of this method strongly depends on the functional layer 
	 *  provided by the hardware or software platform considered for the execution of 
	 *  timeline-based plan.
	 * 
	 * @param node
	 * @return
	 */
	public abstract boolean isPlatformCommand(ExecutionNode node);
	
	
	/**
	 * 
	 * @param observation
	 */
	public void notify(PlatformObservation<? extends Object> observation)
	{
		// get platform observers
		synchronized (observers) {
			// notify observers
			for (PlatformObserver observer : observers) {
				// notify observation to platform observer
				observer.observation(observation);
			}
		}
	}
	
	/**
	 * 
	 * @param feedback
	 */
	public void notify(PlatformFeedback feedback)
	{
		// get platform observers
		synchronized (observers) {
			// notify observers
			for (PlatformObserver observer : observers) {
				// notify observation to platform observer
				observer.feedback(feedback);
				
			}
		}
	}
	
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public static String extractCommandName(ExecutionNode node) 
	{ 
		// get signature
		String name = node.getGroundSignature();
		// "clear" command name from "control tags"
		if (name.startsWith("_")) {
			// remove partial controllability tag
			name = name.replaceFirst("_", "");
		}
		
		// discard parameters if any 
		String[] splits = name.split("-");
		// set the first element as command name
		name = splits[0];
		
		// get cleared command name 
		return name.toLowerCase();
	}
	
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public static String[] extractCommandParameters(ExecutionNode node) {
		// extract command parameter from node to execute
		String[] splits = node.getGroundSignature().split("-");
		// get parameters
		String[] parameters = Arrays.copyOfRange(splits, 1, splits.length);
		// get parameters		
		return parameters;
	}
}
