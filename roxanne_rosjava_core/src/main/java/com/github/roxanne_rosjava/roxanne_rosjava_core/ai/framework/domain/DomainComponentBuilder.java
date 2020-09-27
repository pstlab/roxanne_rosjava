package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.DomainComponent;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.domain.component.DomainComponentType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.framework.DomainComponentConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.framework.ParameterFacadePlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.framework.ResolverListPlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.framework.TemporalFacadePlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.lifecycle.PostConstruct;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.Resolver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ResolverBuilder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ResolverType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.parameter.ParameterFacade;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.utils.reflection.FrameworkReflectionUtils;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.TemporalFacade;

/**
 * 
 * @author anacleto
 *
 */
public class DomainComponentBuilder 
{
	/**
	 * 
	 * @param name
	 * @param type
	 * @param tf
	 * @param pf
	 * @return
	 */
	public synchronized static <T extends DomainComponent> T createAndSet(String name, DomainComponentType type, TemporalFacade tf, ParameterFacade pf)
	{
		// create domain component
		T c = doCreateDomainComponent(name, type.getClassClassName());
		
		try
		{
			// inject temporal facade
			FrameworkReflectionUtils.doInjectReferenceThroughAnnotation(c, TemporalFacadePlaceholder.class, tf);
		}
		catch (Exception ex) {
			throw new RuntimeException("Error while injecting temporal facade reference into component\n- message: " + ex.getMessage() + "\n");
		}
		
		try
		{
			// inject parameter facade
			FrameworkReflectionUtils.doInjectReferenceThroughAnnotation(c, ParameterFacadePlaceholder.class, pf);
		}
		catch (Exception ex) {
			throw new RuntimeException("Error while injecting parameter facade reference into component\n- message: " + ex.getMessage() + "\n");
		}
		
		// find component configuration annotation
		DomainComponentConfiguration annot = FrameworkReflectionUtils.doFindnAnnotation(c.getClass(), DomainComponentConfiguration.class);
		// check if annotation is null
		if (annot == null) {
			throw new RuntimeException("Error while creating domain component:\n- message= Configuration annotation not found\n");
		}
		
		// initialize the list of resolvers
		List<Resolver<?>> list = new ArrayList<>();
		// create resolvers
		for (ResolverType resvType : annot.resolvers()) {
			// create resolver
			Resolver<?> resv = ResolverBuilder.createAndSet(resvType, tf, pf, c);
			list.add(resv);
		}
		
		try
		{
			// inject resolvers to component
			FrameworkReflectionUtils.doInjectReferenceThroughAnnotation(c, ResolverListPlaceholder.class, list);
		}
		catch (Exception ex) {
			throw new RuntimeException("Error while injecting resolver references into component\n- message: " + ex.getMessage() + "\n");
		}
		
		
		try
		{
			// finalize component initialization
			FrameworkReflectionUtils.doInvokeMethodTaggedWithAnnotation(c, PostConstruct.class);
		}
		catch (Exception ex) {
			throw new RuntimeException("Error while calling post construct method of component\n- message: " + ex.getMessage() + "\n");
		}
		
		// get component
		return c;
	}
	
	/**
	 * 
	 * @param compName
	 * @param className
	 * @return
	 */
	private static <T extends DomainComponent> T doCreateDomainComponent(String compName, String className)
	{
		// component instance
		T comp = null;
		try 
		{
			// create component
			Class<?> clazz = Class.forName(className);
			// get declared constructors
			@SuppressWarnings("unchecked")
			Constructor<T> c = (Constructor<T>) clazz.getDeclaredConstructor(String.class);
			// set accessible
			c.setAccessible(true);
			// create instance
			comp = c.newInstance(compName);
		}
		catch (Exception ex) {
			throw new RuntimeException("Error while creating domain component from class: " + className + "\n- message: " + ex.getMessage() + "\n");
		}
		
		// get component instance
		return comp;
	}
}
