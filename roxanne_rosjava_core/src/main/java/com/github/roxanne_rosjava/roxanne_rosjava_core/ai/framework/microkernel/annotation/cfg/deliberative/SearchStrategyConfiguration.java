package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.deliberative.strategy.SearchStrategy;

/**
 * 
 * @author anacleto
 *
 */
@Target({
	ElementType.TYPE,
	ElementType.CONSTRUCTOR
})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchStrategyConfiguration {
	
	// search strategy
	Class<? extends SearchStrategy> strategy();
}