package com.github.roxanne_rosjava.roxanne_rosjava_taskplanner;

import com.github.roxanne_rosjava.roxanne_rosjava_core.control.acting.GoalOrientedActingAgent;
import it.cnr.istc.pst.platinum.control.lang.AgentTaskDescription;
import it.cnr.istc.pst.platinum.control.lang.Goal;
import it.cnr.istc.pst.platinum.control.lang.TokenDescription;
import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.topic.Subscriber;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ActingNode extends AbstractNodeMain
{
    private static final String HOME = System.getenv("ROXANNE_HOME") != null ?
            System.getenv("ROXANNE_HOME") + "/" : "";

    private static final String PROPERTY_FILE =  HOME + "etc/agent.properties";

    private GoalOrientedActingAgent agent;          // timeline-based acting agent

    private Log log;

    /**
     *
     * @return
     */
    @Override
    public GraphName getDefaultNodeName() {
        // set node name
        return GraphName.of("/roxanne/acting");
    }

    /**
     *
     * @param connectedNode
     */
    @Override
    public void onStart(ConnectedNode connectedNode)
    {
        // set logger
        this.log = connectedNode.getLog();
        try
        {
	        // creating platform
	        this.log.info("Setting up platform...");
	        // creating acting agent
	        this.log.info("Setting up goal-oriented agent...");
            // create the acting agent
            this.agent = new GoalOrientedActingAgent(PROPERTY_FILE, connectedNode);
            // start the agent
            this.log.info("Starting agent...");
            // start   acting agent
            this.agent.start();
            // set acting agent
            this.agent.initialize();
            // ready to receive messages
            this.log.info("... agent ready to receive goals!");

            // create a subscriber to the goal input topic
            Subscriber<roxanne_rosjava_msgs.ActingGoal> subscriber = connectedNode.newSubscriber(
                    "roxanne/acting/goal",
                    roxanne_rosjava_msgs.ActingGoal._TYPE);

            // synchronous goal management
            subscriber.addMessageListener(new MessageListener<roxanne_rosjava_msgs.ActingGoal>()
            {
                @Override
                public void onNewMessage(roxanne_rosjava_msgs.ActingGoal message)
                {
                    // get goal tokens
                    List<roxanne_rosjava_msgs.Token> goals = message.getGoals();
                    // get facts
                    List<roxanne_rosjava_msgs.Token> facts = message.getFacts();

                    // check message data
                    if (goals == null || goals.isEmpty()) {
                        // mandatory parameter missing
                        log.warn("I have received an invalid task request: \"" + message + "\"\n" +
                                "No goal has been specified");
                    }
                    else {
                        // received input goal
                        log.info("I have received a task to plan for: \"" + message + "\"\n");

                        // prepare a task description
                        AgentTaskDescription task = new AgentTaskDescription();

                        // check if facts exist
                        if (facts != null && !facts.isEmpty()) {
                            // set facts
                            for (roxanne_rosjava_msgs.Token tk : facts)
                            {
                                // get component
                                String component = tk.getComponent();
                                // get predicate
                                String predicate = tk.getPredicate();
                                // get parameters
                                String[] params = (((List<String>) tk.getParameters()) != null && ((List<String>) tk.getParameters()).size() > 0) ? ((List<String>) tk.getParameters()).toArray(new String[tk.getParameters().size()]) : null;
                                // get start
                                long[] start = (((long[]) tk.getStart()) != null && ((long[]) tk.getStart()).length > 0) ? (long[]) tk.getStart() : null;
                                // get end
                                long[] end = (((long[]) tk.getEnd()) != null && ((long[]) tk.getEnd()).length > 0) ? (long[]) tk.getEnd() : null;
                                // get duration
                                long[] duration = (((long[]) tk.getDuration()) != null && ((long[]) tk.getDuration()).length > 0) ? (long[]) tk.getDuration() : null;

                                log.info("Adding fact:\n" +
                                        "- id: " + tk.getId() + "\n" +
                                        "- component: " + component + "\n" +
                                        "- predicate: " + predicate + "\n" +
                                        "- params: " + Arrays.asList(params) + "\n" +
                                        "- start: [" + start[0] + "," + start[1] + "]\n" +
                                        "- end: [" + end[0] + "," + end[1] +"]\n" +
                                        "- duration: [" + duration[0] + "," + duration[1] + "]\n");

                                // add fact description from received request
                                task.addFactDescription(new TokenDescription(
                                        component,
                                        predicate,
                                        params,
                                        start,
                                        end,
                                        duration));
                            }
                        }

                        // set goals
                        for (roxanne_rosjava_msgs.Token tk : goals)
                        {
                            // get component
                            String component = tk.getComponent();
                            // get predicate
                            String predicate = tk.getPredicate();
                            // get parameters
                            String[] params = (((List<String>) tk.getParameters()) != null && ((List<String>) tk.getParameters()).size() > 0) ? ((List<String>) tk.getParameters()).toArray(new String[tk.getParameters().size()]) : null;
                            // get start
                            long[] start = (((long[]) tk.getStart()) != null && ((long[]) tk.getStart()).length > 0) ? (long[]) tk.getStart() : null;
                            // get end
                            long[] end = (((long[]) tk.getEnd()) != null && ((long[]) tk.getEnd()).length > 0) ? (long[]) tk.getEnd() : null;
                            // get duration
                            long[] duration = (((long[]) tk.getDuration()) != null && ((long[]) tk.getDuration()).length > 0) ? (long[]) tk.getDuration() : null;

                            log.info("Adding goal:\n" +
                                    "- id: " + tk.getId() + "\n" +
                                    "- component: " + component + "\n" +
                                    "- predicate: " + predicate + "\n" +
                                    "- params: " + Arrays.asList(params) + "\n" +
                                    "- start: [" + start[0] + "," + start[1] + "]\n" +
                                    "- end: [" + end[0] + "," + end[1] +"]\n" +
                                    "- duration: [" + duration[0] + "," + duration[1] + "]\n");

                            // add goal description from received request
                            task.addGoalDescription(new TokenDescription(
                                    component,
                                    predicate,
                                    params,
                                    start,
                                    end,
                                    duration));
                        }

                        try
                        {
                            // notify goal to the agent
                            agent.buffer(task);
                            // wait a response - blocking call
                            List<Goal> results = agent.getResults();
                            // print goal information
                            for (Goal result : results) {
                                // print some statistics
                                log.info("Completed task " + result + ":\n" +
                                        "\t- Planing: " + result.getPlanningAttempts() + " attempts for a total time of " + (result.getTotalContingencyHandlingTime() / 1000) + " seconds\n" +
                                        "\t- Execution: " + result.getExecutionAttempts() + " attempts for a total time of " + (result.getTotalExecutionTime() / 1000) + " seconds\n" +
                                        "\t- Contingency handling: " + result.getContingencyHandlingAttempts() + " attempts for a total tiem of " + (result.getTotalContingencyHandlingTime() / 1000) + " seconds");
                            }
                        } catch (InterruptedException ex) {
                            // error
                            log.error("Interrupted acting process:\n" + ex.getMessage() + "\n");
                        }
                    }
                }
            });
        }
        catch (Exception ex) {
            // error
            this.log.error("Error while starting acting agent\n" + ex.getMessage() + "\n");
        }
    }

    /**
     *
     * @param node
     */
    @Override
    public void onShutdownComplete(Node node) {
        // stop acting agent if necessary
        if (this.agent != null)
        {
            try
            {
                // stop acting agent
                this.agent.stop();
                // clear internal data structures
                this.agent.clear();
            }
            catch (Exception ex) {
                this.log.error("Error while stopping acting agent:\n" + ex.getMessage() + "\n");
            }
            finally {
                // clear variable
                this.agent = null;
            }
        }
    }
}
