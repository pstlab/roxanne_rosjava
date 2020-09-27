package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.resolver.ResolverType;

/**
 * Component Configuration annotation
 * 
 * @author anacleto
 *
 */
@Target({
	ElementType.TYPE,
	ElementType.CONSTRUCTOR
})
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainComponentConfiguration {
	
	/**
	 * 
	 * @return
	 */
	ResolverType[] resolvers();
}
