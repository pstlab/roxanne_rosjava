package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.dispatcher.ConditionCheckingDispatcher;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.dispatcher.Dispatcher;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ExecutionFeedback;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ExecutionFeedbackType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.ex.ExecutionException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.lang.failure.ExecutionFailureCause;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.monitor.ConditionCheckingMonitor;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.monitor.Monitor;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ControllabilityType;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNode;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutionNodeStatus;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.pdb.ExecutivePlanDataBase;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.FrameworkObject;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.FrameworkLoggerConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.executive.DispatcherConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.cfg.executive.MonitorConfiguration;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.executive.DispatcherPlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.executive.ExecutivePlanDataBasePlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.microkernel.annotation.inject.executive.MonitorPlaceholder;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.protocol.lang.PlanProtocolDescriptor;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.time.ex.TemporalConstraintPropagationException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.utils.log.FrameworkLoggingLevel;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.utils.properties.FilePropertyReader;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.utils.view.executive.ExecutiveWindow;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.lang.Goal;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformCommand;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformFeedback;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.PlatformObservation;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.lang.ex.PlatformException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.stats.dataset.ModelDataset;
import com.github.roxanne_rosjava.roxanne_rosjava_core.stats.dataset.mongo.MongoModelDataset;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.PlatformObserver;
import com.github.roxanne_rosjava.roxanne_rosjava_core.platform.PlatformProxy;
import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.executive.Executive;


/**
 * 
 * @author anacleto
 *
 */
@FrameworkLoggerConfiguration(
		level = FrameworkLoggingLevel.OFF
)
@MonitorConfiguration(
		monitor = ConditionCheckingMonitor.class
)
@DispatcherConfiguration(
		dispatcher = ConditionCheckingDispatcher.class
)
public class ROSJavaExecutive extends Executive {
	
	/**
	 *
	 */
	protected ROSJavaExecutive() {
		super();
	}
}
