package com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting.ex.ActingAgentInitializationException;
import com.github.roxanne_rosjava.roxanne_rosjava_core.control.platform.RosJavaPlatformProxyBuilder;
import it.cnr.istc.pst.platinum.ai.deliberative.Planner;
import it.cnr.istc.pst.platinum.ai.executive.Executive;
import it.cnr.istc.pst.platinum.ai.executive.lang.failure.ExecutionFailureCause;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNode;
import it.cnr.istc.pst.platinum.ai.executive.pdb.ExecutionNodeStatus;
import it.cnr.istc.pst.platinum.ai.framework.domain.PlanDataBaseBuilder;
import it.cnr.istc.pst.platinum.ai.framework.domain.component.ComponentValue;
import it.cnr.istc.pst.platinum.ai.framework.domain.component.Decision;
import it.cnr.istc.pst.platinum.ai.framework.domain.component.DomainComponent;
import it.cnr.istc.pst.platinum.ai.framework.domain.component.PlanDataBase;
import it.cnr.istc.pst.platinum.ai.framework.domain.component.ex.DecisionPropagationException;
import it.cnr.istc.pst.platinum.ai.framework.domain.component.ex.RelationPropagationException;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.ex.NoSolutionFoundException;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.ex.SynchronizationCycleException;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.plan.SolutionPlan;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.relations.Relation;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.relations.RelationType;
import it.cnr.istc.pst.platinum.ai.framework.microkernel.lang.relations.parameter.BindParameterRelation;
import it.cnr.istc.pst.platinum.ai.framework.parameter.lang.constraints.BindParameterConstraint;
import it.cnr.istc.pst.platinum.ai.framework.utils.properties.FilePropertyReader;
import it.cnr.istc.pst.platinum.control.lang.*;
import it.cnr.istc.pst.platinum.control.lang.ex.PlatformException;
import it.cnr.istc.pst.platinum.control.platform.PlatformObserver;
import it.cnr.istc.pst.platinum.control.platform.PlatformProxy;
import org.apache.commons.logging.Log;
import org.ros.node.ConnectedNode;

import java.util.*;

/**
 *
 */
public class GoalOrientedActingAgent implements PlatformObserver {

	private final Object lock;								// lock state;
	private ActingAgentStatus status;						// agent status

	private final Map<GoalStatus, List<Goal>> queue;		// goal queue
	
	private String ddl;										// path to the domain specification file
	private PlanDataBase pdb;								// internal plan database representation
	
	private List<Thread> processes;							// goal oriented processes
	private DeliberativeProcess deliberative;				// internal deliberative process
	private Class<? extends Planner> pClass;				// planner class
	private boolean displayPlan;							// display plan flag
	
	private ExecutiveProcess executive;						// internal executive process
	private ContingencyHandlerProcess contingencyHandler;	// internal contingency handler process
	private Class<? extends Executive> eClass;				// executive class
	
	protected PlatformProxy proxy;
	private FilePropertyReader properties;

	private Log log;

	/**
	 *
	 * @param propertyFile
	 * @param node
	 * @throws ActingAgentInitializationException
	 */
	public GoalOrientedActingAgent(String propertyFile, ConnectedNode node)
			throws ActingAgentInitializationException {

		try {

			// set the log
			this.log = node.getLog();
			// set lock and status
			this.lock = new Object();
			// set status
			this.status = ActingAgentStatus.OFFLINE;
			// set goal buffer
			this.queue = new HashMap<>();
			// set goal queue
			for (GoalStatus s : GoalStatus.values()) {
				this.queue.put(s, new ArrayList<Goal>());
			}

			// set internal plan database representation
			this.pdb = null;
			// set platform
			this.processes = null;

			// get agent property file
			this.properties = new FilePropertyReader(propertyFile);
			// get DDL file
			String path = this.properties.getProperty("model");
			// set absolute path to the file
			this.ddl = path.replace("~", System.getenv("HOME"));
			// check if null
			if (this.ddl == null || this.ddl.equals("")) {
				throw new ActingAgentInitializationException("You need to specify an acting model of the agent in \"etc/agent.properties\"!");
			}

			// read the class name of the planner
			String plannerClassName = this.properties.getProperty("planner");
			// set planner class
			this.pClass = (Class<? extends Planner>) Class.forName(plannerClassName);
			// set display plan flag
			this.displayPlan = this.properties.getProperty("display_plan").equals("1") ? true : false;
			// read the class name of the executive
			String executiveClassName = this.properties.getProperty("executive");
			// set executive class
			this.eClass = (Class<? extends Executive>) Class.forName(executiveClassName);


			// read the class of the platform
			String platformClassName = this.properties.getProperty("platform");
			// check if a platform is necessary
			if (platformClassName != null && !platformClassName.equals("")) {
				// get platform configuration file
				String pCfgPath = this.properties.getProperty("platform_config_file");
				// set absolute path to file
				String configFile = pCfgPath.replace("~", System.getenv("HOME"));
				// check platform configuration file
				if (configFile == null || configFile.equals("")) {
					throw new ActingAgentInitializationException("Specify a configuration file for the platform in \"" + propertyFile + "\"!");
				}

				// create platform PROXY
				Class<? extends PlatformProxy> clazz = (Class<? extends PlatformProxy>)
						Class.forName(platformClassName);
				// create PROXY
				this.proxy = RosJavaPlatformProxyBuilder.build(clazz, node, configFile);
			}

			// setup deliberative and executive processes
			this.setupProcesses();

		} catch (PlatformException | ClassNotFoundException ex) {
			throw new ActingAgentInitializationException(ex.getMessage());
		}
	}
	
	/**
	 * 
	 */
	private void setupProcesses() {

		// set the list of processes
		this.processes = new ArrayList<>();
		// set goal listener thread
		this.processes.add(new Thread(new Runnable() {

			/**
			 * 
			 */
			@Override
			public void run() {
				boolean running = true;
				while(running) {

					try {

						// check buffered goals
						Goal goal = waitGoal(GoalStatus.BUFFERED);
						// simply select the extracted goal
						select(goal);

					} catch (InterruptedException ex) {
						running = false;
					}
				}
			}
		}));
		
		
		// set goal deliberative
		this.deliberative = new DeliberativeProcess(this.pClass, this.displayPlan, this);
		this.processes.add(new Thread(this.deliberative));
	
		// set goal executive
		this.executive = new ExecutiveProcess(this.eClass, this, this.log);
		this.processes.add(new Thread(this.executive));
	
		// set goal failure handler
		this.contingencyHandler = new ContingencyHandlerProcess(this);
		this.processes.add(new Thread(this.contingencyHandler));

		// register agent to the proxy
		if (this.proxy != null) {
			// register to platform events
			this.proxy.register(this);
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized ActingAgentStatus getStatus() {
		return status;
	}

	/**
	 *
	 * @param task
	 */
	@Override
	public void task(AgentTaskDescription task) {
		// buffer received task request
		this.buffer(task);
	}

	/**
	 *
	 * @param platformFeedback
	 */
	@Override
	public void feedback(PlatformFeedback platformFeedback) {
		// nothing to do


	}

	/**
	 *
	 * @param platformObservation
	 */
	@Override
	public void observation(PlatformObservation<?> platformObservation) {
		// nothing to do
	}

	/**
	 * Trigger acting process by buffering a description of a goal to plan and execute for
	 * 
	 * @param description
	 */
	public void buffer(AgentTaskDescription description) {

		// protect access to the queue
		synchronized (this.queue) {

			// create goal 
			Goal goal = new Goal(description);
			// set goal status
			goal.setStatus(GoalStatus.BUFFERED);
			// add a goal to the queue
			this.queue.get(goal.getStatus()).add(goal);
			this.log.info("[GoalOrientedActingAgent] Received task request:\n" +
					"- Request-ID: " + description.getId() + "\n" +
					"- Number of buffered requests: " + this.queue.get(GoalStatus.BUFFERED).size() + "\n");


			// send signal
			this.queue.notifyAll();
		}
	}
	
	/**
	 * Blocking call returning a list of finished or aborted goals.
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public List<Goal> getResults()
			throws InterruptedException {

		// wait some finished or aborted goal
		List<Goal> goals = new ArrayList<>();
		synchronized (this.queue) {
			while (this.queue.get(GoalStatus.ABORTED).isEmpty() && 
					this.queue.get(GoalStatus.FINISHED).isEmpty()) {
				// wait
				this.queue.wait();
			}
			
			// take aborted goals
			goals.addAll(this.queue.get(GoalStatus.ABORTED));
			// clear queue
			this.queue.get(GoalStatus.ABORTED).clear();
			// take finished goals
			goals.addAll(this.queue.get(GoalStatus.FINISHED));
			// clear queue
			this.queue.get(GoalStatus.FINISHED).clear();
			
			// send signal
			this.queue.notifyAll();
		}
		
		// get finished and aborted goals
		return goals;
	}
	
	/**
	 * 
	 */
	protected void select(Goal goal) {

		// protect access to the queue
		synchronized (this.queue) {

			// set goal status
			goal.setStatus(GoalStatus.SELECTED);
			// add goal to the queue
			this.queue.get(goal.getStatus()).add(goal);
			log.info("[GoalOrientedActingAgent] Selecting goal from input buffer:\n" +
					"- Selected Request-ID: " + goal.getTaskDescription().getId() +  "\n" +
					"- Number of buffered requests: " + this.queue.get(GoalStatus.BUFFERED).size() + "\n" +
					"- Number of selected goals: " + this.queue.get(GoalStatus.SELECTED).size() + "\n");

			// send signal
			this.queue.notifyAll();
		}
	}
	
	/**
	 * 
	 */
	protected void commit(Goal goal) {

		// protect access to the queue
		synchronized (this.queue) {

			// set goal status
			goal.setStatus(GoalStatus.COMMITTED);
			// add goal to the queue
			this.queue.get(goal.getStatus()).add(goal);
			// send signal
			this.queue.notifyAll();
		}
	}
	
	/**
	 * 
	 */
	protected void suspend(Goal goal) {

		// protect access to the queue
		synchronized (this.queue) {

			// set goal status
			goal.setStatus(GoalStatus.SUSPENDED);
			// add goal to the queue
			this.queue.get(goal.getStatus()).add(goal);
			// send signal
			this.queue.notifyAll();
		}
	}

	/**
	 * 
	 */
	protected void finish(Goal goal) {

		// protect access to the queue
		synchronized (this.queue) {

			// set goal status
			goal.setStatus(GoalStatus.FINISHED);
			// add goal to the queue
			this.queue.get(goal.getStatus()).add(goal);
			// send signal
			this.queue.notifyAll();
		}
	}
	
	/**
	 * 
	 */
	protected void abort(Goal goal) {

		// protect access to the queue
		synchronized (this.queue) {

			// set goal status
			goal.setStatus(GoalStatus.ABORTED);
			// add goal to the queue
			this.queue.get(goal.getStatus()).add(goal);
			// send signal
			this.queue.notifyAll();
		}		
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public void start() 
			throws InterruptedException {

		synchronized (this.lock) {
			while (!this.status.equals(ActingAgentStatus.OFFLINE)) {
				// wait 
				this.lock.wait();
			}
			
			// change status
			this.status = ActingAgentStatus.STARTING;
			// send signal
			this.lock.notifyAll();
		}
		
		
		// start all internal processes
		for (Thread p : this.processes) {
			p.start();
		}

		synchronized (this.lock) {
			// change status
			this.status = ActingAgentStatus.RUNNING;
			// notify all
			this.lock.notifyAll();
		}
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public void stop() 
			throws InterruptedException, PlatformException {

		synchronized (this.lock) {
			while (!this.status.equals(ActingAgentStatus.READY) && 
					!this.status.equals(ActingAgentStatus.RUNNING)) {
				// wait 
				this.lock.wait();
			}
			
			// change status
			this.status = ActingAgentStatus.STOPPING;
			// send signal
			this.lock.notifyAll();
		}
		
		
		// interrupt internal processes and wait termination
		for (Thread p : this.processes) {
			p.interrupt();
			p.join();
		}
		
		/*
		 * TODO : close platform PROXY
		 */

		synchronized (this.lock) {
			// change status
			this.status = ActingAgentStatus.OFFLINE;
			// notify all
			this.lock.notifyAll();
		}
	}

	/**
	 *
	 * @throws ActingAgentInitializationException
	 */
	public void initialize() 
			throws ActingAgentInitializationException {

		// ready flag
		boolean ready = false;
		try {

			synchronized (this.lock) {
				while (!this.status.equals(ActingAgentStatus.RUNNING)) {
					// wait a signal
					this.lock.wait();
				}

				// change status
				this.status = ActingAgentStatus.INITIALIZING;
				// send signal
				this.lock.notifyAll();
			}

			/**
			 * TODO : do initialization steps
			 */

			// set flag
			ready = true;

		} catch (InterruptedException ex) {
			throw new ActingAgentInitializationException(ex.getMessage());

		} finally {

				synchronized (this.lock) {

					// change status
					if (ready) {
						this.status = ActingAgentStatus.READY;
					}
					else {
						// simply set as running
						this.status = ActingAgentStatus.RUNNING;
					}

					// send signal
					this.lock.notifyAll();
				}
		}
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public void clear() 
			throws InterruptedException {

		synchronized (this.lock) {
			while (!this.status.equals(ActingAgentStatus.FAILURE) && 
					!this.status.equals(ActingAgentStatus.READY)) {
				// wait
				this.lock.wait();
			}
			
			// change status
			this.status = ActingAgentStatus.CLEARNING;
			// send signal 
			this.lock.notifyAll();
		}
		
		// clear queue
		this.queue.clear();
		// clear domain file specification
		this.ddl = null;
		// clear plan database 
		this.pdb = null;
		// clear PROXY
		this.proxy = null;
		
		synchronized (this.lock) {
			// change status
			this.status = ActingAgentStatus.RUNNING;
			// send signal
			this.lock.notifyAll();
		}
 	}

	/**
	 *
	 * @param task
	 * @param goals
	 * @param facts
	 * @param activatedRelations
	 * @throws DecisionPropagationException
	 * @throws RelationPropagationException
	 */
	 private void propagate(AgentTaskDescription task, List<Decision> goals, List<Decision> facts, List<Relation> activatedRelations)
			 throws DecisionPropagationException, RelationPropagationException {

		 // fact ID
		 int factId = 0;
		 // set known information concerning components
		 for (TokenDescription f : task.getFacts()) {

			 // get domain component
			 DomainComponent component = this.pdb.getComponentByName(f.getComponent());
			 // get goal referred value
			 ComponentValue value = component.getValueByName(f.getValue());
			 // check start time bound
			 long[] start = f.getStart();
			 if (start == null) {
				 start = new long[] {
						 this.pdb.getOrigin(),
						 this.pdb.getHorizon()
				 };
			 }

			 // check end time bound
			 long[] end = f.getEnd();
			 if (end == null) {
				 end = new long[] {
						 this.pdb.getOrigin(),
						 this.pdb.getHorizon()
				 };
			 }

			 // check duration bound
			 long[] duration = f.getDuration();
			 if (duration == null) {
				 duration = new long[] {
						 value.getDurationLowerBound(),
						 value.getDurationUpperBound()
				 };
			 }

			 // set labels
			 String[] labels = new String[] {};
			 // get parameters
			 String[] params = f.getLabels();
			 if (params != null && params.length > 0) {
				 // set parameter labels
				 labels = new String[params.length];
				 for (int i = 0; i < params.length; i++) {
					 // create parameter label
					 labels[i] = "?f" + factId + "l" + i;
				 }
			 }

			 // create fact decision
			 Decision decision = component.create(
					 value,
					 labels,
					 start,
					 end,
					 duration);

			 // also activate fact decision
			 component.activate(decision);

			 // bind parameter labels
			 for (int i = 0; i < params.length; i++) {

				 // get parameter label
				 String pLabel = labels[i];
				 // get parameter value
				 String pValue = params[i];

				 // create BIND parameter relation
				 BindParameterRelation pRel = component.create(RelationType.BIND_PARAMETER, decision, decision);
				 // set relation data
				 pRel.setReferenceParameterLabel(pLabel);
				 pRel.setValue(pValue);

				 // activate relation
				 component.activate(pRel);
				 // add activated relation
				 activatedRelations.add(pRel);
			 }


			 // add decision to fact list
			 facts.add(decision);
			 // increment fact ID
			 factId++;
		 }

		 // goal ID counter
		 int goalId = 0;
		 // set planning goals
		 for (TokenDescription g : task.getGoals())
		 {
			 // get domain component
			 DomainComponent component = this.pdb.getComponentByName(g.getComponent());
			 // get goal referred value
			 ComponentValue value = component.getValueByName(g.getValue());
			 // check start time bound
			 long[] start = g.getStart();
			 if (start == null) {
				 start = new long[] {
						 this.pdb.getOrigin(),
						 this.pdb.getHorizon()
				 };
			 }

			 // check end time bound
			 long[] end = g.getEnd();
			 if (end == null) {
				 end = new long[] {
						 this.pdb.getOrigin(),
						 this.pdb.getHorizon()
				 };
			 }

			 // check duration bound
			 long[] duration = g.getDuration();
			 if (duration == null) {
				 duration = new long[] {
						 value.getDurationLowerBound(),
						 value.getDurationUpperBound()
				 };
			 }


			 // set labels
			 String[] labels = new String[] {};
			 // get parameters
			 String[] params = g.getLabels();
			 if (params != null && params.length > 0) {
				 // set parameter labels
				 labels = new String[params.length];
				 for (int i = 0; i < params.length; i++) {
					 // create parameter label
					 labels[i] = "?g" + goalId + "l" + i;
				 }
			 }

			 // create goal decision
			 Decision decision = component.create(
					 value,
					 labels,
					 start,
					 end,
					 duration);

			 // bind parameter labels
			 for (int i = 0; i < params.length; i++) {

				 // get parameter label
				 String pLabel = labels[i];
				 // get parameter value
				 String pValue = params[i];

				 // create BIND parameter relation
				 BindParameterRelation pRel = component.create(RelationType.BIND_PARAMETER, decision, decision);
				 // set relation data
				 pRel.setReferenceParameterLabel(pLabel);
				 pRel.setValue(pValue);

				 // activate relation
				 component.activate(pRel);
				 // add activated relation
				 activatedRelations.add(pRel);
			 }

			 // add decision to goal list
			 goals.add(decision);
			 // increment goal ID
			 goalId++;
		 }
	 }

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws NoSolutionFoundException 
	 */
	protected boolean plan(Goal goal) 
			throws InterruptedException {

		// wait when planning can be actually performed if necessary
		synchronized (this.lock) {
			while (!this.status.equals(ActingAgentStatus.READY)) {
				// wait 
				this.lock.wait();
			}
			
			// change status
			this.status = ActingAgentStatus.DELIBERATING;
			// send signal
			this.lock.notifyAll();
		}
		
		// planning process result
		boolean success = true;

		// list of goal decisions
		List<Decision> goals = new ArrayList<>();
		// list of fact decisions
		List<Decision> facts = new ArrayList<>();
		// list of created (and activated) relations
		List<Relation> activatedRelations = new ArrayList<>();
		// start planning time
		long now = System.currentTimeMillis();
		try {

			// first create a clear data structure
			this.log.info("Setup timeline-based specification \n" + this.ddl + "\n");
			// set plan database on the given planning domain
			this.pdb = PlanDataBaseBuilder.createAndSet(this.ddl);

			// get task description
			AgentTaskDescription task = goal.getTaskDescription();
			// propagate task description state
			this.propagate(task, goals, facts, activatedRelations);

			// start planning time
			now = System.currentTimeMillis();

			// start planning
			this.log.info("Start planning on goal:\n-" +
					"" + goal + "\n");

			// deliberate on the current status of the plan database
			SolutionPlan plan = this.deliberative.doPlan(this.pdb);
			// set generated plan
			goal.setPlan(plan);
			// solution found
			this.log.info("Solution found after " + ((System.currentTimeMillis() - now) / 1000) + " seconds:\n" +
					"- Solution plan:\n" +
					"" + plan + "\n\n");

		} catch (SynchronizationCycleException | DecisionPropagationException | RelationPropagationException ex) {

			// problem setup error
			success = false;

			// deactivate and remove relations
			for (Relation rel : activatedRelations) {

				DomainComponent comp = rel.getReference().getComponent();
				// deactivate relation
				comp.deactivate(rel);
				// remove relation
				comp.delete(rel);
			}

			// remove and deactivate facts
			for (Decision f : facts) {
				f.getComponent().deactivate(f);
				f.getComponent().free(f);
			}

			// remove and deactivate goals
			for (Decision g : goals) {
				g.getComponent().deactivate(g);
				g.getComponent().free(g);
			}

			// print an error message
			this.log.error("Error while propagating initial facts from task description:\n"
					+ "\t- message: " + ex.getMessage() + "\n");


		} catch (NoSolutionFoundException ex) {

			// no solution found
			this.log.warn("No solution found on goal:\n" +
					"" + goal + "\n\n");
			// failure - no plan can be found
			success = false;
			// remove and deactivate facts
			for (Decision f : facts) {
				f.getComponent().deactivate(f);
				f.getComponent().free(f);
			}

			// remove and deactivate goals
			for (Decision g : goals) {
				g.getComponent().deactivate(g);
				g.getComponent().free(g);
			}
		} finally {

			// compute actual planning time
			long time = System.currentTimeMillis() - now;
			// add planning time attempt to the goal
			goal.addPlanningAttempt(time);
		}

		
		// update agent status
		synchronized (this.lock) {

			// update status according to the result of the planning process
			if (success) {
				this.status = ActingAgentStatus.PREPARING_EXECUTION;

			} else {

				// failure
				this.status = ActingAgentStatus.FAILURE;
			}
			
			// send signal
			this.lock.notifyAll();
		}
		
		// return planning process result
		return success;
	}
	
	/**
	 * 
	 * @param goal
	 * @return
	 * @throws InterruptedException
	 */
	protected boolean execute(Goal goal) 
			throws InterruptedException {

		synchronized (this.lock) {
			while (!this.status.equals(ActingAgentStatus.PREPARING_EXECUTION)) {
				// wait
				this.lock.wait();
			}
			
			// update status
			this.status = ActingAgentStatus.EXECUTING;
			// send signal
			this.lock.notifyAll();
 		}
		
		// execution result
		boolean complete = true;
		// start execution time
		long now = System.currentTimeMillis();
		try {

			// ready to start execution
			this.log.info("Ready to execute (timeline-based) plan for goal\n" +
					"" + goal + "\n");

			// execute the plan
			this.executive.doExecute(goal);

			// successful execution
			this.log.info("Plan successfully executed...");

		} catch (Exception ex) {

			// execution failure
			complete = false;
			// successful execution
			this.log.info("Plan execution failure!");

		}  finally {

			// compute actual execution time
			long time = System.currentTimeMillis() - now;
			// add execution attempt time
			goal.addExecutionAttempt(time);
		}
		
		// update agent status
		synchronized (this.lock) {

			// update status according to the execution results
			if (complete) {

				// agent ready to execute another plan
				this.status = ActingAgentStatus.READY;

			} else {

				// suspend executive
				this.status = ActingAgentStatus.SUSPENDING;
			}
			
			// send signal
			this.lock.notifyAll();
		}
		
		// return execution result
		return complete;
	}
	
	/**
	 * 
	 * @param goal
	 * @return
	 * @throws InterruptedException
	 */
	protected boolean repair(Goal goal) 
			throws InterruptedException {

		synchronized (this.lock) {
			while (!this.status.equals(ActingAgentStatus.SUSPENDING)) {
				// wait
				this.lock.wait();
			}
			
			// update status
			this.status = ActingAgentStatus.REPAIRING;
			// send signal
			this.lock.notifyAll();
 		}

		 /*
		// repairing result
		boolean success = true;
		// start contingency handling time
		long now = System.currentTimeMillis();
		try {

			// repair plan data
			this.log.warn("Clearing plan structure before re-planning");
			
			// list of kept decisions 
			List<Decision> kept = new ArrayList<>();
			// clear domain components
			for (DomainComponent comp : this.pdb.getComponents()) {

				// clear component 
				this.log.info("Clearing component \"" + comp.getName() + "\"");
				// list of pending decisions
				List<Decision> pendings = comp.getPendingDecisions();
				for (Decision pending : pendings) {

					// completely remove decision and related relations
					this.log.info("\tClearing PENDING component decision \"" + pending + "\" ...");
					comp.deactivate(pending);
					comp.free(pending);
				}
				
				// get execution trace 
				List<ExecutionNode> trace = goal.getExecutionTraceByComponentName(comp.getName());
				// remove active decisions that have not been executed
				this.log.info("Check ACTIVE decisions of the component");
				// list of active decisions
				List<Decision> actives = comp.getActiveDecisions();
				for (Decision active : actives) {

					boolean executed = false;
					for (ExecutionNode node : trace) {
						// check if the temporal interval has been executed
						if (node.getInterval().equals(active.getToken().getInterval())){
							executed = true;
							break;
						}
					}
					
					// check flag
					if (executed) {

						// keep the decision as active
						this.log.info("\tKeep ACTIVE decision \"" + active + "\" since completely executed ... ");
						kept.add(active);
					}
					else {

						// clear and remove decision and related relations
						this.log.info("\tClear ACTIVE decision \"" + active + "\" and related relations since not executed yet");
						comp.deactivate(active);
						comp.free(active);
					}
				}
			}
			
			
			// check execution failure cause
			ExecutionFailureCause cause = goal.getFailureCause();
			this.log.info("Check cause of execution failure");
			// check type
			switch (cause.getType())
			{
				case NODE_DURATION_OVERFLOW : {

					// keep the decision as active and consider it as executed
					this.log.info("Handle DURATION OVERFLOW failure ...");
					ExecutionNode node = cause.getInterruptionNode();
					// find the related decision
					for (DomainComponent comp : this.pdb.getComponents()) {
						// get active decisions
						List<Decision> actives = comp.getActiveDecisions();
						for (Decision active : actives) {
							// check temporal intervals
							if (node.getInterval().equals(active.getToken().getInterval())) {
								// keep the decision as active 
								this.log.debug("\tKeep active decision \"" + active + "\"");
								kept.add(active);
							}
						}
					}
				}
				break;
				
				case NODE_EXECUTION_ERROR :
				case NODE_START_OVERFLOW : {

					// remove decisions they are going to be re-planned
					this.log.warn("Handle START OVERFLOW or TOKEN EXECUTION ERROR failures ...\n");
					ExecutionNode node = cause.getInterruptionNode();
					// find the related decision
					for (DomainComponent comp : this.pdb.getComponents()) {
						// get active decisions
						List<Decision> actives = comp.getActiveDecisions();
						for (Decision active : actives) {
							// check temporal intervals
							if (node.getInterval().equals(active.getToken().getInterval())) {
								// keep the decision as active 
								this.log.debug("\tClear ACTIVE decision \"" + active + "\"");
								comp.deactivate(active);
								comp.free(active);
							}
						}
					}
				}
				break;
				
				default:
					throw new RuntimeException("Unknown Execution Failure Cause \"" + cause.getType() + "\"");
			}
			
			
			
			// get task description
			AgentTaskDescription task = goal.getTaskDescription();
			// set planning goals 
			for (TokenDescription g : task.getGoals()) {

				// get domain component
				DomainComponent component = this.pdb.getComponentByName(g.getComponent());
				// get goal referred value
				ComponentValue value = component.getValueByName(g.getValue());
				// check start time bound
				long[] start = g.getStart();
				if (start == null) {
					start = new long[] {
						this.pdb.getOrigin(),
						this.pdb.getHorizon()
					};
				}
				
				// check end time bound
				long[] end = g.getEnd();
				if (end == null) {
					end = new long[] {
						this.pdb.getOrigin(),
						this.pdb.getHorizon()
					};
				}
				
				// check duration bound
				long[] duration = g.getDuration();
				if (duration == null) {
					duration = new long[] {
						value.getDurationLowerBound(),
						value.getDurationUpperBound()
					};
				}
				
				// check labels
				String[] labels = g.getLabels();
				if (labels == null) {
					labels = new String[] {};
				}
				

				// TODO : check parameter relations

				
				// create goal decision
				Decision decision = component.create(
						value, 
						labels,
						start,
						end,
						duration,
						ExecutionNodeStatus.IN_EXECUTION);
				
				// add decision to goal list
				this.log.info("Start re-planning for goal : [" + decision.getId() +"]:" + decision.getComponent().getName() + "." + decision.getValue().getLabel() + " "
						+ "AT [" + decision.getStart()[0]  + ", " + decision.getStart()[1] + "] "
						+ "[" + decision.getEnd()[0] + ", " + decision.getEnd()[1] + "] "
						+ "[" + decision.getDuration()[0] + ", " + decision.getDuration()[1] + "]");
			}
			
			
			// deliberate on the current status of the plan database
			SolutionPlan plan = this.contingencyHandler.doHandle(this.pClass, this.pdb);
			// set repaired plan
			goal.setPlan(plan);
			// set goal as repaired
			goal.setRepaired(true);
			// set the tick the execution will start
			goal.setExecutionTick(goal.getFailureCause().getInterruptionTick());
			// clear execution trace
			goal.clearExecutionTrace();

		} catch (Exception ex) {

			// error while repairing
			success = false;
			// error message
			this.log.error("Error while trying to repair the plan\n"
					+ "\t- message: " + ex.getMessage() + "\n");
			
			// completely clear all the plan database
			for (DomainComponent comp : this.pdb.getComponents()) {
				// remove all pending decisions
				List<Decision> pendings = comp.getPendingDecisions();
				for (Decision pending : pendings) {
					comp.deactivate(pending);
					comp.free(pending);
					
				}
				
				// remove all active decisions
				List<Decision> actives = comp.getActiveDecisions();
				for (Decision active : actives) {
					comp.deactivate(active);
					comp.free(active);
				}
				
				// completely clear component
				comp.clear();
			}
		}  finally {

			// compute actual planning time
			long time = System.currentTimeMillis() - now;
			// add planning time attempt to the goal
			goal.addContingencyHandlingAttempt(time);
			goal.addPlanningAttempt(time);
		}
		*/

		// force abort without a recovery strategy
		boolean success = false;
		synchronized (this.lock) {
			// update status according to the execution results

			this.status = ActingAgentStatus.READY;
			// send signal
			this.lock.notifyAll();
		}
		
		// return execution result
		return success;
	}
	
	/**
	 * 
	 * @param status
	 * @throws InterruptedException
	 */
	protected Goal waitGoal(GoalStatus status) 
			throws InterruptedException {

		// goal 
		Goal goal = null;
		// wait a selected goal
		synchronized (this.queue) {

			// check selected buffer
			while (this.queue.get(status).isEmpty()) {
				// wait a selected goal
				this.queue.wait();
			}

			// print the number of committed goals
			this.log.info("[GoalOrientedActingAgent] Checking available goals to be processed\n" +
					"- Goal status: " + status + "\n" +
 					"- Number of queued goals: " + this.queue.get(status).size() + "\n");
			
			// remove the first selected goal from the queue
			goal = this.queue.get(status).remove(0);
			// send signal
			this.queue.notifyAll();
		}
		
		// get extracted goal
		return goal;
	}
}
