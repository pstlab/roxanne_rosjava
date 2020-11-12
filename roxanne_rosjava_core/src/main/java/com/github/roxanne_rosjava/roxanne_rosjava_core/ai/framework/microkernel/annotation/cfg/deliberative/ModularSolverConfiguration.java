package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.deliberative;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author anacleto
 *
 */
@Target({
	ElementType.FIELD,
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModularSolverConfiguration {

	// reference to solver algorithm
	Class<? extends ModularSolver> solver();
}
